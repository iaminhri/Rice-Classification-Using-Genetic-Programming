package rice_gp;

/*
 * Importing ECJ's GPData Class
 * GPData is the base class for all data types which we want to use in ECJ
 */
import ec.gp.GPData;


/**
 * FloatData Class extends GPData Class
 * //float variable x holds the terminal of Float Type.
 */
@SuppressWarnings("Serial")
public class FloatData extends GPData{
	//float variable x holds the terminal of Float Type.
	public float x; 
	
	/**
	 * GPData's method copyTo is implemented here.
	 * this is how we instantiate of Float data type in ECJ 
	 */
	public void copyTo(final GPData gpd) {
		((FloatData)gpd).x = x;
	}
}

