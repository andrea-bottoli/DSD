package dsd.model.calculation;

import java.util.ArrayList;

import dsd.controller.mathEngineTask.SafetyFactorTask;

public class SafetyFactor {
	
	/*
	 * COMBINATION ARE TD  ==> T:traffic  , D:debris
	 */
	//T=0 , D=0
	private SafetyFactorCombo safetyFactorCombo00;
	//T=0 , D=1
	private SafetyFactorCombo safetyFactorCombo01;
	//T=1 , D=0
	private SafetyFactorCombo safetyFactorCombo10;
	//T=1 , D=1
	private SafetyFactorCombo safetyFactorCombo11;
	
	private ArrayList<SafetyFactorCombo> safetyFactorComboList;
	
	
	public SafetyFactor() {
		this.safetyFactorCombo00 = new SafetyFactorCombo(Boolean.FALSE, Boolean.FALSE);
		this.safetyFactorCombo01 = new SafetyFactorCombo(Boolean.FALSE, Boolean.TRUE);
		this.safetyFactorCombo10 = new SafetyFactorCombo(Boolean.TRUE, Boolean.FALSE);
		this.safetyFactorCombo11 = new SafetyFactorCombo(Boolean.TRUE, Boolean.TRUE);
		
		this.safetyFactorComboList = new ArrayList<SafetyFactorCombo>();
		this.safetyFactorComboList.add(safetyFactorCombo00);
		this.safetyFactorComboList.add(safetyFactorCombo01);
		this.safetyFactorComboList.add(safetyFactorCombo10);
		this.safetyFactorComboList.add(safetyFactorCombo11);
	}


	/**
	 * @return the safetyFactorCombo00
	 */
	public SafetyFactorCombo getSafetyFactorCombo00() {
		return safetyFactorCombo00;
	}


	/**
	 * @return the safetyFactorCombo01
	 */
	public SafetyFactorCombo getSafetyFactorCombo01() {
		return safetyFactorCombo01;
	}


	/**
	 * @return the safetyFactorCombo10
	 */
	public SafetyFactorCombo getSafetyFactorCombo10() {
		return safetyFactorCombo10;
	}


	/**
	 * @return the safetyFactorCombo11
	 */
	public SafetyFactorCombo getSafetyFactorCombo11() {
		return safetyFactorCombo11;
	}
	
	/**
	 * this method returns the correct safety factor combo based on the T-D
	 * 
	 * @param traffic traffic status
	 * @param debris debris status
	 * @return the safety factor combo based on the T-D inputs.
	 */
	public SafetyFactorCombo getSpecificFactorCombo(Boolean traffic, Boolean debris)
	{
		for(SafetyFactorCombo sf : this.safetyFactorComboList)
		{
			if((sf.getTraffic() == traffic) && (sf.getDebris() == debris))
			{
				return sf;
			}
		}
		
		return null;
	}
}
