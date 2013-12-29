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
package dsd.model;

import java.util.ArrayList;
import java.util.List;

public class MNDomain {
	
	private List<Float> m;
	private List<Float> n;
	
	public MNDomain(){
		this.m = new ArrayList<Float>();
		this.n = new ArrayList<Float>();
	}
	
	public MNDomain(List<Float> m, List<Float> n){
		this.m = m;
		this.n = n;
	}

	/**
	 * @return the m
	 */
	public List<Float> getM() {
		return m;
	}

	/**
	 * @return the n
	 */
	public List<Float> getN() {
		return n;
	}
}
