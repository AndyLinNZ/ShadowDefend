package Base;

import Global.Status;
import Slicers.Slicer;
import bagel.util.Point;
import java.util.*;

/**
 * The wave is the set of instructions for the events to occur in Shadowdefend
 */
public class Wave {
    //The events are the set of instructions, either to delay or to spawn slicers
    private final List<Event> events;
    private Event currentEvent;
    private final int waveNumber;
    // Represents if the wave is finished
    private boolean complete;


    /**
     * Creates a new instance of a wave
     * @param waveNumber the wave number of the wave
     */
    public Wave(int waveNumber) {
        this.events = new ArrayList<>();
        this.waveNumber = waveNumber;
        this.currentEvent = null;
    }

    /**
     * Adds an event to the wave
     * @param event the event to be added for this particular wave
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Update and process this wave through its events
     * @param polyline the polyline for which the slicers follow
     * @param slicers  the slicers in the level
     */
    public void update(List<Point> polyline, List<Slicer> slicers) {
        // Whenever we are not placing and we are processing a wave, the wave will be in progress
        if (!Status.getCurrentStatus().equals(Status.PLACING)) {
            Status.setStatus(Status.PROGRESS);
        }
        // No more events in this wave, all slicers have finished on the map, so the wave is complete
        if (events.size() == 0 && currentEvent.isFinished() && slicers.isEmpty()) {
            complete = true;
        }
        // Could be on the last event in the wave, but the slicers have not been eliminated yet
        else if (events.size() == 0 && currentEvent.isFinished()) {
            complete = false;
        }
        // Otherwise, process the current event of the wave
        else {
            Event event = getEvent();
            event.update(slicers, polyline);
        }
    }

    /**
     * We return the current event the wave is currently on
     * @return the current event of the wave
     */
    private Event getEvent() {
        // If there is no event or if the event has finished, grab the next event.
        if (currentEvent == null || currentEvent.isFinished()) {
            currentEvent = events.get(0);
            events.remove(0);
        }
        return currentEvent;
    }

    /**
     * Gets wave number
     * @return the wave number
     */
    public int getWaveNumber() {
        return waveNumber;
    }

    /**
     * Is complete boolean
     * @return if the wave is complete
     */
    public boolean isComplete() {
        return complete;
    }
}
