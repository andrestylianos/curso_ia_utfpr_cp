package player;

import java.util.Collection;
import java.util.List;

import pacman.Game;
import pacman.Location;
import pacman.LocationSet;
import pacman.Move;
import pacman.State;

// Classe que contém as heuristicas



public class AvaliacaoHeuristica {
    
    static double maxManhattanDistance = Game.xDim+Game.yDim;
    static double maxEuclideanDistance = Math.sqrt(Math.pow(Game.xDim, 2)+Math.pow(Game.yDim, 2));

	// Calcula a distancia euclideana entre o pacman e o fantasma mais próximo.
	// Quanto maior melhor
	public double closestEuclideanGhost(Location pacManLocation, List<Location> ghostLocations){
		double minDistance = Double.POSITIVE_INFINITY;
		if(15<Location.euclideanDistanceToClosest(pacManLocation, ghostLocations)){
			minDistance = maxEuclideanDistance-Location.euclideanDistanceToClosest(pacManLocation, ghostLocations);
		}else{
			minDistance = 0.1*(maxEuclideanDistance-Location.euclideanDistanceToClosest(pacManLocation, ghostLocations));
		}
		return -minDistance;
	}

	// Calcula a distancia de manhattan entre o pacman e o fantasma mais próximo.
	// Quanto maior melhor
	public double closestManhattanGhost(Location pacManLocation, List<Location> ghostLocations){
		double minDistance  = Double.POSITIVE_INFINITY;
		if(15<Location.manhattanDistanceToClosest(pacManLocation, ghostLocations)){
                    minDistance = (maxManhattanDistance-Location.manhattanDistanceToClosest(pacManLocation, ghostLocations));
		}else{
			minDistance = 0.1*maxManhattanDistance-Location.manhattanDistanceToClosest(pacManLocation, ghostLocations);
                }
		return -minDistance;
	}

	// Calcula a distancia euclidiana média entre o pacman e todos os fantasmas.
	// Quanto maior melhor
	public double averageEuclideanGhost(Location pacManLocation, List<Location> ghostLocations){
		double i=0, average = 0, sum = 0;
		for(Location l: ghostLocations){
			sum = sum + Location.euclideanDistance(pacManLocation, l);
			i++;
		}
		average = sum/i;
		return average;
	}

	// Calcula a distancia de manhattan média entre o pacman e todos os fantasmas.
	// Quanto maior melhor
	public double averageManhattanGhost(Location pacManLocation, List<Location> ghostLocations){
		double i=0, average = 0, sum = 0;
		for(Location l: ghostLocations){
			sum = sum + Location.manhattanDistance(pacManLocation, l);
			i++;
		}
		average = sum/i;
		return average;
	}

	// Varia o peso atribuido para a heuristica da distancia euclideana entre o pacman e o segundo fantasma mais próximo
	// com base em pequenas ou grandes distancias.
	public double secondEuclideanGhost(Location pacManLocation,List<Location> ghostLocations){
		double value;
		double secondClosest = getSecondClosestEuclDis(pacManLocation,
				ghostLocations);
		if(secondClosest<15){
			value=maxEuclideanDistance-secondClosest;
		}else{
			value=0.01*(maxEuclideanDistance-secondClosest);
		}
		return -value;
	}

	// Calcula a distancia euclideana entre o pacman e o segundo fantasma mais próximo.
	// Quanto maior melhor
	private double getSecondClosestEuclDis(Location pacManLocation,
			List<Location> ghostLocations) {
		double closest = 0, secondClosest=Double.POSITIVE_INFINITY, i=0;
		closest = Location.euclideanDistanceToClosest(pacManLocation, ghostLocations);
		for(Location l: ghostLocations){
			if((closest==Location.euclideanDistance(pacManLocation, l))&&(i==0)){
				i++;
			}else if((closest==Location.euclideanDistance(pacManLocation, l))&&(i==1)){
				secondClosest=Location.euclideanDistance(pacManLocation, l);
			}else if(secondClosest>Location.euclideanDistance(pacManLocation, l)){
				secondClosest=Location.euclideanDistance(pacManLocation, l);
			}
		}
		return secondClosest;
	}

	// Varia o peso atribuido para a heuristica da distancia euclideana entre o pacman e o terceiro fantasma mais próximo
	// com base em pequenas ou grandes distancias.
	public double thirdEuclideanGhost(Location pacManLocation,List<Location> ghostLocations){
		double value;
		double thirdClosest = getThirdClosestEuclDis(pacManLocation,
				ghostLocations);
		if(thirdClosest<20){
			value=(maxEuclideanDistance-thirdClosest);
		}else{
			value=0.01*(maxEuclideanDistance-thirdClosest);
		}
		return -value;
	}

	// Calcula a distancia euclideana entre o pacman e o terceiro fantasma mais próximo.
	// Quanto maior melhor
	private double getThirdClosestEuclDis(Location pacManLocation,
			List<Location> ghostLocations) {
		double thirdclosest = Double.POSITIVE_INFINITY, secondclosest = 0,i=0;
		secondclosest = getSecondClosestEuclDis(pacManLocation, ghostLocations);
		for(Location l: ghostLocations){
			if(secondclosest==Location.euclideanDistance(pacManLocation, l)&&(i==0)){
				i++;
			}else if(secondclosest==Location.euclideanDistance(pacManLocation, l)&&(i==1)){
				thirdclosest=secondclosest;
			}else if(thirdclosest>Location.euclideanDistance(pacManLocation, l)){
				thirdclosest=Location.euclideanDistance(pacManLocation, l);
			}
		}
		return thirdclosest;
	}
	
	// Varia o peso atribuido para a heuristica da distancia euclideana entre o pacman e o segundo fantasma mais próximo
		// com base em pequenas ou grandes distancias.
		public double thirdManhattanGhost(Location pacManLocation,List<Location> ghostLocations){
			double value;
			double thirdClosest = getThirdClosestManhDis(pacManLocation,
					ghostLocations);
			if(thirdClosest<20){
				value=maxManhattanDistance-thirdClosest;
			}else{
				value=0.01*(maxManhattanDistance-thirdClosest);
			}
			return -value;
		}

		// Calcula a distancia euclideana entre o pacman e o terceiro fantasma mais próximo.
		// Quanto maior melhor
		private double getThirdClosestManhDis(Location pacManLocation,
				List<Location> ghostLocations) {
			double thirdclosest = Double.POSITIVE_INFINITY, secondclosest = 0,i=0;
			secondclosest = getSecondClosestManhGhostDis(pacManLocation, ghostLocations);
			for(Location l: ghostLocations){
				if(secondclosest==Location.manhattanDistance(pacManLocation, l)&&(i==0)){
					i++;
				}else if(secondclosest==Location.manhattanDistance(pacManLocation, l)&&(i==1)){
					thirdclosest=secondclosest;
				}else if(thirdclosest>Location.manhattanDistance(pacManLocation, l)){
					thirdclosest=Location.manhattanDistance(pacManLocation, l);
				}
			}
			return thirdclosest;
		}

	// Varia o peso atribuido para a heuristica da distancia de manhattan entre o pacman e o terceiro fantasma mais próximo
	// com base em pequenas ou grandes distancias.
	public double secondManhattanGhost(Location pacManLocation,List<Location> ghostLocations){
		double value;
		double secondClosest = getSecondClosestManhGhostDis(pacManLocation,
				ghostLocations);
		if(secondClosest<15){
			value=maxManhattanDistance-secondClosest;
		}else{
			value=0.01*(maxManhattanDistance-secondClosest);
		}
		return -value;
	}

	// Calcula a distancia de manhattan entre o pacman e o segundo fantasma mais próximo.
	// Quanto maior melhor
	private double getSecondClosestManhGhostDis(Location pacManLocation,
			List<Location> ghostLocations) {
		double closest = 0, secondClosest=Double.POSITIVE_INFINITY, i=0;
		closest = Location.manhattanDistanceToClosest(pacManLocation, ghostLocations);
		for(Location l: ghostLocations){
			if((closest==Location.manhattanDistance(pacManLocation, l))&&(i==0)){
				i++;
			}else if((closest==Location.manhattanDistance(pacManLocation, l))&&(i==1)){
				secondClosest=Location.manhattanDistance(pacManLocation, l);
			}else if(secondClosest>Location.manhattanDistance(pacManLocation, l)){
				secondClosest=Location.manhattanDistance(pacManLocation, l);
			}
		}
		return secondClosest;
	}

	// Contabiliza o numero de pontos restantes no tabuleiro.
	// Quanto menor melhor
	public double numberDots(LocationSet dot,DFSPacManPlayer pacman){
		return (pacman.numberDots-dot.size());
	}

	// Calcula a distancia euclideana media entre o pacman e os pontos.
	// Quanto menor melhor
	public double averageEuclideanDot(Location pacManLocation, Collection<Location> dotLocations){
		double i=0, average = 0, sum = 0;
		for(Location l: dotLocations){
			sum = sum + Location.euclideanDistance(pacManLocation, l);
			i++;
		}
		average = sum/i;
                if(average<10) average=0;
		return -average;
	}

	// Calcula a distancia de manhattan media entre o pacman e os pontos.
	// Quanto menor melhor
	public double averageManhattanDot(Location pacManLocation, Collection<Location> dotLocations){
		double i=0, average = 0, sum = 0;
		for(Location l: dotLocations){
			sum = sum + Location.manhattanDistance(pacManLocation, l);
			i++;
		}
		average = sum/i;
                if(average<10) average=0;
		return -average;
	}

	// Calcula a distancia euclideana entre o pacman e o ponto mais próximo.
	// Quanto menor melhor
	public double closestDotEuclidean(Location pacmanlocation, Collection<Location> dotlocations){
		if(Location.euclideanDistanceToClosest(pacmanlocation, dotlocations)>5){
            return -Location.euclideanDistanceToClosest(pacmanlocation, dotlocations);}
                else{
                    return -0.2*Location.euclideanDistanceToClosest(pacmanlocation, dotlocations);
                }
	}

	// Calcula a distancia de manhattan entre o pacman e o ponto mais próximo.
	// Quanto menor melhor
	public double closestDotManhattan(Location pacmanlocation, Collection<Location> dotlocations){
            if(Location.manhattanDistanceToClosest(pacmanlocation, dotlocations)>5){
		return -Location.manhattanDistanceToClosest(pacmanlocation, dotlocations);
            }else{
                return -0.2*Location.manhattanDistanceToClosest(pacmanlocation, dotlocations);
            }
	}

	// Calcula a heuristica do ultimo estado caso o pacman continuasse em linha reta até encontrar uma parede.
	public double keepStraight(State state,Move pacmanmove, DFSPacManPlayer player){
		List<State> projectedstate = Game.getProjectedStates(state, pacmanmove);
		double value=0;
		value = player.evaluateState(projectedstate.get(projectedstate.size()-1));
		return value;
	}
}
