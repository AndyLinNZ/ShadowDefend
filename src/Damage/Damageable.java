package Damage;

import bagel.Input;

/**
 * A damageable is an object that can damage a slicer
 */
public interface Damageable {
    /**
     * Update the position of the damageable object
     */
    void update(Input input);

    /**
     * Indicates when the object can be removed from the map
     * @return if the damageable object is finished and can be removed from the map
     */
    boolean isFinished();
}
