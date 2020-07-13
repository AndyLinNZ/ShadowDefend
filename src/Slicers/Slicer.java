package Slicers;

import Base.Sprite;
import Base.ShadowDefend;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

/**
 * A slicer is the main enemy of ShadowDefend
 */
public abstract class Slicer extends Sprite {
    // Units for a Slicer
    protected final List<Point> polyline;
    protected int targetPointIndex;
    protected boolean finished;
    protected boolean eliminated;
    protected int reward;
    protected double speed;
    protected int penalty;
    protected int health;

    /**
     * Creates a new Slicer. Slicers begin at the beginning of the polyline
     *
     * @param polyline  The polyline that the slicer must traverse (must have at least 1 point)
     * @param imageFile The image for the Slicer entity
     * @param reward    The money the slicer gives to the Player if eliminated by a tower
     * @param speed     The speed in which the Slicer traverses the polyline
     * @param penalty   The penalty to the player if the Slicer is not eliminated
     * @param health    The health of the Slicer
     */
    public Slicer(List<Point> polyline, String imageFile, int reward, double speed, int penalty, int health) {
        super(polyline.get(0), imageFile);
        this.polyline = polyline;
        this.targetPointIndex = 1;
        this.eliminated = false;
        this.finished = false;
        this.reward = reward;
        this.speed = speed;
        this.health = health;
        this.penalty = penalty;
    }

    /**
     * Creates a Slicer at a given position, it doesn't have to spawn in the first position of the polyline
     *
     * @param polyline         The polyline that the slicer must traverse (must have at least 1 point)
     * @param point            The point of where the Slicer should be spawned
     * @param targetPointIndex The next target in the polyline the Slicer will traverse to
     * @param imageFile        The image for the Slicer entity
     * @param reward           The money the slicer gives to the Player if eliminated by a tower
     * @param speed            The speed in which the Slicer traverses the polyline
     * @param penalty          The penalty to the player if the Slicer is not eliminated
     * @param health           The health of the Slicer
     */
    public Slicer(List<Point> polyline, Point point, int targetPointIndex, String imageFile, int reward,
                  double speed, int penalty, int health) {
        super(point, imageFile);
        this.polyline = polyline;
        this.targetPointIndex = targetPointIndex;
        this.eliminated = false;
        this.finished = false;
        this.reward = reward;
        this.speed = speed;
        this.health = health;
        this.penalty = penalty;
    }


    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     */
    @Override
    public void update(Input input) {
        // If the slicer is below 0 health, it is eliminated
        if (health <= 0) {
            finished = true;
            eliminated = true;
        }
        // If the slicer is finished, it may safely be removed from the game
        if (finished) {
            return;
        }
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < speed * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Make our focus the next point in the polyline
                targetPointIndex += 1;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(speed * ShadowDefend.getTimescale()));
        // Update current rotation angle to face target point
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update(input);
    }

    /**
     * If the slicer can be removed form the game.
     * @return the boolean
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * If the slicer is eliminated by a turret
     * @return the boolean
     */
    public boolean isEliminated() {
        return eliminated;
    }

    /**
     * Gets poly line.
     * @return the poly line
     */
    public List<Point> getPolyLine() {
        return this.polyline;
    }

    /**
     * Gets target point index.
     * @return the target point index
     */
    public int getTargetPointIndex() {
        return this.targetPointIndex;
    }

    /**
     * Sets health.
     * @param damage the damage
     */
    public void setHealth(double damage) {
        this.health -=damage;
    }

    /**
     * Gets reward.
     * @return the reward
     */
    public int getReward() {
        return reward;
    }

    /**
     * Gets penalty.
     * @return the penalty
     */
    public int getPenalty() { return penalty; }

    /**
     * Creates child Slicers if eliminated by a tower
     *
     * @param slicers          are the list of slicers in which we spawn the slicers into
     * @param targetPointIndex the next target for the children slicers to reach
     */
    public abstract void createChild(List<Slicer> slicers, int targetPointIndex);
}
