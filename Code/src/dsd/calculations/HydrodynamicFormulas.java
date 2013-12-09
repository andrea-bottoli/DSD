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
	/**
	 * @param a: parameters to calculate water flow rate
	 * @param Idro1: mean value of water level
	 * @param b: parameters to calculate water flow rate
	 * @param c: parameters to calculate water flow rate
	 * @return The water flow rate
	 */
	public static double FlowRate(double a, double Idro1, double b, double c) {

		double Q = 0;
		
		try
		{
			Q = a*Math.pow(Idro1, 2) + b*Idro1 + c;
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
	/**
	 * @param a: parameters to calculate water speed
	 * @param Idro1: mean value of water level
	 * @param b: parameters to calculate water speed
	 * @param c: parameters to calculate water speed
	 * @return The water speed
	 */
	public static double WaterSpeed(double aA, double aIdro1, double aB, double aC) {

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
	 *  Formula: 	As = Bs * hs
	 *  
	 *  page 8 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the stack area on which there is the water thrust
	 */
	/**
	 * @param Bs: stack base area
	 * @param Hs: stack height under water
	 * @return The stack area on which the water can push
	 */
	public static double StackArea(double Bs, double Hs) {

		double StackArea = 0;
		
		try
		{
			StackArea = Bs*Hs;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return StackArea;
	}
	
	
	/*
	 *  Formula: 	Swater=0.5*Cd*RHOwater*(As*BetaA)*(Vwater^2)
	 *  
	 *  page 8 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the hydrodynamic thrust on the stack
	 */
	/**
	 * @param Cd: "Drag" parameter of dynamic push
	 * @param RhoWater: water density parameter
	 * @param As: Stack area
	 * @param BetaA: reduction area parameter in case of debris
	 * @param Vwater: water speed
	 * @return The value of the force of water push
	 */
	public static double HysrodynamicThrust(double Cd, double RhoWater, double As, double BetaA, double Vwater) {

		double Swater = 0;
		
		try
		{
			Swater = 0.5 * Cd * RhoWater * (As * BetaA) * Math.pow(Vwater, 2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return Swater;
	}

}
