package functions;

import rice_gp.FloatData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;


/**
 * Defining the class for function "+"
 */

@SuppressWarnings("Serial")
public class Add extends GPNode{
	
	/**
	 * @expectedChildren() -> returns the number of values this function expects to receive.
	 * f.g -> if we had sin function we would have returned 1.
	 */
	public int expectedChildren() {
		return 2;
	}

	/**
	 * @eval(EvaluationState state, int thred, GPData input, ADFStack stack, 
	 * 		 GPIndividual individual, Problem problem) --> ECJ calls this function when it evaluates 
	 * the tree
	 * When we have two or more inputs in the function we need to store each value of the branch under 
	 * new variables. 
	 * @rd -> this is the variable ECJ will save the result of evaluating the branch of the node in.
	 * we need to evaluate the left branch beneath this node and save the result of it in result 
	 * variables and then we evaluate the right branch of this node, and we store the final result back
	 * in rd variable. So ECJ could use it on the node above.
	 * The other functions changes would be change in class name, toString output, expectedChildren and eval functions
	 */
	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, 
			GPIndividual individual, Problem problem) {
		// TODO Auto-generated method stub
		
		float result;
		FloatData rd = ((FloatData)(input));
		
		// Evaluate left child
		children[0].eval(state, thread, rd, stack, individual, problem);
		result = rd.x;
		
		//evaluates right child
		children[1].eval(state, thread, rd, stack, individual, problem);
		
		//add the evaluated result and return the result
		rd.x = result + rd.x;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "+";
	}
	
}
