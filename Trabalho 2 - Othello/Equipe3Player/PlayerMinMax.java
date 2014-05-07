package Equipe3Player;

import jogo.AbstractPlayer;
import jogo.Casa;
import jogo.Jogada;
import jogo.JogoOthello;

public class PlayerMinMax extends AbstractPlayer {

	public PlayerMinMax(int depth) {
		super(depth);
	}

	@Override
	public Casa jogar(int[][] tab) {
		Jogada jogada;
		JogoOthello jogo = new JogoOthello();
		jogada = new BuscaLargLim(jogo, tab, depth, getMinhaMarcaTabuleiro());
		
		return null;
	}

}
