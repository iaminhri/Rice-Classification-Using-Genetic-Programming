package functions;

import java.util.Random;
import rice_gp.FloatData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

@SuppressWarnings("Serial")
public class Ephemeral extends GPNode {

	FloatData rd = new FloatData();
	
	public Ephemeral() {
		Random rand = new Random();
		rd.x = Math.round(rand.nextFloat()); // stores random float to FloadData Variable
	}
	
	public int expectedChildren() {
		return 0;
	}
	
	@Override
	public void eval(final EvolutionState state, final int thread, final GPData input,
			final ADFStack stack, final GPIndividual individual, final Problem problem) {
		// TODO Auto-generated method stub
		((FloatData)input).x = rd.x; // assigns initialized random inputs of ECJ.
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return rd != null ? String.valueOf(rd.x) : "n";
	}
	
}
