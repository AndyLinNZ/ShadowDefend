package Base;

import Slicers.*;
import bagel.util.Point;
import java.util.List;

/**
 * A line in a wave that indicates how many slicers to spawn, which type, and spawn rate
 */
public class SpawnEvent extends Event {
    // The total number of slicers we can spawn
    private final int spawnNumber;
    // Type of slicer to spawn
    private final String slicerType;
    // How fast we can spawn slicers relative to the TimeScale
    private final double spawnRate;
    // The current amount of slicers we have spawned
    private int spawnedSlicers;

    /**
     * Create a new instance of an event that spawns slicers
     * @param spawnNumber the number of slicers to spawn
     * @param slicerType  the slicer type to spawn
     * @param spawnRate   the spawn rate of spawning consecutive slicers
     */
    public SpawnEvent(int spawnNumber, String slicerType, double spawnRate) {
        this.spawnNumber = spawnNumber;
        this.spawnedSlicers = 0;
        this.slicerType = slicerType;
        this.spawnRate = spawnRate/1000;
    }

    /**
     * Spawn slicers into the Level
     * @param slicers are spawned in the case of a spawn event
     * @param polyLine's first point is where the spawning slicers start
     */
    @Override
    public void update(List<Slicer> slicers, List<Point> polyLine) {
        frameCount += ShadowDefend.getTimescale();
        // If the rate is up, we can spawn slicers
        if (frameCount / ShadowDefend.FPS >= spawnRate && spawnedSlicers != spawnNumber) {
            // Depending on the slicer type, spawn a slicer
            switch (slicerType) {
                case "slicer":
                    slicers.add(new RegularSlicer(polyLine));
                    break;
                case "superslicer":
                    slicers.add(new SuperSlicer(polyLine));
                    break;
                case "megaslicer":
                    slicers.add(new MegaSlicer(polyLine));
                    break;
                case "apexslicer":
                    slicers.add(new ApexSlicer(polyLine));
                    break;
            }
            // Reset the rate
            frameCount = 0;
            spawnedSlicers += 1;
        }
        // We have spawned all the slicers we needed to
        if (spawnedSlicers == spawnNumber) {
            // The event is finished
            setFinished(true);
        }
    }
}
