import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas {

	// *************** do not change between this line and the next one ***
	// CONSTANTS
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = false;// true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	// Graphics stuff
	private Graphics2D g;
	private BufferStrategy strategy; // Use accelerated page flipping
	// ***************** do not change above this line **********

	// Your code goes below here

	// Time (in ms) to pause after each step of the game loop.
	// This, in part, determines the speed at which the game is
	// updated on the screen.
	public final int PAUSE_INTERVAL = 30; // NOT USED FOR FALL 2010
	public final int SCREEN_WIDTH = 800;
	public final int SCREEN_HEIGHT = 600;

	// VARIABLES
	private RowOfAlien row;
	private Canon canon;
	private Bullet bullet;
	public boolean win = true;
	public int Countwin;
	public int Countloss;

	/*
	 * Create a new game that is ready to play
	 */
	public Game() {
		graphicsInitialization();
		initAllObjects();
	}

	public void playGame() {
		boolean gameRunning = true;

		long currTime = System.currentTimeMillis();
		long timeStep;

		while (gameRunning) {
			timeStep = System.currentTimeMillis() - currTime;
			currTime += timeStep;

			moveAllObjects(timeStep);

			for (int index = 0; index <= row.topIndex(); index++) {

				if (row.Index(index).hitBy(bullet) == true) {
					row.remove(index);
					bullet.fire();
				}

				if (row.topIndex() == -1) {
					gameRunning = false;
					Countwin++;
					win = true;
				}

				if (row.Index(index).moving() == false) {
					gameRunning = false;
					win = false;
					Countloss++;
				}

			}

			graphicsUpdateStartOfLoop();
			drawAllObjects();
			graphicsUpdateEndOfLoop();

		}
	}

	private void initAllObjects() {
		final int ALIEN_X_POSITION = 20;
		final int ALIEN_Y_POSITION = 40;
		final int CANON_X_POSITION = 390;
		final int CANON_Y_POSITION = 570;
		final int BULLET_X_POSITION = 400;
		final int BULLET_Y_POSITION = 560;

		row = new RowOfAlien(5);

		for (int i = 0; i <= row.maxSize() - 1; i++) {

			Alien anAlien = new Alien(ALIEN_X_POSITION + i
					* SpriteStore.get().getSprite("alien.gif").getWidth(),
					ALIEN_Y_POSITION, SCREEN_WIDTH, SCREEN_HEIGHT);

			row.add(anAlien);

		}

		canon = new Canon(CANON_X_POSITION, CANON_Y_POSITION, SCREEN_WIDTH,

		SCREEN_HEIGHT);
		bullet = new Bullet(BULLET_X_POSITION, BULLET_Y_POSITION, SCREEN_WIDTH,
				SCREEN_HEIGHT);

		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}

	private void moveAllObjects(long millis) {
		bullet.fire();
		row.move();
	}

	private void drawAllObjects() {

		row.draw(g);
		canon.draw(g);
		bullet.draw(g);
	}

	public boolean win() {
		return win;
	}

	public int Countwin() {
		return Countwin;
	}

	public int Countloss() {
		return Countloss;
	}

	// *************** do not change anything from here to the end of Game
	// Do not change any of the methods whose names begin with "graphics"
	public void graphicsUpdateStartOfLoop() {
		// Get hold of a graphics context for the accelerated
		// surface and blank it out
		g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	// Do not change any of the methods whose names begin with "graphics"
	public void graphicsUpdateEndOfLoop() {

		// Finally, we've completed drawing so clear up the graphics
		// and flip the buffer over
		g.dispose();
		strategy.show();

		// Finally pause for a bit. Note: this should run us at about
		// 100 fps but on windows this might vary each loop due to
		// a bad implementation of timer
		// try {
		// Thread.sleep(PAUSE_INTERVAL);
		// } catch (Exception e) {
		// }
	}

	// Do no change any of the methods whose names begin with "graphics"
	public void graphicsInitialization() {

		// Create a frame to contain our game
		JFrame container = new JFrame("Space Invaders 110");

		// Get the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setLayout(null);

		// Setup our canvas size and put it into the content of the frame
		setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		panel.add(this);

		// Tell AWT not to bother repainting our canvas since we're
		// going to do that ourself in accelerated mode
		setIgnoreRepaint(true);

		// Make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		// Add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addKeyListener(new KeyInputHandler());

		// Request the focus so key events come to us
		requestFocus();

		// Create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	/**
	 * A class to handle keyboard input from the user. The class handles both
	 * dynamic input during game play, i.e. left/right and shoot, and more
	 * static type input (i.e. press any key to continue)
	 * 
	 * This has been implemented as an inner class more through habbit then
	 * anything else. Its perfectly normal to implement this as seperate class
	 * if slight less convienient.
	 * 
	 * @author Kevin Glass
	 */
	private class KeyInputHandler extends KeyAdapter {
		/**
		 * The number of key presses we've had while waiting for an "any key"
		 * press
		 */
		private int pressCount = 1;

		/**
		 * Notification from AWT that a key has been pressed. Note that a key
		 * being pressed is equal to being pushed down but *NOT* released. Thats
		 * where keyTyped() comes in.
		 * 
		 * @param e
		 *            The details of the key that was pressed
		 */
		public void keyPressed(KeyEvent e) {

			// if we're waiting for an "any key" typed then we don't
			// want to do anything with just a "press"
			if (waitingForKeyPress) {
				return;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				// System.out.println("left pressed");
				leftPressed = true;
				canon.moveLeft();
				bullet.moveLeft();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				// System.out.println("right pressed");
				rightPressed = true;
				canon.moveRight();
				bullet.moveRight();
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				// System.out.println("fire pressed");
				firePressed = true;

			}
		}

		/**
		 * Notification from AWT that a key has been released.
		 * 
		 * @param e
		 *            The details of the key that was released
		 */
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that typing a
		 * key means to both press and then release it.
		 * 
		 * @param e
		 *            The details of the key that was typed.
		 */
		public void keyTyped(KeyEvent e) {
			// if we're waiting for a "any key" type then
			// check if we've received any recently. We may
			// have had a keyType() event from the user releasing
			// the shoot or move keys, hence the use of the "pressCount"
			// counter.
			if (waitingForKeyPress) {
				if (pressCount == 1) {
					// since we've now recieved our key typed
					// event we can mark it as such and start
					// our new game
					waitingForKeyPress = false;
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
		}
	}
}
