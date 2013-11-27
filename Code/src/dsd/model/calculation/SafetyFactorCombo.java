package dsd.model.calculation;

public class SafetyFactorCombo {
	
	private Boolean traffic;
	private Boolean debris;
	private float value;
	private Pylon stressed_pylon;
	private float pylon1_N;
	private float pylon1_M;
	private float pylon2_N;
	private float pylon2_M;
	private float pylon3_N;
	private float pylon3_M;
	private float pylon4_N;
	private float pylon4_M;
	private float pylon5_N;
	private float pylon5_M;
	private float pylon6_N;
	private float pylon6_M;
	
	
	public SafetyFactorCombo(Boolean traffic, Boolean debris) {
		
		this.traffic = traffic;
		this.debris = debris;
		this.value = 0;
		this.stressed_pylon = null;
		this.pylon1_N = 0;
		this.pylon1_M = 0;
		this.pylon2_N = 0;
		this.pylon2_M = 0;
		this.pylon3_N = 0;
		this.pylon3_M = 0;
		this.pylon4_N = 0;
		this.pylon4_M = 0;
		this.pylon5_N = 0;
		this.pylon5_M = 0;
		this.pylon6_N = 0;
		this.pylon6_M = 0;
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


	/**
	 * @return the stressed_pylon
	 */
	public Pylon getStressed_pylon() {
		return stressed_pylon;
	}


	/**
	 * @param stressed_pylon the stressed_pylon to set
	 */
	public void setStressed_pylon(Pylon stressed_pylon) {
		this.stressed_pylon = stressed_pylon;
	}


	/**
	 * @return the pylon1_N
	 */
	public float getPylon1_N() {
		return pylon1_N;
	}


	/**
	 * @param pylon1_N the pylon1_N to set
	 */
	private void setPylon1_N(float pylon1_N) {
		this.pylon1_N = pylon1_N;
	}


	/**
	 * @return the pylon1_M
	 */
	public float getPylon1_M() {
		return pylon1_M;
	}


	/**
	 * @param pylon1_M the pylon1_M to set
	 */
	private void setPylon1_M(float pylon1_M) {
		this.pylon1_M = pylon1_M;
	}

	/**
	 * @param pylon_N: the pylon1 N component
	 * @param pylon_M: the pylon1 M component
	 */
	public void setPylon1_N_M(float pylon_N, float pylon_M) {
		setPylon1_N(pylon_N);
		setPylon1_M(pylon_M);
	}
	
	/**
	 * @return the pylon2_N
	 */
	public float getPylon2_N() {
		return pylon2_N;
	}


	/**
	 * @param pylon2_N the pylon2_N to set
	 */
	private void setPylon2_N(float pylon2_N) {
		this.pylon2_N = pylon2_N;
	}


	/**
	 * @return the pylon2_M
	 */
	public float getPylon2_M() {
		return pylon2_M;
	}


	/**
	 * @param pylon2_M the pylon2_M to set
	 */
	private void setPylon2_M(float pylon2_M) {
		this.pylon2_M = pylon2_M;
	}

	/**
	 * @param pylon_N: the pylon2 N component
	 * @param pylon_M: the pylon2 M component
	 */
	public void setPylon2_N_M(float pylon_N, float pylon_M) {
		setPylon2_N(pylon_N);
		setPylon2_M(pylon_M);
	}
	
	/**
	 * @return the pylon3_N
	 */
	public float getPylon3_N() {
		return pylon3_N;
	}


	/**
	 * @param pylon3_N the pylon3_N to set
	 */
	private void setPylon3_N(float pylon3_N) {
		this.pylon3_N = pylon3_N;
	}


	/**
	 * @return the pylon3_M
	 */
	public float getPylon3_M() {
		return pylon3_M;
	}


	/**
	 * @param pylon3_M the pylon3_M to set
	 */
	private void setPylon3_M(float pylon3_M) {
		this.pylon3_M = pylon3_M;
	}

	/**
	 * @param pylon_N: the pylon3 N component
	 * @param pylon_M: the pylon3 M component
	 */
	public void setPylon3_N_M(float pylon_N, float pylon_M) {
		setPylon3_N(pylon_N);
		setPylon3_M(pylon_M);
	}
	
	/**
	 * @return the pylon4_N
	 */
	public float getPylon4_N() {
		return pylon4_N;
	}


	/**
	 * @param pylon4_N the pylon4_N to set
	 */
	private void setPylon4_N(float pylon4_N) {
		this.pylon4_N = pylon4_N;
	}


	/**
	 * @return the pylon4_M
	 */
	public float getPylon4_M() {
		return pylon4_M;
	}


	/**
	 * @param pylon4_M the pylon4_M to set
	 */
	private void setPylon4_M(float pylon4_M) {
		this.pylon4_M = pylon4_M;
	}

	/**
	 * @param pylon_N: the pylon4 N component
	 * @param pylon_M: the pylon4 M component
	 */
	public void setPylon4_N_M(float pylon_N, float pylon_M) {
		setPylon4_N(pylon_N);
		setPylon4_M(pylon_M);
	}
	
	/**
	 * @return the pylon5_N
	 */
	public float getPylon5_N() {
		return pylon5_N;
	}


	/**
	 * @param pylon5_N the pylon5_N to set
	 */
	private void setPylon5_N(float pylon5_N) {
		this.pylon5_N = pylon5_N;
	}


	/**
	 * @return the pylon5_M
	 */
	public float getPylon5_M() {
		return pylon5_M;
	}


	/**
	 * @param pylon5_M the pylon5_M to set
	 */
	private void setPylon5_M(float pylon5_M) {
		this.pylon5_M = pylon5_M;
	}

	/**
	 * @param pylon_N: the pylon5 N component
	 * @param pylon_M: the pylon5 M component
	 */
	public void setPylon5_N_M(float pylon_N, float pylon_M) {
		setPylon5_N(pylon_N);
		setPylon5_M(pylon_M);
	}
	
	/**
	 * @return the pylon6_N
	 */
	public float getPylon6_N() {
		return pylon6_N;
	}


	/**
	 * @param pylon6_N the pylon6_N to set
	 */
	private void setPylon6_N(float pylon6_N) {
		this.pylon6_N = pylon6_N;
	}


	/**
	 * @return the pylon6_M
	 */
	public float getPylon6_M() {
		return pylon6_M;
	}


	/**
	 * @param pylon6_M the pylon6_M to set
	 */
	private void setPylon6_M(float pylon6_M) {
		this.pylon6_M = pylon6_M;
	}
	
	/**
	 * @param pylon_N: the pylon6 N component
	 * @param pylon_M: the pylon6 M component
	 */
	public void setPylon6_N_M(float pylon_N, float pylon_M) {
		setPylon6_N(pylon_N);
		setPylon6_M(pylon_M);
	}
}
