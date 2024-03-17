package functions;

import ec.*;
import rice_gp.*;
import ec.gp.*;

@SuppressWarnings("Serial")
public class Div extends GPNode{
	
	public int expectedChildren() {
		return 2;
	}
	
	@Override
	public void eval(
			final EvolutionState state,
			final int thread,
			final GPData input, 
			final ADFStack stack,
			final GPIndividual individual,
			final Problem problem) {
		// TODO Auto-generated method stub		
		FloatData rd = ((FloatData)(input));
		
		children[1].eval(state, thread, input, stack, individual, problem);
		if(Math.abs(rd.x) < 0.0001) {
			rd.x = (float) 1.0;
		}
		else {
			float result;
			result = rd.x;
			
			children[0].eval(state, thread, input, stack, individual, problem);
			rd.x = rd.x / result;
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "/";
	}

}
