package dsd.calculations;

import java.util.List;

public class MathEngine {
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####			Instruments Data					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	/**
	 * @param list of values on which calculate the mean value
	 * @return the mean value of the list of values
	 */
	public static float meanValue(List<Float> list)
	{
		return StatisticsFormulas.meanValue(list);
	}
	
	/**
	 * @param list of values of which calculate the variance
	 * @return the variance of the list of values
	 */
	public static float variance(List<Float> list)
	{
		return StatisticsFormulas.variance(list);
	}
	
	
	
	
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####				WIND PUSH						#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	/**
	 * @param Ane2: maximum wind speed
	 * @param Ane4: wind direction related to the max wind speed
	 * @param Alpha: parameter of bridge angle respects to the north
	 * @return The value represents the Effective Wind Speed
	 */
	public static float EffectiveWindSpeed(float Ane2, float Ane4, float Alpha)
	{
		double effectiveWindSpeed = 0;
		double lAne2 = (double)Ane2;
		double lAne4 = (double)Ane4;
		double lAlpha = (double)Alpha;
		
		try
		{
			effectiveWindSpeed = WindPushFormulas.EffectiveWindSpeed(lAne2, lAne4, lAlpha);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)effectiveWindSpeed;
	}
	
	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Aplank: planking area on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the planking
	 */
	public static float WindPushOnPlank(float Cdwi, float RhoAir, float Aplank, float Veffwind)
	{
		double windPushOnPlank = 0;
		double lCdwi = (double)Cdwi;
		double lRhoAir = (double)RhoAir;
		double lAplank = (double)Aplank;
		double lVeffwind = (double)Veffwind;
		
		try
		{
			windPushOnPlank = WindPushFormulas.WindPushOnPlank(lCdwi, lRhoAir, lAplank, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnPlank;
	}
	
	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Beta1: reduction area parameter
	 * @param Atraf: traffic area/surface on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the A1 traffic combination
	 */
	public static float WindPushOnA1TrafficCombination(float Cdwi, float RhoAir, float Beta1, float Atraf, float Veffwind)
	{
		double windPushOnA1TrafficCombination = 0;
		double lCdwi = (double)Cdwi;
		double lRhoAir = (double)RhoAir;
		double lBeta1 = (double)Beta1;
		double lAtraf = (double)Atraf;
		double lVeffwind = (double)Veffwind;
		
		try
		{
			windPushOnA1TrafficCombination = WindPushFormulas.WindPushOnA1TrafficCombination(lCdwi, lRhoAir, lBeta1, lAtraf, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnA1TrafficCombination;
	}

	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Beta1: reduction area parameter
	 * @param Atraf: traffic area/surface on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the A2 traffic combination
	 */
	public static float WindPushOnA2TrafficCombination(float Cdwi, float RhoAir, float Beta1, float Atraf, float Veffwind)
	{
		double windPushOnA2TrafficCombination = 0;
		double lCdwi = (double)Cdwi;
		double lRhoAir = (double)RhoAir;
		double lBeta1 = (double)Beta1;
		double lAtraf = (double)Atraf;
		double lVeffwind = (double)Veffwind;
		
		try
		{
			windPushOnA2TrafficCombination = WindPushFormulas.WindPushOnA2TrafficCombination(lCdwi, lRhoAir, lBeta1, lAtraf, lVeffwind);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)windPushOnA2TrafficCombination;
	}

	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Beta1: reduction area parameter
	 * @param Atraf: traffic area/surface on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the A3 traffic combination
	 */
	public static float WindPushOnA3TrafficCombination(float Cdwi, float RhoAir, float Beta2, float Atraf, float Veffwind)
	{
		double windPushOnA3TrafficCombination = 0;
		double lCdwi = (double)Cdwi;
		double lRhoAir = (double)RhoAir;
		double lBeta2 = (double)Beta2;
		double lAtraf = (double)Atraf;
		double lVeffwind = (double)Veffwind;
		
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
	
	/**
	 * @param a: parameters to calculate water flow rate
	 * @param Idro1: mean value of water level
	 * @param b: parameters to calculate water flow rate
	 * @param c: parameters to calculate water flow rate
	 * @return The water flow rate
	 */
	public static float FlowRate(float a, float Idro1, float b, float c)
	{
		double Q = 0;
		double lA = (double)a;
		double lIdro1 = (double)Idro1;
		double lB = (double)b;
		double lC = (double)c;
		
		try
		{
			Q = HydrodynamicFormulas.FlowRate(lA,lIdro1,lB,lC);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)Q;
	}
	
	/**
	 * @param a: parameters to calculate water speed
	 * @param Idro1: mean value of water level
	 * @param b: parameters to calculate water speed
	 * @param c: parameters to calculate water speed
	 * @return The water speed
	 */
	public static float WaterSpeed(float a, float Idro1, float b, float c)
	{
		double WaterSpeed = 0;
		double lA = (double)a;
		double lIdro1 = (double)Idro1;
		double lB = (double)b;
		double lC = (double)c;
		
		try
		{
			WaterSpeed = HydrodynamicFormulas.WaterSpeed(lA,lIdro1,lB,lC);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)WaterSpeed;
	}
	
	/**
	 * @param Bs: stack base area
	 * @param Hs: stack height under water
	 * @return The stack area on which the water can push
	 */
	public static double StackArea(float Bs, float Hs) {

		double StackArea = 0;
		double lBs = (double)Bs;
		double lHs = (double)Hs;
		
		try
		{
			StackArea = HydrodynamicFormulas.StackArea(lBs, lHs);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return (float)StackArea;
	}
	
	
	/**
	 * @param Cd: "Drag" parameter of dynamic push
	 * @param RhoWater: water density parameter
	 * @param As: Stack area
	 * @param Vwater: water speed
	 * @return The value of the force of water push
	 */
	public static float HydrodynamicThrustWithOutDebris(float Cd, float RhoWater, float As, float Vwater) {

		double Swater = 0;
		double lCd = (double)Cd;
		double lRhoWater = (double)RhoWater;
		double lAs = (double)As;
		double lVwater = (double)Vwater;
		
		try
		{
			Swater = HydrodynamicFormulas.HysrodynamicThrust(lCd, lRhoWater, lAs, 1, lVwater);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return (float)Swater;
	}
	
	/**
	 * @param Cd: "Drag" parameter of dynamic push
	 * @param RhoWater: water density parameter
	 * @param As: Stack area
	 * @param BetaA: reduction area parameter in case of debris
	 * @param Vwater: water speed
	 * @return The value of the force of water push in case of debris
	 */
	public static float HydrodynamicThrustWithDebris(float Cd, float RhoWater, float As, float BetaA, float Vwater) {

		double Swater = 0;
		double lCd = (double)Cd;
		double lRhoWater = (double)RhoWater;
		double lAs = (double)As;
		double lBetaA = (double)BetaA;
		double lVwater = (double)Vwater;
		
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
	
	/**
	 * @param Ppu: pulvino weight
	 * @param Ptp: trunk of pylon weight
	 * @param Pb: beam weight
	 * @param Ppy: pylon weight
	 * @param Hbeam: beam height
	 * @param Sonar1: SONAR1 value
	 * @return The value of Structure Weight 
	 */
	public static float StackWeight(float Ppu, float Ptp, float Pb, float Ppy, float Hbeam, float Sonar1)
	{
		double stackW = 0;
		double lPpu = (double)Ppu;
		double lPtp = (double)Ptp;
		double lPb = (double)Pb;
		double lPpy = (double)Ppy;
		double lHbeam = (double)Hbeam;
		double lSonar1 = (double)Sonar1;
		
		try
		{
			stackW = WeightFormulas.StackWeight(lPpu, lPtp, lPb, lPpy, lHbeam, lSonar1);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return (float)stackW;
	}
	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####				PYLON FORCES					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	/* #######################
	 * ##	AXIAL LOAD N	##
	 * #######################
	 */
	/**
	 * @param stackWaight: the portion of stack waight on the single line of pylons
	 * @return the value of axial force N due to the stack waight on the single pylon
	 */
	public static float ForceNcontributionPP(float stackWeight) {
		float contributionPP;
		contributionPP = PylonForcesFormulas.ForceNcontributionPP(stackWeight);
		return contributionPP;
	}
	
	/**
	 * @param n: the structure load that act on a single line of pylons
	 * @return the value of axial force N due to the external load on the single pylon
	 */
	public static float ForceNcontributionN(float n) {
		float contributionN;
		contributionN = PylonForcesFormulas.ForceNcontributionN(n);
		return contributionN;
	}
	
	/**
	 * @param ty: cutting force Ty
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of Ty force at the Axial load N on a single pylon
	 */
	public static float ForceNcontributionTy(float ty, float d, float h1, float l2) {
		float contributionTy;
		
		contributionTy = PylonForcesFormulas.ForceNcontributionTy(ty, d, h1, l2);
		
		return contributionTy;
	}
	
	
	/**
	 * @param mx: bending moment on x axis
	 * @return The contribution of bending moment Mx to the axial load N on a single pylon
	 */
	public static float ForceNcontributionMx(float mx) {
		float contributionMx;
		
		contributionMx = PylonForcesFormulas.ForceNcontributionMx(mx);
		
		return contributionMx;
	}
	
	/**
	 * @param H1: cutting force due to the level of water in case A (hi > h2)
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of water at the axial load N on a single pylon
	 */
	public static float ForceNcontributionQyCaseA(float H1, float d, float h1, float l2) {
		float contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceNcontributionQy(H1, d, h1, l2);
		
		return contributionQy;
	}
	
	/**
	 * @param H1: cutting force due to the level of water in case B (hi <= h2)
	 * @param d: distance between two line of pylons
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of water at the axial load N on a single pylon
	 */
	public static float ForceNcontributionQyCaseB(float H1, float d, float l2) {
		float contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceNcontributionQy(H1, d, 0, l2);
		
		return contributionQy;
	}
	
	
	
	/* ###########################
	 * ##	CUTTING FORCE TX	##
	 * ###########################
	 */
	/**
	 * @param tx: cutting force Tx acting on the line
	 * @return The contribution at the Tx force on each pylon
	 */
	public static float ForceTxContributionTx(float tx){
		float contributionTx;
		
		contributionTx = PylonForcesFormulas.ForceTxContributionTx(tx);
		
		return contributionTx;
	}
	
	
	
	/* ###########################
	 * ##	CUTTING FORCE TY	##
	 * ###########################
	 */
	/**
	 * @param ty: cutting force Ty acting on the line
	 * @return The contribution at the Ty force on each pylon
	 */
	public static float ForceTyContributionTy(float ty){
		float contributionTy;
		
		contributionTy = PylonForcesFormulas.ForceTyContributionTy(ty);
		
		return contributionTy;
	}
	
	/**
	 * @param H: bending moment due to the water level
	 * @return The contribution at the Qy force on each pylon
	 */
	public static float ForceTyContributionQy(float H){
		float contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceTyContributionQy(H);
		
		return contributionQy;
	}
	
	/* ###########################
	 * ##	BENDING MOMENT Mx	##
	 * ###########################
	 */
	/**
	 * @param ty: cutting force Ty acting on the line
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution at the Ty force on each pylon
	 */
	public static float ForceMxContributionTy(float ty, float l2){
		float contributionTy;
		
		contributionTy = PylonForcesFormulas.ForceMxContributionTy(ty, l2);
		
		return contributionTy;
	}
	
	public static float ForceMxContributionQy(float H1, float l2){
		float contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceMxContributionQy(H1, l2);
		
		return contributionQy;
	}
	
	
	/* ###########################
	 * ##	BENDING MOMENT My	##
	 * ###########################
	 */
	/**
	 * @param tx: cutting force Tx acting on the line
	 * @param l: distance between pulvino and the joint (l2 + h1)
	 * @return The contribution at the Tx force on each pylon
	 */
	public static float ForceMyContributionTx(float tx, float l){
		float contributionTx;
		
		contributionTx = PylonForcesFormulas.ForceMyContributionTx(tx, l);
		
		return contributionTx;
	}
}
