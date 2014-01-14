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

public class LineForces
{
	private Combination combo01A =null;
	private Combination combo01B =null;
	private Combination combo02A =null;
	private Combination combo02B =null;
	private Combination combo03A =null;
	private Combination combo03B =null;
	private Combination combo04A =null;
	private Combination combo04B =null;
	private Combination combo05A =null;
	private Combination combo05B =null;
	private Combination combo06A =null;
	private Combination combo06B =null;
	private Combination combo07A =null;
	private Combination combo07B =null;
	private Combination combo08A =null;
	private Combination combo08B =null;
	private Combination combo09A =null;
	private Combination combo09B =null;
	private Combination combo10A =null;
	private Combination combo10B =null;
	private Combination combo11A =null;
	private Combination combo11B =null;
	private Combination combo12A =null;
	private Combination combo12B =null;
	private Combination combo13A =null;
	private Combination combo13B =null;
	private Combination combo14A =null;
	private Combination combo14B =null;
	private Combination combo15A =null;
	private Combination combo15B =null;
	private Combination combo16A =null;
	private Combination combo16B =null;
	private Combination combo17A =null;
	private Combination combo17B =null;
	
	private ArrayList<Combination> comboList = null;
	
	public LineForces()
	{
		combo01A = new Combination(1,Boolean.FALSE, Boolean.FALSE);
		combo01B = new Combination(2,Boolean.FALSE, Boolean.TRUE);
		combo02A = new Combination(3,Boolean.TRUE, Boolean.FALSE);
		combo02B = new Combination(4,Boolean.TRUE, Boolean.TRUE);
		combo03A = new Combination(5,Boolean.TRUE, Boolean.FALSE);
		combo03B = new Combination(6,Boolean.TRUE, Boolean.TRUE);
		combo04A = new Combination(7,Boolean.TRUE, Boolean.FALSE);
		combo04B = new Combination(8,Boolean.TRUE, Boolean.TRUE);
		combo05A = new Combination(9,Boolean.TRUE, Boolean.FALSE);
		combo05B = new Combination(10,Boolean.TRUE, Boolean.TRUE);
		combo06A = new Combination(11,Boolean.TRUE, Boolean.FALSE);
		combo06B = new Combination(12,Boolean.TRUE, Boolean.TRUE);
		combo07A = new Combination(13,Boolean.TRUE, Boolean.FALSE);
		combo07B = new Combination(14,Boolean.TRUE, Boolean.TRUE);
		combo08A = new Combination(15,Boolean.TRUE, Boolean.FALSE);
		combo08B = new Combination(16,Boolean.TRUE, Boolean.TRUE);
		combo09A = new Combination(17,Boolean.TRUE, Boolean.FALSE);
		combo09B = new Combination(18,Boolean.TRUE, Boolean.TRUE);
		combo10A = new Combination(19,Boolean.TRUE, Boolean.FALSE);
		combo10B = new Combination(20,Boolean.TRUE, Boolean.TRUE);
		combo11A = new Combination(21,Boolean.TRUE, Boolean.FALSE);
		combo11B = new Combination(22,Boolean.TRUE, Boolean.TRUE);
		combo12A = new Combination(23,Boolean.TRUE, Boolean.FALSE);
		combo12B = new Combination(24,Boolean.TRUE, Boolean.TRUE);
		combo13A = new Combination(25,Boolean.TRUE, Boolean.FALSE);
		combo13B = new Combination(26,Boolean.TRUE, Boolean.TRUE);
		combo14A = new Combination(27,Boolean.TRUE, Boolean.FALSE);
		combo14B = new Combination(28,Boolean.TRUE, Boolean.TRUE);
		combo15A = new Combination(29,Boolean.TRUE, Boolean.FALSE);
		combo15B = new Combination(30,Boolean.TRUE, Boolean.TRUE);
		combo16A = new Combination(31,Boolean.TRUE, Boolean.FALSE);
		combo16B = new Combination(32,Boolean.TRUE, Boolean.TRUE);
		combo17A = new Combination(33,Boolean.TRUE, Boolean.FALSE);
		combo17B = new Combination(34,Boolean.TRUE, Boolean.TRUE);
		
		comboList = new ArrayList<Combination>();
		comboList.add(combo01A);
		comboList.add(combo01B);
		comboList.add(combo02A);
		comboList.add(combo02B);
		comboList.add(combo03A);
		comboList.add(combo03B);
		comboList.add(combo04A);
		comboList.add(combo04B);
		comboList.add(combo05A);
		comboList.add(combo05B);
		comboList.add(combo06A);
		comboList.add(combo06B);
		comboList.add(combo07A);
		comboList.add(combo07B);
		comboList.add(combo08A);
		comboList.add(combo08B);
		comboList.add(combo09A);
		comboList.add(combo09B);
		comboList.add(combo10A);
		comboList.add(combo10B);
		comboList.add(combo11A);
		comboList.add(combo11B);
		comboList.add(combo12A);
		comboList.add(combo12B);
		comboList.add(combo13A);
		comboList.add(combo13B);
		comboList.add(combo14A);
		comboList.add(combo14B);
		comboList.add(combo15A);
		comboList.add(combo15B);
		comboList.add(combo16A);
		comboList.add(combo16B);
		comboList.add(combo17A);
		comboList.add(combo17B);
	}

	/**
	 * @return the comboList
	 */
	public ArrayList<Combination> getComboList() {
		return comboList;
	}
}
