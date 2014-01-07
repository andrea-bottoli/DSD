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

public class WeightFormulas {
	
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
	/**
	 * @param Ppu: pulvino weight
	 * @param Ptp: trunk of pylon weight
	 * @param Pb: beam weight
	 * @param Ppy: pylon weight
	 * @param Hbeam: beam height
	 * @param Sonar1: SONAR1 value
	 * @return The value of Structure Weight 
	 */
	public static double StackWeight(double Ppu, double Ptp, double Pb, double Ppy, double Hbeam, double Sonar1) {
		
		double stackWeight = 0;
		
		try
		{
			stackWeight = ((2*Ppu + 6*Ptp + 2*Pb) + 6*(Ppy*(Hbeam - Sonar1)));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return stackWeight;
	}

}
