package Slicers;

import bagel.util.Point;
import java.util.List;

/**
 * A type of Slicer, called ApexSlicer
 */
public class ApexSlicer extends Slicer {
    // Units for an ApexSlicer
    private final static String IMAGE_FILE = "res/images/apexslicer.png";
    private final static double SPEED = 0.75;
    private final static int HEALTH = 100;
    private final static int REWARD = 150;
    private final static int PENALTY = 16;

    /**
     * Creates a new instance of an ApexSlicer
     *
     * @param polyLine the polyline in which the slicer traverses through
     */
    public ApexSlicer(List<Point> polyLine) {
        super(polyLine, IMAGE_FILE, REWARD, SPEED, PENALTY, HEALTH);
    }

    /**
     * Creates children if eliminated
     * @param slicers          are the list of slicers in which we spawn the slicers into
     * @param targetPointIndex the next target for the children slicers to reach
     */
    @Override
    public void createChild(List<Slicer> slicers, int targetPointIndex) {
        slicers.add(new MegaSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
        slicers.add(new MegaSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
        slicers.add(new MegaSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
        slicers.add(new MegaSlicer(this.getPolyLine(), this.getCenter(), targetPointIndex));
    }
}
