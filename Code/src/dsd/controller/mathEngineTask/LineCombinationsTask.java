/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brcic, Dzana Kujan, Nikola Radisavljevic, Jorn Tillmanns, Miraldi Fifo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package dsd.controller.mathEngineTask;

import dsd.model.calculation.Combination;
import dsd.model.calculation.LineForcesMatrix;
import dsd.model.calculation.LineForces;
import dsd.model.calculation.PlankForces;

public class LineCombinationsTask implements Runnable{
	
	private LineForcesMatrix lineForcesMatrix;
	private LineForces lineForces;
	private PlankForces plankForces;
	private float n=0, tx=0, ty=0, qy=0, mx=0;
	
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
			
			
	public LineCombinationsTask(PlankForces plankForces, LineForcesMatrix lineForcesMatrix, LineForces lineForces)
	{
		this.lineForcesMatrix = lineForcesMatrix;
		this.lineForces = lineForces;
		this.plankForces = plankForces;
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
		
		for(Combination c : this.lineForces.getComboList())
		{
			clearVariables();
			j = c.getCombinationNumber()-1;
			
			for(int i=0; i<this.components[j].length ; i++)
			{
				this.n  += this.lineForcesMatrix.getForcesList().get(this.components[j][i]).getN();
				this.tx += this.lineForcesMatrix.getForcesList().get(this.components[j][i]).getTx();
				this.ty += this.lineForcesMatrix.getForcesList().get(this.components[j][i]).getTy();
				this.qy += this.lineForcesMatrix.getForcesList().get(this.components[j][i]).getQy();
				this.mx += this.lineForcesMatrix.getForcesList().get(this.components[j][i]).getMx();
			}
			
			c.setN(this.n);
			c.setStackWeight(this.plankForces.getStackWeight()/2);
			c.setTx(this.tx);
			c.setTy(this.ty);
			c.setQy(this.qy);
			c.setMx(this.mx);
		}
	}
}
