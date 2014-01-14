/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
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
	 *  Formula: 	Veffwind = abs{ [ANE2]*sin([ANE4]+ALPHA) }
	 *  
	 *  page 6 of Inputs_Conversion_&_Formulas_Calculation
	 *  
	 *  This formula calculates the effective Wind speed
	 */
	/**
	 * 
	 * @param Ane2: maximum wind speed
	 * @param Ane4: wind direction related to the max wind speed
	 * @param Alpha: parameter of bridge angle respects to the north
	 * @return The value represents the Effective Wind Speed
	 */
	public static double EffectiveWindSpeed(double Ane2, double Ane4, double Alpha)
	{
		double effectiveWindSpeed = 0;
		
		try
		{
			effectiveWindSpeed = Math.abs((Ane2 * Math.sin((Math.toRadians(Ane4 + Alpha)))));
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
	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Aplank: planking area on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the planking
	 */
	public static double WindPushOnPlank(double Cdwi, double RhoAir, double Aplank, double Veffwind)
	{
		double windPushOnPlank = 0;
		
		try
		{
			windPushOnPlank = 0.5 * Cdwi * RhoAir * Aplank * Math.pow(Veffwind,2);
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
	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Beta1: reduction area parameter
	 * @param Atraf: traffic area/surface on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the A1 traffic combination
	 */
	public static double WindPushOnA1TrafficCombination(double Cdwi, double RhoAir, double Beta1, double Atraf, double Veffwind)
	{
		double windPushOnA1TrafficCombination = 0;
		
		try
		{
			windPushOnA1TrafficCombination = 0.5 * Cdwi * RhoAir * (Beta1*Atraf) * Math.pow(Veffwind,2);
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
	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Beta1: reduction area parameter
	 * @param Atraf: traffic area/surface on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the A2 traffic combination
	 */
	public static double WindPushOnA2TrafficCombination(double Cdwi, double RhoAir, double Beta1, double Atraf, double Veffwind)
	{
		double windPushOnA2TrafficCombination = 0;
		
		try
		{
			windPushOnA2TrafficCombination = 0.5 * Cdwi * RhoAir * (Beta1*Atraf) * Math.pow(Veffwind,2);
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
	/**
	 * @param Cdwi: "Drag" parameter of dynamic push
	 * @param RhoAir: parameter of air density
	 * @param Beta1: reduction area parameter
	 * @param Atraf: traffic area/surface on which the wind can push
	 * @param Veffwind: the effective wind speed
	 * @return The value of wind push on the A3 traffic combination
	 */
	public static double WindPushOnA3TrafficCombination(double Cdwi, double RhoAir, double Beta2, double Atraf, double Veffwind)
	{
		double windPushOnA3TrafficCombination = 0;
		
		try
		{
			windPushOnA3TrafficCombination = 0.5 * Cdwi * RhoAir * (Beta2*Atraf) * Math.pow(Veffwind,2);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return windPushOnA3TrafficCombination;
	}

}
