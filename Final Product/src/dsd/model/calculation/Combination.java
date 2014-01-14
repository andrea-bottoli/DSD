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

public class Combination extends Force{
	
	private float stackWeight;
	private int combinationNumber;
	private boolean debris;
	private boolean traffic;
	
	public Combination(int combinationNumber, boolean t, boolean d)
	{
		super();
		this.stackWeight = 0;
		this.combinationNumber = combinationNumber;
		this.debris=d;
		this.traffic=t;
	}

	/**
	 * @param the stack weight
	 */
	public void setStackWeight(float stackWeight) {
		this.stackWeight = stackWeight;
	}
	
	/**
	 * @return the stack weight
	 */
	public float getStackWeight() {
		return this.stackWeight;
	}
	
	/**
	 * @return the status of presence of debris
	 */
	public boolean getDebris() {
		return this.debris;
	}

	/**
	 * @return the status of presence of traffic
	 */
	public boolean getTraffic() {
		return this.traffic;
	}

	/**
	 * @return the combinationNumber
	 */
	public int getCombinationNumber() {
		return combinationNumber;
	}
}
