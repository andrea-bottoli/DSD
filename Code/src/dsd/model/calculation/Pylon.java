package dsd.model.calculation;

public class Pylon extends Force {
	
	private int pylonNumber;
	
	public Pylon(int pylonNumber)
	{	
		super();
		this.pylonNumber = pylonNumber;
	}

	/**
	 * @return the pylonNumber
	 */
	public int getPylonNumber()
	{
		return pylonNumber;
	}

}
