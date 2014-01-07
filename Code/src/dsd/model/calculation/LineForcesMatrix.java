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

import java.util.ArrayList;

public class LineForcesMatrix {
	
	/*
	 * Define a variable for each raw on the "matrix" and then
	 * define also a constant that represents the matrix row
	 * in which this variable is
	 * 
	 * These costants are used by the CombinationsCalculationTask
	 * to define which components made the combinations so which
	 * forces contribute at the calculations of each combination.
	 */
	private Force ps;
	public static final int PS = 0;
	
	private Force a110;
	public static final int A110 = 1;
	
	private Force a120;
	public static final int A120 = 2;
	
	private Force a210;
	public static final int A210 = 3;
	
	private Force a220;
	public static final int A220 = 4;
	
	private Force a311;
	public static final int A311 = 5;
	
	private Force a312;
	public static final int A312 = 6;
	
	private Force a321;
	public static final int A321 = 7;
	
	private Force a322;
	public static final int A322 = 8;
	
	private Force fr01;
	public static final int FR01 = 9;
	
	private Force fr02;
	public static final int FR02 = 10;
	
	private Force vt0;
	public static final int VT0 = 11;
	
	private Force vt1a1;
	public static final int VT1A1 = 12;
	
	private Force vt1a2;
	public static final int VT1A2 = 13;
	
	private Force vt1a3;
	public static final int VT1A3 = 14;
	
	private Force aqd0;
	public static final int AQD0 = 15;
	
	private Force aqd1;
	public static final int AQD1 = 16;
	
	private ArrayList<Force> forcesList = null;
	
	public LineForcesMatrix()
	{
		super();
		ps = new Force();
		a110 = new Force();
		a120 = new Force();
		a210 = new Force();
		a220 = new Force();
		a311 = new Force();
		a312 = new Force();
		a321 = new Force();
		a322 = new Force();
		fr01 = new Force();
		fr02 = new Force();
		vt0 = new Force();
		vt1a1 = new Force();
		vt1a2 = new Force();
		vt1a3 = new Force();
		aqd0 = new Force();
		aqd1 = new Force();
		
		forcesList = new ArrayList<Force>();
		forcesList.add(ps);
		forcesList.add(a110);
		forcesList.add(a120);
		forcesList.add(a210);
		forcesList.add(a220);
		forcesList.add(a311);
		forcesList.add(a312);
		forcesList.add(a321);
		forcesList.add(a322);
		forcesList.add(fr01);
		forcesList.add(fr02);
		forcesList.add(vt0);
		forcesList.add(vt1a1);
		forcesList.add(vt1a2);
		forcesList.add(vt1a3);
		forcesList.add(aqd0);
		forcesList.add(aqd1);		
	}

	/**
	 * @return the ps
	 */
	public Force getPs() {
		return ps;
	}

	/**
	 * @return the a110
	 */
	public Force getA110() {
		return a110;
	}

	/**
	 * @return the a120
	 */
	public Force getA120() {
		return a120;
	}

	/**
	 * @return the a210
	 */
	public Force getA210() {
		return a210;
	}

	/**
	 * @return the a220
	 */
	public Force getA220() {
		return a220;
	}

	/**
	 * @return the a311
	 */
	public Force getA311() {
		return a311;
	}

	/**
	 * @return the a312
	 */
	public Force getA312() {
		return a312;
	}

	/**
	 * @return the a321
	 */
	public Force getA321() {
		return a321;
	}

	/**
	 * @return the a322
	 */
	public Force getA322() {
		return a322;
	}

	/**
	 * @return the fr01
	 */
	public Force getFR01() {
		return fr01;
	}

	/**
	 * @return the fr02
	 */
	public Force getFR02() {
		return fr02;
	}

	/**
	 * @return the vt0
	 */
	public Force getVT0() {
		return vt0;
	}

	/**
	 * @return the vt1a1
	 */
	public Force getVT1A1() {
		return vt1a1;
	}

	/**
	 * @return the vt1a2
	 */
	public Force getVT1A2() {
		return vt1a2;
	}

	/**
	 * @return the vt1a3
	 */
	public Force getVT1A3() {
		return vt1a3;
	}

	/**
	 * @return the aqd0
	 */
	public Force getAQD0() {
		return aqd0;
	}

	/**
	 * @return the aqd1
	 */
	public Force getAQD1() {
		return aqd1;
	}

	/**
	 * @return the forcesList
	 */
	public ArrayList<Force> getForcesList() {
		return forcesList;
	}
}
