/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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

public class PylonCombination {
	
	private Combination combination = null;
	private Pylon pylon1;
	private Pylon pylon2;
	private Pylon pylon3;
	private ArrayList<Pylon> pylonList;
	
	public PylonCombination(int side)
	{	
		
		if(side == 0) //Mantova side
		{
			this.pylon1 = new Pylon(5);
			this.pylon2 = new Pylon(3);
			this.pylon3 = new Pylon(1);
		}
		else if(side == 1) //Modena side
		{
			this.pylon1 = new Pylon(6);
			this.pylon2 = new Pylon(4);
			this.pylon3 = new Pylon(2);
		}
		
		this.pylonList = new ArrayList<Pylon>();
		pylonList.add(pylon1);
		pylonList.add(pylon2);
		pylonList.add(pylon3);
		
	}

	/**
	 * @return the combination
	 */
	public Combination getCombination()
	{
		return combination;
	}
	
	/**
	 * @param combination
	 */
	public void setCombination(Combination combination)
	{
		this.combination = combination;
	}

	/**
	 * @return the pylon1
	 */
	public Pylon getPylon1()
	{
		return pylon1;
	}

	/**
	 * @return the pylon2
	 */
	public Pylon getPylon2()
	{
		return pylon2;
	}

	/**
	 * @return the pylon3
	 */
	public Pylon getPylon3()
	{
		return pylon3;
	}
	
	/**
	 * @return the pylonList
	 */
	public ArrayList<Pylon> getPylonList()
	{
		return pylonList;
	}
	
}
