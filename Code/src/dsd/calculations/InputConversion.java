package dsd.calculations;

public class InputConversion {

	/*
	 * ##########################################################
	 * ##########################################################
	 * #####												#####
	 * #####			PARSER CONVERSIONS					#####
	 * #####												#####
	 * ##########################################################
	 * ##########################################################
	 */
	
	/**
	 * This method converts the wind speed from [mmA] to [m/s]
	 * 
	 * @param windSpeedToBeConvert: value of wind speed in [mmA] measured by the Anemometer
	 * @return The value of wind speed converted in [m/s]
	 */
	public static double windSpeedConversion(double windSpeedToBeConvert)
	{
		double windSpeed=0;
		windSpeed = (((windSpeedToBeConvert * 1000) - 4) * 3.75) ;
		return windSpeed;
	}
	
	
	
	/**
	 *  This method converts the wind direction from [mmA] to [° - degrees]
	 *  The degrees are measured from north clockwise, following the wind rose.
	 *  
	 * @param windDirectionToBeConvert: the value of wind direction in [mmA] measure by the Anemometer
	 * @return The value of wind direction converted in [° - degrees]
	 */
	public static double windDirectionConversion(double windDirectionToBeConvert)
	{
		double windDirection=0;
		windDirection = (((windDirectionToBeConvert * 1000) - 4) * 22.5) ;
		return windDirection;
	}
	
	
	/**
	 * This method converts the distance from the hydrometer and water from [mmA] into the
	 * height of the water level in [m]. Convert a distance into a height.
	 * 
	 * @param waterLevelToBeConvert: is the value in [mmA] of the distance between the Hydrometer and water
	 * @return The value of the height of water level.
	 */
	public static double waterLevelConversion(double waterLevelToBeConvert)
	{
		double waterLevel=0;
		/*
		 * 29.86 is a hydrometer parameter; is its altitude. Is inserted as a constant.
		 */
		waterLevel = 29.86 - waterDistanceConversion(waterLevelToBeConvert);
		return waterLevel;
	}
	
	/**
	 * This method convert the distance between the hydrometer and water from [mmA] to [m]
	 * 
	 * @param waterDistanceToBeConverted: the distance between the hydrometer and water in [mmA]
	 * @return The distance between hydrometer and water in [m]
	 */
	public static double waterDistanceConversion(double waterDistanceToBeConverted)
	{
		double waterDistance = 0;
		waterDistance = 20 + (((waterDistanceToBeConverted * 1000) - 4) * (-1.25));
		return waterDistance;
	}
	
	
	/**
	 * This method converts the distance between the sonar ant the river bed from [m] into
	 * the height of the river bed also in [m].
	 * 
	 * @param sonarToBeConvert: the value measured by the Sonar, that represent the distance between the sonar and the river bed
	 * @return The value of the height of the river bed.
	 */
	public static double sonarConversion(double sonarToBeConvert)
	{
		double sonar=0;
		/*
		 * 12.33 is a sonar parameter; is its altitude. Is inserted as a constant.
		 */
		sonar = 12.33 - sonarToBeConvert;
		return sonar;
	}
}
