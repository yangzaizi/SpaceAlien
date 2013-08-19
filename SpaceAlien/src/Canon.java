import java.awt.Graphics;
import java.awt.Image;

public class Canon {
	// Constants
	private final String CANON_ICON = "base.gif";
	private final int MAX_WIDTH;// the size of the canvas
	private final int MAX_HEIGHT;
	private double upperLeftX; // Position of base on canvas
	private double upperLeftY;
	private final Sprite SPRITE;// Graphical representation of canon
	private final double INITIAL_X_SPEED = 5.5;

	// Create a Canon constructor
	public Canon(int theX, int theY, int theMaxWidth, int theMaxHeight) {
		upperLeftX = theX;
		theX = theMaxWidth / 2;
		upperLeftY = theY;
		theY = theMaxHeight;
		MAX_WIDTH = theMaxWidth;
		MAX_HEIGHT = theMaxHeight;

		SPRITE = SpriteStore.get().getSprite(CANON_ICON);
	}

	public void draw(Graphics g) {
		SPRITE.draw(g, (int) upperLeftX, (int) upperLeftY);

	}

	public void moveRight() {
		if (upperLeftX <= 800)

			upperLeftX = upperLeftX + INITIAL_X_SPEED;

	}

	public void moveLeft() {
		if (upperLeftX >= 0)
			upperLeftX = upperLeftX - INITIAL_X_SPEED;
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