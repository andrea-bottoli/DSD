/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dsd.controller.mathEngineTask;

import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import dsd.calculations.MathEngine;
import dsd.model.calculation.Pylon;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;
import dsd.model.calculation.WorstCase;

public class WorstCasesTask implements Runnable{
	
	private boolean traffic;
	private boolean debris;
	private WorstCase worstCase;
	private PylonForces pylonForces;
	
	private long timeStamp;
	
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
	
	
	
	
	public WorstCasesTask(PylonForces pylonForces, WorstCase worstCase, long timeStamp) {
		super();
		this.traffic = worstCase.getTraffic();
		this.debris = worstCase.getDebris();
		this.worstCase = worstCase;
		this.pylonForces = pylonForces;
		
		this.timeStamp = timeStamp;
		
		this.pylonList = new ArrayList<Pylon>();
		
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
		float sf;
		ArrayList<PylonCombination> pylonCombinationList;
		
		
		pylonCombinationList = this.pylonForces.getPylonComboList(this.traffic, this.debris);
		
		for(PylonCombination pc : pylonCombinationList)
		{
			index = 0;
			
			for(Pylon p : pc.getPylonList())
			{
				sf = calculateSingleSafetyFactor(p);
				p.setSafetyFactor(sf);
				
				if(p.getSafetyFactor() < this.minList.get(index))
				{
					setPylon(index, p);
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
		double cs;
		
		b = MathEngine.realIntersectionBetweenCubicAndLinearFunctions(mnDomainFunctionA, mnDomainFunctionB, mnDomainFunctionC, mnDomainFunctionD, p.getM(), p.getN(), 0);
		cs = MathEngine.safetyFactor(o, a, b);
		
		return (float)cs;
	}
	
	private void setPylon(int index, Pylon p)
	{
		if(this.pylonList.isEmpty()){
			this.pylonList.add(index, p);
		}else if((index +1) > this.pylonList.size()){
			this.pylonList.add(index, p);
		}else{
			this.pylonList.set(index, p);
		}
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
			this.worstCase.setTimeStamp(this.timeStamp);
			
			index++;
		}
	}
}

