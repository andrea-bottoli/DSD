package dsd.controller.mathEngineTask;

import java.util.ArrayList;
import java.util.ListIterator;

import dsd.model.calculation.Pylon;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;
import dsd.model.calculation.SafetyFactor;

public class SafetyFactorTask implements Runnable{

	private PylonForces mnPylonsForces;
	private PylonForces moPylonsForces;
	private SafetyFactor safetyFactor;
	
	private ListIterator<PylonCombination> mnIterator;
	private ListIterator<PylonCombination> moIterator;
	
	private float maxN;
	private float maxM;
	private int stressedPylon;
	

	public SafetyFactorTask(PylonForces mnPylonsForces, PylonForces moPylonsForces,	SafetyFactor safetyFactor) {
		this.mnPylonsForces = mnPylonsForces;
		this.moPylonsForces = moPylonsForces;
		this.safetyFactor = safetyFactor;
		this.maxN = 0;
		this.maxM = 0;
		this.stressedPylon = 0;
		
		this.mnIterator = this.mnPylonsForces.getPylonComboList().listIterator();
		this.moIterator = this.moPylonsForces.getPylonComboList().listIterator();
	}

	@Override
	public void run() {
		RiskFactor();
	}
	
	/**
	 * This method cleans the work variables
	 */
	private void cleanVariables()
	{
		this.maxN = 0;
		this.maxM = 0;
		this.stressedPylon = 0;
	}
	
	private void RiskFactor(){
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
		
		PylonCombination pcMn, pcMo;
		ArrayList<Pylon> pylonList = new ArrayList<Pylon>();
		
		iteratorsAtTheBeginnig();
		
		while(this.mnIterator.hasNext() && this.moIterator.hasNext())
		{
			pcMn = this.mnIterator.next();
			pcMo = this.moIterator.next();
			
			if((pcMn.getCombination().getTraffic() == traffic) && (pcMn.getCombination().getDebris() == debris) &&
				(pcMo.getCombination().getTraffic() == traffic) && (pcMo.getCombination().getDebris() == debris) &&
				(pcMn.getCombination().getCombinationNumber() == pcMn.getCombination().getCombinationNumber()))
			{
				pylonList.add(pcMn.getPylon3());
				pylonList.add(pcMo.getPylon3());
				pylonList.add(pcMn.getPylon2());
				pylonList.add(pcMo.getPylon2());
				pylonList.add(pcMn.getPylon1());
				pylonList.add(pcMo.getPylon1());
				
				for(Pylon p : pylonList)
				{
					//TO-DO
					/*
					 * EVALUTAIONS OF THE RISK FACTOR
					 * AND
					 * DETERMINES THE MORE STRESSED PYLON
					 */
				}
				
				pylonList.clear();
			}
			
		}
		
		
	}
	
	private void iteratorsAtTheBeginnig(){
		while(this.mnIterator.hasPrevious() || this.moIterator.hasPrevious())
		{
			if(this.mnIterator.hasPrevious())
			{
				this.mnIterator.previous();
			}
			
			if(this.moIterator.hasPrevious())
			{
				this.moIterator.previous();
			}
		}
	}

}
