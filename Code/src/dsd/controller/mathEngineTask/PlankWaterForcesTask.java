package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.PlankForces;

public class PlankWaterForcesTask implements Runnable{
	
	private InstrumentsData instrumentsData;
	private PlankForces plankForces;
	
	public PlankWaterForcesTask(InstrumentsData instrumentsData, PlankForces plankForces)
	{
		this.instrumentsData = instrumentsData;
		this.plankForces = plankForces;
	}
	
	
	@Override
	public void run()
	{
		CalculatePlankWaterForces();
	}
	
	/*
	 * This method calculates the three components
	 * of water force on the planking
	 */
	/**
	 * This method calculates the three components
	 * of water force on the planking: Q Flow rate,
	 * V Water speed, Sw Water Push
	 */
	private void CalculatePlankWaterForces() {
		
		float lFlowRate=0, lWaterSpeed=0, lAs=0, lHs=0, lBs=0, lSwater=0;
		
		/*##############################
		 *CHANGE 17 WITH Hwater1, 22 WITH Hwater2 and 25.3 WITH Hmax
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if(instrumentsData.getIdro1()<17)
		{
			/*##############################
			 *CHANGE 1 WITH a1, 2 WITH b1 and 3 with c1
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, instrumentsData.getIdro1(), 2, 3);
		}else if(instrumentsData.getIdro1()<22)
		{
			/*##############################
			 *CHANGE 1 WITH a2, 2 WITH b2 and 3 with c2
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, instrumentsData.getIdro1(), 2, 3);			
		}else if(instrumentsData.getIdro1()<25.3)
		{
			/*##############################
			 *CHANGE 1 WITH a3, 2 WITH b3 and 3 with c3
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, instrumentsData.getIdro1(), 2, 3);
		}
		plankForces.setFlowRate(lFlowRate);
		/*##############################
		 *CHANGE 1 WITH a, 2 WITH b and 3 with c
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWaterSpeed = MathEngine.WaterSpeed(1, instrumentsData.getIdro1(), 2, 3);
		
		/*##############################
		 *CHANGE 1 WITH bottom_ref
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if(instrumentsData.getSonar1()<1)
		{
			lHs=instrumentsData.getIdro1() - 1;
		}else
		{
			lHs=instrumentsData.getIdro1() - instrumentsData.getSonar1();
		}
		plankForces.setHs(lHs);
		/*##############################
		 *CHANGE 1 WITH Cspan, 2 WITH Cd0, 3 WITH RHOwater
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lBs = 1;
		plankForces.setBsWithOutDebris(lBs);
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithOutDebris(2, 3, lAs, lWaterSpeed);
		plankForces.setHydrodynamicThrustWithOutDebris(lSwater);
		
		/*##############################
		 *CHANGE 666 WITH Dpylon, 2WITH Cd1, 3 WITH RHOwater, 4 WITH BetaA
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lBs = 2*666;
		plankForces.setBsWithDebris(lBs);
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithDebris(2, 3, lAs, 4, lWaterSpeed);
		plankForces.setHydrodynamicThrustWithDebris(lSwater);
	}
}
