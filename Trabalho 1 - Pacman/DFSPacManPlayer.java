package player;

import java.util.Collection;
import java.util.List;

import pacman.*;

public class DFSPacManPlayer implements PacManPlayer, StateEvaluator {
    
    static int nivel = 4;
    public Move lastmove = null;
    static boolean Aestrela = false;

    public Move getLastmove() {
        return lastmove;
    }
    
  @Override
  public Move chooseMove(Game game){
	  MapaFolhas map = new MapaFolhas();
	  Node arvore = new Node(game.getCurrentState(),this, map);
          lastmove=arvore.retornaJogada(map.retornaMaior());
          return lastmove;
  }

  @Override
  public double evaluateState(State state) {
	 double value = 0; 
         if(Game.isFinal(state)){
		  if(Game.isWinning(state)){
                      return 100000;
		  }
		  if (Game.isLosing(state)){
			  return -100000000;
		  }
	  }
          Location pacmanlocal = state.getPacManLocation();
          List<Location> ghostlocal = state.getGhostLocations();
          LocationSet dots = state.getDotLocations();
          
          value = value + closestEuclideanGhost(pacmanlocal, ghostlocal);
          value = value + closestManhattanGhost(pacmanlocal, ghostlocal);
          value = value + averageEuclideanGhost(pacmanlocal, ghostlocal);
          value = value + averageManhattanGhost(pacmanlocal, ghostlocal);   
          value = value + numberDots(dots);
          value = value + secondEuclideanGhost(pacmanlocal, ghostlocal);
          value = value + secondManhattanGhost(pacmanlocal, ghostlocal);
          value+= averageEuclideanDot(pacmanlocal, dots);
          value+= averageManhattanDot(pacmanlocal, dots);
          value+= closestDotEuclidean(pacmanlocal, dots);
          value+= closestDotManhattan(pacmanlocal, dots);
          
    return value;
  }
  
  public double closestEuclideanGhost(Location pacManLocation, List<Location> ghostLocations){
      double minDistance = Double.POSITIVE_INFINITY;
      if(10<Location.euclideanDistanceToClosest(pacManLocation, ghostLocations)){
          minDistance = 0.1*Location.euclideanDistanceToClosest(pacManLocation, ghostLocations);
      }else{
          minDistance = 2*Location.euclideanDistanceToClosest(pacManLocation, ghostLocations);
      }
      return minDistance;
  }
  
  public double closestManhattanGhost(Location pacManLocation, List<Location> ghostLocations){
      double minDistance  = Double.POSITIVE_INFINITY;
      if(10<Location.manhattanDistanceToClosest(pacManLocation, ghostLocations)){
          minDistance = 0.2*Location.manhattanDistanceToClosest(pacManLocation, ghostLocations);
      }else{
          minDistance = 4*Location.manhattanDistanceToClosest(pacManLocation, ghostLocations);
      }
      return minDistance;
  }
  
  public double averageEuclideanGhost(Location pacManLocation, List<Location> ghostLocations){
      double i=0, average = 0, sum = 0;
      for(Location l: ghostLocations){
          sum = sum + Location.euclideanDistance(pacManLocation, l);
          i++;
      }
      average = sum/i;
      return average;
  }
  
  public double averageManhattanGhost(Location pacManLocation, List<Location> ghostLocations){
      double i=0, average = 0, sum = 0;
      for(Location l: ghostLocations){
          sum = sum + Location.manhattanDistance(pacManLocation, l);
          i++;
      }
      average = sum/i;
      return average;
  }
  
  public double numberDots(LocationSet dot){
      return -dot.size();
  }
  
  public double secondEuclideanGhost(Location pacManLocation,List<Location> ghostLocations){
      double value = 0, closest = 0, secondClosest=Double.POSITIVE_INFINITY, i=0;
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
      if(secondClosest<10){
          value=0.05*secondClosest;
      }else{
          value=1.5*secondClosest;
      }
      return value;
  }
  
  public double secondManhattanGhost(Location pacManLocation,List<Location> ghostLocations){
      double value = 0, closest = 0, secondClosest=Double.POSITIVE_INFINITY, i=0;
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
      if(secondClosest<10){
          value=0.1*secondClosest;
      }else{
          value=2*secondClosest;
      }
      return value;
  }
  
  public double averageEuclideanDot(Location pacManLocation, Collection<Location> dotLocations){
      double i=0, average = 0, sum = 0;
      for(Location l: dotLocations){
          sum = sum + Location.euclideanDistance(pacManLocation, l);
          i++;
      }
      average = sum/i;
      return -average;
  }
  
  public double averageManhattanDot(Location pacManLocation, Collection<Location> dotLocations){
      double i=0, average = 0, sum = 0;
      for(Location l: dotLocations){
          sum = sum + Location.manhattanDistance(pacManLocation, l);
          i++;
      }
      average = sum/i;
      return -average;
  }
  
  public double closestDotManhattan(Location pacmanlocation, Collection<Location> dotlocations){
	  return -Location.manhattanDistanceToClosest(pacmanlocation, dotlocations);
  }
  
  public double closestDotEuclidean(Location pacmanlocation, Collection<Location> dotlocations){
	  return -Location.euclideanDistanceToClosest(pacmanlocation, dotlocations);
  }
}
