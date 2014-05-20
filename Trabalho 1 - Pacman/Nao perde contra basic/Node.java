package player;

import java.util.ArrayList;
import java.util.List;

import pacman.Game;
import pacman.Move;
import pacman.State;

public class Node {

	List<Node> nodes = new ArrayList<Node>(); // Lista contendo os nós filhos
	Node raiz=null; // Ponteiro para o nó pai
	Move movimentorealizado=null; // Movimento realizado pelo estado contido no nó
	State estadoatual; // Estado atual do nó
	double heuristica = 0; // Valor da heuristica
	int profundidade=0; // Profundidade do nó
	int penalidade=0; // Penalidade para evitar que o pacman realize um movimento oposto ao anterior
	double heuristicaprojetada=0; // Valor de heuristica para projeçoes recursivas caso o pacman encontre uma bifurcação

	// Metódo construtor para nó vazio
	public Node(){
	}

	// Método contrutor para o nó inicial (raiz)
	public Node(State state, DFSPacManPlayer player, MapaFolhas map){
		this.estadoatual=state;
		this.nodes = criaArv(estadoatual, player, map);
		if(DFSPacManPlayer.Aestrela){
			Node n = map.retornaMaior();
			while((n.profundidade<DFSPacManPlayer.nivel)&&!Game.isFinal(n.estadoatual)){
				map.substituiFolha(n, player);
				n = map.retornaMaior();
			}
		}
	}

	// Método construtor para nós comuns
	public Node(int profundidade, Node raiz, State state, Move pacManMove, DFSPacManPlayer player, MapaFolhas map){
		this.profundidade=profundidade;
		this.raiz=raiz;
		this.estadoatual= Game.getNextState(state, pacManMove);
		this.movimentorealizado = pacManMove;
		if(DFSPacManPlayer.Aestrela){
			criaNodeEstrela(profundidade, pacManMove, player, map);
		}else{
			criaNode(profundidade, pacManMove, player, map);
		}
	}

	// Método construtor para nós no caso de uma árvore que tenha uma bifurcação no primeiro estado
	public Node(int profundidade, Node raiz, State state, Move pacManMove, DFSPacManPlayer player, MapaFolhas map, double heuristicaprojetada){
		this.profundidade=profundidade;
		this.raiz=raiz;
		this.heuristicaprojetada=heuristicaprojetada;
		this.estadoatual= Game.getNextState(state, pacManMove);
		this.movimentorealizado = pacManMove;
		if(DFSPacManPlayer.Aestrela){
			criaNodeEstrela(profundidade, pacManMove, player, map);
		}else{
			criaNode(profundidade, pacManMove, player, map);
		}
	}

	// Define o valor da heuristica de um nó
	private void setHeuristica(DFSPacManPlayer player) {
		this.heuristica = player.evaluateState(estadoatual);
		// Atribui valor maior para nós ganhadores com menor profundidade. Não é utilizado na busca A*
		if(Game.isWinning(estadoatual)&&!DFSPacManPlayer.Aestrela){
			this.heuristica=this.heuristica*(1+(DFSPacManPlayer.nivel-this.profundidade));
		}
		// Adiciona a penalidade à heuristica
		this.heuristica-=this.penalidade;
		// Adiciona o custo do caminho na busca A*
		if(DFSPacManPlayer.Aestrela){
			this.heuristica-=profundidade*50;
		}
		// Caso o estado inicial seja uma bifurcação insere o valor das heuristicas projetadas no calculo da heuristica final
		if(this.heuristicaprojetada!=0){
			this.heuristica+=this.heuristicaprojetada*0.8;
		}

	}

	// Adiciona penalidade caso o movimento seja o oposto do movimento realizado anteriormente
	private void loopPenalty(int profundidade, Move pacManMove,
			DFSPacManPlayer player) {
		if((profundidade==1)&&(pacManMove.getOpposite()==player.getLastmove())){
			this.penalidade=this.raiz.penalidade+50;
		}else if(pacManMove.getOpposite()==this.raiz.movimentorealizado){
			this.penalidade=this.raiz.penalidade+50;
		}
	}

	// Método para expansão de nós
	public List<Node> criaArv(State state, DFSPacManPlayer player, MapaFolhas map){
		List<Move> movimentos = Game.getLegalPacManMoves(state);
		boolean bifurcacao=true;
		AvaliacaoHeuristica avalia = new AvaliacaoHeuristica();
		// Caso o primeiro estado não for uma bifurcação, define bifurcacao como false
		if((movimentos.size()<=2)&&(this.profundidade==0)){
//			bifurcacao=false;
		}
		// Loop para criação de filhos
		for(Move m : movimentos){
			Node proximoestado = new Node();
			if(bifurcacao){
				if(this.profundidade==1){
					// Realiza o calculo da heuristica projetada pois existe uma bifurcacao no nó de profundidade 1
					this.heuristicaprojetada=avalia.keepStraight(state, m, player);
				}
				// Cria o próximo nó com o parametro adicional heuristicaprojetada
				proximoestado = new Node(this.profundidade+1,this,state,m, player, map, heuristicaprojetada);
			}else{
				// Cria o nó comum caso não exista uma bifurcação
				proximoestado = new Node(this.profundidade+1,this,state,m, player, map);
			}
			// Adiciona o nó criado na lista de nós filhos
			nodes.add(proximoestado);
		}
		return nodes;
	}


	// Confere se o nó é folha ou não
	public boolean noFolha(int profundidade){
		if(profundidade==DFSPacManPlayer.nivel){
			return true;
		}
		return false;
	}
	
	// Com base no nó de maior heuristica retorna o movimento que deve ser realizado pelo pacman
	public Move retornaJogada(Node maiorValor){
		Node atual = new Node();
		atual = maiorValor;
		while(atual.raiz!=this){
			atual = atual.raiz;
		}
		return atual.movimentorealizado;
	}

	// Cria um nó comum
	private void criaNode(int profundidade, Move pacManMove, DFSPacManPlayer player, MapaFolhas map) {
		loopPenalty(profundidade, pacManMove, player);
		// Impede de ser criado um novo nó caso seja encontrado um nó de profundidade maxima ou um estado final.
		if(!noFolha(this.profundidade)&&!Game.isFinal(estadoatual)){
			this.nodes = criaArv(estadoatual, player, map);
		}else{
			setHeuristica(player);
			map.adicionaFolha(this);
		}
	}

	// Cria um nó A*
	private void criaNodeEstrela(int profundidade, Move pacManMove, DFSPacManPlayer player, MapaFolhas map) {
		loopPenalty(profundidade, pacManMove, player);
		setHeuristica(player);
		map.adicionaFolha(this);
	}
}
