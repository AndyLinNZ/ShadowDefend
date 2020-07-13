package Towers;

import bagel.util.Point;

/**
 * A tank is a basic type of ActiveTower in ShadowDefend
 */
public class Tank extends ActiveTower {
    // Units for this specific type of ActiveTower
    private final static double EFFECT_RADIUS = 100;
    private final static int DAMAGE = 1;
    private final static int COOLDOWN = 1;
    private final static String PROJECTILE_IMG = "res/images/tank_projectile.png";
    public final static String TANK_IMG = "res/images/tank.png";

    /**
     * Creates an instance of a Tank
     * @param point is the point where the Tank will be placed on the map
     */
    public Tank(Point point) {
        super(point, TANK_IMG, EFFECT_RADIUS, DAMAGE, COOLDOWN, PROJECTILE_IMG);
    }
}
