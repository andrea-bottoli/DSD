/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
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

import java.util.ArrayList;

import dsd.calculations.MathEngine;
import dsd.model.RawData;
import dsd.model.calculation.InstrumentsData;

public class InstrumentsAnemometerDataTask implements Runnable{

	private ArrayList<RawData> rawData;
	private InstrumentsData instrumentsData;
	
	
	
	public InstrumentsAnemometerDataTask(ArrayList<RawData> rawData, InstrumentsData instrumentsData) {
		super();
		this.rawData = rawData;
		this.instrumentsData = instrumentsData;
	}



	@Override
	public void run() {
		calculateInstrumentsData();
	}



	/**
	 * This method calculates the instrument values of anemometer
	 * ANE1, ANE2, ANE3, ANE4
	 */
	private void calculateInstrumentsData() {
		
		float meanWindSpeed, maxWindSpeed, meanWindDirection,maxWindDirection;
		ArrayList<Float> list1 = new ArrayList<Float>();
		ArrayList<Float> list2 = new ArrayList<Float>();
		
		meanWindSpeed=0;
		meanWindDirection=0;
		maxWindSpeed=0;
		maxWindDirection=0;
		
		for(RawData rd : this.rawData)
		{
			
			//Anemometer operations
			list1.add(rd.getWindSpeed());
			list2.add(rd.getWindDirection());
			
			if(rd.getWindSpeed() > maxWindSpeed)
			{
				maxWindSpeed = rd.getWindSpeed();
				maxWindDirection = rd.getWindDirection();
			}
		}
		
		//calculation of mean values for all instruments plus sonar statistics
		//Anemometer
		meanWindSpeed = MathEngine.meanValue(list1);
		meanWindDirection = MathEngine.meanValue(list2);;
		
		
		//Results are saved into class variables
		//Anemometer
		this.instrumentsData.setAne1(meanWindSpeed);
		this.instrumentsData.setAne2(maxWindSpeed);
		this.instrumentsData.setAne3(meanWindDirection);
		this.instrumentsData.setAne4(maxWindDirection);		
	}
}
