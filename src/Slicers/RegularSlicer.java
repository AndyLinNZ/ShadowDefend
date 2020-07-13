package Slicers;

import bagel.util.Point;
import java.util.List;

/**
 * A type of Slicer, called RegularSlicer
 */
public class RegularSlicer extends Slicer {
    // Units for a RegularSlicer
    private final static String IMAGE_FILE = "res/images/slicer.png";
    private final static int REWARD = 2;
    private final static double SPEED = 2;
    private final static int PENALTY = 1;
    private final static int HEALTH = 1;

    /**
     * Creates a new instance of a RegularSlicer at the start of the polyline
     * @param polyline  the polyline in which the slicer traverses through
     */
    public RegularSlicer(List<Point> polyline) {
        super(polyline, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * Creates a new instance of a RegularSlicer at a certain position
     * @param polyLine         the polyline in which the slicer traverses through
     * @param point            the point in which the RegularSlicer is spawned at
     * @param targetPointIndex the index of the next point in the polyline
     */
    public RegularSlicer(List<Point> polyLine, Point point, int targetPointIndex) {
        super(polyLine, point, targetPointIndex, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * A regular slicer will not spawn any child slicers
     */
    @Override
    public void createChild(List<Slicer> slicers, int targetPointIndex) {
        ;
    }
}
