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
		
		lEffectiveWindSpeed = MathEngine.EffectiveWindSpeed(instrumentsData.getAne2(), instrumentsData.getAne4(), ParametersController.getParameter(eParameter.PlanimetricAnticlockwiseInclinationOfTheBridgeFormTheNorth).getValue());
		
		lWindPushOnPlank = MathEngine.WindPushOnPlank(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
														ParametersController.getParameter(eParameter.AirDensity).getValue(),
														ParametersController.getParameter(eParameter.PlankingAreaExposedToTheWindPressure).getValue(), lEffectiveWindSpeed);
		
		lWindPushOnA1traf = MathEngine.WindPushOnA1TrafficCombination(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
																		ParametersController.getParameter(eParameter.AirDensity).getValue(),
																		ParametersController.getParameter(eParameter.CoefficientOfReductionForA1AndA2TrafficScenarios).getValue(),
																		ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure).getValue(),
																		lEffectiveWindSpeed);
		
		lWindPushOnA2traf = MathEngine.WindPushOnA2TrafficCombination(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
																		ParametersController.getParameter(eParameter.AirDensity).getValue(),
																		ParametersController.getParameter(eParameter.CoefficientOfReductionForA1AndA2TrafficScenarios).getValue(),
																		ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure).getValue(),
																		lEffectiveWindSpeed);
		
		lWindPushOnA3traf = MathEngine.WindPushOnA3TrafficCombination(ParametersController.getParameter(eParameter.DragPlankingCoefficient).getValue(),
																		ParametersController.getParameter(eParameter.AirDensity).getValue(),
																		ParametersController.getParameter(eParameter.CoefficientOfReductionForA3TrafficScenario).getValue(),
																		ParametersController.getParameter(eParameter.SurfaceOfTrafficExposedToTheWindPressure).getValue(),
																		lEffectiveWindSpeed);
		
		plankForces.setWindPushOnPlank(lWindPushOnPlank);
		plankForces.setWindPushOnA1TrafficCombination(lWindPushOnA1traf);
		plankForces.setWindPushOnA2TrafficCombination(lWindPushOnA2traf);
		plankForces.setWindPushOnA3TrafficCombination(lWindPushOnA3traf);
			
	}

}
