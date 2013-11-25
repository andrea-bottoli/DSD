package dsd.model.calculation;

import java.util.Date;

public class InstrumentsData {
	
	private float ane1;		//mean wind speed
	private float ane2;		//max wind speed
	private float ane3;		//mean wind direction
	private float ane4;		//direction of the max wind speed
	private float idro1;	//mean water level
	private float idro2;	//variance of water level
	private float sonar1; 	//mean value of river bed height with data of type 1+2
	private float sonar2; 	//variance of sample data with data of type 1+2
	private float sonar3; 	//% of data of type 1+2 utilized over the whole sample.
	private float sonar4; 	//% of wrong data of type 3 that there are in the whole sample.
	private float sonar5; 	//% of out of water data of type 4 that there are in the whole sample.
	private float sonar6; 	//% of error data of type 5 that there are in the whole sample.
	private float sonar7; 	//% of uncertain data of type 2 utilized over the 1+2 sample.
	private long timestamp;	//timestamp of this row data
	
	/**
	 * @return the ANE1: Mean value of Wind Speed
	 */
	public float getAne1() {
		return ane1;
	}
	/**
	 * @param meanWindSpeed the ANE1 to set
	 */
	public void setAne1(float meanWindSpeed) {
		this.ane1 = meanWindSpeed;
	}
	/**
	 * @return the ANE2: Max value of Wind Speed
	 */
	public float getAne2() {
		return ane2;
	}
	/**
	 * @param maxWindSpeed the ANE2 to set
	 */
	public void setAne2(float maxWindSpeed) {
		this.ane2 = maxWindSpeed;
	}
	/**
	 * @return the ANE3: Mean value of Wind Direction
	 */
	public float getAne3() {
		return ane3;
	}
	/**
	 * @param meanWindDirection the ANE3 to set
	 */
	public void setAne3(float meanWindDirection) {
		this.ane3 = meanWindDirection;
	}
	/**
	 * @return the ANE34: Max value of Wind Direction
	 */
	public float getAne4() {
		return ane4;
	}
	/**
	 * @param maxWindDirection the ANE4 to set
	 */
	public void setAne4(float maxWindDirection) {
		this.ane4 = maxWindDirection;
	}
	/**
	 * @return the IDRO1: Mean value of Water Level
	 */
	public float getIdro1() {
		return idro1;
	}
	/**
	 * @param meanWaterLevel the IDRO1 to set
	 */
	public void setIdro1(float meanWaterLevel) {
		this.idro1 = meanWaterLevel;
	}
	/**
	 * @return the IDRO2: Variance of Water Level
	 */
	public float getIdro2() {
		return idro2;
	}
	/**
	 * @param varianceWaterLevel the IDRO2 to set
	 */
	public void setIdro2(float varianceWaterLevel) {
		this.idro2 = varianceWaterLevel;
	}
	/**
	 * @return the SONAR1: Mean value of River Bottom height
	 */
	public float getSonar1() {
		return sonar1;
	}
	/**
	 * @param riverBottonHeight the SONAR1 to set
	 */
	public void setSonar1(float riverBottonHeight) {
		this.sonar1 = riverBottonHeight;
	}
	/**
	 * @return the SONAR2: Variance of the River Bottom Height
	 */
	public float getSonar2() {
		return sonar2;
	}
	/**
	 * @param varianceRiverBottonHeight the SONAR2 to set
	 */
	public void setSonar2(float varianceRiverBottonHeight) {
		this.sonar2 = varianceRiverBottonHeight;
	}
	/**
	 * @return the SONAR3: Percent of utilized data of type 1 and 2 over the whole sample
	 */
	public float getSonar3() {
		return sonar3;
	}
	/**
	 * @param percUtilizedData12OverWholeSample the SONAR3 to set
	 */
	public void setSonar3(float percUtilizedData12OverWholeSample) {
		this.sonar3 = percUtilizedData12OverWholeSample;
	}
	/**
	 * @return the SONAR4: Percent of wrong data 3 over the whole sample
	 */
	public float getSonar4() {
		return sonar4;
	}
	/**
	 * @param percWrongData3OverWholeSample the SONAR4 to set
	 */
	public void setSonar4(float percWrongData3OverWholeSample) {
		this.sonar4 = percWrongData3OverWholeSample;
	}
	/**
	 * @return the SONAR5: Percent of Out of Water data 4 over the whole sample
	 */
	public float getSonar5() {
		return sonar5;
	}
	/**
	 * @param percOutWaterData4OverWholeSample the SONAR5 to set
	 */
	public void setSonar5(float percOutWaterData4OverWholeSample) {
		this.sonar5 = percOutWaterData4OverWholeSample;
	}
	/**
	 * @return the SONAR6: Percent of Error data 5 over the whole sample
	 */
	public float getSonar6() {
		return sonar6;
	}
	/**
	 * @param percErrorData5OverWholeSample the SONAR6 to set
	 */
	public void setSonar6(float percErrorData5OverWholeSample) {
		this.sonar6 = percErrorData5OverWholeSample;
	}
	/**
	 * @return the SONAR7: Percent of Uncertain data 2 over the 1+2 sample
	 */
	public float getSonar7() {
		return sonar7;
	}
	/**
	 * @param percUncertainData2Over12Sample the SONAR7 to set
	 */
	public void setSonar7(float percUncertainData2Over12Sample) {
		this.sonar7 = percUncertainData2Over12Sample;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
