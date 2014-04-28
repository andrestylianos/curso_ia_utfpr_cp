package equipe3Player;

import java.util.List;
import java.util.Random;

import jogo.AbstractPlayer;
import jogo.Casa;
import jogo.Jogada;
import jogo.JogoVelha;

public class PlayerEspec extends AbstractPlayer{
	

	public PlayerEspec(int depth) {
		super(depth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Casa jogar(int[][] tab) {
		JogoVelha jogo = new JogoVelha();
		List<Jogada> jogadas = jogo.getJogadasValidas(tab, getMinhaMarcaTabuleiro());
		return classificaEstado(jogo, jogadas).getCasa();
	}

	
    public boolean oponenteGanha(Jogada jogada, JogoVelha jogo){
    	int[][] tab = jogada.getJogada();
    	tab[jogada.getCasa().getLinha()][jogada.getCasa().getColuna()] = getMarcaTabuleiroOponente();
    	if((jogo.teste_terminal(tab,getMarcaTabuleiroOponente())==1)){
        	tab[jogada.getCasa().getLinha()][jogada.getCasa().getColuna()] = getMinhaMarcaTabuleiro();
    		return true;
    	}else{
        	tab[jogada.getCasa().getLinha()][jogada.getCasa().getColuna()] = getMinhaMarcaTabuleiro();
    		return false;
    	}
    }

    public Jogada classificaEstado(JogoVelha jogo, List<Jogada> jogadas){
        for(Jogada a: jogadas){            
            if(jogo.teste_terminal(a.getJogada(),getMinhaMarcaTabuleiro())==1){
                return a;
            }else if(oponenteGanha(a, jogo)){
                return a;
            }
        }
        Random randomnum = new Random();
        return jogadas.get(randomnum.nextInt(jogadas.size()));
    }    
}
