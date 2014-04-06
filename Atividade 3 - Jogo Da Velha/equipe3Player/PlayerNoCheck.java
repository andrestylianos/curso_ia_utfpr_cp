/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equipe3Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jogo.*;

/**
*
* @author André Ramos
*    	   Vinicius Aguiar Moraes
*/
public class PlayerNoCheck extends AbstractPlayer{
    
    public PlayerNoCheck(int depth) {
		super(depth);
	}
    
    @Override
    public Casa jogar(int[][] tab) {
		JogoVelha jogo = new JogoVelha();
		List<Jogada> jogadas = jogo.getJogadasValidas(tab, getMinhaMarcaTabuleiro());
		return classificaEstado(jogo, jogadas).getCasa();
    }
    
    public Jogada classificaEstado(JogoVelha jogo, List<Jogada> jogadas){
        Map<Jogada,Integer> valorHeuristica = new HashMap<Jogada,Integer>();
        for(Jogada a: jogadas){            
        	int valor = 0;
            if(jogo.teste_terminal(a.getJogada(),getMinhaMarcaTabuleiro())==1){
                valor=20;
            }else{
            	valor+=heuristicaRetas(jogo, a, getMinhaMarcaTabuleiro(), getMarcaTabuleiroOponente());
            	valor+=heuristicaDiagonais(jogo, a, getMinhaMarcaTabuleiro(), getMarcaTabuleiroOponente());
            }
            //System.out.println("Casa ["+ a.getCasa().getLinha()+"]["+a.getCasa().getColuna()+"] \n Valor:"+valor);
            valorHeuristica.put(a, valor);
        }
        int max = Collections.max(valorHeuristica.values());
        for(Jogada a: jogadas){
        	if(valorHeuristica.get(a)==max){
        		return a;
        	}
        	
        }
        //System.out.println("DEU ERRO");
        return jogadas.get(0);
    }
    
    public int heuristicaRetas(JogoVelha jogo, Jogada a, int marcajogador, int marcaoponente){
        int linha=0,coluna=0,linhaOponente=0,colunaOponente=0,valor=0;
    	for(int i=0;i<jogo.tam;i++){
            for(int j=0;j<jogo.tam;j++){
                if(a.getJogada()[i][j]==marcajogador){
                    linha++;
                }else if(a.getJogada()[i][j]==marcaoponente){
                    linhaOponente++;
                }
                if(a.getJogada()[j][i]==marcajogador){
                    coluna++;
                }else if(a.getJogada()[j][i]==marcaoponente){
                    colunaOponente++;
                }
            }
            if((linha>=1)&&(linhaOponente==0)){
            	valor+=1;
            }
            if((coluna>=1)&&(colunaOponente==0)){
            	valor+=1;
            }
            if((linhaOponente>=1)&&(linha==0)){
            	valor-=1;
            }
            if((colunaOponente>=1)&&(coluna==0)){
            	valor-=1;
            }
            linha=0;
            coluna=0;
            linhaOponente=0;
            colunaOponente=0;
        }
    	return valor;
    }
    
    public int heuristicaDiagonais(JogoVelha jogo, Jogada a, int marcajogador, int marcaoponente){
        int diagonal1=0, diagonal2=0, diagonalOponente1=0, diagonalOponente2=0, valor=0;
    	for(int i=0; i<jogo.tam;i++){
            if(a.getJogada()[i][i]==marcajogador){
                diagonal1++;
            }else if(a.getJogada()[i][i]==marcaoponente){
                diagonalOponente1++;
            }
            if(a.getJogada()[i][jogo.tam-(i+1)]==marcajogador){
                diagonal2++;
            }else if(a.getJogada()[i][jogo.tam-(i+1)]==marcaoponente){
                diagonalOponente2++;
            }
        }
        if((diagonal1>=1)&&(diagonalOponente1==0)){
            valor+=1;
        }
        if((diagonal2>=1)&&(diagonalOponente2==0)){
        	valor+=1;
        }
        if((diagonalOponente1>=1)&&(diagonal1==0)){
            valor-=1;
        }
        if((diagonalOponente2>=1)&&(diagonal2==0)){
        	valor-=1;
        }
        return valor;
    }
    
}
