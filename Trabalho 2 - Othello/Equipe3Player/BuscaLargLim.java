package Equipe3Player;

import java.util.ArrayList;
import java.util.List;

import jogo.Casa;
import jogo.Jogada;
import jogo.JogoOthello;

public class BuscaLargLim {
	private Casa jogada;
	private int depth;
	private int minhamarca;
	
	public int getDepth() {
		return depth;
	}

	public int getMinhamarca() {
		return minhamarca;
	}
	
	public int getMarcaOponente() {
		return -minhamarca;
	}

	public BuscaLargLim(JogoOthello jogo,int[][] tab, int depth, int minhamarca){
		this.depth = depth;
		this.minhamarca = minhamarca;
		List<Double> heurisiticasjogadas = new ArrayList<Double>();
		List<Jogada> jogadas = jogo.getJogadasValidas(tab, getMinhamarca());
		for(Jogada j : jogadas){

			
		}
	}
	
	private double max(List<Double> heuristicas){
		double max = Double.MIN_VALUE;
		for(double h : heuristicas){
			if(h>max){
				max= h;
			}
		}
		return max;
	}
	
	private double min(List<Double> heuristicas){
		double min = Double.MAX_VALUE;
		for(double h : heuristicas){
			if(h>min){
				min= h;
			}
		}
		return min;
	}
	
	private double expandeMinMax(){
		return null;
	}
}
