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
package dsd.calculations;

import java.util.List;

public class StatisticsFormulas {

	/**
	 * @param list of values on which calculate the mean value
	 * @return the mean value of the list of values
	 */
	protected static float meanValue(List<Float> list)
	{
		float sum = 0;
		
		if(list.size() > 0){
			for(Float f : list)
			{
				sum += f;
			}
			
			return (sum/list.size());
		}else{
			return (float) 0;
		}
		
	}
	
	protected static float maxValue(List<Float> list)
	{
		float max = 0;
		
		for(Float f : list)
		{
			if(f > max)
			{
				max = f;
			}
		}
		
		return max;
	}
	
	
	protected static float variance(List<Float> list)
	{
		float mean = 0;
		float sum = 0;
		float variance = 0;
		
		if(list.size() > 0)
		{
			mean = meanValue(list);
			
			for(Float f : list)
			{
				sum += Math.pow((f - mean),2);
			}
			
			variance = sum/list.size();
			
			return variance;
		}else
		{
			return (float)0;
		}
	}
}
