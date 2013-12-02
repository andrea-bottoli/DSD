package dsd.controller.mathEngineTask;

import java.util.ArrayList;

import dsd.calculations.MathEngine;
import dsd.model.RawData;
import dsd.model.calculation.InstrumentsData;
import dsd.model.enums.eSonarType;

public class InstrumentsSonarDataTask implements Runnable{

	private ArrayList<RawData> rawData;
	private InstrumentsData instrumentsData;
	
	
	
	public InstrumentsSonarDataTask(ArrayList<RawData> rawData, InstrumentsData instrumentsData) {
		super();
		this.rawData = rawData;
		this.instrumentsData = instrumentsData;
	}



	@Override
	public void run() {
		calculateInstrumentsData();
	}



	private void calculateInstrumentsData() {
		
		float meanRiverBottomLevel, varianceRiverBottomLevel;
		float percUtilizedData12OverWholeSample, percWrongData3OverWholeSample, percOutWaterData4OverWholeSample;
		float percErrorData5OverWholeSample, percUncertainData2Over12Sample;
		float numbCertainValue, numbUncertainValue, numbWrongValue, numbOutOfWaterValue, numbErrorValue;
		ArrayList<Float> meanList = new ArrayList<Float>();
		
		meanRiverBottomLevel = 0;
		varianceRiverBottomLevel = 0;
		percUtilizedData12OverWholeSample = 0;
		percWrongData3OverWholeSample = 0;
		percOutWaterData4OverWholeSample = 0;
		percErrorData5OverWholeSample = 0;
		percUncertainData2Over12Sample = 0;
		numbCertainValue=0;
		numbUncertainValue=0;
		numbWrongValue=0;
		numbOutOfWaterValue=0;
		numbErrorValue=0;
		
		for(RawData rd : this.rawData)
		{
			//Sonar operations
			if(rd.getSonarType().equals(eSonarType.CorrectData))
			{
				meanList.add(rd.getSonar());
				numbCertainValue++;
			}else if(rd.getSonarType().equals(eSonarType.UncertainData))
			{
				meanList.add(rd.getSonar());
				numbUncertainValue++;
			}else if(rd.getSonarType().equals(eSonarType.WrongData))
			{
				numbWrongValue++;
			}else if(rd.getSonarType().equals(eSonarType.SonarOutOfWaterData))
			{
				numbOutOfWaterValue++;
			}else if(rd.getSonarType().equals(eSonarType.ErrorData))
			{
				numbErrorValue++;
			}
		}
				
		
		//calculation of mean values for all instruments plus sonar statistics
		//SONAR
		meanRiverBottomLevel = MathEngine.meanValue(meanList);
		
		percUtilizedData12OverWholeSample = (numbCertainValue+numbUncertainValue)/this.rawData.size();
		percWrongData3OverWholeSample = numbWrongValue/this.rawData.size();;
		percOutWaterData4OverWholeSample = numbOutOfWaterValue/this.rawData.size();;
		percErrorData5OverWholeSample = numbErrorValue/this.rawData.size();;
		percUncertainData2Over12Sample = numbUncertainValue/(numbCertainValue + numbUncertainValue);
		
		varianceRiverBottomLevel = MathEngine.variance(meanList);
		
		//Results are saved into class variables		
		//Sonar
		this.instrumentsData.setSonar1(meanRiverBottomLevel);
		this.instrumentsData.setSonar2(varianceRiverBottomLevel);
		this.instrumentsData.setSonar3(percUtilizedData12OverWholeSample);
		this.instrumentsData.setSonar4(percWrongData3OverWholeSample);
		this.instrumentsData.setSonar5(percOutWaterData4OverWholeSample);
		this.instrumentsData.setSonar6(percErrorData5OverWholeSample);
		this.instrumentsData.setSonar7(percUncertainData2Over12Sample);
		
	}

}
