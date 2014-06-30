package Equipe3Player;

import jogo.Casa;
import jogo.JogoOthello;
import jogo.Jogada;

import java.util.List;

public class Avaliacao {

	static double calcHeuristica(int[][] tab, int marcatabuleiro, JogoOthello jogo){
		double heuristica=0;
		heuristica+= estadoVencedor(tab, marcatabuleiro, jogo);
                heuristica+= preencheCantos(tab, marcatabuleiro, jogo);
                heuristica+= ocupaLaterais(tab, marcatabuleiro, jogo);
                heuristica+= quantMovimentos(tab, marcatabuleiro, jogo);
                heuristica+= quantPontos(tab, marcatabuleiro, jogo);
		return heuristica;
	}
	
	private static double estadoVencedor(int[][] tab, int marcatabuleiro, JogoOthello jogo){
		int valor = jogo.teste_terminal(tab, marcatabuleiro);
		if(valor==1){
			return Double.POSITIVE_INFINITY;
		}else{
			return 0;
		}
	}
        
        private static double preencheCantos(int[][] tab, int marcatabuleiro, JogoOthello jogo){
            int meusCantos = 0;
            int cantosOponente = 0;
            double valor = 0;
            if(tab[0][0]!=0){
                if(tab[0][0]==marcatabuleiro){
                    meusCantos++;
                }else{
                    cantosOponente++;
                }
            }
            if(tab[0][jogo.tam-1]!=0){
                if(tab[0][jogo.tam-1]==marcatabuleiro){
                    meusCantos++;
                }else{
                    cantosOponente++;
                }
            }
            if(tab[jogo.tam-1][0]!=0){
                if(tab[jogo.tam-1][0]==marcatabuleiro){
                    meusCantos++;
                }else{
                    cantosOponente++;
                }
            }
            if(tab[jogo.tam-1][jogo.tam-1]!=0){
                if(tab[jogo.tam-1][jogo.tam-1]==marcatabuleiro){
                    meusCantos++;
                }else{
                    cantosOponente++;
                }
            }
            valor = meusCantos - cantosOponente;
            return valor;
        }
        
        private static double ocupaLaterais(int[][] tab, int marcatabuleiro, JogoOthello jogo){
            int minhaLaterais = 0;
            int lateraisOponente = 0;
            double valor = 0;
            for(int i=1; i<jogo.tam; i++){
                if(tab[0][i]!=0){
                    if(tab[0][i]==marcatabuleiro){
                        minhaLaterais++;
                    }else{
                        lateraisOponente++;
                    }
                }
                if(tab[i][0]!=0){
                    if(tab[i][0]==marcatabuleiro){
                        minhaLaterais++;
                    }else{
                        lateraisOponente++;
                    }
                }
                if(tab[i][jogo.tam-1]!=0){
                    if(tab[i][jogo.tam-1]==marcatabuleiro){
                        minhaLaterais++;
                    }else{
                        lateraisOponente++;
                    }
                }
                if(tab[jogo.tam-1][i]!=0){
                    if(tab[jogo.tam-1][i]==marcatabuleiro){
                        minhaLaterais++;
                    }else{
                        lateraisOponente++;
                    }
                }
            }
            valor = minhaLaterais - lateraisOponente;
            return valor;
            
        }
        
        private static double quantMovimentos(int[][] tab, int marcatabuleiro, JogoOthello jogo){
		List<Jogada> jogadasvalidas = jogo.getJogadasValidas(tab, marcatabuleiro);
		List<Jogada> jogadasvalidasoponente = jogo.getJogadasValidas(tab, -marcatabuleiro);
		return jogadasvalidas.size()-jogadasvalidasoponente.size();
	}
	
	private static double quantPontos(int[][] tab, int marcatabuleiro, JogoOthello jogo){
		int player=0, oponente=0;
		for(int i=0;i<jogo.tam-1;i++){
			for(int j=0;j<jogo.tam-1;j++){
				if(tab[i][j]==marcatabuleiro){
					player++;
				}else if(tab[i][j]==(-marcatabuleiro)){
					oponente++;
				}
			}
		}
		return player-oponente;
		
	}
	
	public static double calcEspecialista(int[][] tab, int marcatabuleiro, Casa casa, JogoOthello jogo){
		double especialista=0;
		especialista+=ocupaCantosEspecialista(tab, marcatabuleiro, casa, jogo);
		return especialista;
		}
	
	private static double ocupaCantosEspecialista(int[][] tab, int marcatabuleiro, Casa casa, JogoOthello jogo){
		if((casa.getColuna()==0)&&casa.getLinha()==0){
			return 150;
		}
		if((casa.getColuna()==0)&&casa.getLinha()==(jogo.tam-1)){
			return 150;
		}
		if((casa.getColuna()==(jogo.tam-1))&&casa.getLinha()==0){
			return 150;
		}
		if((casa.getColuna()==(jogo.tam-1))&&casa.getLinha()==(jogo.tam-1)){
			return 150;
		}
		return 0;
	}
}
