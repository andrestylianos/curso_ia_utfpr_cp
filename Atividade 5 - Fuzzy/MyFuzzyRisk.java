import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;


public class MyFuzzyRisk {
	public static void main(String[] args) throws Exception{
		//Carrega o arquivo FCL
		String fileName = "/home/pantera/opt/JFuzzy/riscoprojeto.fcl";
		FIS fis = FIS.load(fileName,true);
		
		//Verifica se o arquivo foi carregado com exito
		if(fis == null){
			System.err.println("Can't load file: "+ fileName + "");
			return;
		}
		
		//Exibe graficamente as funçoes de pertinencia(membership functions
		FunctionBlock fb = fis.getFunctionBlock(null);
		JFuzzyChart.get().chart(fb);
		
		fb.setVariable("recurso", 0);
		fb.setVariable("equipe", 8);
		
		fb.evaluate();
		Variable tip = fb.getVariable("risco");
		
		JFuzzyChart.get().chart(tip, tip.getDefuzzifier(),true);
		Gpr.debug("baixo[risco]: "+fb.getVariable("risco").getMembership("baixo"));
		Gpr.debug("medio[risco]: "+fb.getVariable("risco").getMembership("medio"));
		Gpr.debug("alto[risco]: "+fb.getVariable("risco").getMembership("alto"));
	}
}