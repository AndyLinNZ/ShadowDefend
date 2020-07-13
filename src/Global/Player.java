package Global;

/**
 * A single instance of a player that can play the game
 */
public class Player {

    private static Player player;
    // Default values for the player
    private final static int DEFAULT_MONEY = 500;
    private final static int DEFAULT_LIVES = 25;
    // Current inventory of the player
    private int money;
    private int lives;

    /**
     * The money the player has
     * @return the money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * Add money to the inventory of the player
     * @param newMoney is the amount of money given to the player
     */
    public void addMoney(int newMoney) {
        this.money += newMoney;
    }

    /**
     * The lives of the player
     * @return the lives of the player
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Decrease the lives the player has
     * @param newLives is the new life points the player has
     */
    public void minusLives(int newLives) {
        this.lives -= newLives;
    }

    /**
     * Creates only one instance of a player
     */
    private Player() {
        this.money = DEFAULT_MONEY;
        this.lives = DEFAULT_LIVES;
    }

    /**
     * Global access to the player. A singleton design
     * @return the player instance
     */
    public static Player getPlayer() {
        // Create a player if we haven't done so already
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    /**
     * Reset the inventory of the player
     */
    public void reset() {
        money = DEFAULT_MONEY;
        lives = DEFAULT_LIVES;
    }
}
