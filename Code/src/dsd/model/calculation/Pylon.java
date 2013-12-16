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
