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
