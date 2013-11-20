package dsd.model.calculation;

public class Combination extends Force{
	
	private int combinationNumber;
	private boolean debris;
	private boolean traffic;
	
	public Combination(int combinationNumber, boolean t, boolean d)
	{
		super();
		this.combinationNumber = combinationNumber;
		this.debris=d;
		this.traffic=t;
	}

	/**
	 * @return the status of presence of debris
	 */
	public boolean areThereDebris() {
		return this.debris;
	}

	/**
	 * @return the status of presence of traffic
	 */
	public boolean isThereTraffic() {
		return this.traffic;
	}

	/**
	 * @return the combinationNumber
	 */
	public int getCombinationNumber() {
		return combinationNumber;
	}
}