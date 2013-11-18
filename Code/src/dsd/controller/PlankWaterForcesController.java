package dsd.controller;

import dsd.calculations.MathEngine;

public class PlankWaterForcesController implements Runnable{
	
	private CalculationsController calculationControl = null;
	
	public PlankWaterForcesController(CalculationsController calContr)
	{
		this.calculationControl = calContr;
	}
	
	@Override
	public void run() {
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
		
		float lFlowRate, lWaterSpeed, lAs, lHs, lBs, lSwater;
		
		/*##############################
		 *CHANGE 17 WITH Hwater1, 22 WITH Hwater2 and 25.3 WITH Hmax
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if(this.calculationControl.getInstrumentsData().getIdro1()<17)
		{
			/*##############################
			 *CHANGE 1 WITH a1, 2 WITH b1 and 3 with c1
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, this.calculationControl.getInstrumentsData().getIdro1(), 2, 3);
		}else if(this.calculationControl.getInstrumentsData().getIdro1()<22)
		{
			/*##############################
			 *CHANGE 1 WITH a2, 2 WITH b2 and 3 with c2
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, this.calculationControl.getInstrumentsData().getIdro1(), 2, 3);			
		}else if(this.calculationControl.getInstrumentsData().getIdro1()<25.3)
		{
			/*##############################
			 *CHANGE 1 WITH a3, 2 WITH b3 and 3 with c3
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			lFlowRate = MathEngine.FlowRate(1, this.calculationControl.getInstrumentsData().getIdro1(), 2, 3);
		}
		
		/*##############################
		 *CHANGE 1 WITH a, 2 WITH b and 3 with c
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lWaterSpeed = MathEngine.WaterSpeed(1, this.calculationControl.getInstrumentsData().getIdro1(), 2, 3);
		
		/*##############################
		 *CHANGE 1 WITH bottom_ref
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if(this.calculationControl.getInstrumentsData().getSonar1()<1)
		{
			lHs=this.calculationControl.getInstrumentsData().getIdro2() - 1;
		}else
		{
			lHs=this.calculationControl.getInstrumentsData().getIdro2() - this.calculationControl.getInstrumentsData().getSonar1();
		}
		
		/*##############################
		 *CHANGE 1 WITH Cspan, 2 WITH Cd0, 3 WITH RHOwater
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lBs = 1;
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithOutDebris(2, 3, lAs, lWaterSpeed);
		this.calculationControl.getPlankForces().setHydrodynamicThrustWithOutDebris(lSwater);
		
		/*##############################
		 *CHANGE 666 WITH Dpylon, 2WITH Cd1, 3 WITH RHOwater, 4 WITH BetaA
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		lBs = 2*666;
		lAs = lBs*lHs;
		lSwater = MathEngine.HydrodynamicThrustWithDebris(2, 3, lAs, 4, lWaterSpeed);
		this.calculationControl.getPlankForces().setHydrodynamicThrustWithDebris(lSwater);
	}
}
