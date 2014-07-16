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

		int tempo=0, pontos=0, valorfitness=0; 
		String individuo;
		HashMap<String, Integer> valores;
		individuo = ind.genotypeToStringForHumans();
		valores = ToDecimalArray(individuo);
		String ghostString = createGhostString(valores);
		for(int i=0;i<3;i++){
			int[] stats = null;		
			try {
				stats = Game.init(30,"none",ghostString,valores);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pontos=stats[0];
			tempo=stats[2];
			//System.out.println(valores.get("multiplier"));
			valorfitness+=((3*pontos)+(tempo))*valores.get("multiplier");
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
			decimal+= (Character.getNumericValue(ind.charAt(i+3))*1);
			valuesmap.put(("h"+(i/4)), decimal);
		}
		decimal= (Character.getNumericValue(ind.charAt(52))*4);
		decimal+= (Character.getNumericValue(ind.charAt(53))*2);
		decimal+= (Character.getNumericValue(ind.charAt(54))*1);
		valuesmap.put("nivel", decimal);
		for(int i=55;i<67;i+=2){
			decimal= (Character.getNumericValue(ind.charAt(i))*2);
			decimal+= (Character.getNumericValue(ind.charAt(i+1))*1);
			valuesmap.put(("G"+((i-55)/2)), decimal);
		}
		return valuesmap;

	}
	
	private String createGhostString(HashMap<String, Integer> values){
		String ghostString = new String();
		boolean stalkingexists = false;
		int multiplier=0;
		for(int i=0;i<6;i++){
			switch (values.get("G"+i)){
			case 0:
				break;
			case 1:
				ghostString=ghostString+"ec.Pacman.ghosts.BasicGhostPlayer,";
				multiplier=multiplier+6;
				break;
			case 2:
				ghostString=ghostString+"ec.Pacman.ghosts.RandomGhostPlayer,";
				multiplier=multiplier+15;
				break;
			case 3:
				ghostString=ghostString+"ec.Pacman.ghosts.StalkingGhostPlayer,";
				if(!stalkingexists){
					multiplier=multiplier+70;
					stalkingexists=true;
				}
				break;
			}
		}
		if((multiplier%6==0)||(multiplier==15)||(multiplier==70)){
			ghostString="ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer,ec.Pacman.ghosts.RandomGhostPlayer,";
			multiplier=60;
		}
		values.put("multiplier", multiplier);
		if(ghostString!="") {
			return ghostString.substring(0, (ghostString.length())-1);
		} else {
			return null;
		}
	}
}
