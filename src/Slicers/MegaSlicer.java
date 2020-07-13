package Slicers;

import bagel.util.Point;
import java.util.List;

/**
 * A type of Slicer, called MegaSlicer
 */
public class MegaSlicer extends Slicer {
    // Units for a MegaSlicer
    private final static String IMAGE_FILE = "res/images/megaslicer.png";
    private final static double SPEED = 1.5;
    private final static int HEALTH = 2;
    private final static int REWARD = 10;
    private final static int PENALTY = 4;

    /**
     * Creates a new instance of a MegaSlicer at the start of the polyline
     * @param polyLine the polyline in which the slicer traverses through
     */
    public MegaSlicer(List<Point> polyLine) {
        super(polyLine, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * Creates a new instance of a MegaSlicer at a certain position
     * @param polyLine         the polyline in which the slicer traverses through
     * @param point            the point in which the MegaSlicer is spawned at
     * @param targetPointIndex the index of the next point in the polyline
     */
    public MegaSlicer(List<Point> polyLine, Point point, int targetPointIndex) {
        super(polyLine, point, targetPointIndex, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * Creates children if eliminated
     * @param slicers          are the list of slicers in which we spawn the slicers into
     * @param targetPointIndex the next target for the children slicers to reach
     */
    @Override
    public void createChild(List<Slicer> slicers, int targetPointIndex) {
        this.eliminated = false;
        slicers.add(new SuperSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
        slicers.add(new SuperSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
    }
}
