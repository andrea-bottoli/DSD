package dsd.controller.mathEngineTask;

import dsd.controller.CalculationsController;
import dsd.model.calculation.Combination;
import dsd.model.calculation.LineForcesMatrix;
import dsd.model.calculation.LineForces;

public class CombinationsCalculationTask implements Runnable{
	
	private CalculationsController calculationController = null;
	private int side;
	private float n=0, tx=0, ty=0, qy=0, mx=0;
	private LineForcesMatrix forcesMatrix = null;
	private LineForces lineForces = null;
	
	/*
	 * DEFINITIONS OF WHICH COMPONENTS OF LINE_FORCES_MATRIX HAVE TO BE USED FOR EACH COMBINATION CALCULATION
	 */
	private int components[][] =   {{LineForcesMatrix.PS,LineForcesMatrix.VT0,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.VT0,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A110,LineForcesMatrix.FR01,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A110,LineForcesMatrix.FR01,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A110,LineForcesMatrix.FR02,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A110,LineForcesMatrix.FR02,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A120,LineForcesMatrix.FR01,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A120,LineForcesMatrix.FR01,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A120,LineForcesMatrix.FR02,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A120,LineForcesMatrix.FR02,LineForcesMatrix.VT1A1,LineForcesMatrix.AQD1},
	
									{LineForcesMatrix.PS,LineForcesMatrix.A210,LineForcesMatrix.FR01,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A210,LineForcesMatrix.FR01,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A210,LineForcesMatrix.FR02,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A210,LineForcesMatrix.FR02,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A220,LineForcesMatrix.FR01,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A220,LineForcesMatrix.FR01,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A220,LineForcesMatrix.FR02,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A220,LineForcesMatrix.FR02,LineForcesMatrix.VT1A2,LineForcesMatrix.AQD1},
	
									{LineForcesMatrix.PS,LineForcesMatrix.A311,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A311,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A311,LineForcesMatrix.FR02,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A311,LineForcesMatrix.FR02,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A312,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A312,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A312,LineForcesMatrix.FR02,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A312,LineForcesMatrix.FR02,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A321,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A321,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A321,LineForcesMatrix.FR02,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A321,LineForcesMatrix.FR02,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD1},
									{LineForcesMatrix.PS,LineForcesMatrix.A322,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A322,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A322,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0},
									{LineForcesMatrix.PS,LineForcesMatrix.A322,LineForcesMatrix.FR01,LineForcesMatrix.VT1A3,LineForcesMatrix.AQD0}};
			
			
	public CombinationsCalculationTask(CalculationsController calculationController, int side)
	{
		this.calculationController = calculationController;
		this.side = side;
	}
	
	@Override
	public void run() {
		CombinationsCalculation();
	}
	
	/**
	 * this method clear the variables
	 */
	private void clearVariables(){
		this.n=0;
		this.tx=0;
		this.ty=0;
		this.qy=0;
		this.mx=0;
	}
	/**
	 * THis method calculates the resulting five forces N, Ty, Tx, qy, Mx
	 * of each combinations
	 */
	private void CombinationsCalculation()
	{	
		int j=0;
		
		if(side==0)
		{
			forcesMatrix = this.calculationController.getMantovaLineMatrix();
			lineForces = this.calculationController.getMantovaLineForces();
		}else if(side==1)
		{
			forcesMatrix = this.calculationController.getModenaLineForcesMatrix();
			lineForces = this.calculationController.getModenaLineForces();
		}
		
		for(Combination c : this.lineForces.getComboList())
		{
			clearVariables();
			j = c.getCombinationNumber()-1;
			
			for(int i=0; i<this.components[j].length ; i++)
			{
				this.n  += forcesMatrix.getForcesList().get(this.components[j][i]).getN();
				this.tx += forcesMatrix.getForcesList().get(this.components[j][i]).getTx();
				this.ty += forcesMatrix.getForcesList().get(this.components[j][i]).getTy();
				this.qy += forcesMatrix.getForcesList().get(this.components[j][i]).getQy();
				this.mx += forcesMatrix.getForcesList().get(this.components[j][i]).getMx();
			}
			
			c.setN(this.n + (this.calculationController.getPlankForces().getStackWeight()/2));
			c.setTx(this.tx);
			c.setTy(this.ty);
			c.setQy(this.qy);
			c.setMx(this.mx);
		}
	}
}
