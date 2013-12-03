package dsd.controller.mathEngineTask;

import java.util.ArrayList;
import dsd.model.calculation.Pylon;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;
import dsd.model.calculation.WorstCase;

public class WorstCasesTask implements Runnable{
	
	private boolean traffic;
	private boolean debris;
	private WorstCase worstCase;
	private PylonForces pylonForces;
	
	private ArrayList<Pylon> pylonList;
	private ArrayList<Float> minList;
	private ArrayList<Integer> comboNumberList;
	
	
	
	public WorstCasesTask(PylonForces pylonForces, WorstCase worstCase) {
		super();
		this.traffic = worstCase.getTraffic();
		this.debris = worstCase.getDebris();
		this.worstCase = worstCase;
		this.pylonForces = pylonForces;
		
		this.minList = new ArrayList<Float>();
		this.minList.add(new Float(999999999));
		this.minList.add(new Float(999999999));
		this.minList.add(new Float(999999999));
		
		this.comboNumberList = new ArrayList<Integer>();
		this.comboNumberList.add(new Integer(0));
		this.comboNumberList.add(new Integer(0));
		this.comboNumberList.add(new Integer(0));
		
	}


	@Override
	public void run() {
		calculateWorstCase();
	}
	
	
	
	/**
	 * This method calculates teh worst case for the load combination type
	 * that it has setted into variables traffic and debris
	 */
	private void calculateWorstCase()
	{
		int index;
		ArrayList<PylonCombination> pylonCombinationList;
		
		
		pylonCombinationList = this.pylonForces.getPylonComboList(this.traffic, this.debris);
		
		for(PylonCombination pc : pylonCombinationList)
		{
			index = 0;
			
			for(Pylon p : pc.getPylonList())
			{
				/*
				 * ##########################################
				 * #										#
				 * #	CALCULATE THE SAFETY FACTOR			#
				 * #		INTO THE M-N DOMAIN !!!			#
				 * #										#
				 * ##########################################
				 */
				
				if(p.getSafetyFactor() < this.minList.get(index))
				{
					this.pylonList.set(index, p);
					this.minList.set(index, p.getSafetyFactor());
					this.comboNumberList.set(index, pc.getCombination().getCombinationNumber());
				}
				
				index++;
			}
		}
		
		saveValues();
	}
	
	
	
	private void saveValues()
	{
		int index = 0;
		
		for(Pylon p : this.pylonList)
		{
			this.worstCase.setPylon(p);
			this.worstCase.setSafetyFactor(this.minList.get(index), p.getPylonNumber());
			this.worstCase.setPylonComboNumber(this.comboNumberList.get(index), p.getPylonNumber());
			
			index++;
		}
	}
}

