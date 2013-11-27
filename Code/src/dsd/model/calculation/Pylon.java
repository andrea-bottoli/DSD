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
	
	
	/**
	 * @return the Total N of the pylon
	 */
	public float getPylonN()
	{
		return this.getN();
	}
	
	/**
	 * @return the Total M of the pylon
	 */
	public float getPylonM()
	{
		double m;
		/*
		 * M = (Mx^2 + My^2)^(0.5)
		 */
		m = Math.sqrt((Math.pow(this.getMx(), 2)) + (Math.pow(this.getMy(), 2)));
		return (float)m;
	}
}
