package dsd.calculations;

public class WindPushFormulas {
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####			FORMULAS for WIND PUSH				#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	/*
	 *  Formula: 	Veffwind = [ANE2]*sin([ANE4]+ALPHA)
	 *  
	 *  page 6 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the effective Wind speed
	 */
	protected static double EffectiveWindSpeed(double aAne2, double aAne4, double aAlpha)
	{
		double effectiveWindSpeed = 0;
		
		try
		{
			effectiveWindSpeed = aAne2 * Math.sin(aAne4 + aAlpha);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return effectiveWindSpeed;
	}
	
	/*
	 *  Formula: 	Svplank = 0.5*Cdwi*RHOair*Aplank*(Veffwind)^2
	 *  
	 *  page 7 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the wind push on the planking
	 */
	protected static double WindPushOnPlank(double aCdwi, double aRhoAir, double aAplank, double aVeffwind)
	{
		double windPushOnPlank = 0;
		
		try
		{
			windPushOnPlank = 0.5 * aCdwi * aRhoAir * aAplank * Math.pow(aVeffwind,2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return windPushOnPlank;
	}
	
	/*
	 *  Formula: 	Sv(a1 tr) = 0.5*Cdwi*RHOair*(Beta1*Atraf)*(Veffwind)^2
	 *  
	 *  page 7 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the wind push on the traffic combination A1
	 */
	protected static double WindPushOnA1TrafficCombination(double aCdwi, double aRhoAir, double aBeta1, double aAtraf, double aVeffwind)
	{
		double windPushOnA1TrafficCombination = 0;
		
		try
		{
			windPushOnA1TrafficCombination = 0.5 * aCdwi * aRhoAir * (aBeta1*aAtraf) * Math.pow(aVeffwind,2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return windPushOnA1TrafficCombination;
	}

	/*
	 *  Formula: 	Sv(a2 tr) = 0.5*Cdwi*RHOair*(Beta1*Atraf)*(Veffwind)^2
	 *  
	 *  page 7 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the wind push on the traffic combination A2
	 */
	protected static double WindPushOnA2TrafficCombination(double aCdwi, double aRhoAir, double aBeta1, double aAtraf, double aVeffwind)
	{
		double windPushOnA2TrafficCombination = 0;
		
		try
		{
			windPushOnA2TrafficCombination = 0.5 * aCdwi * aRhoAir * (aBeta1*aAtraf) * Math.pow(aVeffwind,2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return windPushOnA2TrafficCombination;
	}

	/*
	 *  Formula: 	Sv(a3 tr) = 0.5*Cdwi*RHOair*(Beta2*Atraf)*(Veffwind)^2
	 *  
	 *  page 7 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the wind push on the traffic combination A3
	 */
	protected static double WindPushOnA3TrafficCombination(double aCdwi, double aRhoAir, double aBeta2, double aAtraf, double aVeffwind)
	{
		double windPushOnA3TrafficCombination = 0;
		
		try
		{
			windPushOnA3TrafficCombination = 0.5 * aCdwi * aRhoAir * (aBeta2*aAtraf) * Math.pow(aVeffwind,2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return windPushOnA3TrafficCombination;
	}

}
