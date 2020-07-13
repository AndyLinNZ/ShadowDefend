package Towers;

import Base.ShadowDefend;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * An airplane is a type of PassiveTower that flies across the map and drops explosives
 */
public class Airplane extends PassiveTower {
    public final static String IMAGE_FILE = "res/images/airsupport.png";
    public final static double SPEED = 3;

    /**
     * Determines if a horizontal or vertical airplane should be spawned.
     * The airplane will fly across to either the width or the height of the ShadowDefend window
     * @param point where the player placed the plane
     * @param horizontal if the airplane should be horizontal or not
     * @return an airplane
     */
    public static Tower createAirplane(Point point, boolean horizontal) {
        // If horizontal, the airplane will fly across the x axis, adjusting the player's point
        if (horizontal) {
            return new Airplane(new Point(0, point.y), new Point(ShadowDefend.getWidth(), point.y));
        }
        // If vertical, the airplane will fly across the y axis, adjusting the players point
        else {
            return new Airplane(new Point(point.x, 0), new Point(point.x, ShadowDefend.getHeight()));
        }
    }

    /**
     * Creates a new instance of an Airplane
     * @param point where the airplane begins at
     * @param target where the airplane will travel to
     */
    public Airplane(Point point, Point target) {
        super(point, target, IMAGE_FILE);
        // If horizontal, the airplane will face towards east
        if (point.x == 0) {
            setAngle(0.5 * Math.PI);
        }
        // If vertical, the airplane will face towards south
        else {
            setAngle(Math.PI);
        }
    }

    /**
     * Updates the location of the airplane and moves it towards its target point
     * @param input to update
     */
    @Override
    public void update(Input input) {
        Point currentPoint = this.getCenter();
        Point targetPoint = this.target;
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        double magnitude = distance.length();
        // If the airplane has reached the end, it can be removed from the game
        if (magnitude < SPEED * ShadowDefend.getTimescale()) {
            isFinished = true;
        }
        // Move the airplane and update its image
        super.move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        super.update(input);
    }
}
