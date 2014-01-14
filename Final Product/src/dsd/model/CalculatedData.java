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
package dsd.model;

import java.util.Date;


public class CalculatedData {
	
	private long calulcatedDataID;
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
	private float sonarPercError;
	private float sonarPercUncertain;
	private float safetyFactor00;
	private float safetyFactor01;
	private float safetyFactor10;
	private float safetyFactor11;
	private float waterSpeed;
	private float waterFlowRate;
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
							float sonarPercOutOfWater, float sonarPercError, float sonarPercUncertain,
							float safetyFactor00,
							float safetyFactor01,
							float safetyFactor10,
							float safetyFactor11,
							float waterSpeed,
							float waterFlowRate,
							long timestamp) {
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
		this.sonarPercError = sonarPercError;
		this.sonarPercUncertain = sonarPercUncertain;
		this.safetyFactor00 = safetyFactor00;
		this.safetyFactor01 = safetyFactor01;
		this.safetyFactor10 = safetyFactor10;
		this.safetyFactor11 = safetyFactor11;
		this.waterSpeed = waterSpeed;
		this.waterFlowRate = waterFlowRate;
		this.timestamp = timestamp;
	}

	/**
	 * @return the calulcatedData ID
	 */
	public long getCalulcatedDataID()
	{
		return calulcatedDataID;
	}

	/**
	 * @param calulcatedDataID the calulcatedData ID to set
	 */
	public void setCalulcatedDataID(long calulcatedDataID)
	{
		this.calulcatedDataID = calulcatedDataID;
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
	public float getSonarPercError() {
		return sonarPercError;
	}

	/**
	 * @param sonarError the sonarError to set
	 */
	public void setSonarPercError(float sonarPercError) {
		this.sonarPercError = sonarPercError;
	}

	/**
	 * @return the sonarUncertain
	 */
	public float getSonarPercUncertain() {
		return sonarPercUncertain;
	}

	/**
	 * @param sonarUncertain the sonarUncertain to set
	 */
	public void setSonarPercUncertain(float sonarPercUncertain) {
		this.sonarPercUncertain = sonarPercUncertain;
	}

	/**
	 * @return the safetyFactor00
	 */
	public float getSafetyFactor00() {
		return safetyFactor00;
	}

	/**
	 * @param safetyFactor00 the safetyFactor00 to set
	 */
	public void setSafetyFactor00(float safetyFactor00) {
		this.safetyFactor00 = safetyFactor00;
	}

	/**
	 * @return the safetyFactor01
	 */
	public float getSafetyFactor01() {
		return safetyFactor01;
	}

	/**
	 * @param safetyFactor01 the safetyFactor01 to set
	 */
	public void setSafetyFactor01(float safetyFactor01) {
		this.safetyFactor01 = safetyFactor01;
	}

	/**
	 * @return the safetyFactor10
	 */
	public float getSafetyFactor10() {
		return safetyFactor10;
	}

	/**
	 * @param safetyFactor10 the safetyFactor10 to set
	 */
	public void setSafetyFactor10(float safetyFactor10) {
		this.safetyFactor10 = safetyFactor10;
	}

	/**
	 * @return the safetyFactor11
	 */
	public float getSafetyFactor11() {
		return safetyFactor11;
	}

	/**
	 * @param safetyFactor11 the safetyFactor11 to set
	 */
	public void setSafetyFactor11(float safetyFactor11) {
		this.safetyFactor11 = safetyFactor11;
	}
	
	/**
	 * @return the waterSpeed
	 */
	public float getWaterSpeed() {
		return waterSpeed;
	}

	/**
	 * @param waterSpeed the waterSpeed to set
	 */
	public void setWaterSpeed(float waterSpeed) {
		this.waterSpeed = waterSpeed;
	}

	/**
	 * @return the waterFlowRate
	 */
	public float getWaterFlowRate() {
		return waterFlowRate;
	}

	/**
	 * @param waterFlowRate the waterFlowRate to set
	 */
	public void setWaterFlowRate(float waterFlowRate) {
		this.waterFlowRate = waterFlowRate;
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
