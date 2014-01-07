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

public class PlankWaterForcesTask implements Runnable{
	
	private InstrumentsData instrumentsData;
	private PlankForces plankForces;
	
	public PlankWaterForcesTask(InstrumentsData instrumentsData, PlankForces plankForces)
	{
		this.instrumentsData = instrumentsData;
		this.plankForces = plankForces;
	}
	
	@Override
	public void run()
	{
		CalculatePlankWaterForces();
	}
	
	/**
	 * This method calculates the three components
	 * of water force on the planking: Q Flow rate,
	 * V Water speed, Sw Water Push
	 */
	private void CalculatePlankWaterForces() {
		
		float lFlowRate=0, lWaterSpeed=0, lAs=0, lHs=0, lBs=0, lSwater=0;
		
		/*
		 * Q [m3/s]
		 */
		if(instrumentsData.getIdro1() < ParametersController.getParameter(eParameter.HeightLimitOfTheRiverForParametersa1b1c1).getValue())
		{
			lFlowRate = MathEngine.FlowRate(ParametersController.getParameter(eParameter.CoefficientA1forQhWhenIDRO1lessThanHwater1).getValue(),
											instrumentsData.getIdro1(),
											ParametersController.getParameter(eParameter.CoefficientB1forQhWhenIDRO1lessThanHwater1).getValue(),
											ParametersController.getParameter(eParameter.CoefficientC1forQhWhenIDRO1lessThanHwater1).getValue());
			
		}else if(instrumentsData.getIdro1()<ParametersController.getParameter(eParameter.HeightLimitOfTheRiverForParametersa2b2c2).getValue())
		{
			lFlowRate = MathEngine.FlowRate(ParametersController.getParameter(eParameter.CoefficientA2forQhWhenIDRO1lessThanHwater1).getValue(),
											instrumentsData.getIdro1(),
											ParametersController.getParameter(eParameter.CoefficientB2forQhWhenIDRO1lessThanHwater1).getValue(),
											ParametersController.getParameter(eParameter.CoefficientC2forQhWhenIDRO1lessThanHwater1).getValue());
			
		}else if(instrumentsData.getIdro1() < ParametersController.getParameter(eParameter.MaxHeightLevelOfTheRiverAndLimitForUseParametersa3b3c3).getValue())
		{
			lFlowRate = MathEngine.FlowRate(ParametersController.getParameter(eParameter.CoefficientA3forQhWhenIDRO1lessThanHwater1).getValue(),
											instrumentsData.getIdro1(),
											ParametersController.getParameter(eParameter.CoefficientB3forQhWhenIDRO1lessThanHwater1).getValue(),
											ParametersController.getParameter(eParameter.CoefficientC3forQhWhenIDRO1lessThanHwater1).getValue());
		}
		plankForces.setFlowRate(lFlowRate);
		
		/*
		 * Vwater [m/s]
		 */
		lWaterSpeed = MathEngine.WaterSpeed(ParametersController.getParameter(eParameter.CoefficientAforTheRelationVwaterIDRO1).getValue(),
											instrumentsData.getIdro1(),
											ParametersController.getParameter(eParameter.CoefficientBforTheRelationVwaterIDRO1).getValue(),
											ParametersController.getParameter(eParameter.CoefficientCforTheRelationVwaterIDRO1).getValue());
		
		plankForces.setWaterSpeed(lWaterSpeed);
		
		/*
		 * lHs [m]
		 */
		if(instrumentsData.getSonar1() < ParametersController.getParameter(eParameter.HeightOfTheReferenceOfTheBottomOfTheRiver).getValue())
		{
			lHs=instrumentsData.getIdro1() - ParametersController.getParameter(eParameter.HeightOfTheReferenceOfTheBottomOfTheRiver).getValue();
			if(lHs < 0)
			{
				lHs = ParametersController.getParameter(eParameter.HeightOfTheReferenceOfTheBottomOfTheRiver).getValue();
			}
		}else
		{
			lHs=instrumentsData.getIdro1() - instrumentsData.getSonar1();
			if(lHs < 0)
			{
				lHs = instrumentsData.getSonar1();
			}
		}
		plankForces.setHs(lHs);
		
		/*
		 * D = 0;
		 * Bs = 2*Dpylon
		 */
		lBs = 2*ParametersController.getParameter(eParameter.DiameterOfThePylon).getValue(); // lBs [m]
		plankForces.setBsWithOutDebris(lBs);
		lAs = lBs*lHs; // lAs [m2]
		/*
		 * lSwater [N]
		 */
		lSwater = MathEngine.HydrodynamicThrustWithOutDebris(ParametersController.getParameter(eParameter.DragPlankingCoefficientDequals0).getValue(),
																ParametersController.getParameter(eParameter.WaterDensity).getValue(), lAs, lWaterSpeed);
		
		/*
		 * Transformation from [N] to [kN]
		 */
		lSwater /= 1000;
		plankForces.setHydrodynamicThrustWithOutDebris(lSwater);
		
		/*
		 * D = 1;
		 * Bs = Cspan
		 */
		lBs = ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue(); // lBs [m]
		plankForces.setBsWithDebris(lBs);
		lAs = lBs*lHs; // lAs [m2]
		lSwater = MathEngine.HydrodynamicThrustWithDebris(ParametersController.getParameter(eParameter.DragPlankingCoefficientDequals1).getValue(),
															ParametersController.getParameter(eParameter.WaterDensity).getValue(),
															lAs,
															ParametersController.getParameter(eParameter.AreaReductionForDequals1).getValue(),
															lWaterSpeed);
		
		/*
		 * Transformation from [N] to [kN]
		 */
		lSwater /= 1000;
		plankForces.setHydrodynamicThrustWithDebris(lSwater);
	}
}
