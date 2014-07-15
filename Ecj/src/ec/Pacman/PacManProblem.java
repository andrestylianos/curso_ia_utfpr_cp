/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */


package ec.Pacman;

import java.util.HashMap;
import ec.*;
import ec.simple.*;
import ec.vector.*;
import ec.Pacman.pacman.*;

public class PacManProblem extends Problem implements SimpleProblemForm
{

	public void evaluate(final EvolutionState state,
			final Individual ind,
			final int subpopulation,
			final int threadnum)
	{
		if (ind.evaluated) return;

		if (!(ind instanceof BitVectorIndividual))
			state.output.fatal("Whoa!  It's not a BitVectorIndividual!!!",null);

		int tempo=0;
		int pontos=0;
		int valorfitness=0;
		String individuo;
		HashMap<String, Integer> valores;
		individuo = ind.genotypeToStringForHumans();
		valores = ToDecimalArray(individuo);
		for(int i=0;i<3;i++){
			int[] stats = null, heuristica = new int[13];		
			try {
				stats = Game.init(10,"none","ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.StalkingGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer",valores);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pontos=stats[0];
			tempo=stats[2];
			valorfitness+=((10*pontos)+(3*tempo));
		}
		BitVectorIndividual ind2 = (BitVectorIndividual)ind;

		if (!(ind2.fitness instanceof SimpleFitness))
			state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
		((SimpleFitness)ind2.fitness).setFitness(state,
				/// ...the fitness...
				(valorfitness),
				///... is the individual ideal?  Indicate here...
				valorfitness == Integer.MAX_VALUE);
		ind2.evaluated = true;
	}

	static public HashMap<String, Integer> ToDecimalArray(String ind){
		int decimal=0;
		HashMap<String, Integer> valuesmap=new HashMap<>();
		for(int i=0;i<52;i+=4){
			decimal= (Character.getNumericValue(ind.charAt(i))*8);
			decimal+= (Character.getNumericValue(ind.charAt(i+1))*4);
			decimal+= (Character.getNumericValue(ind.charAt(i+2))*2);
			decimal+= (Character.getNumericValue(ind.charAt(i+3))*0);
			valuesmap.put(("h"+(i/4)), decimal);
		}
		decimal= (Character.getNumericValue(ind.charAt(52))*4);
		decimal+= (Character.getNumericValue(ind.charAt(53))*2);
		decimal+= (Character.getNumericValue(ind.charAt(54))*0);
		valuesmap.put("nivel", decimal);
		return valuesmap;

	}
}
