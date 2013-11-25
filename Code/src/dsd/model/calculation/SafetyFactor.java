package dsd.model.calculation;

public class SafetyFactor {
	
	/*
	 * COMBINATION ARE TD  ==> T:traffic  , D:debris
	 */
	//T=0 , D=0
	private float safetyFactor00;
	private Pylon stressed_pylon00;
	//T=0 , D=1
	private float safetyFactor01;
	private Pylon stressed_pylon01;
	//T=1 , D=0
	private float safetyFactor10;
	private Pylon stressed_pylon10;
	//T=1 , D=1
	private float safetyFactor11;
	private Pylon stressed_pylon11;
	
	
	public SafetyFactor() {
		this.safetyFactor00 = 0;
		this.stressed_pylon00 = null;
		this.safetyFactor01 = 0;
		this.stressed_pylon01 = null;
		this.safetyFactor10 = 0;
		this.stressed_pylon10 = null;
		this.safetyFactor11 = 0;
		this.stressed_pylon11 = null;
	}
	
	
	/**
	 * @return the safetyFactor00
	 */
	public float getSafetyFactor00() {
		return safetyFactor00;
	}
	/**
	 * @param safetyFactor00 the safetyFactor00 to set
	 */
	public void setSafetyFactor00(float safetyFactor00) {
		this.safetyFactor00 = safetyFactor00;
	}
	/**
	 * @return the stressed_pylon00
	 */
	public Pylon getStressed_pylon00() {
		return stressed_pylon00;
	}
	/**
	 * @param stressed_pylon00 the stressed_pylon00 to set
	 */
	public void setStressed_pylon00(Pylon stressed_pylon00) {
		this.stressed_pylon00 = stressed_pylon00;
	}
	/**
	 * @return the safetyFactor01
	 */
	public float getSafetyFactor01() {
		return safetyFactor01;
	}
	/**
	 * @param safetyFactor01 the safetyFactor01 to set
	 */
	public void setSafetyFactor01(float safetyFactor01) {
		this.safetyFactor01 = safetyFactor01;
	}
	/**
	 * @return the stressed_pylon01
	 */
	public Pylon getStressed_pylon01() {
		return stressed_pylon01;
	}
	/**
	 * @param stressed_pylon01 the stressed_pylon01 to set
	 */
	public void setStressed_pylon01(Pylon stressed_pylon01) {
		this.stressed_pylon01 = stressed_pylon01;
	}
	/**
	 * @return the safetyFactor10
	 */
	public float getSafetyFactor10() {
		return safetyFactor10;
	}
	/**
	 * @param safetyFactor10 the safetyFactor10 to set
	 */
	public void setSafetyFactor10(float safetyFactor10) {
		this.safetyFactor10 = safetyFactor10;
	}
	/**
	 * @return the stressed_pylon10
	 */
	public Pylon getStressed_pylon10() {
		return stressed_pylon10;
	}
	/**
	 * @param stressed_pylon10 the stressed_pylon10 to set
	 */
	public void setStressed_pylon10(Pylon stressed_pylon10) {
		this.stressed_pylon10 = stressed_pylon10;
	}
	/**
	 * @return the safetyFactor11
	 */
	public float getSafetyFactor11() {
		return safetyFactor11;
	}
	/**
	 * @param safetyFactor11 the safetyFactor11 to set
	 */
	public void setSafetyFactor11(float safetyFactor11) {
		this.safetyFactor11 = safetyFactor11;
	}
	/**
	 * @return the stressed_pylon11
	 */
	public Pylon getStressed_pylon11() {
		return stressed_pylon11;
	}
	/**
	 * @param stressed_pylon11 the stressed_pylon11 to set
	 */
	public void setStressed_pylon11(Pylon stressed_pylon11) {
		this.stressed_pylon11 = stressed_pylon11;
	}
}
