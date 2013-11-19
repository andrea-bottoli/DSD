package dsd.model.calculation;

public class Combination extends Force{
	
	private final boolean debris;
	private final boolean traffic;
	
	public Combination(boolean t, boolean d)
	{
		super();
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
	
	
}