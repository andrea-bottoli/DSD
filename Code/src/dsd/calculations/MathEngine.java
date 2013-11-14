package dsd.calculations;

public class MathEngine {
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####				WIND PUSH						#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	
	public static float EffectiveWindSpeed(float aAne2, float aAne4, float aAlpha)
	{
		double effectiveWindSpeed = 0;
		double lAne2 = (double)aAne2;
		double lAne4 = (double)aAne4;
		double lAlpha = (double)aAlpha;
		
		try
		{
			effectiveWindSpeed = WindPushFormulas.EffectiveWindSpeed(lAne2, lAne4, lAlpha);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)effectiveWindSpeed;
	}
	
	public static float WindPushOnPlank(float aCdwi, float aRhoAir, float aAplank, float aVeffwind)
	{
		double windPushOnPlank = 0;
		double lCdwi = (double)aCdwi;
		double lRhoAir = (double)aRhoAir;
		double lAplank = (double)aAplank;
		double lVeffwind = (double)aVeffwind;
		
		try
		{
			windPushOnPlank = WindPushFormulas.WindPushOnPlank(lCdwi, lRhoAir, lAplank, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnPlank;
	}
	
	public static float WindPushOnA1TrafficCombination(float aCdwi, float aRhoAir, float aBeta1, float aAtraf, float aVeffwind)
	{
		double windPushOnA1TrafficCombination = 0;
		double lCdwi = (double)aCdwi;
		double lRhoAir = (double)aRhoAir;
		double lBeta1 = (double)aBeta1;
		double lAtraf = (double)aAtraf;
		double lVeffwind = (double)aVeffwind;
		
		try
		{
			windPushOnA1TrafficCombination = WindPushFormulas.WindPushOnA1TrafficCombination(lCdwi, lRhoAir, lBeta1, lAtraf, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnA1TrafficCombination;
	}

	public static float WindPushOnA2TrafficCombination(float aCdwi, float aRhoAir, float aBeta1, float aAtraf, float aVeffwind)
	{
		double windPushOnA2TrafficCombination = 0;
		double lCdwi = (double)aCdwi;
		double lRhoAir = (double)aRhoAir;
		double lBeta1 = (double)aBeta1;
		double lAtraf = (double)aAtraf;
		double lVeffwind = (double)aVeffwind;
		
		try
		{
			windPushOnA2TrafficCombination = WindPushFormulas.WindPushOnA2TrafficCombination(lCdwi, lRhoAir, lBeta1, lAtraf, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnA2TrafficCombination;
	}

	public static float WindPushOnA3TrafficCombination(float aCdwi, float aRhoAir, float aBeta2, float aAtraf, float aVeffwind)
	{
		double windPushOnA3TrafficCombination = 0;
		double lCdwi = (double)aCdwi;
		double lRhoAir = (double)aRhoAir;
		double lBeta2 = (double)aBeta2;
		double lAtraf = (double)aAtraf;
		double lVeffwind = (double)aVeffwind;
		
		try
		{
			windPushOnA3TrafficCombination = WindPushFormulas.WindPushOnA3TrafficCombination(lCdwi, lRhoAir, lBeta2, lAtraf, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnA3TrafficCombination;
	}
	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####			Hydrodynamic Thrust					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	public static float FlowRate(float aA, float aIdro1, float aB, float aC)
	{
		double Q = 0;
		double lA = (double)aA;
		double lIdro1 = (double)aIdro1;
		double lB = (double)aB;
		double lC = (double)aC;
		
		try
		{
			Q = HydrodynamicFormulas.FlowRate(lA,lIdro1,lB,lC);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)Q;
	}
	
	public static float WaterSpeed(float aA, float aIdro1, float aB, float aC)
	{
		double WaterSpeed = 0;
		double lA = (double)aA;
		double lIdro1 = (double)aIdro1;
		double lB = (double)aB;
		double lC = (double)aC;
		
		try
		{
			WaterSpeed = HydrodynamicFormulas.WaterSpeed(lA,lIdro1,lB,lC);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)WaterSpeed;
	}
	
	public static float HysrodynamicThrustWithOutDebris(float aCd, float aRhoWater, float aAs, float aVwater) {

		double Swater = 0;
		double lCd = (double)aCd;
		double lRhoWater = (double)aRhoWater;
		double lAs = (double)aAs;
		double lVwater = (double)aVwater;
		
		try
		{
			Swater = HydrodynamicFormulas.HysrodynamicThrust(lCd, lRhoWater, lAs, 1, lVwater);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return (float)Swater;
	}
	
	public static float HysrodynamicThrustWithDebris(float aCd, float aRhoWater, float aAs, float aBetaA, float aVwater) {

		double Swater = 0;
		double lCd = (double)aCd;
		double lRhoWater = (double)aRhoWater;
		double lAs = (double)aAs;
		double lBetaA = (double)aBetaA;
		double lVwater = (double)aVwater;
		
		try
		{
			Swater = HydrodynamicFormulas.HysrodynamicThrust(lCd, lRhoWater, lAs, lBetaA, lVwater);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return (float)Swater;
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####			STRUCTURE WEIGHT					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	public static float StructureWeight(float aPp, float aPpu, float aPtp, float aPb, float aPpy, float aHbeam, float aSonar1)
	{
		double StructW = 0;
		double lPp = (double)aPp;
		double lPpu = (double)aPpu;
		double lPtp = (double)aPtp;
		double lPb = (double)aPb;
		double lPpy = (double)aPpy;
		double lHbeam = (double)aHbeam;
		double lSonar1 = (double)aSonar1;
		
		try
		{
			StructW = Weight.StructureWeight(lPp, lPpu, lPtp, lPb, lPpy, lHbeam, lSonar1);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)StructW;
	}
	
}
