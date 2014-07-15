package ec.Pacman.pacman;

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



	public void setH1(int h1);
	
	public void setH2(int h2);

	public void setH3(int h3);

	public void setH4(int h4);

	public void setH5(int h5);

	public void setH6(int h6);

	public void setH7(int h7);

	public void setH8(int h8);

	public void setH9(int h9);

	public void setH10(int h10);

	public void setH11(int h11);	

	public void setH12(int h12);

	public void setH13(int h13);
	
	public void setNivel(int profundidade);

	Move chooseMove(Game game);
  
}
