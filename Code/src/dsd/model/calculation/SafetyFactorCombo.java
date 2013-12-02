package dsd.model.calculation;

public class SafetyFactorCombo {
	
	private Boolean traffic;
	private Boolean debris;
	private float value;
	
	
	public SafetyFactorCombo(Boolean traffic, Boolean debris) {
		
		this.traffic = traffic;
		this.debris = debris;
		this.value = 0;
	}

	
	/**
	 * @return the traffic
	 */
	public Boolean getTraffic() {
		return traffic;
	}


	/**
	 * @return the debris
	 */
	public Boolean getDebris() {
		return debris;
	}


	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
}
