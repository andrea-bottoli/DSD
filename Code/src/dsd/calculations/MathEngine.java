/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dsd.calculations;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

public class MathEngine {
	
	/*
	 * ##########################################################
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
		double contributionPP;
		contributionPP = PylonForcesFormulas.ForceNcontributionPP((double)stackWeight);
		return (float)contributionPP;
	}
	
	/**
	 * @param n: the structure load that act on a single line of pylons
	 * @return the value of axial force N due to the external load on the single pylon
	 */
	public static float ForceNcontributionN(float n) {
		double contributionN;
		contributionN = PylonForcesFormulas.ForceNcontributionN((double)n);
		return (float)contributionN;
	}
	
	/**
	 * @param ty: cutting force Ty
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of Ty force at the Axial load N on a single pylon
	 */
	public static float ForceNcontributionTy(float ty, float d, float h1, float l2) {
		double contributionTy;
		
		contributionTy = PylonForcesFormulas.ForceNcontributionTy((double)ty, (double)d, (double)h1, (double)l2);
		
		return (float)contributionTy;
	}
	
	
	/**
	 * @param mx: bending moment on x axis
	 * @return The contribution of bending moment Mx to the axial load N on a single pylon
	 */
	public static float ForceNcontributionMx(float mx) {
		double contributionMx;
		
		contributionMx = PylonForcesFormulas.ForceNcontributionMx((double)mx);
		
		return (float)contributionMx;
	}
	
	/**
	 * @param H1: cutting force due to the level of water in case A (hi > h2)
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of water at the axial load N on a single pylon
	 */
	public static float ForceNcontributionQyCaseA(float H1, float d, float h1, float l2) {
		double contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceNcontributionQy((double)H1, (double)d, (double)h1, (double)l2);
		
		return (float)contributionQy;
	}
	
	/**
	 * @param H1: cutting force due to the level of water in case B (hi <= h2)
	 * @param d: distance between two line of pylons
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of water at the axial load N on a single pylon
	 */
	public static float ForceNcontributionQyCaseB(float H1, float d, float l2) {
		double contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceNcontributionQy((double)H1, (double)d, (double)0, (double)l2);
		
		return (float)contributionQy;
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
		double contributionTx;
		
		contributionTx = PylonForcesFormulas.ForceTxContributionTx((double)tx);
		
		return (float)contributionTx;
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
		double contributionTy;
		
		contributionTy = PylonForcesFormulas.ForceTyContributionTy((double)ty);
		
		return (float)contributionTy;
	}
	
	/**
	 * @param H: bending moment due to the water level
	 * @return The contribution at the Qy force on each pylon
	 */
	public static float ForceTyContributionQy(float H){
		double contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceTyContributionQy((double)H);
		
		return (float)contributionQy;
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
		double contributionTy;
		
		contributionTy = PylonForcesFormulas.ForceMxContributionTy((double)ty, (double)l2);
		
		return (float)contributionTy;
	}
	
	public static float ForceMxContributionQy(float H1, float l2){
		double contributionQy;
		
		contributionQy = PylonForcesFormulas.ForceMxContributionQy((double)H1, (double)l2);
		
		return (float)contributionQy;
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
		double contributionTx;
		
		contributionTx = PylonForcesFormulas.ForceMyContributionTx((double)tx, (double)l);
		
		return (float)contributionTx;
	}
	
	
	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
		/*
		 * ##########################################################
		 * ##########################################################
		 * #####												#####
		 * #####				Algebraic Formulas				#####
		 * #####												#####
		 * ##########################################################
		 * ##########################################################
		 */
	
	/**
	 * This method calculates the roots of a polynomial function of degree 3
	 *  in the following form:
	 * 
	 * 			a*x^3 + b*x^2 + c*x^1 + d*x^0
	 * 
	 * The method return all the three roots of the function, real and imaginary.
	 * 
	 * @param a the coefficient of the term of degree 3
	 * @param b the coefficient of the term of degree 2
	 * @param c the coefficient of the term of degree 1
	 * @param d the coefficient of the term of degree 0
	 * @return the list of three roots of the 3rd degree function
	 */
	public static List<Double> rootsOf3rdDegreeFunction(double a, double b, double c, double d)
	{
		return AlgebraicFunctions.rootsOf3rdDegreeFunction(a, b, c, d);
	}
	
	/**
	 * This method calculates the roots of a polynomial function of degree 3
	 *  in the following form:
	 * 
	 * 			a*x^3 + b*x^2 + c*x^1 + d*x^0
	 * 
	 * and return only the real roots of the functions. If there aren't return null.
	 * 
	 * @param a the coefficient of the term of degree 3
	 * @param b the coefficient of the term of degree 2
	 * @param c the coefficient of the term of degree 1
	 * @param d known term
	 * @return the list of three roots of the 3rd degree function
	 */
	public static List<Double> realRootsOf3rdDegreeFunction(double a, double b, double c, double d)
	{
		ArrayList<Double> rootsList = new ArrayList<Double>();
		
		for(Double r : rootsOf3rdDegreeFunction(a, b, c, d)){
			if(r.getY() == 0){
				rootsList.add(r);
			}
		}
		return rootsList;
	}
	
	
	/**
	 * @param a the coefficient of the term of degree 3
	 * @param b the coefficient of the term of degree 2
	 * @param c the coefficient of the term of degree 1
	 * @param d known term
	 * @param m_force the M force of the pylon
	 * @param n_force the N force of the pylon
	 * @param q the known term of the straight line passing through the origin and the point (n_force,m_force)
	 * @return the coordinates of the intersection point nearest the pylon point (n_force,m_force)
	 */
	public static Double realIntersectionBetweenCubicAndLinearFunctions(double a, double b, double c, double d, double m_force, double n_force, double q)
	{	
		double s,m,d1,d2;
		Double intersection = new Double();
		ArrayList<Double> rootsList = new ArrayList<Double>();
		
		if(m_force >= 0){
			s=1;
		}else{
			s=-1;
		}
		
		if(n_force == 0){
			rootsList.add(new Double(0,s*d));
		}else{
			m = m_force/n_force;
			rootsList.addAll(realRootsOf3rdDegreeFunction(s*a, s*b, (s*c)-m, (s*d)-q));
		}
		
		intersection.setLocation(rootsList.get(0).getX(),0);
		
		for(Double r : rootsList){
			d1 = AlgebraicFunctions.getDistanceBetweenTwo1DPoints(n_force, intersection.getX()); 	//Distance between the pylon_x and the savet_intersection_x
			d2 = AlgebraicFunctions.getDistanceBetweenTwo1DPoints(n_force, r.getX());				//Distance between the pylon_x and the current root_x
			
			if((r.getX() >= 0) && (n_force >= 0) &&	(d2 < d1)){
				intersection.setLocation(r.getX(), 0);
			}else if((r.getX() < 0) && (n_force < 0) &&	(d2 < d1)){
				intersection.setLocation(r.getX(), 0);
			}
		}
		
		intersection.setLocation(intersection.getX(), AlgebraicFunctions.get3rdDegreePolynomialFunctionValue(a, b, c, d, intersection.getX()));
		
		return intersection;
	}
	
	
	
	/**
	 * @param p1 first point
	 * @param p2 second point
	 * @return the distance between the two points in two dimensions
	 */
	public static double getDistanceBetweenTwo2DPoints(Double p1, Double p2){
		return AlgebraicFunctions.getDistanceBetweenTwo2DPoints(p1, p2);
	}
	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
			/*
			 * ##########################################################
			 * ##########################################################
			 * #####												#####
			 * #####				M-N Domain Formulas				#####
			 * #####												#####
			 * ##########################################################
			 * ##########################################################
			 */
	
	
	/**
	 * @param o the base point; the cartesian axes origin
	 * @param a the pylon point
	 * @param b the intersection point with the domain
	 * @return the value of the safety factor
	 */
	public static double safetyFactor(Double o, Double a, Double b)
	{	
		return SafetyFactorFormulas.safetyFactor(o, a, b);		
	}
}
