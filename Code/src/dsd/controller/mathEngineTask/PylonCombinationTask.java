package dsd.controller.mathEngineTask;

import dsd.controller.CalculationsController;

public class PylonCombinationTask implements Runnable {

	private CalculationsController calculationController;
	
	public PylonCombinationTask(CalculationsController calculationController)
	{
		this.calculationController = calculationController;
	}
	
	@Override
	public void run()
	{
		pylonCalculation();
	}
	
	/**
	 * Some calculation
	 */
	private void pylonCalculation()
	{
		
	}

}
