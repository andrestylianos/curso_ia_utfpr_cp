package player;

import java.util.List;

import pacman.*;

public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {
    
    static int nivel = 10;
    public Move lastmove = null;
    static boolean Aestrela = true;

    public Move getLastmove() {
        return lastmove;
    }
    
  @Override
  public Move chooseMove(Game game){
	  MapaFolhas map = new MapaFolhas();
	  Node arvore = new Node(game.getCurrentState(),this, map);
          lastmove=arvore.retornaJogada(map.retornaMaior());
          return lastmove;
  }

  @Override
  public double evaluateState(State state) {
	  double value = 0; 
	  if(Game.isFinal(state)){
		  if(Game.isWinning(state)){
			  return 100000;
		  }
		  if (Game.isLosing(state)){
			  return -100000000;
		  }
	  }
	  Location pacmanlocal = state.getPacManLocation();
	  List<Location> ghostlocal = state.getGhostLocations();
	  LocationSet dots = state.getDotLocations();
	  AvaliacaoHeuristica avalia = new AvaliacaoHeuristica();
	  value+= (avalia.closestEuclideanGhost(pacmanlocal, ghostlocal)*0.5);
	  value+= (avalia.closestManhattanGhost(pacmanlocal, ghostlocal)*0.8);
	  value+= (avalia.averageEuclideanGhost(pacmanlocal, ghostlocal)*0.15);
	  value+= (avalia.averageManhattanGhost(pacmanlocal, ghostlocal)*0.15);   
	  value+= (avalia.numberDots(dots)*0.5);
	  value+= (avalia.secondEuclideanGhost(pacmanlocal, ghostlocal)*0.15);
	  value+= (avalia.secondManhattanGhost(pacmanlocal, ghostlocal)*0.3);
	  value+= (avalia.averageEuclideanDot(pacmanlocal, dots)*0.05);
	  value+= (avalia.averageManhattanDot(pacmanlocal, dots)*0.1);
	  value+= (avalia.closestDotEuclidean(pacmanlocal, dots)*0.3);
	  value+= (avalia.closestDotManhattan(pacmanlocal, dots)*0.6);

	  return value;
  }


}
