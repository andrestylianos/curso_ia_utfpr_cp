package ec.Pacman.player;

import java.util.List;

import ec.Pacman.pacman.*;

public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {

	static int nivel = 3; // Profundidade maxima da arvore
	public Move lastmove = null;
	static boolean Aestrela = true; // Ativa ou desativa a busca A*
	public int h1 = 0;
	public int h2 = 0;
	public int h3 = 0;
	public int h4 = 0;
	public int h5 = 0;
	public int h6 = 0;
	public int h7 = 0;
	public int h8 = 0;
	public int h9 = 0;
	public int h10 = 0;
	public int h11 = 0;
	public int h12 = 0;
	public int h13 = 0;


	// Retorna o ultimo movimento realizado pelo pacman no jogo.
	public Move getLastmove() {
		return lastmove;
	}
	
	public void setNivel(int nivel){
		DFSPacManPlayer.nivel = nivel;
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
		value+= h1*(avalia.closestEuclideanGhost(pacmanlocal, ghostlocal));
		value+= h2*(avalia.closestManhattanGhost(pacmanlocal, ghostlocal));
		value+= h3*(avalia.averageEuclideanGhost(pacmanlocal, ghostlocal));
		value+= h4*(avalia.averageManhattanGhost(pacmanlocal, ghostlocal));   
		value+= h5*(avalia.numberDots(dots));
		value+= h6*(avalia.secondEuclideanGhost(pacmanlocal, ghostlocal));
		value+= h7*(avalia.secondManhattanGhost(pacmanlocal, ghostlocal));
		value+= h8*(avalia.averageEuclideanDot(pacmanlocal, dots));
		value+= h9*(avalia.averageManhattanDot(pacmanlocal, dots));
		value+= h10*(avalia.thirdEuclideanGhost(pacmanlocal, ghostlocal));
		value+= h11*(avalia.thirdManhattanGhost(pacmanlocal, ghostlocal));			
		value+= h12*(avalia.closestDotEuclidean(pacmanlocal, dots));
		value+= h13*(avalia.closestDotManhattan(pacmanlocal, dots));

		return value;
	}

	@Override
	public void setH1(int h1) {
		this.h1=h1;
	}

	public void setH2(int h2) {
		this.h2 = h2;
	}

	public void setH3(int h3) {
		this.h3 = h3;
	}

	public void setH4(int h4) {
		this.h4 = h4;
	}

	public void setH5(int h5) {
		this.h5 = h5;
	}

	public void setH6(int h6) {
		this.h6 = h6;
	}

	public void setH7(int h7) {
		this.h7 = h7;
	}

	public void setH8(int h8) {
		this.h8 = h8;
	}

	public void setH9(int h9) {
		this.h9 = h9;
	}

	public void setH10(int h10) {
		this.h10 = h10;
	}

	public void setH11(int h11) {
		this.h11 = h11;
	}

	public void setH12(int h12) {
		this.h12 = h12;
	}

	public void setH13(int h13) {
		this.h13 = h13;
	}


}
