package dsd.controller.mathEngineTask;

import java.util.ArrayList;
import java.util.ListIterator;

import dsd.model.calculation.Pylon;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;
import dsd.model.calculation.SafetyFactor;
import dsd.model.calculation.WorstCase;

public class SafetyFactorTask implements Runnable{

	private WorstCase worstCase00;
	private WorstCase worstCase01;
	private WorstCase worstCase10;
	private WorstCase worstCase11;
	private SafetyFactor safetyFactor;
	

	public SafetyFactorTask(WorstCase worstCase00, WorstCase worstCase01, WorstCase worstCase10, WorstCase worstCase11,	SafetyFactor safetyFactor) {
		this.worstCase00 = worstCase00;
		this.worstCase01 = worstCase01;
		this.worstCase10 = worstCase10;
		this.worstCase11 = worstCase11;
		this.safetyFactor = safetyFactor;

	}

	@Override
	public void run() {
		SafetyFactor();
	}
	
	/**
	 * This method cleans the work variables
	 */
	private void cleanVariables()
	{
		
	}
	
	private void SafetyFactor(){
		Boolean t;
		Boolean d;
		
		//COMBINATION T=0 D=0
		cleanVariables();
		t = Boolean.FALSE;
		d = Boolean.FALSE;
		CalculatedRiskFactor(t, d);
		
		//COMBINATION T=0 D=1
		cleanVariables();
		t = Boolean.FALSE;
		d = Boolean.TRUE;
		CalculatedRiskFactor(t, d);
		
		//COMBINATION T=1 D=0
		cleanVariables();
		t = Boolean.TRUE;
		d = Boolean.FALSE;
		CalculatedRiskFactor(t, d);
		
		//COMBINATION T=1 D=1
		cleanVariables();
		t = Boolean.TRUE;
		d = Boolean.TRUE;
		CalculatedRiskFactor(t, d);
	}
	
	
	/**
	 * This method calculates the risk factor and
	 * detect also the more stressed pylon that generate
	 * the risk factor for the combinations that have
	 * D & T as the inputs
	 */
	private void CalculatedRiskFactor(Boolean traffic, Boolean debris) {
		
		
		
		
		
		
	}
	
//	private void iteratorsAtTheBeginnig(){
//		while(this.mnIterator.hasPrevious() || this.moIterator.hasPrevious())
//		{
//			if(this.mnIterator.hasPrevious())
//			{
//				this.mnIterator.previous();
//			}
//			
//			if(this.moIterator.hasPrevious())
//			{
//				this.moIterator.previous();
//			}
//		}
//	}

}
