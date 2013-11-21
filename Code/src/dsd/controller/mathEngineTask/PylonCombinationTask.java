package dsd.controller.mathEngineTask;

import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.LineForces;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;


public class PylonCombinationTask implements Runnable {

	private LineForces lineForces;
	private PylonForces pylonForces;
	private InstrumentsData instrumetnsData;
	
	public PylonCombinationTask( InstrumentsData instrumetnsData, LineForces lineForces, PylonForces pylonForces)
	{
		this.instrumetnsData = instrumetnsData;
		this.lineForces = lineForces;
		this.pylonForces = pylonForces;
	}
	
	@Override
	public void run()
	{
		pylonCalculation();
	}
	
	/**
	 * Calculations the distributions of the line forces
	 * due to the combinations, on the single pylons.
	 */
	private void pylonCalculation()
	{
		
	}

}
