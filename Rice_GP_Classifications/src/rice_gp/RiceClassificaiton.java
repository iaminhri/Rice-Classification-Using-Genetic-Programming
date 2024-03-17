package rice_gp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.gp.*;
import ec.util.Parameter;

@SuppressWarnings("Serial")
public class RiceClassificaiton extends GPProblem implements SimpleProblemForm{
	/**
	 * 
	 */
	public static final String P_DATA = "data";
	
	public static float area;
	public static float perimeter;
	public static float major_axis_length;
	public static float minor_axis_length;
	public static float eccentricity;
	public static float convex_area;
	public static float extent;
	
	public int training_data_size; 
	
	public static float [][] dataset;
    public static String [] answer;

    public static int splitIndex;

    static float[][] trainingSet;
    static float[][] testingSet;

    static String [] trainingAns;
    static String [] testingAns;
		
	public static String currentAns;
	
	/**
	 * 
	 * This section describes when to read the values so that ECJ can use them?
	 * ECJ has the method setup, which gets called before any GP stuff gets involved.
	 * That is where you should instantiate all of your data.
	 * @setup function is called before any ECJ classes or methods call
	 * 
	 */
	public void setup(final EvolutionState state, final Parameter base) {
		/**
		 * Super class setup takes two parameters
		 * @state -> EvaluationState state
		 * @base -> Parameter base
		 * 
		 */
		super.setup(state, base);
		
		/**
		 * Verifies if our input is the right class or subclasses from it.
		 * Meaning, if we are working with FloatData then check if input is an instance of FloatData
		 * that is created in the FloatData Class.
		 * 
		 * @GPProblem -> defines an instance of input
		 * verifies our input is of correct class
		 */
		if(!(input instanceof FloatData)) {
			state.output.fatal("GPData class must subclass from " + FloatData.class,
					base.push(P_DATA), null);
		}
		readFromFile(); // reads from file 
    	shuffleDataset();
        splitDataset();
		state.output.println("Training Set Length: " + trainingSet.length, 1);
		state.output.println("Testing Set Length: " + testingSet.length, 1);


	}
	
	/**
	 * fitness evaluation function, ECJ provides us with the handy function called evaluate,
	 * and it will be called for each individual in population and sets each individuals fitness
	 * 
	 */

	@Override
	public void evaluate(final EvolutionState state, final Individual ind, final int subpopulation, final int threadnum) {
		// TODO Auto-generated method stub
		
		// if individual is evaluated then don't bother reevaluating it.
		if(!ind.evaluated) {
			FloatData input = (FloatData)(this.input);
			
			int hits = 0;
			float sum = 0;
			float expectedResult;
			float result;
			
			
			for(int i = 0; i < trainingSet.length; i++) {
				
				area = trainingSet[i][0];
				perimeter = trainingSet[i][1];
				major_axis_length = trainingSet[i][2];
				minor_axis_length = trainingSet[i][3];
				eccentricity = trainingSet[i][4];
				convex_area = trainingSet[i][5];
				extent = trainingSet[i][6];
				
				currentAns = trainingAns[i];
				
				// GP TREE evaluating
				((GPIndividual)ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual)ind), this);
				
				// gp_ans produced by evaluating GP tree
				float gp_ans = input.x;
				
				
				//fitness function 
				if (gp_ans >= 0.0 && currentAns.equals("Cammeo"))
					hits++;
				else if (gp_ans < 0.0 && currentAns.equals("Osmancik"))
					hits++;;
				sum = hits;

			}
			
			// Instantiating KozaFitness class --> this better be Koza-fitness
			double fitness = trainingSet.length - sum;
			
//			double adjustedFitness = (1 / (1 + fitness));
			
			KozaFitness f = ((KozaFitness)ind.fitness);
			f.setStandardizedFitness(state, fitness);
			f.hits = hits;
			ind.evaluated = true;
		}
		
		testBestIndividual(state, ((GPIndividual)ind), subpopulation, threadnum);
	}
	
	public void testBestIndividual(final EvolutionState state, final Individual ind, final int subpopulation, final int threadnum) {
        FloatData input = (FloatData) (this.input);
        int hits = 0;

        for (int i = 0; i < testingSet.length; i++) {
            area = testingSet[i][0];
            perimeter = testingSet[i][1];
            major_axis_length = testingSet[i][2];
            minor_axis_length = testingSet[i][3];
            eccentricity = testingSet[i][4];
            convex_area = testingSet[i][5];
            extent = testingSet[i][6];

            currentAns = testingAns[i];

            ((GPIndividual) ind).trees[0].child.eval(state, threadnum, input, stack, ((GPIndividual)ind), this);

            float gp_ans = input.x;

            if ((gp_ans >= 0.0 && currentAns.equals("Cammeo")) || (gp_ans < 0.0 && currentAns.equals("Osmancik"))) {
                hits++;
            }
        }

        double fitness = testingSet.length - hits;
        KozaFitness f = ((KozaFitness) ind.fitness);
        f.setStandardizedFitness(state, fitness);
        f.hits = hits;
    }
	
	public static void splitDataset(){

        for(int i = 0; i < splitIndex; i++){
            trainingSet[i] = dataset[i];
            trainingAns[i] = answer[i];
        }

        for(int i = splitIndex; i < dataset.length; i++){
            testingSet[i - splitIndex] = dataset[i];
            testingAns[i - splitIndex] = answer[i];
        }
    }
	
	public static void shuffleDataset(){
        Random rand = new Random();

        for(int i = dataset.length - 1; i > 0; i--){
            int randIndex = rand.nextInt(i+1);

            //swap dataset attributes except answers
            float[] temp = dataset[randIndex];
            dataset[randIndex] = dataset[i];
            dataset[i] = temp;

            //swap dataset attributes only answers
            String tempAns = answer[randIndex];
            answer[randIndex] = answer[i];
            answer[i] = tempAns;
        }
    }
	
	private static void readFromFile() {
        File file = new File("src/rice_gp/Rice_Cammeo_Osmancik.arff");

        readDataset(file);
    }
    
	private static void readDataset(File file) {
        // TODO Auto-generated method stub

        List<float[]> readLines = new ArrayList<>();
        List<String> ans = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;

            boolean dataSection = false;

            while ( (line = br.readLine()) != null) {
                if(!dataSection) {
                    if(line.trim().equals("@DATA"))
                        dataSection = true;
                }
                else {
                    String[] str = line.split(",");
                    float [] values =  new float[str.length];
                    for(int i = 0; i < str.length-1; i++) {
                        values[i] = Float.parseFloat(str[i]);
                    }
                    readLines.add(values);

                    ans.add(str[str.length-1]);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }


        dataset = new float[readLines.size()][];
        answer = new String[ans.size()];

        readLines.toArray(dataset);
        ans.toArray(answer);

        splitIndex = (int) (dataset.length * 0.6);
        trainingSet = new float[splitIndex][];
        testingSet = new float[dataset.length - splitIndex][];
        trainingAns = new String[splitIndex];
        testingAns = new String[dataset.length - splitIndex];
    }
	
}
