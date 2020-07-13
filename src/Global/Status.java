package Global;

/**
 * The global access to the status of ShadowDefend
 */
public class Status {
    /**
     * The constant WINNER where we have won the game. Highest priority.
     */
    public final static String WINNER = "Winner!";
    /**
     * The constant PLACING where we are placing turrets. Second highest priority
     */
    public final static String PLACING = "Placing";
    /**
     * The constant PROGRESS. Takes place when there is a wave happening and hasen't been completed
     */
    public final static String PROGRESS = "Wave In Progress";
    /**
     * The constant AWAITING. Takes place when there is no wave being completed
     */
    public final static String AWAITING = "Awaiting Start";
    // Initialise the starting status to awaiting
    private static String currentStatus = Status.AWAITING;

    /**
     * Sets status of ShadowDefend
     * @param state the new state of ShadowDefend
     */
    public static void setStatus(String state) {
        if (state.equals(AWAITING) || state.equals(PLACING) || state.equals(WINNER) || state.equals(PROGRESS)) {
            currentStatus = state;
        }
    }

    /**
     * Gets current status
     * @return the current status
     */
    public static String getCurrentStatus() {
        return currentStatus;
    }
}

