/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.Pacman;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import ec.*;
import ec.simple.*;
import ec.vector.*;

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
        
        Process pacman = null;
		try {
			//pacman = Runtime.getRuntime().exec("java -cp workspace/EvoPacman/bin/ pacman.Game -pacman player.DFSPacManPlayer -display none -ghosts ghosts.RandomGhostPlayer,ghosts.RandomGhostPlayer,ghosts.RandomGhostPlayer,ghosts.RandomGhostPlayer -heuristicas 1,1,1,1,1,1,1,1,1,1,1,0,0");
			String individuo,stringheuristica;
			individuo = ind.genotypeToStringForHumans();
			stringheuristica = (individuo.charAt(0)+","+individuo.charAt(1)+","+individuo.charAt(2)+","+individuo.charAt(3)+","+individuo.charAt(4)+","+individuo.charAt(5)+","+individuo.charAt(6)+","+individuo.charAt(7)+","+individuo.charAt(8)+","+individuo.charAt(9)+","+individuo.charAt(10)+","+individuo.charAt(11)+","+individuo.charAt(12));
			ProcessBuilder builder = new ProcessBuilder("java","-cp","workspace/EvoPacman/bin/", "pacman.Game", "-pacman", "player.DFSPacManPlayer", "-display", "none", "-ghosts", "ghosts.RandomGhostPlayer,ghosts.RandomGhostPlayer,ghosts.RandomGhostPlayer,ghosts.RandomGhostPlayer","-levels","10", "-heuristicas",stringheuristica);
			builder.redirectErrorStream(true);
			builder.directory(new File("/home/pantera"));
			pacman = builder.start();
		} catch (IOException e) {
	         System.err.println("Error on exec() method");
	         e.printStackTrace();  
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(pacman.getInputStream()));
		try {
			for (String line = input.readLine(); line != null; line = input.readLine()) {
				if(line.startsWith("Time")){
					tempo=Integer.parseInt(line.split(":   ")[1]);
				}
				if(line.startsWith("Points")){
					pontos=Integer.parseInt(line.split(": ")[1]);
				}
				if(line.startsWith("Level")){
					level=Integer.parseInt(line.split(": ")[1]);
				}
			}

			input.close();
		} catch (IOException e) {
	         System.err.println("Error on inStream.readLine()");
	         e.printStackTrace();  
		}
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
