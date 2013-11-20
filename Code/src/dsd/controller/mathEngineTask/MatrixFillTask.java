package dsd.controller.mathEngineTask;

import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.LineForcesMatrix;
import dsd.model.calculation.PlankForces;

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
		if(side==0)
		{
			//MANTOVA
			/*##############################
			 *CHANGE 11111 WITH Pplank, 666 WITH Mt, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 11111/2;
			r2 = 666/999;
			this.lineForcesMatrix.getPs().setN(r1+r2);
			
		}else if(side==1)
		{
			//MODENA
			/*##############################
			 *CHANGE 11111 WITH Pplank, 666 WITH Mt, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 11111/2;
			r2 = 666/999;
			this.lineForcesMatrix.getPs().setN(r1-r2);			
		}
		
		
		/*
		 * Calculate and set the shifting loads
		 * with all three traffic combinations
		 */
		if(side==0)
		{
			//MANTOVA
			//A110 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA110().setN(r1+r2);
			this.lineForcesMatrix.getA110().setMx(m1);
			
			//A120 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA120().setN(r1+r2);
			this.lineForcesMatrix.getA120().setMx(-m1);
			
			//A210 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA210().setN(r1+r2);
			this.lineForcesMatrix.getA210().setMx(m1);
			
			//A220 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA220().setN(r1-r2);
			this.lineForcesMatrix.getA220().setMx(-m1);
			
			//A311 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA311().setN(r1+r2);
			this.lineForcesMatrix.getA311().setMx(m1);
			
			//A312 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA312().setN(r1-r2);
			this.lineForcesMatrix.getA312().setMx(+m1);
			
			//A321 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA321().setN(r1+r2);
			this.lineForcesMatrix.getA321().setMx(-m1);
			
			//A322 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA322().setN(r1-r2);
			this.lineForcesMatrix.getA322().setMx(-m1);
		}else if(side==1)
		{
			//MODENA
			//A110 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA110().setN(r1-r2);
			this.lineForcesMatrix.getA110().setMx(-m1);
			
			//A120 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA120().setN(r1+r2);
			this.lineForcesMatrix.getA120().setMx(m1);
			
			//A210 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA210().setN(r1-r2);
			this.lineForcesMatrix.getA210().setMx(m1);
			
			//A220 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA220().setN(r1+r2);
			this.lineForcesMatrix.getA220().setMx(-m1);
			
			//A311 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA311().setN(r1-r2);
			this.lineForcesMatrix.getA311().setMx(m1);
			
			//A312 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA312().setN(r1+r2);
			this.lineForcesMatrix.getA312().setMx(+m1);
			
			//A321 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.lineForcesMatrix.getA321().setN(r1-r2);
			this.lineForcesMatrix.getA321().setMx(-m1);
			
			//A322 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getA322().setN(r1+r2);
			this.lineForcesMatrix.getA322().setMx(-m1);
		}
		
		/*
		 * Calculate and set the Braking Vehicles
		 */
		if(side==0)
		{
			//MANTOVA
			//FR01
			/*##############################
			 *CHANGE 111 WITH Fr, 666 WITH n, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			t1 = 111/2;
			r1 = (111*666)/999;
			this.lineForcesMatrix.getFR01().setN(r1);
			this.lineForcesMatrix.getFR01().setTx(t1);
			
			//FR02
			/*##############################
			 *CHANGE 111 WITH Fr, 666 WITH n, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getFR02().setN(-r1);
			this.lineForcesMatrix.getFR02().setTx(-t1);
		}else if(side==1)
		{
			//MODENA
			//FR01
			/*##############################
			 *CHANGE 111 WITH Fr, 666 WITH n, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			t1 = 111/2;
			r1 = (111*666)/999;
			this.lineForcesMatrix.getFR01().setN(-r1);
			this.lineForcesMatrix.getFR01().setTx(t1);
			
			//FR02
			/*##############################
			 *CHANGE 111 WITH Fr, 666 WITH n, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.lineForcesMatrix.getFR02().setN(-r1);
			this.lineForcesMatrix.getFR02().setTx(-t1);		
		}
		
		/*##############################
		 *CHANGE 111 WITH alpha
		 *PARAMETERS ARE MISSING
		 *############################# 
		 */
		if((this.instrumentsData.getAne4()<(180-111)) || (this.instrumentsData.getAne4()>(360-111)))
		{
			ws=1;
		}else
		{
			ws=-1;
		}
		
		/*
		 * Calculate and set the Wind component
		 * both on plank and on traffic
		 */
		if(side==0)
		{
			//MANTOVA
			//VT01
			/*##############################
			 *CHANGE 666 WITH r, 999 WITH c_span, 777 WITH e_plank
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = this.plankForces.getWindPushOnPlank()/2;
			r = (this.plankForces.getWindPushOnPlank()*666)/999;
			m1 = (this.plankForces.getWindPushOnPlank()*777)/2;
			this.lineForcesMatrix.getVT0().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT0().setMx(ws*(m1));
			
			//VT1A1
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA1TrafficCombination())/2;
			m1 = (this.plankForces.getWindPushOnPlank()*777 + this.plankForces.getWindPushOnA1TrafficCombination()*888)/2;
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
			
			//VT1A2
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA2TrafficCombination())/2;
			m1 = (this.plankForces.getWindPushOnPlank()*777 + this.plankForces.getWindPushOnA2TrafficCombination()*888)/2;
			this.lineForcesMatrix.getVT1A2().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT1A2().setMx(ws*(m1));
			
			//VT1A3
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA3TrafficCombination())/2;
			m1 = (this.plankForces.getWindPushOnPlank()*777 + this.plankForces.getWindPushOnA3TrafficCombination()*888)/2;
			this.lineForcesMatrix.getVT1A3().setTy(ws*(f+r));
			this.lineForcesMatrix.getVT1A3().setMx(ws*(m1));
		}else if(side==1)
		{
			//MODENA
			//VT01
			/*##############################
			 *CHANGE 666 WITH r, 999 WITH c_span, 777 WITH e_plank
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = this.plankForces.getWindPushOnPlank()/2;
			r = (this.plankForces.getWindPushOnPlank()*666)/999;
			m1 = (this.plankForces.getWindPushOnPlank()*777)/2;
			this.lineForcesMatrix.getVT0().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT0().setMx(ws*(m1));
			
			//VT1A1
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA1TrafficCombination())/2;
			m1 = (this.plankForces.getWindPushOnPlank()*777 + this.plankForces.getWindPushOnA1TrafficCombination()*888)/2;
			this.lineForcesMatrix.getVT1A1().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT1A1().setMx(ws*(m1));
			
			//VT1A2
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA2TrafficCombination())/2;
			m1 = (this.plankForces.getWindPushOnPlank()*777 + this.plankForces.getWindPushOnA2TrafficCombination()*888)/2;
			this.lineForcesMatrix.getVT1A2().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT1A2().setMx(ws*(m1));
			
			//VT1A3
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.plankForces.getWindPushOnPlank()+this.plankForces.getWindPushOnA3TrafficCombination())/2;
			m1 = (this.plankForces.getWindPushOnPlank()*777 + this.plankForces.getWindPushOnA3TrafficCombination()*888)/2;
			this.lineForcesMatrix.getVT1A3().setTy(ws*(f-r));
			this.lineForcesMatrix.getVT1A3().setMx(ws*(m1));
		}
		
		/*
		 * Calculate and set the Water thrust
		 */
		if(side==0)
		{
			//MANTOVA
			//AQD0
			t1 = (this.plankForces.getHydrodynamicThrustWithOutDebris()/this.plankForces.getHs())/2;
			this.lineForcesMatrix.getAQD0().setQy(t1);
			
			//AQD1
			t1 = (this.plankForces.getHydrodynamicThrustWithDebris()/this.plankForces.getHs())/2;
			this.lineForcesMatrix.getAQD1().setQy(t1);
		}else if(side==1)
		{
			//MODENA
			//AQD0
			t1 = (this.plankForces.getHydrodynamicThrustWithOutDebris()/this.plankForces.getHs())/2;
			this.lineForcesMatrix.getAQD0().setQy(t1);
			
			//AQD1
			t1 = (this.plankForces.getHydrodynamicThrustWithDebris()/this.plankForces.getHs())/2;
			this.lineForcesMatrix.getAQD1().setQy(t1);
		}
	}
}
