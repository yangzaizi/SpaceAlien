import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;

/**
 * A single alien in the Space Invaders game.
 * 
 * @author Kevin Glass
 */
class Alien {

	// CONSTANTS
	private final String ALIEN_ICON = "alien.gif";
	private final int MAX_WIDTH; // Size of canvas on which Alien is moving
	private final int MAX_HEIGHT;
	private final Sound ALIENKILLED;
	private final String ALIENKILLED_WAV = "explosion.wav";

	// Initial speed at which the alien moves horizontally
	private final double INITIAL_X_SPEED = 0.7; // NOT an int - must be double

	private final Sprite SPRITE; // Graphical representation of alien

	// VARIABLES
	private double upperLeftX; // Position of alien on canvas
	private double upperLeftY;
	boolean moveRight = true;
	boolean moveDown = false;

	/*
	 * Create a new alien object - Constructor
	 */
	public Alien(double upperLeftX2, double upperLeftY2, int theMaxWidth,
			int theMaxHeight) {
		upperLeftX = upperLeftX2;
		upperLeftY = upperLeftY2;
		MAX_WIDTH = theMaxWidth;
		MAX_HEIGHT = theMaxHeight;

		// Get the graphical representation of the alien
		SPRITE = SpriteStore.get().getSprite(ALIEN_ICON);
		ALIENKILLED = SoundStore.get().getSound(ALIENKILLED_WAV);

	}

	public void draw(Graphics g) {
		SPRITE.draw(g, (int) upperLeftX, (int) upperLeftY);

	}

	public boolean moving() {
		if (upperLeftY + SPRITE.getHeight() > MAX_HEIGHT)
			return false;
		else {

			return true;
		}
	}

	public void move() {
		// Put your code here

		if (moving() == true) {

			if (upperLeftX + SPRITE.getWidth() > MAX_WIDTH) {
				moveRight = false;
				moveDown = true;
			}

			if (upperLeftX < 0) {
				moveRight = true;
				moveDown = true;
			}

			if (moveRight == true)
				upperLeftX = upperLeftX + INITIAL_X_SPEED;

			if (moveRight == false)
				upperLeftX = upperLeftX - INITIAL_X_SPEED;

			if (moveDown == true) {
				upperLeftY = upperLeftY + SPRITE.getHeight();
				moveDown = false;
			}
		}
	}

	public boolean hitBy(Bullet b) {
		// Test if alien is hit
		if ((getUpperLeftX() <= b.getUpperLeftX())
				&& (getUpperLeftX() + getWidth() >= b.getUpperLeftX())
				&& (testCollision(b) == true)) {

			ALIENKILLED.playSound();// play the explosion sound
			return true;
		} else
			return false;
	}

	public boolean testCollision(Bullet b) {
		// Test the if the y position intersect
		if ((getUpperLeftY() <= b.getUpperLeftY())
				&& (getUpperLeftY() + getHeight() >= b.getUpperLeftY()))
			return true;
		else
			return false;
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
