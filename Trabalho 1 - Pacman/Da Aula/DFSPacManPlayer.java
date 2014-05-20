package player;

import java.util.List;

import pacman.*;

public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {

	static int nivel = 8; // Profundidade maxima da arvore
	public Move lastmove = null;
	static boolean Aestrela = false; // Ativa ou desativa a busca A*
        int numberDots = 0;

	// Retorna o ultimo movimento realizado pelo pacman no jogo.
	public Move getLastmove() {
		return lastmove;
	}

	@Override
	public Move chooseMove(Game game){
            if(numberDots<game.getCurrentState().getDotLocations().size()){
                numberDots = game.getCurrentState().getDotLocations().size();
            }
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
				return 100000;
			}
			if (Game.isLosing(state)){
				return Double.NEGATIVE_INFINITY;
			}
		}
		Location pacmanlocal = state.getPacManLocation();
		List<Location> ghostlocal = state.getGhostLocations();
		LocationSet dots = state.getDotLocations();
		AvaliacaoHeuristica avalia = new AvaliacaoHeuristica();
		value+= (avalia.closestEuclideanGhost(pacmanlocal, ghostlocal)*0.5);
		value+= (avalia.closestManhattanGhost(pacmanlocal, ghostlocal)*0.5);
		value+= (avalia.averageEuclideanGhost(pacmanlocal, ghostlocal)*1);
		value+= (avalia.averageManhattanGhost(pacmanlocal, ghostlocal)*1);   
		value+= (avalia.numberDots(dots,this)*6);
		value+= (avalia.secondEuclideanGhost(pacmanlocal, ghostlocal)*4);
		value+= (avalia.secondManhattanGhost(pacmanlocal, ghostlocal)*4);
		value+= (avalia.averageEuclideanDot(pacmanlocal, dots)*0.5);
		value+= (avalia.averageManhattanDot(pacmanlocal, dots)*0.5);
		value+= (avalia.thirdEuclideanGhost(pacmanlocal, ghostlocal)*3);
		value+= (avalia.thirdManhattanGhost(pacmanlocal, ghostlocal)*3);			
		value+= (avalia.closestDotEuclidean(pacmanlocal, dots)*0.5);
		value+= (avalia.closestDotManhattan(pacmanlocal, dots)*0.5);

		return value;
	}


}
