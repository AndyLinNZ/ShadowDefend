package Towers;

import Slicers.Slicer;
import Damage.*;
import Base.ShadowDefend;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.List;

/**
 * An ActiveTower is a type of Tower that deals damage to enemies in its radius
 */
public abstract class ActiveTower extends Tower {
    private final int damage;
    private final String projectileImg;
    private final Rectangle rect;

    /**
     * Creates a new instance of an ActiveTower
     * @param point where the ActiveTower is placed on the map
     * @param tankImg the image for the Tower entity
     * @param effectRadius the area around the Tower where it can shoot projectiles if slicers are nearby
     * @param damage the damage of the projectiles from the ActiveTower
     * @param cooldown the cooldown period before the tower can shoot its next Projectile
     * @param projectileImg the image for the Projectile entity
     */
    public ActiveTower(Point point, String tankImg, double effectRadius, int damage, double cooldown,
                       String projectileImg) {
        super(point, tankImg, cooldown);
        this.projectileImg = projectileImg;
        this.damage = damage;
        // The rectangular area around the center of the Tower
        this.rect = new Rectangle(this.getCenter().x-effectRadius, this.getCenter().y - effectRadius,
                effectRadius*2, effectRadius*2);
    }

    /**
     * Scan slicers in its effect radius to spawn Projectiles when the first slicer enters its radius
     * @param input input from the user
     * @param slicers list of slicers the Tower can potentially attack
     * @param damageUnits list of damageable objects the towers can spawn projectiles into
     */
    @Override
    public void attack(Input input, List<Slicer> slicers, List<Damageable> damageUnits) {
        frameCount += ShadowDefend.getTimescale();
        // Tower can shoot a projectile
        if (frameCount / ShadowDefend.FPS >= cooldown) {
            // Find all slicers within the ActiveTower's effect radius
            for (Slicer slicer : slicers) {
                boolean enemyNearby = rect.intersects(slicer.getCenter());
                // There is a slicer the ActiveTower can shoot a projectile onto
                if (enemyNearby) {
                    Point currentPoint = getCenter();
                    Point targetPoint = slicer.getCenter();
                    // Make the tower face the slicer
                    setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x) + 0.5*Math.PI);
                    // Spawn a projectile to shoot at the slicer
                    damageUnits.add(new Projectile(this.getCenter(), projectileImg, damage, slicer));
                    // Reset the cooldown
                    frameCount = 0;
                    // Only shoot 1 slicer at a time, so return once we found the first slicer
                    return;
                }
            }
        }
    }
}
