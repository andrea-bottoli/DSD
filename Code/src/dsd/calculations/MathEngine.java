package dsd.calculations;

public class MathEngine {
	
	/*##########
	 * FORMULAS
	 *#########
	 */
	
	/*
	 *  Formula: 	Veffwind = [ANE2]*sin([ANE4]+alpha)
	 *  
	 *  page 6 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the effective Wind speed
	 */
	public static float EffectiveWindSpeed(float aAne2, float aAne4, float aAlpha)
	{
		float effectiveWindSpeed = 0;
		
		//start calculation
		effectiveWindSpeed = aAne2 * (float)Math.sin((double)(aAne4 + aAlpha));
		//end calculation
		
		return effectiveWindSpeed;
	}	
}
