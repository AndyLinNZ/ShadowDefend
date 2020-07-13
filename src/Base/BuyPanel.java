package Base;

import Towers.Tower;
import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.MouseButtons;
import bagel.Input;
import java.util.ArrayList;
import java.util.List;
import Global.*;

/**
 * A type of panel that the player can purchase towers. Also shows the key presses and player's income.
 * Manages the purchase and placement of a Tower.
 */
public class BuyPanel {

    private final static String PANEL_IMG = "res/images/buypanel.png";
    // List of Towers that are purchaseable
    private final List<TowerIcon> icons;
    // Font to display descriptions of Towers, through font style and size
    private final Font keyFont;
    private final Font moneyFont;
    private final Font shopFont;
    // The current Tower the player is interested in
    private TowerIcon current;
    private final Point point;
    private final Image image;
    // Previous state to temporarily update the state, and then switch back to previousState
    private final String previousStatus;

    /**
     * Instantiates a new Buy panel.
     *
     * @param fontString the font string
     */
    public BuyPanel(String fontString) {
        // Different fonts for the money, key press descriptions, and income of the player
        this.keyFont = new Font(fontString, 15);
        this.moneyFont = new Font(fontString, 50);
        this.shopFont = new Font(fontString, 25);
        // The shop contains 3 types of Towers that are purchaseable, Tank, SuperTank and Airplane
        this.icons = new ArrayList<>();
        this.icons.add(new TowerIcon(TowerType.TANK));
        this.icons.add(new TowerIcon(TowerType.SUPERTANK));
        this.icons.add(new TowerIcon(TowerType.AIRPLANE));
        // Point to render the buyPanel style image
        this.point = new Point(512, 50);
        this.image = new Image(PANEL_IMG);

        this.previousStatus = Status.getCurrentStatus();
    }

    /**
     * Gets point of the BuyPanel image
     * @return the point
     */
    public Point getPoint() {
        return this.point;
    }

    /**
     * Gets image of the BuyPanel
     * @return the image of the BuyPanel
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Render the details of the money, key presses and player money onto the screen
     */
    public void render() {
        Player player = Player.getPlayer();
        // Draw the image of the BuyPanel
        image.draw(point.x, point.y);
        // Render the key press descriptions
        keyFont.drawString("Key binds:", 450, 25);
        keyFont.drawString("S - Start Wave", 450, 55);
        keyFont.drawString("L - Increase Timescale", 450, 70);
        keyFont.drawString("K - Decrease Timescale", 450, 85);
        moneyFont.drawString("$" + player.getMoney(), 824, 65);
        // Render description of TowerIcon through its price and Image
        for (TowerIcon icon : icons) {
            icon.draw(icon.getIconPoint());
            icon.showPrice(player, shopFont);
        }
    }

    /**
     * Update any changes based on player key presses or mouse clicks
     * @param input the input of the player, specifically mouse clicks
     */
    public void update(Input input) {
        // If we left clicked, check if any purchase was done.
        if (input.wasPressed(MouseButtons.LEFT)) {
            for (TowerIcon icon : icons) {
                // If the click was anywhere near the TowerIcon, check if that type of Tower is purchaseable
                if (icon.purchaseable(input, Player.getPlayer().getMoney())) {
                    // Update status
                    if (!Status.getCurrentStatus().equals(Status.WINNER)) {
                        Status.setStatus(Status.PLACING);
                    }
                    // The player is interested in this current TowerIcon
                    current = icon;
                }
            }
        }
        // If we right clicked, remove the image from cursor
        if (input.wasPressed(MouseButtons.RIGHT)) {
            Status.setStatus(previousStatus);
            current = null;
        }
    }

    /**
     * Follow movement of Mouse by rendering the image of the Tower based on player's mouse points
     * If the player left clicks on a suitable position of the map, spawn tbe Tower
     * @param input       the input and location of the players mouse
     * @param towers      the towers of the player to spawn a new Tower in
     * @param map         the map of the level
     * @param buyPanel    the buy panel
     * @param statusPanel the status panel
     */
    public void place(Input input, List<Tower> towers, TiledMap map, BuyPanel buyPanel, StatusPanel statusPanel) {
        // Start off with any position the player can place
        boolean canPlace = true;
        Point mousePoint = input.getMousePosition();
        // The player cannot place any Tower outside of the Game window
        int outX = ShadowDefend.getWidth();
        int outY = ShadowDefend.getHeight();
        if (mousePoint.x >= outX || mousePoint.x <= 0 || mousePoint.y <= 0 || mousePoint.y >= outY) {
            return;
        }
        // The player cannot place the Tower on the BuyPanel or the StatusPanel
        if (Status.getCurrentStatus().equals(Status.PLACING) && current != null) {
            if  ((buyPanel.getImage().getBoundingBoxAt(buyPanel.getPoint()).intersects(mousePoint)) ||
                (statusPanel.getImage().getBoundingBoxAt(statusPanel.getPoint()).intersects(mousePoint))) {
                canPlace = false;
            }
            // Player cannot place a Tower on the polyline unless the tower is an Airplane
            if (!(current.getType() == TowerType.AIRPLANE) &&
            (map.getPropertyBoolean((int)mousePoint.x, (int)mousePoint.y, "blocked", false))) {
                canPlace = false;
            }
            // Player cannot place a Tower on top of another Tower
            for (Tower tower : towers) {
                if (tower.getRect().intersects(mousePoint)) {
                    canPlace = false;
                }
            }
            // If the player did not disturb any of the above conditions, the location of the players mouse
            // is a point where the player can place a Tower
            if (canPlace) {
                current.draw(mousePoint);
            }
        }
        // Player has confirmed a location where it is eligible to place a tower, and so a Tower is
        // successfully purchased
        if (canPlace && input.wasPressed(MouseButtons.LEFT) && Status.getCurrentStatus().equals(Status.PLACING)) {
            towers.add(current.purchase(mousePoint));
            Status.setStatus(Status.AWAITING);
            current = null;
        }
    }

    /**
     * Reset the count of Airplane's purchased by the player on this level
     */
    public void resetIcons() {
        for (TowerIcon towericon : icons) {
            towericon.resetAirplanePrice();;
        }
    }
}

