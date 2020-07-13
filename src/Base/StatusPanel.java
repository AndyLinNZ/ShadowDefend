package Base;

import Global.*;
import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

/**
 * Creates a panel to view the wave number, the game state, and the player lives
 */
public class StatusPanel {
    /**
     * Status panel will render the wave number, status and lives of the player
     */
    public final Font font;
    private final static String PANEL_IMG = "res/images/statuspanel.png";
    private final Point point;
    private final Image image;
    private int waveNumber;

    /**
     * Creates an instance of a status panel in ShadowDefend
     * @param fontString is the font style of the text
     */
    public StatusPanel(String fontString) {
        this.font = new Font(fontString, 15);
        this.point = new Point(512, 756);
        this.image = new Image(PANEL_IMG);
        this.waveNumber = 1;
    }

    /**
     * Gets point of the StatusPanel image.
     * @return the point
     */
    public Point getPoint() {
        return this.point;
    }

    /**
     * Gets image of the StatusPanel
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Render the player lives, wave number, Timescale, status of the game and the StatusPanel onto the screen
     */
    public void render() {
        int timeScale = ShadowDefend.getTimescale();
        // Draw the statuspanel image onto the screen
        image.draw(point.x, point.y);
        // Draw the wave number
        font.drawString("Wave: " + waveNumber, 5, 760);
        // If timescale has increased from 1.0, show it in Green
        if (ShadowDefend.getTimescale() > 1) {
            font.drawString("Time Scale: " + timeScale + ".0", 220, 760,
                    new DrawOptions().setBlendColour(Colour.GREEN));
        }
        // If timescale is at a default value of 1, indicate it in normal colour
        else {
            font.drawString("Time Scale: " + timeScale + ".0", 220, 760);
        }
        // Draw the status of the game in the middle
        font.drawString("Status: " + Status.getCurrentStatus(), 450, 760);
        // Draw the player lives on bottom right
        font.drawString("Lives: " + Player.getPlayer().getLives(), 950, 760);
    }

    /**
     * Update the wave we are currently loading on
     * @param newWaveNumber the new wave number
     */
    public void update(int newWaveNumber) {
        waveNumber = newWaveNumber;
    }

}

