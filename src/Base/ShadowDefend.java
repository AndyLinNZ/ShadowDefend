package Base;

import Global.*;
import bagel.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ShadowDefend, a tower defence game.
 */
public class ShadowDefend extends AbstractGame {

    // Default static final values for ShadowDefend
    private static final String FONT = "res/fonts/DejaVuSans-Bold.ttf";
    private static final String WAVE_FILE = "res/levels/waves.txt";
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    // Panels for ShadowDefend
    private final BuyPanel buyPanel;
    private final StatusPanel statusPanel;
    // Levels - can be adjustable for more levels
    private final List<Level> levels;
    private int levelNumber;
    // Change to suit system specifications.
    public static final double FPS = 60;
    private static final int INTIAL_TIMESCALE = 1;
    // Timescale is made static because it is a universal property of the game and the specification
    // says everything in the game is affected by this
    private static int timescale = INTIAL_TIMESCALE;

    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "Base.ShadowDefend");
        this.levels = new ArrayList<>();
        this.buyPanel = new BuyPanel(FONT);
        this.statusPanel = new StatusPanel(FONT);
        this.levelNumber = 0;

        // Can add more levels
        levels.add(new Level("res/levels/1.tmx", WAVE_FILE, buyPanel, statusPanel));
        levels.add(new Level("res/levels/2.tmx", WAVE_FILE, buyPanel, statusPanel));
    }

    /**
     * The entry-point for the game
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }

    /**
     * Gets timescale.
     * @return the timescale
     */
    public static int getTimescale() {
        return timescale;
    }

    /**
     * Increases the timescale
     */
    public static void increaseTimescale() {
        timescale++;
    }

    /**
     * Decreases the timescale but doesn't go below the base timescale
     */
    public static void decreaseTimescale() {
        if (timescale > INTIAL_TIMESCALE) {
            timescale--;
        }
    }

    /**
     * Update the state of the game, potentially reading from input
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // If all levels are complete, show the winner status
        if (Status.getCurrentStatus().equals(Status.WINNER) || levels.size() == levelNumber) {
            // Show the state of the game in the most current level the player finished
            Level level = levels.get(levelNumber-1);
            level.update(input);
            return;
        }

        // Check if the player is alive
        if (Player.getPlayer().getLives() <= 0) {
            Window.close();;
        }

        // Play the current level and update it
        Level level = levels.get(levelNumber);
        level.update(input);

        // Check if the level is complete
        if (level.isComplete()) {
            // Next level
            levelNumber += 1;
            // All levels have been completed
            if (levelNumber == levels.size()) {
                Status.setStatus(Status.WINNER);
                return;
            }
            // Reset the game state
            buyPanel.resetIcons();
            Player.getPlayer().reset();
            Status.setStatus(Status.AWAITING);
        }
    }

    /**
     * Gets width of the Game window
     * @return the width
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Gets height of the Game window
     * @return the height
     */
    public static int getHeight() {
        return HEIGHT;
    }
}
