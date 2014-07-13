/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.Pacman;

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
        int level=0;
        int[] stats = null, heuristica = new int[13];
        String individuo,stringheuristica;
		individuo = ind.genotypeToStringForHumans();
		stringheuristica = (individuo.charAt(0)+","+individuo.charAt(1)+","+individuo.charAt(2)+","+individuo.charAt(3)+","+individuo.charAt(4)+","+individuo.charAt(5)+","+individuo.charAt(6)+","+individuo.charAt(7)+","+individuo.charAt(8)+","+individuo.charAt(9)+","+individuo.charAt(10)+","+individuo.charAt(11)+","+individuo.charAt(12));
		for(int i=0;i<individuo.length();i++){
			heuristica[i]= Character.getNumericValue(stringheuristica.charAt(i));
		}
		try {
			stats = Game.init(10,"none","ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.StalkingGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer",heuristica);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pontos=stats[0];
		level=stats[1];
		tempo=stats[2];
        valorfitness=(pontos+tempo)*level;
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
    }
