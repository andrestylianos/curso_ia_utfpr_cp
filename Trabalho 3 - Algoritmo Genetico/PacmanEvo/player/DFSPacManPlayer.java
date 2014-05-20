package player;

import java.util.List;

import pacman.*;

public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {

	static int nivel = 3; // Profundidade maxima da arvore
	public Move lastmove = null;
	static boolean Aestrela = true; // Ativa ou desativa a busca A*
	public boolean h1 = false;
	public boolean h2 = false;
	public boolean h3 = false;
	public boolean h4 = false;
	public boolean h5 = false;
	public boolean h6 = false;
	public boolean h7 = false;
	public boolean h8 = false;
	public boolean h9 = false;
	public boolean h10 = false;
	public boolean h11 = false;
	public boolean h12 = false;
	public boolean h13 = false;


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
		if(isH1()) value+= (avalia.closestEuclideanGhost(pacmanlocal, ghostlocal));
		if(isH2()) value+= (avalia.closestManhattanGhost(pacmanlocal, ghostlocal));
		if(isH3()) value+= (avalia.averageEuclideanGhost(pacmanlocal, ghostlocal));
		if(isH4()) value+= (avalia.averageManhattanGhost(pacmanlocal, ghostlocal));   
		if(isH5()) value+= (avalia.numberDots(dots));
		if(isH6()) value+= (avalia.secondEuclideanGhost(pacmanlocal, ghostlocal));
		if(isH7()) value+= (avalia.secondManhattanGhost(pacmanlocal, ghostlocal));
		if(isH8()) value+= (avalia.averageEuclideanDot(pacmanlocal, dots));
		if(isH9()) value+= (avalia.averageManhattanDot(pacmanlocal, dots));
		if(isH10()) value+= (avalia.thirdEuclideanGhost(pacmanlocal, ghostlocal));
		if(isH11()) value+= (avalia.thirdManhattanGhost(pacmanlocal, ghostlocal));			
		if(isH12()) value+= (avalia.closestDotEuclidean(pacmanlocal, dots));
		if(isH13()) value+= (avalia.closestDotManhattan(pacmanlocal, dots));

		return value;
	}

	@Override
	public boolean isH1() {
		return this.h1;
	}

	@Override
	public void setH1(boolean h1) {
		this.h1=h1;
	}

	public boolean isH2() {
		return h2;
	}

	public void setH2(boolean h2) {
		this.h2 = h2;
	}

	public boolean isH3() {
		return h3;
	}

	public void setH3(boolean h3) {
		this.h3 = h3;
	}

	public boolean isH4() {
		return h4;
	}

	public void setH4(boolean h4) {
		this.h4 = h4;
	}

	public boolean isH5() {
		return h5;
	}

	public void setH5(boolean h5) {
		this.h5 = h5;
	}

	public boolean isH6() {
		return h6;
	}

	public void setH6(boolean h6) {
		this.h6 = h6;
	}

	public boolean isH7() {
		return h7;
	}

	public void setH7(boolean h7) {
		this.h7 = h7;
	}

	public boolean isH8() {
		return h8;
	}

	public void setH8(boolean h8) {
		this.h8 = h8;
	}

	public boolean isH9() {
		return h9;
	}

	public void setH9(boolean h9) {
		this.h9 = h9;
	}

	public boolean isH10() {
		return h10;
	}

	public void setH10(boolean h10) {
		this.h10 = h10;
	}

	public boolean isH11() {
		return h11;
	}

	public void setH11(boolean h11) {
		this.h11 = h11;
	}

	public boolean isH12() {
		return h12;
	}

	public void setH12(boolean h12) {
		this.h12 = h12;
	}

	public boolean isH13() {
		return h13;
	}

	public void setH13(boolean h13) {
		this.h13 = h13;
	}


}
