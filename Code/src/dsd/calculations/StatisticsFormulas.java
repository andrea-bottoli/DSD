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
		
		for(Float f : list)
		{
			sum += f;
		}
		
		return (sum/list.size());
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
		
		mean = meanValue(list);
		
		for(Float f : list)
		{
			sum += Math.pow((f - mean),2);
		}
		
		variance = sum/list.size();
		
		return variance;
	}
}
