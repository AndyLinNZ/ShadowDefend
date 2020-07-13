package Base;

import Towers.*;
import Global.Player;

import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * TowerIcons are icons that are in the buyPanel that represent the towers a player can buy
 */
public class TowerIcon {
    private final Image image;
    private final int price;
    private final static int TANK_PRICE = 250;
    private final static int SUPERTANK_PRICE = 600;
    private final static int AIRPLANE_PRICE = 500;
    private final static int AIRPLANE_COUNT_DEFAULT = -1;
    private final Point iconPoint;
    private final Point textPoint;
    private final Rectangle rect;
    private final TowerType type;
    private int airplaneCount;

    /**
     * Instantiates a new Tower icon.
     * @param tower the type of Tower we want
     */
    public TowerIcon(TowerType tower) {
        // TowerIcon representing the Tank
        if (tower == TowerType.TANK) {
            this.image = new Image(Tank.TANK_IMG);
            this.price = TANK_PRICE;
            this.textPoint = new Point(30, 90);
            this.iconPoint = new Point(64, 40);
        }
        // TowerIcon representing the SuperTank
        else if (tower == TowerType.SUPERTANK) {
            this.image = new Image(SuperTank.TANK_IMG);
            this.price = SUPERTANK_PRICE;
            this.textPoint = new Point(150, 90);
            this.iconPoint = new Point(184, 40);
        }
        // TowerIcon for the airplane
        else {
            this.image = new Image(Airplane.IMAGE_FILE);
            this.price = AIRPLANE_PRICE;
            this.textPoint = new Point(270, 90);
            this.iconPoint = new Point(304, 40);
        }
        // All TowerIcons have a bounding box
        this.rect = image.getBoundingBoxAt(iconPoint);
        this.type = tower;
        // To keep track of whether the airplane should be horizontal or vertical
        airplaneCount = AIRPLANE_COUNT_DEFAULT;
    }

    /**
     * Render the image of the TowerIcon
     * @param point the point or position to be rendered at
     */
    public void draw(Point point) {
        this.image.draw(point.x, point.y);
    }

    /**
     * Gets the position of the TowerIcon
     * @return the icon point
     */
    public Point getIconPoint() {
        return this.iconPoint;
    }

    /**
     * Gets the type of Tower
     * @return the type of the TowerIcon we have
     */
    public TowerType getType() {
        return type;
    }

    /**
     * Visualise the money of the TowerIcon in either green or red
     * @param player the player to check if the player has enough money
     * @param font   the font to show the money in
     */
    public void showPrice(Player player, Font font) {
        // If the player has enough money to purchase this Tower, visualise the text to Green
        if (player.getMoney() >= this.price) {
            font.drawString("$" + this.price, this.textPoint.x, this.textPoint.y,
                    new DrawOptions().setBlendColour(Colour.GREEN));
        }
        // If the player does not have enough money to purchase this Tower, visualise the text to red
        else {
            font.drawString("$" + this.price, this.textPoint.x, this.textPoint.y,
                    new DrawOptions().setBlendColour(Colour.RED));
        }
    }

    /**
     * Checks if there is enough money to purchase this type of Tower from the TowerIcon
     * and if the user's mouse left mouse click is on the TowerIcon
     * @param input the left mouse click
     * @param money the money the player has
     * @return the if the Tower is purchaseable
     */
    public boolean purchaseable(Input input, int money) {
        return rect.intersects(input.getMousePosition()) && money >= this.price;
    }

    /**
     * Reset airplane price for when a new level starts
     */
    public void resetAirplanePrice() {
        airplaneCount = AIRPLANE_COUNT_DEFAULT;
    }

    /**
     * Purchase a Tower
     * @param point the point where the Tower will be placed
     * @return the tower that is purchased by the player
     */
    public Tower purchase(Point point) {
        Player player = Player.getPlayer();
        player.addMoney(-price);
        // Buy a tank
        if (price == TANK_PRICE) {
            return new Tank(point);
        }
        // Buy a SuperTank
        else if (price == SUPERTANK_PRICE) {
            return new SuperTank(point);
        }
        // Buy an airplane
        else if (price == AIRPLANE_PRICE) {
            airplaneCount += 1;
            // First airplane purchase will be horizontal, and every second purchase will also be horizontal
            if (airplaneCount % 2 == 0) {
                return Airplane.createAirplane(point, true);
            }
            // Second airplane purchase will be vertical, and every consecutive second purchase is also vertical
            else {
                return Airplane.createAirplane(point, false);
            }
        }
        // Player did not purchase
        return null;
    }
}
