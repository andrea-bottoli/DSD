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
