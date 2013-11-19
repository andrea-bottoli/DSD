package dsd.model.calculation;

import java.util.ArrayList;

import dsd.controller.CalculationsController;

public class LineForces
{
	private Combination combo01A =null;
	private Combination combo01B =null;
	private Combination combo02A =null;
	private Combination combo02B =null;
	private Combination combo03A =null;
	private Combination combo03B =null;
	private Combination combo04A =null;
	private Combination combo04B =null;
	private Combination combo05A =null;
	private Combination combo05B =null;
	private Combination combo06A =null;
	private Combination combo06B =null;
	private Combination combo07A =null;
	private Combination combo07B =null;
	private Combination combo08A =null;
	private Combination combo08B =null;
	private Combination combo09A =null;
	private Combination combo09B =null;
	private Combination combo10A =null;
	private Combination combo10B =null;
	private Combination combo11A =null;
	private Combination combo11B =null;
	private Combination combo12A =null;
	private Combination combo12B =null;
	private Combination combo13A =null;
	private Combination combo13B =null;
	private Combination combo14A =null;
	private Combination combo14B =null;
	private Combination combo15A =null;
	private Combination combo15B =null;
	private Combination combo16A =null;
	private Combination combo16B =null;
	private Combination combo17A =null;
	private Combination combo17B =null;
	
	private ArrayList<Combination> comboList = null;
	
	public LineForces()
	{
		combo01A = new Combination(Boolean.FALSE, Boolean.FALSE);
		combo01B = new Combination(Boolean.FALSE, Boolean.TRUE);
		combo02A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo02B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo03A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo03B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo04A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo04B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo05A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo05B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo06A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo06B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo07A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo07B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo08A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo08B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo09A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo09B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo10A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo10B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo11A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo11B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo12A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo12B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo13A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo13B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo14A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo14B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo15A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo15B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo16A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo16B = new Combination(Boolean.TRUE, Boolean.TRUE);
		combo17A = new Combination(Boolean.TRUE, Boolean.FALSE);
		combo17B = new Combination(Boolean.TRUE, Boolean.TRUE);
		
		comboList = new ArrayList<Combination>();
		comboList.add(combo01A);
		comboList.add(combo01B);
		comboList.add(combo02A);
		comboList.add(combo02B);
		comboList.add(combo03A);
		comboList.add(combo03B);
		comboList.add(combo04A);
		comboList.add(combo04B);
		comboList.add(combo05A);
		comboList.add(combo05B);
		comboList.add(combo06A);
		comboList.add(combo06B);
		comboList.add(combo07A);
		comboList.add(combo07B);
		comboList.add(combo08A);
		comboList.add(combo08B);
		comboList.add(combo09A);
		comboList.add(combo09B);
		comboList.add(combo10A);
		comboList.add(combo10B);
		comboList.add(combo11A);
		comboList.add(combo11B);
		comboList.add(combo12A);
		comboList.add(combo12B);
		comboList.add(combo13A);
		comboList.add(combo13B);
		comboList.add(combo14A);
		comboList.add(combo14B);
		comboList.add(combo15A);
		comboList.add(combo15B);
		comboList.add(combo16A);
		comboList.add(combo16B);
		comboList.add(combo17A);
		comboList.add(combo17B);
	}

	/**
	 * @return the comboList
	 */
	public ArrayList<Combination> getComboList() {
		return comboList;
	}
}
