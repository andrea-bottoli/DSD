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
package dsd.model.calculation;

public class PlankForces
{
	private float windPushOnPlank;
	private float windPushOnA1TrafficCombination;
	private float windPushOnA2TrafficCombination;
	private float windPushOnA3TrafficCombination;
	private float flowRate;
	private float waterSpeed;
	private float hs;
	private float bsWithoutDebris;
	private float bsWithDebris;
	private float hydrodynamicThrustWithOutDebris;
	private float hydrodynamicThrustWithDebris;
	private float plankWeight;
	private float stackWeight;
	/**
	 * @return the windPushOnPlank
	 */
	public float getWindPushOnPlank() {
		return windPushOnPlank;
	}
	/**
	 * @param windPushOnPlank the windPushOnPlank to set
	 */
	public void setWindPushOnPlank(float windPushOnPlank) {
		this.windPushOnPlank = windPushOnPlank;
	}
	/**
	 * @return the windPushOnA1TrafficCombination
	 */
	public float getWindPushOnA1TrafficCombination() {
		return windPushOnA1TrafficCombination;
	}
	/**
	 * @param windPushOnA1TrafficCombination the windPushOnA1TrafficCombination to set
	 */
	public void setWindPushOnA1TrafficCombination(
			float windPushOnA1TrafficCombination) {
		this.windPushOnA1TrafficCombination = windPushOnA1TrafficCombination;
	}
	/**
	 * @return the windPushOnA2TrafficCombination
	 */
	public float getWindPushOnA2TrafficCombination() {
		return windPushOnA2TrafficCombination;
	}
	/**
	 * @param windPushOnA2TrafficCombination the windPushOnA2TrafficCombination to set
	 */
	public void setWindPushOnA2TrafficCombination(
			float windPushOnA2TrafficCombination) {
		this.windPushOnA2TrafficCombination = windPushOnA2TrafficCombination;
	}
	/**
	 * @return the windPushOnA3TrafficCombination
	 */
	public float getWindPushOnA3TrafficCombination() {
		return windPushOnA3TrafficCombination;
	}
	/**
	 * @param windPushOnA3TrafficCombination the windPushOnA3TrafficCombination to set
	 */
	public void setWindPushOnA3TrafficCombination(
			float windPushOnA3TrafficCombination) {
		this.windPushOnA3TrafficCombination = windPushOnA3TrafficCombination;
	}
	/**
	 * @return the flowRate
	 */
	public float getFlowRate() {
		return flowRate;
	}
	/**
	 * @param flowRate the flowRate to set
	 */
	public void setFlowRate(float flowRate) {
		this.flowRate = flowRate;
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
	 * @return the stackBase
	 */
	public float getHs() {
		return hs;
	}
	/**
	 * @param stackBase the stackBase to set
	 */
	public void setHs(float hs) {
		this.hs = hs;
	}
	/**
	 * @return the baseStackWithoutDebris
	 */
	public float getBsWithoutDebris() {
		return bsWithoutDebris;
	}
	/**
	 * @param baseStackWithoutDebris the baseStackWithoutDebris to set
	 */
	public void setBsWithOutDebris(float bsWithoutDebris) {
		this.bsWithoutDebris = bsWithoutDebris;
	}
	/**
	 * @return the baseStackWithDebris
	 */
	public float getBsWithDebris() {
		return bsWithDebris;
	}
	/**
	 * @param baseStackWithDebris the baseStackWithDebris to set
	 */
	public void setBsWithDebris(float bsWithDebris) {
		this.bsWithDebris = bsWithDebris;
	}
	/**
	 * @return the hydrodynamicThrustWithOutDebris
	 */
	public float getHydrodynamicThrustWithOutDebris() {
		return hydrodynamicThrustWithOutDebris;
	}
	/**
	 * @param hydrodynamicThrustWithOutDebris the hydrodynamicThrustWithOutDebris to set
	 */
	public void setHydrodynamicThrustWithOutDebris(
			float hydrodynamicThrustWithOutDebris) {
		this.hydrodynamicThrustWithOutDebris = hydrodynamicThrustWithOutDebris;
	}
	/**
	 * @return the hydrodynamicThrustWithDebris
	 */
	public float getHydrodynamicThrustWithDebris() {
		return hydrodynamicThrustWithDebris;
	}
	/**
	 * @param hydrodynamicThrustWithDebris the hydrodynamicThrustWithDebris to set
	 */
	public void setHydrodynamicThrustWithDebris(float hydrodynamicThrustWithDebris) {
		this.hydrodynamicThrustWithDebris = hydrodynamicThrustWithDebris;
	}
	/**
	 * @return the plankWeight
	 */
	public float getPlankWeight() {
		return plankWeight;
	}
	/**
	 * @param plankWeight the plankWeight to set
	 */
	public void setPlankWeight(float plankWeight) {
		this.plankWeight = plankWeight;
	}
	/**
	 * @return the stackWeight
	 */
	public float getStackWeight() {
		return stackWeight;
	}
	/**
	 * @param stackWeight the stackWeight to set
	 */
	public void setStackWeight(float stackWeight) {
		this.stackWeight = stackWeight;
	}
	/**
	 * @return the structureWeight
	 */
	public float getStructureWeight() {
		return (this.plankWeight+this.stackWeight);
	}
}
