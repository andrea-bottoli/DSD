package dsd.controller.mathEngineTask;

import dsd.controller.CalculationsController;

public class MatrixFillTask implements Runnable{
	
	private CalculationsController calculationController = null;
	private int side;
	
	/**
	 * @param side: 0 = Mantova, 1 = Modena
	 */
	public MatrixFillTask(CalculationsController calculationController, int side)
	{
		this.calculationController = calculationController;
		this.side = side;
	}
	
	@Override
	public void run() {
		FillMatrix();
	}
	
	
	private void FillMatrix()
	{
		
	}
}
