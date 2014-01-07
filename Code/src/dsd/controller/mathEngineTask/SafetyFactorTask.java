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

import dsd.model.WorstPylonCase;
import dsd.model.calculation.SafetyFactor;
import dsd.model.calculation.WorstCase;

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
