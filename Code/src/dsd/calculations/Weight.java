package dsd.calculations;

public class Weight {
	
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
	protected static double StructureWeight(double aPp, double aPpu, double aPtp, double aPb, double aPpy, double aHbeam, double aSonar1) {
		
		double StructureWeight = 0;
		
		try
		{
			StructureWeight = aPp + ((2*aPpu + 6*aPtp + 2*aPb) + 6*(aPpy*(aHbeam - aSonar1)));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return StructureWeight;
	}

}
