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
