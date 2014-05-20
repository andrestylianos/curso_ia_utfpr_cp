package Equipe3Player;

import jogo.AbstractPlayer;
import jogo.Casa;
import jogo.JogoOthello;

public class PlayerMinMax extends AbstractPlayer {

	public PlayerMinMax(int depth) {
		super(depth);
	}

	@Override
	public Casa jogar(int[][] tab) {
		JogoOthello jogo = new JogoOthello();
		BuscaMinMax busca = new BuscaMinMax(jogo, this, tab, getProfundidade());
		return busca.getCasa();
	}

}
