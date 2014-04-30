package player;

import java.util.HashMap;


public class MapaFolhas {
	HashMap <Node,Double> mapavalores = new HashMap<Node,Double>();

	// Adiciona uma folha (chave) e sua heuristica (valor) no hashmap
	public void adicionaFolha(Node folha){
		mapavalores.put(folha, folha.heuristica);
	}

	// Para uso com a busca A*
	// É passado o nó com o valor heuristico mais alto que será expandido.
	// Remove o nó que foi passado, expande na arvore e adiciona seus filhos no hashmap
	public void substituiFolha(Node folha, DFSPacManPlayer player){
		mapavalores.remove(folha);
		folha.nodes = folha.criaArv(folha.estadoatual, player, this);
		for(Node n : folha.nodes){
			mapavalores.put(n, n.heuristica);
		}
	}

	// Retorna o nó de maior valor contido no hashmap
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
