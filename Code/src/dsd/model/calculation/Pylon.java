package dsd.model.calculation;

public class Pylon extends Force {
	
	private int pylonNumber;
	private float safetyFactor;
	
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
	 * M = (Mx^2 + My^2)^(0.5)
	 * 
	 * @return the Total M of the pylon
	 */
	public float getM()
	{
		double m;
		/*
		 * M = (Mx^2 + My^2)^(0.5)
		 */
		m = Math.sqrt((Math.pow(this.getMx(), 2)) + (Math.pow(this.getMy(), 2)));
		return (float)m;
	}

	/**
	 * @return the safetyFactor
	 */
	public float getSafetyFactor() {
		return safetyFactor;
	}

	/**
	 * @param safetyFactor the safetyFactor to set
	 */
	public void setSafetyFactor(float safetyFactor) {
		this.safetyFactor = safetyFactor;
	}
}
