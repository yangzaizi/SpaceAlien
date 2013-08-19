import java.awt.Graphics;

public class RowOfAlien {

	// CONSTANTS
	public final int EMPTY = -1;

	// VARIABLES
	private Alien[] row;
	private int topIndex;

	public RowOfAlien(int numberOfAliens) {
		row = new Alien[numberOfAliens];
		topIndex = EMPTY;

	}

	public void add(Alien alien) {

		topIndex = topIndex + 1;
		row[topIndex] = alien;

	}

	// draw the row of alien
	public void draw(Graphics g) {

		for (int i = 0; i <= topIndex; i++) {

			row[i].draw(g);

		}

	}

	// return the length of the row
	public int maxSize() {
		return row.length;
	}

	// return the topIndex
	public int topIndex() {
		return topIndex;
	}

	public void move() {

		for (int index = 0; index <= topIndex; index++) {
			row[index].move();

		}

	}

	public void remove(int index) {
		// Put your code here

		if ((index <= topIndex) && (index >= 0)) {

			for (int i = index; i < topIndex; i++) {
				row[i] = row[i + 1];
			}
			topIndex--;
		}

	}

	public Alien Index(int Index) {
		return row[Index];
	}

}
