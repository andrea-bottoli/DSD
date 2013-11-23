package dsd.controller.mathEngineTask;

import dsd.controller.ParametersController;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.LineForcesMatrix;
import dsd.model.calculation.PlankForces;
import dsd.model.enums.eParameter;

public class MatrixFillTask implements Runnable{
	
	private LineForcesMatrix lineForcesMatrix;
	private InstrumentsData instrumentsData;
	private PlankForces plankForces;
	private int side;
	
	/**
	 * @param side: 0 = Mantova, 1 = Modena
	 */
	public MatrixFillTask(InstrumentsData instrumentsData, PlankForces plankForces, LineForcesMatrix lineForcesMatrix, int side)
	{
		this.instrumentsData = instrumentsData;
		this.plankForces = plankForces;
		this.lineForcesMatrix = lineForcesMatrix;
		this.side = side;
	}
	
	@Override
	public void run()
	{
		FillMatrix();
	}
	
	/**
	 * This method make some calculations to fill all the
	 * LineForcesMatrix of a line of pylons
	 */
	private void FillMatrix()
	{
		float r1=0,r2=0,m1=0,t1=0,f=0,r=0, ws=1;
		
		/*
		 * Calculate and set the Weight of the structure
		 */
		r1 = ParametersController.getParameter(eParameter.PlankWeightOnTheStack).getValue()/2;
		r2 = ParametersController.getParameter(eParameter.MomentGeneratedByAsymmetry).getValue()/ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		if(side==0)
		{
			//MANTOVA
			this.lineForcesMatrix.getPs().setN(r1+r2);
		}else if(side==1)
		{
			//MODENA
			this.lineForcesMatrix.getPs().setN(r1-r2);			
		}
		
		
		/*
		 * Calculate and set the shifting loads
		 * with the A1 traffic combination
		 */
		r1 = ParametersController.getParameter(eParameter.AxialLoadForLoadCombinationA1).getValue()/2;
		r2 = ParametersController.getParameter(eParameter.BendingMomentYYForLoadCombinationA1).getValue()/ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		m1 = ParametersController.getParameter(eParameter.BendingMomentXXForLoadCombinationA1).getValue()/2;
		if(side==0)
		{
			//MANTOVA
			//A110 traffic
			this.lineForcesMatrix.getA110().setN(r1+r2);
			this.lineForcesMatrix.getA110().setMx(m1);
			
			//A120 traffic
			this.lineForcesMatrix.getA120().setN(r1+r2);
			this.lineForcesMatrix.getA120().setMx(-m1);
		}else if(side==1)
		{
			//MODENA
			//A110 traffic
			this.lineForcesMatrix.getA110().setN(r1-r2);
			this.lineForcesMatrix.getA110().setMx(+m1);
			
			//A120 traffic
			this.lineForcesMatrix.getA120().setN(r1-r2);
			this.lineForcesMatrix.getA120().setMx(-m1);
		}
		
		/*
		 * Calculate and set the shifting loads
		 * with the A2 traffic combination
		 */
		r1 = ParametersController.getParameter(eParameter.AxialLoadForLoadCombinationA2).getValue()/2;
		r2 = ParametersController.getParameter(eParameter.BendingMomentYYForLoadCombinationA2).getValue()/ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		m1 = ParametersController.getParameter(eParameter.BendingMomentXXForLoadCombinationA2).getValue()/2;
		if(side==0)
		{
			//MANTOVA
			//A210 traffic
			this.lineForcesMatrix.getA210().setN(r1+r2);
			this.lineForcesMatrix.getA210().setMx(m1);
			
			//A220 traffic
			this.lineForcesMatrix.getA220().setN(r1-r2);
			this.lineForcesMatrix.getA220().setMx(-m1);
		}else if(side==1)
		{
			//MODENA
			//A210 traffic
			this.lineForcesMatrix.getA210().setN(r1-r2);
			this.lineForcesMatrix.getA210().setMx(+m1);
			
			//A220 traffic
			this.lineForcesMatrix.getA220().setN(r1+r2);
			this.lineForcesMatrix.getA220().setMx(-m1);
		}
		
		/*
		 * Calculate and set the shifting loads
		 * with the A3 traffic combination
		 */
		r1 = ParametersController.getParameter(eParameter.AxialLoadForLoadCombinationA3).getValue()/2;
		r2 = ParametersController.getParameter(eParameter.BendingMomentYYForLoadCombinationA3).getValue()/
				ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		m1 = ParametersController.getParameter(eParameter.BendingMomentXXForLoadCombinationA3).getValue()/2;
		if(side==0)
		{
			//MANTOVA
			//A311 traffic
			this.lineForcesMatrix.getA311().setN(r1+r2);
			this.lineForcesMatrix.getA311().setMx(+m1);
			
			//A312 traffic
			this.lineForcesMatrix.getA312().setN(r1-r2);
			this.lineForcesMatrix.getA312().setMx(+m1);
			
			//A321 traffic
			this.lineForcesMatrix.getA321().setN(r1+r2);
			this.lineForcesMatrix.getA321().setMx(-m1);
			
			//A322 traffic
			this.lineForcesMatrix.getA322().setN(r1-r2);
			this.lineForcesMatrix.getA322().setMx(-m1);
		}else if(side==1)
		{
			//MODENA
			//A311 traffic
			this.lineForcesMatrix.getA311().setN(r1-r2);
			this.lineForcesMatrix.getA311().setMx(+m1);
			
			//A312 traffic
			this.lineForcesMatrix.getA312().setN(r1+r2);
			this.lineForcesMatrix.getA312().setMx(+m1);
			
			//A321 traffic
			this.lineForcesMatrix.getA321().setN(r1-r2);
			this.lineForcesMatrix.getA321().setMx(-m1);
			
			//A322 traffic
			this.lineForcesMatrix.getA322().setN(r1+r2);
			this.lineForcesMatrix.getA322().setMx(-m1);
		}
		
		
		/*
		 * Calculate and set the Braking Vehicles
		 */
		t1 = ParametersController.getParameter(eParameter.ValueOfTheForceDueToTheVehicleBraking).getValue()/2;
		r1 = (ParametersController.getParameter(eParameter.ValueOfTheForceDueToTheVehicleBraking).getValue()*
				ParametersController.getParameter(eParameter.ArmForTheVehicleBrakingMoment).getValue())/
				ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		if(side==0)
		{
			//MANTOVA
			//FR01
			this.lineForcesMatrix.getFR01().setN(r1);
			this.lineForcesMatrix.getFR01().setTx(t1);
			
			//FR02
			this.lineForcesMatrix.getFR02().setN(-r1);
			this.lineForcesMatrix.getFR02().setTx(-t1);
		}else if(side==1)
		{
			//MODENA
			//FR01
			this.lineForcesMatrix.getFR01().setN(-r1);
			this.lineForcesMatrix.getFR01().setTx(t1);
			
			//FR02
			this.lineForcesMatrix.getFR02().setN(+r1);
			this.lineForcesMatrix.getFR02().setTx(-t1);		
		}
		
		
		/*
		 * Calculations of the sign of the wind push.
		 */
		if((this.instrumentsData.getAne4()<(180-ParametersController.getParameter(eParameter.PlanimetricAnticlockwiseInclinationOfTheBridgeFormTheNorth).getValue())) ||
			(this.instrumentsData.getAne4()>(360-ParametersController.getParameter(eParameter.PlanimetricAnticlockwiseInclinationOfTheBridgeFormTheNorth).getValue())))
		{
			ws=1;
		}else
		{
			ws=-1;
		}
		
		/*
		 * Calculate and set the Wind component
		 * both on plank
		 */
		f = this.plankForces.getWindPushOnPlank()/2;
		r = (this.plankForces.getWindPushOnPlank()*ParametersController.getParameter(eParameter.ThrustCenterDueToLongitudinalAsymmetryOnlyOfSVplank).getValue())/
			ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue();
		m1 = (this.plankForces.getWindPushOnPlank()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVplank).getValue())/2;
		if(side==0)
		{
			//MANTOVA
			//VT0			
			this.lineForcesMatrix.getVT0().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT0().setMx(ws*(m1));
		}else if (side==1)
		{
			//MODENA
			//VT0
			this.lineForcesMatrix.getVT0().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT0().setMx(ws*(m1));
		}
		
		/*
		 * Calculate and set the Wind component
		 * both on A1 traffic combination
		 */
		f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA1TrafficCombination())/2;
		m1 = (this.plankForces.getWindPushOnPlank()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVplank).getValue() +
			this.plankForces.getWindPushOnA1TrafficCombination()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVtraf).getValue())/2;
		if(side==0)
		{
			//MANTOVA
			//VT1A1			
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
		}else if (side==1)
		{
			//MODENA
			//VT1A1
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
		}
		
		/*
		 * Calculate and set the Wind component
		 * both on A2 traffic combination
		 */
		f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA2TrafficCombination())/2;
		m1 = (this.plankForces.getWindPushOnPlank()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVplank).getValue() +
			this.plankForces.getWindPushOnA2TrafficCombination()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVtraf).getValue())/2;
		if(side==0)
		{
			//MANTOVA
			//VT1A2		
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
		}else if (side==1)
		{
			//MODENA
			//VT1A2
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
		}
		
		/*
		 * Calculate and set the Wind component
		 * both on A3 traffic combination
		 */
		f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA3TrafficCombination())/2;
		m1 = (this.plankForces.getWindPushOnPlank()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVplank).getValue() +
			this.plankForces.getWindPushOnA3TrafficCombination()*ParametersController.getParameter(eParameter.ArmForBendingMomentOfSVtraf).getValue())/2;
		if(side==0)
		{
			//MANTOVA
			//VT1A3	
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
		}else if (side==1)
		{
			//MODENA
			//VT1A3
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
		}
		
		
		
		
		/*
		 * Calculate and set the Water thrust with D=0
		 */
		//Is the same or both MANTOVA and MODENA side
		//AQD0
		t1 = (this.plankForces.getHydrodynamicThrustWithOutDebris()/this.plankForces.getHs())/2;
		this.lineForcesMatrix.getAQD0().setQy(t1);
		
		//AQD1
		t1 = (this.plankForces.getHydrodynamicThrustWithDebris()/this.plankForces.getHs())/2;
		this.lineForcesMatrix.getAQD1().setQy(t1);
	}
}
