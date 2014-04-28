package player;

import java.util.HashMap;


public class MapaFolhas {
	HashMap <Node,Double> mapavalores = new HashMap<Node,Double>();

	public void adicionaFolha(Node folha){
		mapavalores.put(folha, folha.heuristica);
	}

	public void substituiFolha(Node folha, DFSPacManPlayer player){
		mapavalores.remove(folha);
		folha.nodes = folha.criaArv(folha.estadoatual, player, this);
		for(Node n : folha.nodes){
			mapavalores.put(n, n.heuristica);
		}
	}
	
	public Node retornaMaior(){
		double max = Double.NEGATIVE_INFINITY;
		Node melhorescolha=null;
		for(Node folha : mapavalores.keySet()){
			if(mapavalores.get(folha)>=max){
				max = mapavalores.get(folha);
				melhorescolha=folha;
			}
		}
		return melhorescolha;
	}
}
