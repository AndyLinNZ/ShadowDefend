package Damage;

import Base.ShadowDefend;
import Base.Sprite;
import Slicers.Slicer;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * A projectile is a damageable object that is shot from a Tower towards a Slicer
 */
public class Projectile extends Sprite implements Damageable {
    // Default speed of 10
    private final static double SPEED = 10;
    private final int damage;
    private boolean isFinished;
    private final Slicer target;

    /**
     * Creates an instance of a Projectile
     * @param point is where the projectile is initially spawned
     * @param imageFile the image for the Projectile entity
     * @param damage the damage the projectile deals
     * @param slicer the first slicer the projectile sees and will move towards
     */
    public Projectile(Point point, String imageFile, int damage, Slicer slicer) {
        super(point, imageFile);
        this.damage = damage;
        this.isFinished = false;
        this.target = slicer;
    }

    /**
     * Updates the location of the projectile and moves towards its target slicer
     */
    @Override
    public void update(Input input) {
        // Obtain where we currently are, and which slicer we want to move towards
        Slicer slicer = target;
        Point currentPoint = getCenter();
        Point targetPoint = slicer.getCenter();
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < SPEED * ShadowDefend.getTimescale()) {
            // Check if we have reached the slicer
            if (slicer.getRect().intersects(this.getCenter())) {
                slicer.setHealth(damage);
                // Projectile has been shot and can be removed
                isFinished = true;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the projectile (accounting for the timescale)
        super.move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        super.update(input);
    }

    /**
     * If the projectile has finished reaching its target slicer
     * @return if the projectile is finished
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
