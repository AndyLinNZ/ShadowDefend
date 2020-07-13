package Towers;

import bagel.util.Point;

/**
 * A supertank is a type of ActiveTower in ShadowDefend
 */
public class SuperTank extends ActiveTower {
    // Units for this specific type of ActiveTower
    private final static double EFFECT_RADIUS = 150;
    private final static int DAMAGE = 3;
    private final static double COOLDOWN = 0.5;
    private final static String PROJECTILE_IMG = "res/images/supertank_projectile.png";
    public final static String TANK_IMG = "res/images/supertank.png";

    /**
     * Creates an instance of a SuperTank
     * @param point is the point where the SuperTank will be placed on the map
     */
    public SuperTank(Point point) {
        super(point, TANK_IMG, EFFECT_RADIUS, DAMAGE, COOLDOWN, PROJECTILE_IMG);
    }
}

