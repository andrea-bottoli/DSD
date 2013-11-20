package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.PlankForces;

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
		
		/*##############################
		 *CHANGE 5 WITH ALPHA
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lEffectiveWindSpeed = MathEngine.EffectiveWindSpeed(instrumentsData.getAne2(), instrumentsData.getAne4(), 5);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Aplank
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnPlank = MathEngine.WindPushOnPlank(1, 2, 3, lEffectiveWindSpeed);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Beta1, 4 with Atraf
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnA1traf = MathEngine.WindPushOnA1TrafficCombination(1, 2, 3, 4, lEffectiveWindSpeed);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Beta1, 4 with Atraf
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnA2traf = MathEngine.WindPushOnA2TrafficCombination(1, 2, 3, 4, lEffectiveWindSpeed);
		
		/*##############################
		 *CHANGE 1 WITH Cdwi, 2 with RhoAir, 3 with Beta2, 4 with Atraf
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWindPushOnA3traf = MathEngine.WindPushOnA3TrafficCombination(1, 2, 3, 4, lEffectiveWindSpeed);
		
		plankForces.setWindPushOnPlank(lWindPushOnPlank);
		plankForces.setWindPushOnA1TrafficCombination(lWindPushOnA1traf);
		plankForces.setWindPushOnA2TrafficCombination(lWindPushOnA2traf);
		plankForces.setWindPushOnA3TrafficCombination(lWindPushOnA3traf);
			
	}

}
