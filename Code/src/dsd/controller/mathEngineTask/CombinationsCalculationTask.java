package dsd.controller.mathEngineTask;

import dsd.controller.CalculationsController;

public class CombinationsCalculationTask implements Runnable{
	
	private CalculationsController calculationController = null;
	
	/**
	 * @param side: 0 = Mantova, 1 = Modena
	 */
	public CombinationsCalculationTask(CalculationsController calculationController)
	{
		this.calculationController = calculationController;
	}
	
	@Override
	public void run() {
		CombinationsCalculation();
	}
	
	
	private void CombinationsCalculation()
	{
		
	}
}
