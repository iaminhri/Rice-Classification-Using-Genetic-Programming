package functions;

import ec.gp.*;
import rice_gp.*;
import ec.*;

/**
 * The reason these function classes extends GPNode is
 * because these functions are simple nodes in the tree.
 * ECJ connects with each other and then evaluate step by step, returning the final value based on input 
 * value to the evaluate method in problem class.
 */

@SuppressWarnings("Serial")
public class Perimeter extends GPNode{

	public int expectedChildren() {
		return 0;
	}
	
	
	@Override
	public void eval( final EvolutionState state, 
			final int thread, 
			final GPData input,
			final ADFStack stack, 
			final GPIndividual individual, 
			final Problem problem) {
		// TODO Auto-generated method stub
		FloatData rd = ((FloatData)input);
		
		// evaluates currentX, these X.java terminal has no leave nodes, since they are the leave nodes.
		// Meaning, There are no children, this function returns currentX when evaluated to the RegressionProblem Class
		// which stores the evaluated value of currentX meaning the current node of the tree.
		rd.x = ((RiceClassificaiton)problem).perimeter;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "perimeter";
	}
	
}
