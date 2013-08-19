import java.awt.BorderLayout;

import java.util.Scanner;

public class GameDemo {

	public static void main(String argv[]) {
		Game g = new Game();
		g.playGame();
		int Countwin = 0;
		int Countloss = 0;
		for (boolean keepGame = true; keepGame = true;) {
			if (!g.win()) {
				Countloss++;
				System.out.println("Oops. You got killed.");
				System.out.println("Do you want to play again? (yes/no)");
			}
			if (g.win()) {
				Countwin++;
				System.out.println("You win.");
				System.out.println("Do you want to play again? (yes/no)");
			}
			Scanner keyboard = new Scanner(System.in);
			String s = keyboard.next();
			if (s.equals("yes"))
				new Game().playGame();
			else {
				System.out.println(Countwin + " wins and " + Countloss
						+ " losses.");
				System.exit(0);

			}
		}

	}

}