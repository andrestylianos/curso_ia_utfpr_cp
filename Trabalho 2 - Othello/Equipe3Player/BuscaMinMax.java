package Equipe3Player;

import java.util.ArrayList;
import java.util.List;

import jogo.AbstractPlayer;
import jogo.Casa;
import jogo.Jogada;
import jogo.JogoOthello;

public class BuscaMinMax {
	private int depth;
	private AbstractPlayer jogador;
	private JogoOthello jogo;
	private Casa casaescolhida, casapula;
	

	public BuscaMinMax(JogoOthello jogo, AbstractPlayer jogador, int[][] tab, int depth){
		int nivel = 1;
		double heuristicajogada = 0, especialistajogada=0, valorjogada=0, max = Double.NEGATIVE_INFINITY;
		this.jogo = jogo;
		this.jogador = jogador;
		this.depth = depth;		
		List<Jogada> jogadasvalidas = this.jogo.getJogadasValidas(tab, this.jogador.getMinhaMarcaTabuleiro());
                this.casapula = new Casa(-1,-1);
                System.out.println(this.jogo.getJogadasValidas(tab, this.jogador.getMarcaTabuleiro()).size());
                if((!jogo.getJogadasValidas(tab, jogador.getMarcaTabuleiroOponente()).isEmpty())||(jogadasvalidas.size()==0)){
                    //if(jogo.teste_terminal(tab, jogador.getMarcaTabuleiro())!=0){
                        Jogada jogadavazia = new Jogada(tab, this.casapula);
                        jogadasvalidas.add(jogadavazia);
                    //}
                }
		for(Jogada j : jogadasvalidas){
			if(this.depth>1){
				heuristicajogada = expandeMinMax(j.getJogada(), j, nivel);
			}else{
				heuristicajogada = valorHeuristica(tab, getMarcaJogadorAtual(nivel), nivel);
			}
			especialistajogada = Avaliacao.calcEspecialista(tab, getMarcaJogadorAtual(nivel), j.getCasa(), this.jogo);
			valorjogada = heuristicajogada + especialistajogada;
			if(valorjogada>max){
				max = valorjogada;
				this.casaescolhida = j.getCasa();
			}
		}
	}
	

	public Casa getCasa(){
		return this.casaescolhida;
	}

	
	private double max(List<Double> heuristicas){
		double max = Double.NEGATIVE_INFINITY;
		for(double h : heuristicas){
			if(h>max){	
				max= h;
			}
		}
		return max;
	}
	
	
	private double min(List<Double> heuristicas){
		double min = Double.POSITIVE_INFINITY;
		for(double h : heuristicas){
			if(h<min){
				min= h;
			}
		}
		return min;
	}
	
	
	private boolean noFolha(int nivel){
		if(nivel==this.depth){
			return true;
		}else{
			return false;
		}
	}
	
	
	private int getMarcaJogadorAtual(int nivel){
		if(nivel%2==0){
			return this.jogador.getMarcaTabuleiroOponente();
		}else{
			return this.jogador.getMarcaTabuleiro();
		}
	}
	
	
	private boolean estadoFinal(int[][] tab, int marca){
		int valor = this.jogo.teste_terminal(tab, marca);
		if((valor==1)||(valor==2)){
			return true;
		}else{
			return false;
		}
	}
	
	
	private double minOrMax(List<Double> heuristicas, int nivel){
		if(nivel%2==0){
			return min(heuristicas);
		}else{
			return max(heuristicas);
		}
	}
	
	private double valorHeuristica(int[][] tab, int marca, int nivel){
		if(nivel%2==0){
			return -Avaliacao.calcHeuristica(tab, getMarcaJogadorAtual(nivel), this.jogo);
		}else{
			return Avaliacao.calcHeuristica(tab, getMarcaJogadorAtual(nivel), this.jogo);
		}
	}
	
	
	
	private double expandeMinMax(int[][] tab, Jogada jogada, int nivel){
		nivel++;
		List<Double> heuristicas = new ArrayList<Double>();
		if(!noFolha(nivel)&&(!estadoFinal(tab, getMarcaJogadorAtual(nivel)))){
			List<Jogada> jogadasvalidas = this.jogo.getJogadasValidas(tab, getMarcaJogadorAtual(nivel));
			Jogada jogadavazia = new Jogada(tab, this.casapula);
			jogadasvalidas.add(jogadavazia);
			for(Jogada j : jogadasvalidas){
				heuristicas.add(expandeMinMax(j.getJogada(), j, nivel));
			}
			return minOrMax(heuristicas, nivel);
		}else{
			return valorHeuristica(tab, getMarcaJogadorAtual(nivel), nivel);
		}
	}
}
