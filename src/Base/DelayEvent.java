package Base;

import Base.ShadowDefend;
import Slicers.Slicer;
import bagel.util.Point;

import java.util.List;

/**
 * A type of event that just delays time
 */
public class DelayEvent extends Event {
    private final double delayRate;

    /**
     * Instantiates a new Delay event
     * @param delayRate the delay rate
     */
    public DelayEvent(double delayRate) {
        this.frameCount = 0;
        this.delayRate = delayRate / 1000;
    }

    /**
     * Delays time according to the timescale
     * @param slicers are spawned in the case of a spawn event
     * @param polyLine's first point is where the spawning slicers start
     */
    public void update(List<Slicer> slicers, List<Point> polyLine) {
        frameCount += ShadowDefend.getTimescale();
        if (frameCount / ShadowDefend.FPS >= delayRate) {
            // Finished delaying, so the event is finished
            setFinished(true);
        }
    }
}
