package dsd.calculations;

public class WeightFormulas {
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####				STRUCTURE WEIGHT				#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	/*
	 *  Formula: 	PPstructure = Pp + [(2*Ppu + 6*Ptp + 2*Pb) + 6*(Ppy*(h_beam - SONAR1))]
	 *  
	 *  page 10 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the structure weight
	 */
	/**
	 * @param Ppu: pulvino weight
	 * @param Ptp: trunk of pylon weight
	 * @param Pb: beam weight
	 * @param Ppy: pylon weight
	 * @param Hbeam: beam height
	 * @param Sonar1: SONAR1 value
	 * @return The value of Structure Weight 
	 */
	protected static double StackWeight(double Ppu, double Ptp, double Pb, double Ppy, double Hbeam, double Sonar1) {
		
		double stackWeight = 0;
		
		try
		{
			stackWeight = ((2*Ppu + 6*Ptp + 2*Pb) + 6*(Ppy*(Hbeam - Sonar1)));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return stackWeight;
	}

}
