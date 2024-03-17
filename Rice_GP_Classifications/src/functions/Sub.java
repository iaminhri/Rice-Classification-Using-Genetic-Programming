package functions;

import ec.*;
import ec.gp.*;
import rice_gp.*;

@SuppressWarnings("Serial")
public class Sub extends GPNode {

	public int expectedChildren() {
		return 2;
	}
	
	@Override
	public void eval(final EvolutionState state,
			final int thread, 
			final GPData input,
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem) {
		
		// TODO Auto-generated method stub
		
		float result;
		
		FloatData rd = ((FloatData)(input));
		
		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.x;
		
		children[1].eval(state, thread, input, stack, individual, problem);
		rd.x = result - rd.x;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "-";
	}
	
}
