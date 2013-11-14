package dsd.calculations;

public class HydrodynamicFormulas {
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####		HYDRODYNAMIC THRUST	FORMULAS			#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	/*
	 *  Formula: 	Q = ai * (IDRO1^2) + bi * IDRO1 + ci
	 *  
	 *  page 8 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the flow rate of water
	 */
	protected static double FlowRate(double aA, double aIdro1, double aB, double aC) {

		double Q = 0;
		
		try
		{
			Q = aA*Math.pow(aIdro1, 2) + aB*aIdro1 + aC;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return Q;
	}
	
	/*
	 *  Formula: 	Vwater = a * (IDRO1^3) + b * (IDRO1^2) + c*IDRO1
	 *  
	 *  page 8 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the water speed with the 2D analysis
	 */
	protected static double WaterSpeed(double aA, double aIdro1, double aB, double aC) {

		double Vwater = 0;
		
		try
		{
			Vwater = aA*Math.pow(aIdro1, 3) + aB*Math.pow(aIdro1, 2) + aC*aIdro1;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return Vwater;
	}
	
	/*
	 *  Formula: 	Vwater = a * (IDRO1^3) + b * (IDRO1^2) + c*IDRO1
	 *  
	 *  page 8 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the water speed with the 2D analysis
	 */
	/*protected static double WaterSpeed(double aA, double aIdro1, double aB, double aC) {

		double Vwater = 0;
		
		try
		{
			Vwater = aA*Math.pow(aIdro1, 3) + aB*Math.pow(aIdro1, 2) + aC*aIdro1;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return Vwater;
	}*/

}
