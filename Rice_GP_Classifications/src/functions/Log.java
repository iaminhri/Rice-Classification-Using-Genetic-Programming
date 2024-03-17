package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import rice_gp.FloatData;

@SuppressWarnings("serial")
public class Log extends GPNode {
	
	public String toString() { return "rlog"; }

	public int expectedChildren() { return 1; }

	public void eval(final EvolutionState state,
                 final int thread,
                 final GPData input,
                 final ADFStack stack,
                 final GPIndividual individual,
                 final Problem problem) {
	    FloatData rd = ((FloatData)(input));
	
	    children[0].eval(state,thread,input,stack,individual,problem);
	    rd.x = (float) (rd.x == 0.0 ? 0.0 : /*Strict*/Math.log(/*Strict*/Math.abs(rd.x)));
    }

}
