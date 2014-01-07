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
