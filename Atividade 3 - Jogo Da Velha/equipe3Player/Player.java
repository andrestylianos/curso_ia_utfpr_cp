package equipe3Player;

import java.util.List;
import java.util.Random;
import jogo.AbstractPlayer;
import jogo.Casa;
import jogo.Jogada;
import jogo.JogoVelha;

/**
*
* @author André Ramos
* 		   Vinicius Aguiar Moraes
*/

public class Player extends AbstractPlayer{

	public Player(int depth) {
		super(depth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Casa jogar(int[][] tab) {
		Random randomnum = new Random();
		JogoVelha jogo = new JogoVelha();
		List<Jogada> jogadas = jogo.getJogadasValidas(tab, getMinhaMarcaTabuleiro());
		return jogadas.get(randomnum.nextInt(jogadas.size())).getCasa();
	}

}
