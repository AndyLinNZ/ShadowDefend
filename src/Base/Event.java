package Base;

import Slicers.Slicer;
import bagel.util.Point;
import java.util.List;

/**
 *  An event that can happen in a wave. Either delay the game or spawn slicers.
 */
public abstract class Event {
    protected boolean isFinished = false;
    protected int frameCount = 0;

    /**
     * @param slicers are spawned in the case of a spawn event
     * @param polyLine's first point is where the spawning slicers start
     */
    public abstract void update(List<Slicer> slicers, List<Point> polyLine);

    /**
     * @return if the event is finished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * @param finish to indicate if the event has finished
     */
    protected void setFinished(boolean finish) {
        isFinished  = finish;
    }
}
