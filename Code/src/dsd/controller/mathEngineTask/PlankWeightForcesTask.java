package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.PlankForces;

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
		
		/*##############################
		 *CHANGE 1 WITH Ppu, 2 WITH Ptp, 3 WITH Ppy, 4 WITH Hbeam
		 *PARAMETERS IS MISSING
		 *############################# 
		 */
		lPstack=MathEngine.StackWeight(1, 2, 3, 4, 5, instrumentsData.getSonar1());
		
		/*##############################
		 *CHANGE 1 WITH Pplank
		 *PARAMETERS IS MISSING
		 *############################# 
		 */
		plankForces.setPlankWeight(1);
		plankForces.setStackWeight(lPstack);
			
	}
	
}
