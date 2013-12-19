/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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

public class InstrumentsHydroDataTask implements Runnable{

		private ArrayList<RawData> rawData;
		private InstrumentsData instrumentsData;
		
		
		
		public InstrumentsHydroDataTask(ArrayList<RawData> rawData, InstrumentsData instrumentsData) {
			super();
			this.rawData = rawData;
			this.instrumentsData = instrumentsData;
		}



		@Override
		public void run() {
			calculateInstrumentsData();
		}



		private void calculateInstrumentsData() {
			
			float meanWaterLevel, varianceWaterLevel;
			ArrayList<Float> list = new ArrayList<Float>();
			
			meanWaterLevel = 0;
			varianceWaterLevel = 0;
			
			for(RawData rd : this.rawData)
			{
				
				//Hydrometer operations
				list.add(rd.getHydrometer());
				
			}
			
			//calculation of mean values for all instruments plus sonar statistics
			//Hydrometer
			meanWaterLevel = MathEngine.meanValue(list);
			
			//Calculation of variances
			varianceWaterLevel = MathEngine.variance(list);
			
			//Results are saved into class variables
			//Hydrometer
			this.instrumentsData.setIdro1(meanWaterLevel);
			this.instrumentsData.setIdro2(varianceWaterLevel);			
		}
}
