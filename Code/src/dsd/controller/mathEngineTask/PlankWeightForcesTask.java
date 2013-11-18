package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.controller.CalculationsController;

public class PlankWeightForcesTask implements Runnable{

	private CalculationsController calculationControl = null;
	
	public PlankWeightForcesTask(CalculationsController calContr)
	{
		this.calculationControl = calContr;
	}
	
	@Override
	public void run() {
		CalculatePlankWeightForces();
	}
	
	/*
	 * This method calculates the component
	 * of weight of the planking
	 */
	/**
	 * This method calculates the component
	 * of weight of the planking: PPplank
	 */
	private void CalculatePlankWeightForces() {
		
		float lPstack;
		
		/*##############################
		 *CHANGE 1 WITH Ppu, 2 WITH Ptp, 3 WITH Ppy, 4 WITH Hbeam
		 *PARAMETERS IS MISSING
		 *############################# 
		 */
		lPstack=MathEngine.StackWeight(1, 2, 3, 4, 5, this.calculationControl.getInstrumentsData().getSonar1());
		
		/*##############################
		 *CHANGE 1 WITH Pplank
		 *PARAMETERS IS MISSING
		 *############################# 
		 */
		this.calculationControl.getPlankForces().setPlankWeight(1);
		this.calculationControl.getPlankForces().setStackWeight(lPstack);
	}
}
