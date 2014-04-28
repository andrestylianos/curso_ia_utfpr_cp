package player;

import java.util.ArrayList;
import java.util.List;

import pacman.Game;
import pacman.Move;
import pacman.State;

public class Node {
	
	List<Node> nodes = new ArrayList<Node>();
	Node raiz=null;
	Move movimentorealizado=null;
        State estadoatual;
	double heuristica = 0;
        int profundidade=0; //profundidade do no
        int penalidade=0;
       
	public Node(){
            
	}
        
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

	private void setHeuristica(DFSPacManPlayer player) {
		this.heuristica = player.evaluateState(estadoatual);
		if(Game.isWinning(estadoatual)&&!DFSPacManPlayer.Aestrela){
			this.heuristica=this.heuristica*(1+(DFSPacManPlayer.nivel-this.profundidade));
		}
		this.heuristica-=this.penalidade;
                if(DFSPacManPlayer.Aestrela){
                    this.heuristica-=profundidade*3;
                }
	}

	private void loopPenalty(int profundidade, Move pacManMove,
			DFSPacManPlayer player) {
		if((profundidade==1)&&(pacManMove.getOpposite()==player.getLastmove())){
			this.penalidade=this.raiz.penalidade+100;
		}else if(pacManMove.getOpposite()==this.raiz.movimentorealizado){
			this.penalidade=this.raiz.penalidade+100;
		}
	}

	public List<Node> criaArv(State state, DFSPacManPlayer player, MapaFolhas map){
		List<Move> movimentos = Game.getLegalPacManMoves(state);
		for(Move m : movimentos){
			Node proximoestado = new Node(this.profundidade+1,this,state,m, player, map);
			nodes.add(proximoestado);
		}
		return nodes;
	}
	
	
	public boolean noFolha(int profundidade){
		if(profundidade==DFSPacManPlayer.nivel){
			return true;
		}
		return false;
	}

        public Move retornaJogada(Node maiorValor){
            Node atual = new Node();
            atual = maiorValor;
            while(atual.raiz!=this){
                atual = atual.raiz;
            }
            return atual.movimentorealizado;
        }

    private void criaNode(int profundidade, Move pacManMove, DFSPacManPlayer player, MapaFolhas map) {
        loopPenalty(profundidade, pacManMove, player);
        if(!noFolha(this.profundidade)&&!Game.isFinal(estadoatual)){
                this.nodes = criaArv(estadoatual, player, map);
        }else{
                setHeuristica(player);
                map.adicionaFolha(this);
        }
    }

    private void criaNodeEstrela(int profundidade, Move pacManMove, DFSPacManPlayer player, MapaFolhas map) {
        loopPenalty(profundidade, pacManMove, player);
        setHeuristica(player);
        map.adicionaFolha(this);
    }
  
        
}
