package Base;

import Global.*;
import Damage.Damageable;
import Slicers.Slicer;
import Towers.Tower;
import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Level to represent a game state where a player plays on a map.
 */
public class Level {

    // The set of instructions for this level on this map
    private final List<Wave> waves;
    private final TiledMap map;
    // Each level has its player's game state of Towers brought and slicers spawned
    private final List<Point> polyline;
    private final List<Slicer> slicers;
    private final List<Tower> towers;
    private final List<Damageable> damageUnits;
    // Panels for the Player to purchase, and view the state of the game
    private final BuyPanel buyPanel;
    private final StatusPanel statusPanel;
    // Variables to represent the state of the level
    private boolean complete;
    private boolean canStart;
    private int waveNumber;

    /**
     * Creates a new instance of a level for ShadowDefend
     * @param mapFile     the map file of the map to play the level on
     * @param waveFile    the wave file to indicate the events and wave of the level
     * @param buyPanel    the buy panel to buy Towers
     * @param statusPanel the status panel to show the status of the level
     */
    public Level(String mapFile, String waveFile, BuyPanel buyPanel, StatusPanel statusPanel) {
        this.map = new TiledMap(mapFile);
        this.polyline = map.getAllPolylines().get(0);
        this.slicers = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.waves = new ArrayList<>();
        this.buyPanel = buyPanel;
        this.statusPanel = statusPanel;
        this.damageUnits = new ArrayList<>();
        this.complete = false;
        this.waveNumber = 1;
        // Process the wave file into waves and events
        processWaveFile(waveFile);
    }

    /**
     * From the waveFile, convert the text into respective Waves, and each event into their
     * respective wave events
     * @param waveFile is the location of the file that indicates the Wave events
     */
    private void processWaveFile(String waveFile) {
        // A list of strings from the text file
        List<String> allEventsInString = new ArrayList<>();
        // Add each line in the wave file to allEventsInString
        try (Scanner scanner = new Scanner(new FileReader(waveFile))) {
            while (scanner.hasNextLine()) {
                allEventsInString.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int previousWaveNumber = 0;
        int waveNumber;
        Event event;
        // Loop through each line and create an event from that line
        for (String stringEvent : allEventsInString) {
            List<String> eventDetails = Arrays.asList(stringEvent.split(","));
            // This type of event is a spawn event, convert the strings to make it a SpawnEvent
            if (eventDetails.get(1).equals("spawn")) {
                int spawnNumber = Integer.parseInt(eventDetails.get(2));
                double spawnRate = Double.parseDouble(eventDetails.get(4));
                String slicerType = eventDetails.get(3);
                event = new SpawnEvent(spawnNumber, slicerType, spawnRate);
            }
            // This type of event is a delay event, convert the strings to make it a DelayEvent
            else {
                double delayRate = Double.parseDouble(eventDetails.get(2));
                event = new DelayEvent(delayRate);
            }
            // waveNumber is the number that is given first in each line
            waveNumber = Integer.parseInt(eventDetails.get(0));
            // If we find a new number, create a new wave, and update the previous wave number
            if (waveNumber != previousWaveNumber) {
                waves.add(new Wave(waveNumber));
                previousWaveNumber = waveNumber;
            }
            // Add our event to the corresponding wave
            waves.get(waveNumber - 1).addEvent(event);
        }
    }

    /**
     * Update the state of the game, potentially reading from input
     * @param input The current mouse/keyboard state
     */
    public void update(Input input) {
        // Draw map from the top left of the window
        map.draw(0, 0, 0, 0, ShadowDefend.getWidth(), ShadowDefend.getHeight());

        // If the game has not been won or the player is not placing, and the wave hasen't started
        // Change the status to AWAITING for wave
        if (!canStart && !Status.getCurrentStatus().equals(Status.PLACING) &&
                !Status.getCurrentStatus().equals(Status.WINNER)) {
            Status.setStatus(Status.AWAITING);
        }

        // Handle key presses
        if (input.wasPressed(Keys.S)) {
            canStart = true;
        }
        if (input.wasPressed(Keys.L)) {
            ShadowDefend.increaseTimescale();
        }
        if (input.wasPressed(Keys.K)) {
            ShadowDefend.decreaseTimescale();
        }

        // If there are no more waves in this level, the level is complete
        if (waves.isEmpty() && slicers.isEmpty()) {
            complete = true;
            canStart = false;
        }

        if (canStart) {
            Wave wave = waves.get(0);
            wave.update(polyline, slicers);
            waveNumber = wave.getWaveNumber();
            // If the wave is finished we update to the next wave
            if (wave.isComplete()) {
                Player.getPlayer().addMoney(100 + wave.getWaveNumber() * 150);
                waves.remove(0);
                damageUnits.clear();
                canStart = false;
            }
        }

        // Update all towers, and remove them if they've finished
        for (int i = towers.size() - 1; i >=0; i--) {
            Tower tower = towers.get(i);
            tower.update(input);
            tower.attack(input, slicers, damageUnits);
            if (tower.isFinished()) {
                towers.remove(i);
            }
        }

        // Update all damageUnits such as Projectiles and Explosives and remove them if they've finished
        for (int i = damageUnits.size() - 1; i >= 0; i--) {
            Damageable damageUnit = damageUnits.get(i);
            damageUnit.update(input);
            if (damageUnit.isFinished()) {
                damageUnits.remove(i);
            }
        }

        // Update all slicers, and remove them if they've finished
        for (int i = slicers.size() - 1; i >= 0; i--) {
            Slicer s = slicers.get(i);
            s.update(input);
            // Slicer will be removed from the game if finished
            if (s.isFinished()) {
                // If eliminated by a tower, update player money and spawn children of the slicer
                if (s.isEliminated()) {
                    Player.getPlayer().addMoney(s.getReward());
                    s.createChild(slicers, s.getTargetPointIndex());
                }
                // If slicer reached the end of the polyline, player will lose health
                else {
                    Player.getPlayer().minusLives(s.getPenalty());
                }
                slicers.remove(i);
            }
        }
        // Render the panels, and update player shop
        buyPanel.render();
        buyPanel.update(input);
        buyPanel.place(input, towers, map, buyPanel, statusPanel);
        statusPanel.render();
        statusPanel.update(waveNumber);
    }

    /**
     * Is complete boolean shows if the level is completed
     * @return if the level is complete
     */
    public boolean isComplete() {
        return complete;
    }
}


