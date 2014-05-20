package player;

import java.util.List;

import pacman.*;

public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {

	static int nivel = 3; // Profundidade maxima da arvore
	public Move lastmove = null;
	static boolean Aestrela = true; // Ativa ou desativa a busca A*

	// Retorna o ultimo movimento realizado pelo pacman no jogo.
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

	
	//Realiza o calculo da heuristica total de um estado utilizando as heuristicas parciais e seus respectivos pesos.
	@Override
	public double evaluateState(State state) {
		double value = 0; 
		if(Game.isFinal(state)){
			if(Game.isWinning(state)){
				return 10000000;
			}
			if (Game.isLosing(state)){
				return -1000000;
			}
		}
		Location pacmanlocal = state.getPacManLocation();
		List<Location> ghostlocal = state.getGhostLocations();
		LocationSet dots = state.getDotLocations();
		AvaliacaoHeuristica avalia = new AvaliacaoHeuristica();
		value+= (avalia.closestEuclideanGhost(pacmanlocal, ghostlocal)*10);
		value+= (avalia.closestManhattanGhost(pacmanlocal, ghostlocal)*20);
		value+= (avalia.averageEuclideanGhost(pacmanlocal, ghostlocal)*15);
		value+= (avalia.averageManhattanGhost(pacmanlocal, ghostlocal)*5);   
		value+= (avalia.numberDots(dots)*5);
		value+= (avalia.secondEuclideanGhost(pacmanlocal, ghostlocal)*10);
		value+= (avalia.secondManhattanGhost(pacmanlocal, ghostlocal)*15);
		value+= (avalia.thirdEuclideanGhost(pacmanlocal, ghostlocal)*7);
		value+= (avalia.thirdManhattanGhost(pacmanlocal, ghostlocal)*3);	
		value+= (avalia.averageEuclideanDot(pacmanlocal, dots)*5);
		value+= (avalia.averageManhattanDot(pacmanlocal, dots)*0);
		value+= (avalia.closestDotEuclidean(pacmanlocal, dots)*0);
		value+= (avalia.closestDotManhattan(pacmanlocal, dots)*5);
		System.out.println("\n"+value);
		return value;
	}


}
