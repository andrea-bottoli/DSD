package dsd.controller.mathEngineTask;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import dsd.calculations.MathEngine;
import dsd.model.WorstCase;
import dsd.model.calculation.Pylon;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;

public class WorstCasesTask implements Runnable{
	
	private boolean traffic;
	private boolean debris;
	private WorstCase worstCase;
	private PylonForces pylonForces;
	
	private ArrayList<Pylon> pylonList;
	private ArrayList<Float> minList;
	private ArrayList<Integer> comboNumberList;
	
	/*
	 * Coefficients of the cubic function of the M-N domain
	 */
	final double mnDomainFunctionA = 1.84 * Math.pow(10, -10);
	final double mnDomainFunctionB = -2.87 * Math.pow(10, -5);
	final double mnDomainFunctionC = 5.93 * Math.pow(10, -1);
	final double mnDomainFunctionD = 1.33 * Math.pow(10, 3);
	
	
	
	
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
				p.setSafetyFactor(this.calculateSingleSafetyFactor(p));
				
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
	
	
	
	/**
	 * @param p the pylon of which calculate the safety factor
	 * @return the safety factor for the specific pylon p in input
	 */
	private float calculateSingleSafetyFactor(Pylon p)
	{
		Double o = new Double(0,0); 
		Double a = new Double(p.getN(), p.getM());
		Double b;
		
		b = MathEngine.realIntersectionBetweenCubicAndLinearFunctions(mnDomainFunctionA, mnDomainFunctionB, mnDomainFunctionC, mnDomainFunctionD, p.getM(), p.getN(), 0);
		
		return ((float)MathEngine.safetyFactor(o, a, b));
	}
	
	
	
	/**
	 * this method save the values into the correct variable
	 */
	private void saveValues()
	{
		int index = 0;
		
		for(Pylon p : this.pylonList)
		{
			this.worstCase.setPylon(p);
			this.worstCase.setSafetyFactor(p.getSafetyFactor(), p.getPylonNumber());
			this.worstCase.setPylonComboNumber(this.comboNumberList.get(index), p.getPylonNumber());
			
			index++;
		}
	}
}

