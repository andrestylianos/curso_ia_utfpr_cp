package pacman;

/**
 * An interface for classes that can choose Pac-Man's moves, conditioned
 * on the history and the ghost players.
 * @author grenager
 *
 */
public interface PacManPlayer {
  
  /**
   * Chooses a Move for Pac-Man in the Game.
   * @param game
   * @return a Move for Pac-Man
   */

	

	
	public boolean isH1();

	public void setH1(boolean h1);
	
	public boolean isH2();

	public void setH2(boolean h2);

	public boolean isH3();

	public void setH3(boolean h3);

	public boolean isH4();

	public void setH4(boolean h4);

	public boolean isH5();

	public void setH5(boolean h5);

	public boolean isH6();

	public void setH6(boolean h6);

	public boolean isH7();

	public void setH7(boolean h7);

	public boolean isH8();

	public void setH8(boolean h8);

	public boolean isH9();

	public void setH9(boolean h9);

	public boolean isH10();

	public void setH10(boolean h10);

	public boolean isH11();

	public void setH11(boolean h11);

	public boolean isH12();
	

	public void setH12(boolean h12);

	public boolean isH13();

	public void setH13(boolean h13);

	Move chooseMove(Game game);
  
}
