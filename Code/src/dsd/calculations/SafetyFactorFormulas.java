package dsd.calculations;

import java.awt.geom.Point2D.Double;

public class SafetyFactorFormulas {

	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####			Safety Factor Formulas				#####
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
	protected static double safetyFactor(Double o, Double a, Double b)
	{	
		double oa, ob,sf;
		
		oa = AlgebraicFunctions.getDistanceBetweenTwo2DPoints(o, a);
		ob = AlgebraicFunctions.getDistanceBetweenTwo2DPoints(o, b);
		
		sf = ob/oa;
		
		return sf;		
	}
}
