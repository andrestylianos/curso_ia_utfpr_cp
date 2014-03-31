package player;

import java.util.List;

import pacman.Game;
import pacman.Move;

public class AndreRamosPacManPlayer implements pacman.PacManPlayer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Move chooseMove(Game game) {
		List<Move> possibleMoves = game.getLegalPacManMoves();
		return possibleMoves.get(0);
	}

}
