package Slicers;

import bagel.util.Point;
import java.util.List;

/**
 * The type of Slicer, called SuperSlicer
 */
public class SuperSlicer extends Slicer {
    // Units for a SuperSlicer
    private final static String IMAGE_FILE = "res/images/superslicer.png";
    private final static double SPEED = 1.5;
    private final static int HEALTH = 1;
    private final static int REWARD = 15;
    private final static int PENALTY = 2;

    /**
     * Creates a new instance of a SuperSlicer at the start of the polyline
     * @param polyLine the polyline in which the slicer traverses through
     */
    public SuperSlicer(List<Point> polyLine) {
        super(polyLine, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * Creates a new instance of a SuperSlicer at a certain position
     * @param polyLine         the polyline in which the slicer traverses through
     * @param point            the point in which the SuperSlicer is spawned at
     * @param targetPointIndex the index of the next point in the polyline
     */
    public SuperSlicer(List<Point> polyLine, Point point, int targetPointIndex) {
        super(polyLine, point, targetPointIndex, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * Creates children if eliminated
     * @param slicers          are the list of slicers in which we spawn the slicers into
     * @param targetPointIndex the next target for the children slicers to reach
     */
    @Override
    public void createChild(List<Slicer> slicers, int targetPointIndex) {
        eliminated = false;
        slicers.add(new RegularSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
        slicers.add(new RegularSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
    }
}
