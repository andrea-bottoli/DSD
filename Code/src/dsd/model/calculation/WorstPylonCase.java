package dsd.model.calculation;

public class WorstPylonCase{

	private Pylon pylon;
	private int comboNumber;
	private final int position;
	
	public WorstPylonCase(int position)
	{
		this.position = position;
	}
	
	public WorstPylonCase(Pylon pylon, int comboNumber, int pylonNumber) {
		super();
		this.pylon = pylon;
		this.comboNumber = comboNumber;
		this.position = getPosition(pylonNumber);
	}
	
	
	private int getPosition(int pylonNumber)
	{
		int temp;
		
		switch (pylonNumber)
		{
		case 1:
			temp = 0; break;
		case 2:
			temp = 1; break;
		case 3:
			temp = 2; break;
		case 4:
			temp = 3; break;
		case 5:
			temp = 4; break;
		case 6:
			temp = 5; break;
		default:
			temp = -1;
		}
		
		return temp;
	}
	
	
	
	/**
	 * @return the safetyFactor
	 */
	public float getSafetyFactor() {
		return this.pylon.getSafetyFactor();
	}

	/**
	 * @param safetyFactor the safetyFactor to set
	 */
	public void setSafetyFactor(float safetyFactor) {
		this.pylon.setSafetyFactor(safetyFactor);
	}

	/**
	 * @return the comboNumber
	 */
	public int getComboNumber() {
		return comboNumber;
	}

	/**
	 * @param comboNumber the comboNumber to set
	 */
	public void setComboNumber(int comboNumber) {
		this.comboNumber = comboNumber;
	}

	/**
	 * @param pylon the pylon to set
	 */
	public void setPylon(Pylon pylon) {
		this.pylon = pylon;
	}
	
	/**
	 * @return the pylonNumber
	 */
	public int getPylonNumber() {
		return this.pylon.getPylonNumber();
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getN() {
		return this.pylon.getN();
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getM() {
		return this.pylon.getM();
	}
	
	
	public int getPosition()
	{
		return this.position;
	}
}
