package dsd.model.calculation;

import java.util.ArrayList;

public class PylonForces
{
	
	private PylonCombination pylonCombo01A =null;
	private PylonCombination pylonCombo01B =null;
	private PylonCombination pylonCombo02A =null;
	private PylonCombination pylonCombo02B =null;
	private PylonCombination pylonCombo03A =null;
	private PylonCombination pylonCombo03B =null;
	private PylonCombination pylonCombo04A =null;
	private PylonCombination pylonCombo04B =null;
	private PylonCombination pylonCombo05A =null;
	private PylonCombination pylonCombo05B =null;
	private PylonCombination pylonCombo06A =null;
	private PylonCombination pylonCombo06B =null;
	private PylonCombination pylonCombo07A =null;
	private PylonCombination pylonCombo07B =null;
	private PylonCombination pylonCombo08A =null;
	private PylonCombination pylonCombo08B =null;
	private PylonCombination pylonCombo09A =null;
	private PylonCombination pylonCombo09B =null;
	private PylonCombination pylonCombo10A =null;
	private PylonCombination pylonCombo10B =null;
	private PylonCombination pylonCombo11A =null;
	private PylonCombination pylonCombo11B =null;
	private PylonCombination pylonCombo12A =null;
	private PylonCombination pylonCombo12B =null;
	private PylonCombination pylonCombo13A =null;
	private PylonCombination pylonCombo13B =null;
	private PylonCombination pylonCombo14A =null;
	private PylonCombination pylonCombo14B =null;
	private PylonCombination pylonCombo15A =null;
	private PylonCombination pylonCombo15B =null;
	private PylonCombination pylonCombo16A =null;
	private PylonCombination pylonCombo16B =null;
	private PylonCombination pylonCombo17A =null;
	private PylonCombination pylonCombo17B =null;
	
	private ArrayList<PylonCombination> pylonComboList = null;
	
	public PylonForces(LineForces lineForces, int side)
	{
		
		pylonCombo01A = new PylonCombination(side);
		pylonCombo01B = new PylonCombination(side);
		pylonCombo02A = new PylonCombination(side);
		pylonCombo02B = new PylonCombination(side);
		pylonCombo03A = new PylonCombination(side);
		pylonCombo03B = new PylonCombination(side);
		pylonCombo04A = new PylonCombination(side);
		pylonCombo04B = new PylonCombination(side);
		pylonCombo05A = new PylonCombination(side);
		pylonCombo05B = new PylonCombination(side);
		pylonCombo06A = new PylonCombination(side);
		pylonCombo06B = new PylonCombination(side);
		pylonCombo07A = new PylonCombination(side);
		pylonCombo07B = new PylonCombination(side);
		pylonCombo08A = new PylonCombination(side);
		pylonCombo08B = new PylonCombination(side);
		pylonCombo09A = new PylonCombination(side);
		pylonCombo09B = new PylonCombination(side);
		pylonCombo10A = new PylonCombination(side);
		pylonCombo10B = new PylonCombination(side);
		pylonCombo11A = new PylonCombination(side);
		pylonCombo11B = new PylonCombination(side);
		pylonCombo12A = new PylonCombination(side);
		pylonCombo12B = new PylonCombination(side);
		pylonCombo13A = new PylonCombination(side);
		pylonCombo13B = new PylonCombination(side);
		pylonCombo14A = new PylonCombination(side);
		pylonCombo14B = new PylonCombination(side);
		pylonCombo15A = new PylonCombination(side);
		pylonCombo15B = new PylonCombination(side);
		pylonCombo16A = new PylonCombination(side);
		pylonCombo16B = new PylonCombination(side);
		pylonCombo17A = new PylonCombination(side);
		pylonCombo17B = new PylonCombination(side);
		
		pylonComboList = new ArrayList<PylonCombination>();
		
		pylonComboList.add(pylonCombo01A);
		pylonComboList.add(pylonCombo01B);
		pylonComboList.add(pylonCombo02A);
		pylonComboList.add(pylonCombo02B);
		pylonComboList.add(pylonCombo03A);
		pylonComboList.add(pylonCombo03B);
		pylonComboList.add(pylonCombo04A);
		pylonComboList.add(pylonCombo04B);
		pylonComboList.add(pylonCombo05A);
		pylonComboList.add(pylonCombo05B);
		pylonComboList.add(pylonCombo06A);
		pylonComboList.add(pylonCombo06B);
		pylonComboList.add(pylonCombo07A);
		pylonComboList.add(pylonCombo07B);
		pylonComboList.add(pylonCombo08A);
		pylonComboList.add(pylonCombo08B);
		pylonComboList.add(pylonCombo09A);
		pylonComboList.add(pylonCombo09B);
		pylonComboList.add(pylonCombo10A);
		pylonComboList.add(pylonCombo10B);
		pylonComboList.add(pylonCombo11A);
		pylonComboList.add(pylonCombo11B);
		pylonComboList.add(pylonCombo12A);
		pylonComboList.add(pylonCombo12B);
		pylonComboList.add(pylonCombo13A);
		pylonComboList.add(pylonCombo13B);
		pylonComboList.add(pylonCombo14A);
		pylonComboList.add(pylonCombo14B);
		pylonComboList.add(pylonCombo15A);
		pylonComboList.add(pylonCombo15B);
		pylonComboList.add(pylonCombo16A);
		pylonComboList.add(pylonCombo16B);
		pylonComboList.add(pylonCombo17A);
		pylonComboList.add(pylonCombo17B);
		
		int i=0;
		
		for(Combination c : lineForces.getComboList())
		{
			pylonComboList.get(i).setCombination(c);
			i++;
		}
		
	}
	
	/**
	 * @return the pylonComboList
	 */
	public ArrayList<PylonCombination> getPylonComboList()
	{
		return pylonComboList;
	}
	
	
	/**
	 * @return the pylonComboList with that specific set up of traffic and debris
	 */
	public ArrayList<PylonCombination> getPylonComboList(boolean traffic, boolean debris)
	{
		ArrayList<PylonCombination> list = new ArrayList<PylonCombination>();
		
		for(PylonCombination p : this.pylonComboList)
		{
			if((p.getCombination().getTraffic() == traffic) && (p.getCombination().getDebris() == debris))
			{
				list.add(p);
			}
		}
		
		return list;
	}
	
}
