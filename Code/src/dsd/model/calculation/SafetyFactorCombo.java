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
package dsd.model.calculation;

public class SafetyFactorCombo {
	
	private Boolean traffic;
	private Boolean debris;
	private float value;
	
	
	public SafetyFactorCombo(Boolean traffic, Boolean debris) {
		
		this.traffic = traffic;
		this.debris = debris;
		this.value = 0;
	}

	
	/**
	 * @return the traffic
	 */
	public Boolean getTraffic() {
		return traffic;
	}


	/**
	 * @return the debris
	 */
	public Boolean getDebris() {
		return debris;
	}


	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
}
