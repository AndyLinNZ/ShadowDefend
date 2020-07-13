package Towers;

import Base.Sprite;
import Slicers.Slicer;
import Damage.Damageable;
import bagel.Input;
import bagel.util.Point;
import java.util.List;

/**
 * Towers are used to defend against Slicers in ShadowDefend
 */
public abstract class Tower extends Sprite {

    protected double frameCount;
    protected double cooldown;
    protected boolean isFinished;

    /**
     * Creates a new instance of a PassiveTower
     * @param point where the PassiveTower starts
     * @param imageFile the image for the Tower entity
     * @param cooldown the cooldown of dropping a damageable object
     */
    public Tower(Point point, String imageFile, double cooldown) {
        super(point, imageFile);
        this.frameCount = 0;
        this.cooldown = cooldown;
        this.isFinished = false;
    }

    /**
     * Creates a new instance of an AbstractTower
     * @param point where the ActiveTower is placed
     * @param imageFile the image for the Tower entity
     */
    public Tower(Point point, String imageFile) {
        super(point, imageFile);
        this.frameCount = 0;
        this.cooldown = 0;
        this.isFinished = false;
    }

    /**
     * Each Tower updates and spawns its damage units
     * @param input input from the user
     * @param slicers list of slicers the Tower can potentially attack
     * @param damageUnits list of damaging objects the towers can spawn damaging objects into
     */
    public void attack(Input input, List<Slicer> slicers, List<Damageable> damageUnits) {
        super.update(input);
    }

    /**
     * Indicates if a tower can be removed from the map
     * @return if tower is finished
     */
    public boolean isFinished() {
        return isFinished;
    }
}
