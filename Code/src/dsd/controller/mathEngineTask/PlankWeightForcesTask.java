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
package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.controller.ParametersController;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.PlankForces;
import dsd.model.enums.eParameter;

public class PlankWeightForcesTask implements Runnable{

	private InstrumentsData instrumentsData;
	private PlankForces plankForces;
	
	public PlankWeightForcesTask(InstrumentsData instrumentsData, PlankForces plankForces)
	{
		this.instrumentsData = instrumentsData;
		this.plankForces = plankForces;
	}
	
	@Override
	public void run()
	{
		CalculatePlankWeightForces();
	}
	
	/**
	 * This method calculates the component
	 * of weight of the planking: PPplank
	 */
	private void CalculatePlankWeightForces()
	{
		float lPstack;
		
		/*
		 * lPstack [kN]
		 */
		lPstack=MathEngine.StackWeight(ParametersController.getParameter(eParameter.WeightOfSinglePulvino).getValue(),
										ParametersController.getParameter(eParameter.WeightOfTheTrunkOfPylon).getValue(),
										ParametersController.getParameter(eParameter.WeightOfTheSingleBeam).getValue(),
										ParametersController.getParameter(eParameter.WeightPerMeterOfPylon).getValue(),
										ParametersController.getParameter(eParameter.HeightOfTheLowerBeam).getValue(),
										instrumentsData.getSonar1());
		
		plankForces.setPlankWeight(ParametersController.getParameter(eParameter.PlankWeightOnTheStack).getValue());
		plankForces.setStackWeight(lPstack);
			
	}
	
}
