package dsd.model.calculation;

public class Combination extends Force{
	
	private float stackWeight;
	private int combinationNumber;
	private boolean debris;
	private boolean traffic;
	
	public Combination(int combinationNumber, boolean t, boolean d)
	{
		super();
		this.stackWeight = 0;
		this.combinationNumber = combinationNumber;
		this.debris=d;
		this.traffic=t;
	}

	/**
	 * @param the stack weight
	 */
	public void setStackWeight(float stackWeight) {
		this.stackWeight = stackWeight;
	}
	
	/**
	 * @return the stack weight
	 */
	public float getStackWeight() {
		return this.stackWeight;
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