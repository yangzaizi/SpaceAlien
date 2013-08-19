import java.awt.Graphics;
import java.awt.Image;

public class Bullet {

	// Constants
	private final String BULLET_ICON = "missile.gif";
	private final int MAX_WIDTH;// Size of canvas on which the bullet is moving
	private final int MAX_HEIGHT;

	// Variables
	private double upperLeftX; // Position of missile on canvas
	private double upperLeftY;

	private final Sprite SPRITE;// Graphical representation of the bullet

	// Initial speed at which the bullet is moving vertically
	private final double INITIAL_Y_SPEED = 0.7;
	private final double INITIAL_X_SPEED = 5.5;

	// Create a bullet constructor
	public Bullet(int theX, int theY, int theMaxWidth, int theMaxHeight) {
		upperLeftX = theX;
		theX = theMaxWidth / 2;
		upperLeftY = theY;
		theY = theMaxHeight;
		MAX_WIDTH = theMaxWidth;
		MAX_HEIGHT = theMaxHeight;

		SPRITE = SpriteStore.get().getSprite(BULLET_ICON);

	}

	public void draw(Graphics g) {
		SPRITE.draw(g, (int) upperLeftX, (int) upperLeftY);

	}

	public void fire() {

		upperLeftY = upperLeftY - INITIAL_Y_SPEED;

		// When the bullet disappears on top of the screen, fire another bullet
		if (upperLeftY - SPRITE.getHeight() < 0) {
			upperLeftY = 560;
			fire();
		}
	}

	public double upperLeftX(Canon c) {
		upperLeftX = c.getUpperLeftX();
		return upperLeftX;
	}

	public void moveRight() {

		upperLeftX = upperLeftX + INITIAL_X_SPEED;

	}

	public void moveLeft() {

		upperLeftX = upperLeftX - INITIAL_X_SPEED;

	}

	public int getHeight() {
		return SPRITE.getHeight();
	}

	public int getWidth() {
		return SPRITE.getWidth();
	}

	public double getUpperLeftX() {
		return upperLeftX;
	}

	public void setUpperLeftX(int x) {
		upperLeftX = x;
	}

	public double getUpperLeftY() {
		return upperLeftY;
	}

	public void setUpperLeftY(int y) {
		upperLeftY = y;
	}

}
