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

import dsd.model.calculation.Pylon;

public class WorstPylonCase {

	private long worstCasePylonID;
	private Pylon pylon;
	private int comboNumber;
	private final int position;
	private long timestamp;

	public WorstPylonCase(int pylonNumber) {
		this.pylon = new Pylon(pylonNumber);
		this.position = getPosition(pylonNumber);
	}

	public WorstPylonCase(Pylon pylon, int comboNumber) {
		super();
		this.worstCasePylonID = 0;
		this.pylon = pylon;
		this.comboNumber = comboNumber;
		this.position = getPosition(pylon.getPylonNumber());
		this.timestamp = 0;
	}

	private int getPosition(int pylonNumber) {
		int temp;

		switch (pylonNumber) {
		case 1:
			temp = 0;
			break;
		case 2:
			temp = 1;
			break;
		case 3:
			temp = 2;
			break;
		case 4:
			temp = 3;
			break;
		case 5:
			temp = 4;
			break;
		case 6:
			temp = 5;
			break;
		default:
			temp = -1;
		}

		return temp;
	}

	/**
	 * @return the safetyFactor
	 */
	public float getSafetyFactor() {
		return this.pylon.getSafetyFactor();
	}

	/**
	 * @param safetyFactor
	 *            the safetyFactor to set
	 */
	public void setSafetyFactor(float safetyFactor) {
		this.pylon.setSafetyFactor(safetyFactor);
	}

	/**
	 * @return the comboNumber
	 */
	public int getComboNumber() {
		return comboNumber;
	}

	/**
	 * @param comboNumber
	 *            the comboNumber to set
	 */
	public void setComboNumber(int comboNumber) {
		this.comboNumber = comboNumber;
	}

	/**
	 * @param pylon
	 *            the pylon to set
	 */
	public void setPylon(Pylon pylon) {
		this.pylon = pylon;
	}

	/**
	 * @return the pylonNumber
	 */
	public int getPylonNumber() {
		return this.pylon.getPylonNumber();
	}

	/**
	 * @return the N force acting on the pylon
	 */
	public float getN() {
		return this.pylon.getN();
	}

	/**
	 * @return the N force acting on the pylon
	 */
	public float getM() {
		return this.pylon.getM();
	}

	/**
	 * @return the Mx force acting on the pylon
	 */
	public float getMx() {
		return this.pylon.getMx();
	}

	/**
	 * @return the My force acting on the pylon
	 */
	public float getMy() {
		return this.pylon.getMy();
	}

	/**
	 * @return the Tx force acting on the pylon
	 */
	public float getTx() {
		return this.pylon.getTx();
	}

	/**
	 * @return the Ty force acting on the pylon
	 */
	public float getTy() {
		return this.pylon.getTy();
	}

	public int getPosition() {
		return this.position;
	}

	/**
	 * @return the iD
	 */
	public long getID() {
		return worstCasePylonID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(long iD) {
		worstCasePylonID = iD;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
