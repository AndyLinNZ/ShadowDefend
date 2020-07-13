package Towers;

import Slicers.Slicer;
import Damage.Damageable;
import Damage.Explosive;
import Base.ShadowDefend;
import bagel.Input;
import bagel.util.Point;
import java.util.List;
import java.util.Random;

/**
 * A PassiveTower is a type of Tower that drops explosives
 */
public abstract class PassiveTower extends Tower {

    protected Point point;
    protected Point target;

    /**
     * Creates a new instance of a PassiveTower
     * @param point where the PassiveTower starts
     * @param target is the point where the PassiveTower will travel to
     * @param imageFile the image for the Tower entity
     */
    public PassiveTower(Point point, Point target, String imageFile) {
        super(point, imageFile);
        this.target = target;
        this.isFinished = false;
    }

    /**
     * Spawn Explosives when its not on cooldown
     * @param input input from the user
     * @param slicers list of slicers the Tower can potentially attack
     * @param damageUnits list of damageable objects the PassiveTower can spawn explosives into
     */
    @Override
    public void attack(Input input, List<Slicer> slicers, List<Damageable> damageUnits) {
        frameCount += ShadowDefend.getTimescale();
        // Generate a random double between 1 and 2 seconds
        Random random = new Random();
        double cooldown = 1 + random.nextDouble();
        // Whenever the cooldown is on hold, spawn an explosive
        if (frameCount / ShadowDefend.FPS >= cooldown) {
            damageUnits.add(new Explosive(this.getCenter(), slicers));
            // Put it back into cooldown
            frameCount = 0;
        }
    }
}
