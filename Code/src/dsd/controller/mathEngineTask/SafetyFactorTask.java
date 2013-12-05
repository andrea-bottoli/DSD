package dsd.controller.mathEngineTask;

import dsd.model.calculation.SafetyFactor;
import dsd.model.calculation.WorstCase;
import dsd.model.calculation.WorstPylonCase;

public class SafetyFactorTask implements Runnable{

	private WorstCase worstCase;
	private SafetyFactor safetyFactor;
	

	public SafetyFactorTask(WorstCase worstCase, SafetyFactor safetyFactor) {
		this.worstCase = worstCase;
		this.safetyFactor = safetyFactor;

	}

	@Override
	public void run() {
		SafetyFactor();
	}
	
	
	/**
	 * This method calculates the min safety factor for a worst case
	 */
	private void SafetyFactor(){
		
		float min = (float)(0.9*Math.pow(10, 20));
		
		for(WorstPylonCase wpc : worstCase.getWorstList())
		{
			if(wpc.getSafetyFactor() < min)
			{
				min = wpc.getSafetyFactor();
			}
		}
		
		saveValue(min);
	}
	
	
	/**
	 * This method saves the value into the safety factor variable.
	 * 
	 * @param min the minimun value of safety factor to be saved into the variable
	 */
	private void saveValue(float min) {
		this.safetyFactor.getSpecificFactorCombo(worstCase.getTraffic(), worstCase.getDebris()).setValue(min);
	}
}
