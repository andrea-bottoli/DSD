package dsd.model.calculation;

import java.util.ArrayList;

public class WorstCase {
	
	private boolean traffic;
	private boolean debris;
	
	private WorstPylonCase worstPylon1;
	private WorstPylonCase worstPylon2;
	private WorstPylonCase worstPylon3;
	private WorstPylonCase worstPylon4;
	private WorstPylonCase worstPylon5;
	private WorstPylonCase worstPylon6;
	
	private ArrayList<WorstPylonCase> worstList;
	
	public WorstCase(boolean traffic, boolean debris)
	{
		this.traffic = traffic;
		this.debris = debris;
		
		this.worstPylon1 = new WorstPylonCase(1);
		this.worstPylon2 = new WorstPylonCase(2);
		this.worstPylon3 = new WorstPylonCase(3);
		this.worstPylon4 = new WorstPylonCase(4);
		this.worstPylon5 = new WorstPylonCase(5);
		this.worstPylon6 = new WorstPylonCase(6);
		
		this.worstList = new ArrayList<WorstPylonCase>();
		this.worstList.add(worstPylon1);
		this.worstList.add(worstPylon2);
		this.worstList.add(worstPylon3);
		this.worstList.add(worstPylon4);
		this.worstList.add(worstPylon5);
		this.worstList.add(worstPylon6);
	}
		
	/**
	 * @return the traffic
	 */
	public boolean getTraffic() {
		return this.traffic;
	}
	
	/**
	 * @return the debris
	 */
	public boolean getDebris() {
		return this.debris;
	}

	/**
	 * @return the worstList
	 */
	public ArrayList<WorstPylonCase> getWorstList() {
		return worstList;
	}
	
	/**
	 * @param pylon the pylon to be setted
	 */
	public void setPylon1(Pylon pylon){
		this.worstPylon1.setPylon(pylon);
		setPylonListInternal(worstPylon1, getPosition(this.worstPylon1.getPylonNumber()));
	}
	
	/**
	 * @param pylon the pylon to be setted
	 */
	public void setPylon2(Pylon pylon){
		this.worstPylon2.setPylon(pylon);
		setPylonListInternal(worstPylon2, getPosition(this.worstPylon2.getPylonNumber()));
	}
	
	/**
	 * @param pylon the pylon to be setted
	 */
	public void setPylon3(Pylon pylon){
		this.worstPylon3.setPylon(pylon);
		setPylonListInternal(worstPylon3, getPosition(this.worstPylon3.getPylonNumber()));
	}
	
	/**
	 * @param pylon the pylon to be setted
	 */
	public void setPylon4(Pylon pylon){
		this.worstPylon4.setPylon(pylon);
		setPylonListInternal(worstPylon4, getPosition(this.worstPylon4.getPylonNumber()));
	}
	
	/**
	 * @param pylon the pylon to be setted
	 */
	public void setPylon5(Pylon pylon){
		this.worstPylon5.setPylon(pylon);
		setPylonListInternal(worstPylon5, getPosition(this.worstPylon5.getPylonNumber()));
	}
	
	/**
	 * @param pylon the pylon to be setted
	 */
	public void setPylon6(Pylon pylon){
		this.worstPylon6.setPylon(pylon);
		setPylonListInternal(worstPylon6, getPosition(this.worstPylon6.getPylonNumber()));
	}
	
	
	
	
	/*
	 * PRIVATE SETTERS AND GETTERS
	 */
	/**
	 * @param index indicate which pylon you asked for
	 * @return the N force acting on the pylon
	 */
	private float getNpylonInternal(int index)
	{
		return (this.worstList.get(index).getN());
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the M force acting on the pylon
	 */
	private float getMpylonInternal(int index)
	{
		return (this.worstList.get(index).getM());
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the pylon number
	 */
	private float getPylonNumberInternal(int index)
	{
		return (this.worstList.get(index).getPylonNumber());
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the safety factor for the pylon in position index
	 */
	private float getSafetyFactorInternal(int index)
	{
		return (this.worstList.get(index).getSafetyFactor());
	}
	
	
	/**
	 * @param index indicate which pylon you asked for
	 * @param safetyFactor, the safety factor to be setted
	 */
	private void setSafetyFactorInternal(float safetyFactor, int index)
	{
		this.worstList.get(index).setSafetyFactor(safetyFactor);
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the combination number
	 */
	private float getPylonComboNumberInternal(int index)
	{
		return (this.worstList.get(index).getPylonNumber());
	}
	
	
	/**
	 * @param index indicate which pylon you asked for
	 * @param comboNumber, the combination number to be setted
	 */
	private void setPylonComboNumberInternal(int comboNumber, int index)
	{
		this.worstList.get(index).setComboNumber(comboNumber);
	}
	
	
	/**
	 * @param pylon is the pylon to be saved,
	 * @param index is the index into the list
	 * @param comboNumber, the combination number to be setted
	 */
	private void setPylonInternal(Pylon pylon)
	{
		switch(pylon.getPylonNumber())
		{
		case 1:
			this.setPylon1(pylon);
			break;
		case 2:
			this.setPylon2(pylon);
			break;
		case 3:
			this.setPylon3(pylon);
			break;
		case 4:
			this.setPylon4(pylon);
			break;
		case 5:
			this.setPylon5(pylon);
			break;
		case 6:
			this.setPylon6(pylon);
			break;
		}
	}
	
	private void setPylonListInternal(WorstPylonCase worstPylonCase, int index)
	{
		worstList.set(index, worstPylonCase);
	}
	
	/*
	 * END PRIVATE GETTERS & SETTERS
	 */
	
	
	
	
	
	
	/*
	 * SETTERS & GETTERS FOR PYLON 1
	 */
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getNpylon1()
	{
		return getNpylon(this.worstPylon1.getPosition());
	}
	
	/**
	 * @return the M force acting on the pylon
	 */
	public float getMpylon1()
	{
		return getMpylon(this.worstPylon1.getPosition());
	}
	
	/**
	 * @return the pylon number
	 */
	public float getPylon1Number()
	{
		return getPylonNumber(this.worstPylon1.getPosition());
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getPylon1SafetyFactor()
	{
		return getSafetyFactor(this.worstPylon1.getPosition());
	}
	
	
	/**
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setPylon1SafetyFactor(float safetyFactor)
	{
		setSafetyFactor(safetyFactor, this.worstPylon1.getPosition());
	}
	
	/**
	 * @return the combination number
	 */
	public float getPylon1ComboNumber()
	{
		return getPylonComboNumber(this.worstPylon1.getPosition());
	}
	
	
	/**
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon1ComboNumber(int comboNumber)
	{
		setPylonComboNumber(comboNumber, this.worstPylon1.getPosition());
	}
	
	
	
	
	/*
	 * SETTERS & GETTERS FOR PYLON 2
	 */
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getNpylon2()
	{
		return getNpylon(this.worstPylon2.getPosition());
	}
	
	/**
	 * @return the M force acting on the pylon
	 */
	public float getMpylon2()
	{
		return getMpylon(this.worstPylon2.getPosition());
	}
	
	/**
	 * @return the pylon number
	 */
	public float getPylon2Number()
	{
		return getPylonNumber(this.worstPylon2.getPosition());
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getPylon2SafetyFactor()
	{
		return getSafetyFactor(this.worstPylon2.getPosition());
	}
	
	
	/**
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setPylon2SafetyFactor(float safetyFactor)
	{
		setSafetyFactor(safetyFactor, this.worstPylon2.getPosition());
	}
	
	/**
	 * @return the combination number
	 */
	public float getPylon2ComboNumber()
	{
		return getPylonComboNumber(this.worstPylon2.getPosition());
	}
	
	
	/**
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon2ComboNumber(int comboNumber)
	{
		setPylonComboNumber(comboNumber, this.worstPylon2.getPosition());
	}
	
	
	
	
	/*
	 * SETTERS & GETTERS FOR PYLON 3
	 */
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getNpylon3()
	{
		return getNpylon(this.worstPylon3.getPosition());
	}
	
	/**
	 * @return the M force acting on the pylon
	 */
	public float getMpylon3()
	{
		return getMpylon(this.worstPylon3.getPosition());
	}
	
	/**
	 * @return the pylon number
	 */
	public float getPylon3Number()
	{
		return getPylonNumber(this.worstPylon3.getPosition());
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getPylon3SafetyFactor()
	{
		return getSafetyFactor(this.worstPylon3.getPosition());
	}
	
	
	/**
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setPylon3SafetyFactor(float safetyFactor)
	{
		setSafetyFactor(safetyFactor, this.worstPylon3.getPosition());
	}
	
	/**
	 * @return the combination number
	 */
	public float getPylon3ComboNumber()
	{
		return getPylonComboNumber(this.worstPylon3.getPosition());
	}
	
	
	/**
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon3ComboNumber(int comboNumber)
	{
		setPylonComboNumber(comboNumber, this.worstPylon3.getPosition());
	}
	
	
	
	
	/*
	 * SETTERS & GETTERS FOR PYLON 4
	 */
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getNpylon4()
	{
		return getNpylon(this.worstPylon4.getPosition());
	}
	
	/**
	 * @return the M force acting on the pylon
	 */
	public float getMpylon4()
	{
		return getMpylon(this.worstPylon4.getPosition());
	}
	
	/**
	 * @return the pylon number
	 */
	public float getPylon4Number()
	{
		return getPylonNumber(this.worstPylon4.getPosition());
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getPylon4SafetyFactor()
	{
		return getSafetyFactor(this.worstPylon4.getPosition());
	}
	
	
	/**
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setPylon4SafetyFactor(float safetyFactor)
	{
		setSafetyFactor(safetyFactor, this.worstPylon4.getPosition());
	}
	
	/**
	 * @return the combination number
	 */
	public float getPylon4ComboNumber()
	{
		return getPylonComboNumber(this.worstPylon4.getPosition());
	}
	
	
	/**
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon4ComboNumber(int comboNumber)
	{
		setPylonComboNumber(comboNumber, this.worstPylon4.getPosition());
	}
	
	
	
	
	
	/*
	 * SETTERS & GETTERS FOR PYLON 5
	 */
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getNpylon5()
	{
		return getNpylon(this.worstPylon5.getPosition());
	}
	
	/**
	 * @return the M force acting on the pylon
	 */
	public float getMpylon5()
	{
		return getMpylon(this.worstPylon5.getPosition());
	}
	
	/**
	 * @return the pylon number
	 */
	public float getPylon5Number()
	{
		return getPylonNumber(this.worstPylon5.getPosition());
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getPylon5SafetyFactor()
	{
		return getSafetyFactor(this.worstPylon5.getPosition());
	}
	
	
	/**
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setPylon5SafetyFactor(float safetyFactor)
	{
		setSafetyFactor(safetyFactor, this.worstPylon5.getPosition());
	}
	
	/**
	 * @return the combination number
	 */
	public float getPylon5ComboNumber()
	{
		return getPylonComboNumber(this.worstPylon5.getPosition());
	}
	
	
	/**
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon5ComboNumber(int comboNumber)
	{
		setPylonComboNumber(comboNumber, this.worstPylon5.getPosition());
	}
	
	
	
	
	
	/*
	 * SETTERS & GETTERS FOR PYLON 6
	 */
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getNpylon6()
	{
		return getNpylonInternal(this.worstPylon6.getPosition());
	}
	
	/**
	 * @return the M force acting on the pylon
	 */
	public float getMpylon6()
	{
		return getMpylonInternal(this.worstPylon6.getPosition());
	}
	
	/**
	 * @return the pylon number
	 */
	public float getPylon6Number()
	{
		return getPylonNumberInternal(this.worstPylon6.getPosition());
	}
	
	/**
	 * @return the N force acting on the pylon
	 */
	public float getPylon6SafetyFactor()
	{
		return getSafetyFactorInternal(this.worstPylon6.getPosition());
	}
	
	
	/**
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setPylon6SafetyFactor(float safetyFactor)
	{
		setSafetyFactorInternal(safetyFactor, this.worstPylon6.getPosition());
	}
	
	/**
	 * @return the combination number
	 */
	public float getPylon6ComboNumber()
	{
		return getPylonComboNumberInternal(this.worstPylon6.getPosition());
	}
	
	
	/**
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon6ComboNumber(int comboNumber)
	{
		setPylonComboNumberInternal(comboNumber, this.worstPylon6.getPosition());
	}
	
	
	
	/*
	 * Internal methods
	 */
	private boolean exist(int pylonNumber)
	{
		for(WorstPylonCase wpc : this.worstList)
		{
			if(wpc.getPylonNumber() == pylonNumber){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @param pylonNumber
	 * @return the position into the array list
	 */
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
	
	
	/*
	 * GENERIC PUBLIC GETTERS & SETTERS
	 */
	/**
	 * @param index indicate which pylon you asked for
	 * @return the N force acting on the pylon
	 */
	public float getNpylon(int pylonNumber)
	{
		if(exist(pylonNumber)){
			return getNpylonInternal(getPosition(pylonNumber));
		}else{
			return -1;
		}		
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the M force acting on the pylon
	 */
	public float getMpylon(int pylonNumber)
	{
		if(exist(pylonNumber)){
			return getMpylonInternal(getPosition(pylonNumber));
		}else{
			return -1;
		}
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the pylon number
	 */
	public float getPylonNumber(int pylonNumber)
	{
		if(exist(pylonNumber)){
			return getPylonNumberInternal(getPosition(pylonNumber));
		}else{
			return -1;
		}
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the safety factor for the pylon in position index
	 */
	public float getSafetyFactor(int pylonNumber)
	{
		if(exist(pylonNumber)){
			return getSafetyFactorInternal(getPosition(pylonNumber));
		}else{
			return -1;
		}
	}
	
	
	/**
	 * @param index indicate which pylon you asked for
	 * @param safetyFactor, the safety factor to be setted
	 */
	public void setSafetyFactor(float safetyFactor, int pylonNumber)
	{
		if(exist(pylonNumber)){
			setSafetyFactorInternal(safetyFactor, getPosition(pylonNumber));
		}
	}
	
	/**
	 * @param index indicate which pylon you asked for
	 * @return the combination number
	 */
	public float getPylonComboNumber(int pylonNumber)
	{
		if(exist(pylonNumber)){
			return getPylonComboNumberInternal(getPosition(pylonNumber));
		}else{
			return -1;
		}
	}
	
	
	/**
	 * @param index indicate which pylon you asked for
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylonComboNumber(int comboNumber, int pylonNumber)
	{
		if(exist(pylonNumber)){
			setPylonComboNumberInternal(comboNumber, getPosition(pylonNumber));
		}
	}
	
	
	/**
	 * @param pylon is the pylon to be saved,
	 * @param comboNumber, the combination number to be setted
	 */
	public void setPylon(Pylon pylon)
	{
		if(exist(pylon.getPylonNumber())){
			setPylonInternal(pylon);
		}
	}
}
