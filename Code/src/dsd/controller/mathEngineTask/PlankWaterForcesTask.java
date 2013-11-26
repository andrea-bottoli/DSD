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
		
		lWaterSpeed = MathEngine.WaterSpeed(ParametersController.getParameter(eParameter.CoefficientAforTheRelationVwaterIDRO1).getValue(),
											instrumentsData.getIdro1(),
											ParametersController.getParameter(eParameter.CoefficientBforTheRelationVwaterIDRO1).getValue(),
											ParametersController.getParameter(eParameter.CoefficientCforTheRelationVwaterIDRO1).getValue());
		
		plankForces.setWaterSpeed(lWaterSpeed);
		
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
		
		lBs = ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		plankForces.setBsWithOutDebris(lBs);
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithOutDebris(ParametersController.getParameter(eParameter.DragPlankingCoefficientDequals0).getValue(),
																ParametersController.getParameter(eParameter.WaterDensity).getValue(), lAs, lWaterSpeed);
		plankForces.setHydrodynamicThrustWithOutDebris(lSwater);
		
		lBs = 2*ParametersController.getParameter(eParameter.DiameterOfThePylon).getValue();
		plankForces.setBsWithDebris(lBs);
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithDebris(ParametersController.getParameter(eParameter.DragPlankingCoefficientDequals1).getValue(),
															ParametersController.getParameter(eParameter.WaterDensity).getValue(),
															lAs,
															ParametersController.getParameter(eParameter.AreaReductionForDequals1).getValue(),
															lWaterSpeed);
		
		plankForces.setHydrodynamicThrustWithDebris(lSwater);
	}
}
