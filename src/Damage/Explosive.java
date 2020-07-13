package Damage;

import Base.ShadowDefend;
import Base.Sprite;
import Slicers.Slicer;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.List;

public class Explosive extends Sprite implements Damageable {
    // Units for an explosive
    private final static String IMAGE_FILE = "res/images/explosive.png";
    private final static int DAMAGE = 500;
    private final static int RADIUS = 200;
    private final static double COOLDOWN = 2;
    private final Rectangle rect;
    private boolean isFinished;
    private double frameCount;
    private final List<Slicer> slicers;

    /**
     * Creates an instance of a explosive
     * @param point is where the explosive is placed on the map
     * @param slicers is the list of slicers that can potentially be damaged by the explosive
     */
    public Explosive(Point point, List<Slicer> slicers) {
        super(point, IMAGE_FILE);
        this.isFinished = false;
        this.slicers = slicers;
        // The area around the explosive which is the detonation radius
        this.rect = new Rectangle(this.getCenter().x-RADIUS, this.getCenter().y - RADIUS,
                RADIUS*2, RADIUS*2);
    }

    /**
     * Update the position of the damageable object
     */
    @Override
    public void update(Input input) {
        frameCount += ShadowDefend.getTimescale();
        // When it can detonate, it will deal damage to all slicers in its surrounding
        if (frameCount / ShadowDefend.FPS >= COOLDOWN) {
            for (Slicer slicer : slicers) {
                // If the slicer is within the explosive detonation area, damage the slicers
                if (rect.intersects(slicer.getCenter())) {
                    slicer.setHealth(DAMAGE);
                }
            }
            // Finished detonating, can be removed from the map
            isFinished = true;
        }
        super.update(input);
    }

    /**
     * Indicates when the explosive can be removed from the map
     * @return if the explosive has detonated and can be removed from the map
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
