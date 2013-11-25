package dsd.model.calculation;

public class Force {
	
	private float n;
	private float tx;
	private float ty;
	private float qy;
	private float mx;
	private float my;
	
	public Force()
	{
		this.n = 0;
		this.tx = 0;
		this.ty = 0;
		this.qy = 0;
		this.mx = 0;
		this.my = 0;
	}
	
	public Force(float n, float tx, float ty, float qy, float mx)
	{
		this.n = n;
		this.tx = tx;
		this.ty = ty;
		this.qy = qy;
		this.mx = mx;
	}
	
	public Force(float n, float tx, float ty, float qy, float mx, float my)
	{
		this.n = n;
		this.tx = tx;
		this.ty = ty;
		this.qy = qy;
		this.mx = mx;
		this.my = my;
	}

	/**
	 * @return the n
	 */
	public float getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(float n) {
		this.n = n;
	}

	/**
	 * @return the tx
	 */
	public float getTx() {
		return tx;
	}

	/**
	 * @param tx the tx to set
	 */
	public void setTx(float tx) {
		this.tx = tx;
	}

	/**
	 * @return the ty
	 */
	public float getTy() {
		return ty;
	}

	/**
	 * @param ty the ty to set
	 */
	public void setTy(float ty) {
		this.ty = ty;
	}

	/**
	 * @return the qy
	 */
	public float getQy() {
		return qy;
	}

	/**
	 * @param qy the qy to set
	 */
	public void setQy(float qy) {
		this.qy = qy;
	}

	/**
	 * @return the mx
	 */
	public float getMx() {
		return mx;
	}

	/**
	 * @param mx the mx to set
	 */
	public void setMx(float mx) {
		this.mx = mx;
	}
	
	/**
	 * @return the mx
	 */
	public float getMy() {
		return my;
	}

	/**
	 * @param mx the mx to set
	 */
	public void setMy(float my) {
		this.my = my;
	}
}
