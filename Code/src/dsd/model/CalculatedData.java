package dsd.model;

import java.util.Date;


public class CalculatedData {
	
	private long calcualtedDataID;
	private float windSpeed;
	private float windDirection;
	private float windSpeedMax;
	private float windDirectionMax;
	private float hydrometer;
	private float hydrometerVariance;
	private float sonar;
	private float sonarVariance;
	private float sonarPercCorrect;
	private float sonarPercWrong;
	private float sonarPercOutOfWater;
	private float sonarError;
	private float sonarUncertain;
	private float safety_factor_00;
	private int stressed_pylone_00;
	private float safety_factor_01;
	private int stressed_pylone_01;
	private float safety_factor_10;
	private int stressed_pylone_10;
	private float safety_factor_11;
	private int stressed_pylone_11;
	private long timestamp;
	private Date timestampDate;
	
	public CalculatedData()
	{
		super();
	}

	public CalculatedData(float windSpeed, float windDirection,
							float windSpeedMax, float windDirectionMax, float hydrometer,
							float hydrometerVariance, float sonar, float sonarVariance,
							float sonarPercCorrect, float sonarPercWrong,
							float sonarPercOutOfWater, float sonarError, float sonarUncertain,
							float safety_factor_00, int stressed_pylone_00,
							float safety_factor_01, int stressed_pylone_01,
							float safety_factor_10, int stressed_pylone_10,
							float safety_factor_11, int stressed_pylone_11, long timestamp) {
		super();
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.windSpeedMax = windSpeedMax;
		this.windDirectionMax = windDirectionMax;
		this.hydrometer = hydrometer;
		this.hydrometerVariance = hydrometerVariance;
		this.sonar = sonar;
		this.sonarVariance = sonarVariance;
		this.sonarPercCorrect = sonarPercCorrect;
		this.sonarPercWrong = sonarPercWrong;
		this.sonarPercOutOfWater = sonarPercOutOfWater;
		this.sonarError = sonarError;
		this.sonarUncertain = sonarUncertain;
		this.safety_factor_00 = safety_factor_00;
		this.stressed_pylone_00 = stressed_pylone_00;
		this.safety_factor_01 = safety_factor_01;
		this.stressed_pylone_01 = stressed_pylone_01;
		this.safety_factor_10 = safety_factor_10;
		this.stressed_pylone_10 = stressed_pylone_10;
		this.safety_factor_11 = safety_factor_11;
		this.stressed_pylone_11 = stressed_pylone_11;
		this.timestamp = timestamp;
	}

	/**
	 * @return the windSpeed
	 */
	public float getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * @return the windDirection
	 */
	public float getWindDirection() {
		return windDirection;
	}

	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(float windDirection) {
		this.windDirection = windDirection;
	}

	/**
	 * @return the windSpeedMax
	 */
	public float getWindSpeedMax() {
		return windSpeedMax;
	}

	/**
	 * @param windSpeedMax the windSpeedMax to set
	 */
	public void setWindSpeedMax(float windSpeedMax) {
		this.windSpeedMax = windSpeedMax;
	}

	/**
	 * @return the windDirectionMax
	 */
	public float getWindDirectionMax() {
		return windDirectionMax;
	}

	/**
	 * @param windDirectionMax the windDirectionMax to set
	 */
	public void setWindDirectionMax(float windDirectionMax) {
		this.windDirectionMax = windDirectionMax;
	}

	/**
	 * @return the hydrometer
	 */
	public float getHydrometer() {
		return hydrometer;
	}

	/**
	 * @param hydrometer the hydrometer to set
	 */
	public void setHydrometer(float hydrometer) {
		this.hydrometer = hydrometer;
	}

	/**
	 * @return the hydrometerVariance
	 */
	public float getHydrometerVariance() {
		return hydrometerVariance;
	}

	/**
	 * @param hydrometerVariance the hydrometerVariance to set
	 */
	public void setHydrometerVariance(float hydrometerVariance) {
		this.hydrometerVariance = hydrometerVariance;
	}

	/**
	 * @return the sonar
	 */
	public float getSonar() {
		return sonar;
	}

	/**
	 * @param sonar the sonar to set
	 */
	public void setSonar(float sonar) {
		this.sonar = sonar;
	}

	/**
	 * @return the sonarVariance
	 */
	public float getSonarVariance() {
		return sonarVariance;
	}

	/**
	 * @param sonarVariance the sonarVariance to set
	 */
	public void setSonarVariance(float sonarVariance) {
		this.sonarVariance = sonarVariance;
	}

	/**
	 * @return the sonarPercCorrect
	 */
	public float getSonarPercCorrect() {
		return sonarPercCorrect;
	}

	/**
	 * @param sonarPercCorrect the sonarPercCorrect to set
	 */
	public void setSonarPercCorrect(float sonarPercCorrect) {
		this.sonarPercCorrect = sonarPercCorrect;
	}

	/**
	 * @return the sonarPercWrong
	 */
	public float getSonarPercWrong() {
		return sonarPercWrong;
	}

	/**
	 * @param sonarPercWrong the sonarPercWrong to set
	 */
	public void setSonarPercWrong(float sonarPercWrong) {
		this.sonarPercWrong = sonarPercWrong;
	}

	/**
	 * @return the sonarPercOutOfWater
	 */
	public float getSonarPercOutOfWater() {
		return sonarPercOutOfWater;
	}

	/**
	 * @param sonarPercOutOfWater the sonarPercOutOfWater to set
	 */
	public void setSonarPercOutOfWater(float sonarPercOutOfWater) {
		this.sonarPercOutOfWater = sonarPercOutOfWater;
	}

	/**
	 * @return the sonarError
	 */
	public float getSonarError() {
		return sonarError;
	}

	/**
	 * @param sonarError the sonarError to set
	 */
	public void setSonarError(float sonarError) {
		this.sonarError = sonarError;
	}

	/**
	 * @return the sonarUncertain
	 */
	public float getSonarUncertain() {
		return sonarUncertain;
	}

	/**
	 * @param sonarUncertain the sonarUncertain to set
	 */
	public void setSonarUncertain(float sonarUncertain) {
		this.sonarUncertain = sonarUncertain;
	}

	/**
	 * @return the safety_factor_00
	 */
	public float getSafety_factor_00() {
		return safety_factor_00;
	}

	/**
	 * @param safety_factor_00 the safety_factor_00 to set
	 */
	public void setSafety_factor_00(float safety_factor_00) {
		this.safety_factor_00 = safety_factor_00;
	}

	/**
	 * @return the stressed_pylone_00
	 */
	public int getStressed_pylone_00() {
		return stressed_pylone_00;
	}

	/**
	 * @param stressed_pylone_00 the stressed_pylone_00 to set
	 */
	public void setStressed_pylone_00(int stressed_pylone_00) {
		this.stressed_pylone_00 = stressed_pylone_00;
	}

	/**
	 * @return the safety_factor_01
	 */
	public float getSafety_factor_01() {
		return safety_factor_01;
	}

	/**
	 * @param safety_factor_01 the safety_factor_01 to set
	 */
	public void setSafety_factor_01(float safety_factor_01) {
		this.safety_factor_01 = safety_factor_01;
	}

	/**
	 * @return the stressed_pylone_01
	 */
	public int getStressed_pylone_01() {
		return stressed_pylone_01;
	}

	/**
	 * @param stressed_pylone_01 the stressed_pylone_01 to set
	 */
	public void setStressed_pylone_01(int stressed_pylone_01) {
		this.stressed_pylone_01 = stressed_pylone_01;
	}

	/**
	 * @return the safety_factor_10
	 */
	public float getSafety_factor_10() {
		return safety_factor_10;
	}

	/**
	 * @param safety_factor_10 the safety_factor_10 to set
	 */
	public void setSafety_factor_10(float safety_factor_10) {
		this.safety_factor_10 = safety_factor_10;
	}

	/**
	 * @return the stressed_pylone_10
	 */
	public int getStressed_pylone_10() {
		return stressed_pylone_10;
	}

	/**
	 * @param stressed_pylone_10 the stressed_pylone_10 to set
	 */
	public void setStressed_pylone_10(int stressed_pylone_10) {
		this.stressed_pylone_10 = stressed_pylone_10;
	}

	/**
	 * @return the safety_factor_11
	 */
	public float getSafety_factor_11() {
		return safety_factor_11;
	}

	/**
	 * @param safety_factor_11 the safety_factor_11 to set
	 */
	public void setSafety_factor_11(float safety_factor_11) {
		this.safety_factor_11 = safety_factor_11;
	}

	/**
	 * @return the stressed_pylone_11
	 */
	public int getStressed_pylone_11() {
		return stressed_pylone_11;
	}

	/**
	 * @param stressed_pylone_11 the stressed_pylone_11 to set
	 */
	public void setStressed_pylone_11(int stressed_pylone_11) {
		this.stressed_pylone_11 = stressed_pylone_11;
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
		this.timestampDate = new Date(timestamp);
	}

	/**
	 * @return the timestampDate
	 */
	public Date getTimestampDate() {
		return timestampDate;
	}
}
