package rice_gp;


import ec.Evolve;

public class MainRiceGP {
	public static void main(String[] args) {
		String pathToFiles = "H:\\BrockUniversityThridYear\\Second Semester\\COSC 4P82(GP)\\Assignment 1\\Rice_GP_Classifications\\bin\\results";
		
		int numOfJobs = 10;
		
//		String statisticType = "ec.gp.koza.KozaShortStatistics";
		
		String[] runConfig = new String[] {
				Evolve.A_FILE, "src/rice_gp/ricegp.params",
//				"-p", ("stat="+statisticType), 
				"-p", ("stat.file=$" + pathToFiles + "out.stat"),
				"-p", ("jobs=" + numOfJobs)
		};
		Evolve.main(runConfig);
		
	}
}
