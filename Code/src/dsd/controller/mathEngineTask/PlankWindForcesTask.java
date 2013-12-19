/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.controller.ParametersController;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.PlankForces;
import dsd.model.enums.eParameter;

public class PlankWindForcesTask implements Runnable{
	
	private InstrumentsData instrumentsData;
	private PlankForces plankForces;
	
	public PlankWindForcesTask(InstrumentsData instrumentsData, PlankForces plankForces)
	{
		this.instrumentsData = instrumentsData;
		this.plankForces = plankForces;
	}
	
	@Override
	public void run()
	{
		CalculatePlankWindForces();
	}
	
	/**
	 * This method calculates the four components
	 * of wind force on the planking: Svplank,
	 * Sva1traf, Sva2traf, Sva3traf
	 */
	private void CalculatePlankWindForces()
	{
		
		float lEffectiveWindSpeed;
		float lWindPushOnPlank, lWindPushOnA1traf, lWindPushOnA2traf, lWindPushOnA3traf;
		
		/*
		 * Veff [m/s]
		 */
		lEffectiveWindSpeed = MathEngine.EffectiveWindSpeed(this.instrumentsData.getAne2(),
															this.instrumentsData.getAne4(),
															ParametersController.getParameter(eParameter.PlanimetricAnticlockwiseInclinationOfTheBridgeFormTheNorth).getValue());
		
		/*
		 * Svimp [N]
		 */
		lWindPushOnPlank = MathEngine.WindPushOnPlank(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
														ParametersController.getParameter(eParameter.AirDensity).getValue(),
														ParametersController.getParameter(eParameter.PlankingAreaExposedToTheWindPressure).getValue(), lEffectiveWindSpeed);
		
		/*
		 * SvA1traf [N]
		 */
		lWindPushOnA1traf = MathEngine.WindPushOnA1TrafficCombination(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
																		ParametersController.getParameter(eParameter.AirDensity).getValue(),
																		ParametersController.getParameter(eParameter.CoefficientOfReductionForA1AndA2TrafficScenarios).getValue(),
																		ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure).getValue(),
																		lEffectiveWindSpeed);
		
		/*
		 * SvA2traf [N]
		 */
		lWindPushOnA2traf = MathEngine.WindPushOnA2TrafficCombination(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
																		ParametersController.getParameter(eParameter.AirDensity).getValue(),
																		ParametersController.getParameter(eParameter.CoefficientOfReductionForA1AndA2TrafficScenarios).getValue(),
																		ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure).getValue(),
																		lEffectiveWindSpeed);
		
		/*
		 * SvA3traf [N]
		 */
		lWindPushOnA3traf = MathEngine.WindPushOnA3TrafficCombination(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
																		ParametersController.getParameter(eParameter.AirDensity).getValue(),
																		ParametersController.getParameter(eParameter.CoefficientOfReductionForA3TrafficScenario).getValue(),
																		ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure).getValue(),
																		lEffectiveWindSpeed);
		
		/*
		 * Transformation of the forces from [N] to [kN]
		 */
		lWindPushOnPlank /= 1000;
		lWindPushOnA1traf /= 1000;
		lWindPushOnA2traf /= 1000;
		lWindPushOnA3traf /= 1000;
		
		this.plankForces.setWindPushOnPlank(lWindPushOnPlank);
		this.plankForces.setWindPushOnA1TrafficCombination(lWindPushOnA1traf);
		this.plankForces.setWindPushOnA2TrafficCombination(lWindPushOnA2traf);
		this.plankForces.setWindPushOnA3TrafficCombination(lWindPushOnA3traf);
	}

}
