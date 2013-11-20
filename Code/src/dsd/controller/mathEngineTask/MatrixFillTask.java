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
	
	/**
	 * This method make some calculations to fill all the
	 * LineForcesMatrix of a line of pyons
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
			this.calculationController.getMantovaLineMatrix().getPs().setN(r1+r2);
			
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
			this.calculationController.getModenaLineMatrix().getPs().setN(r1-r2);			
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
			this.calculationController.getMantovaLineMatrix().getA110().setN(r1+r2);
			this.calculationController.getMantovaLineMatrix().getA110().setMx(m1);
			
			//A120 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getMantovaLineMatrix().getA120().setN(r1+r2);
			this.calculationController.getMantovaLineMatrix().getA120().setMx(-m1);
			
			//A210 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getMantovaLineMatrix().getA210().setN(r1+r2);
			this.calculationController.getMantovaLineMatrix().getA210().setMx(m1);
			
			//A220 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getMantovaLineMatrix().getA220().setN(r1-r2);
			this.calculationController.getMantovaLineMatrix().getA220().setMx(-m1);
			
			//A311 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getMantovaLineMatrix().getA311().setN(r1+r2);
			this.calculationController.getMantovaLineMatrix().getA311().setMx(m1);
			
			//A312 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getMantovaLineMatrix().getA312().setN(r1-r2);
			this.calculationController.getMantovaLineMatrix().getA312().setMx(+m1);
			
			//A321 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getMantovaLineMatrix().getA321().setN(r1+r2);
			this.calculationController.getMantovaLineMatrix().getA321().setMx(-m1);
			
			//A322 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getMantovaLineMatrix().getA322().setN(r1-r2);
			this.calculationController.getMantovaLineMatrix().getA322().setMx(-m1);
		}else if(side==1)
		{
			//MODENA
			//A110 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getModenaLineMatrix().getA110().setN(r1-r2);
			this.calculationController.getModenaLineMatrix().getA110().setMx(-m1);
			
			//A120 traffic
			/*##############################
			 *CHANGE 111 WITH N(A1), 666 WITH Myy(A1), 999 WITH c_span, 777 WITH Mxx(A1)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getModenaLineMatrix().getA120().setN(r1+r2);
			this.calculationController.getModenaLineMatrix().getA120().setMx(m1);
			
			//A210 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getModenaLineMatrix().getA210().setN(r1-r2);
			this.calculationController.getModenaLineMatrix().getA210().setMx(m1);
			
			//A220 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getModenaLineMatrix().getA220().setN(r1+r2);
			this.calculationController.getModenaLineMatrix().getA220().setMx(-m1);
			
			//A311 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getModenaLineMatrix().getA311().setN(r1-r2);
			this.calculationController.getModenaLineMatrix().getA311().setMx(m1);
			
			//A312 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getModenaLineMatrix().getA312().setN(r1+r2);
			this.calculationController.getModenaLineMatrix().getA312().setMx(+m1);
			
			//A321 traffic
			/*##############################
			 *CHANGE 111 WITH N(A2), 666 WITH Myy(A2), 999 WITH c_span, 777 WITH Mxx(A2)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			r1 = 111/2;
			r2 = 666/999;
			m1 = 777/2;
			this.calculationController.getModenaLineMatrix().getA321().setN(r1-r2);
			this.calculationController.getModenaLineMatrix().getA321().setMx(-m1);
			
			//A322 traffic
			/*##############################
			 *CHANGE 111 WITH N(A3), 666 WITH Myy(A3), 999 WITH c_span, 777 WITH Mxx(A3)
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getModenaLineMatrix().getA322().setN(r1+r2);
			this.calculationController.getModenaLineMatrix().getA322().setMx(-m1);
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
			this.calculationController.getMantovaLineMatrix().getFR01().setN(r1);
			this.calculationController.getMantovaLineMatrix().getFR01().setTx(t1);
			
			//FR02
			/*##############################
			 *CHANGE 111 WITH Fr, 666 WITH n, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getMantovaLineMatrix().getFR02().setN(-r1);
			this.calculationController.getMantovaLineMatrix().getFR02().setTx(-t1);
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
			this.calculationController.getModenaLineMatrix().getFR01().setN(-r1);
			this.calculationController.getModenaLineMatrix().getFR01().setTx(t1);
			
			//FR02
			/*##############################
			 *CHANGE 111 WITH Fr, 666 WITH n, 999 WITH c_span
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			this.calculationController.getModenaLineMatrix().getFR02().setN(-r1);
			this.calculationController.getModenaLineMatrix().getFR02().setTx(-t1);		
		}
		
		/*
		 * Calculate and set the Wind component
		 * both on plank and on traffic
		 */
		if(side==0)
		{
			//MANTOVA
			/*##############################
			 *CHANGE 111 WITH alpha
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			if((this.calculationController.getInstrumentsData().getAne4()<(180-111)) || (this.calculationController.getInstrumentsData().getAne4()>(360-111)))
			{
				ws=1;
			}else
			{
				ws=-1;
			}
			
			//VT01
			/*##############################
			 *CHANGE 666 WITH r, 999 WITH c_span, 777 WITH e_plank
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = this.calculationController.getPlankForces().getWindPushOnPlank()/2;
			r = (this.calculationController.getPlankForces().getWindPushOnPlank()*666)/999;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777)/2;
			this.calculationController.getMantovaLineMatrix().getVT0().setTy(ws*(f+r));
			this.calculationController.getMantovaLineMatrix().getVT0().setMx(ws*(m1));
			
			//VT1A1
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.calculationController.getPlankForces().getWindPushOnPlank()+this.calculationController.getPlankForces().getWindPushOnA1TrafficCombination())/2;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777 + this.calculationController.getPlankForces().getWindPushOnA1TrafficCombination()*888)/2;
			this.calculationController.getMantovaLineMatrix().getVT1A1().setTy(ws*(f+r));
			this.calculationController.getMantovaLineMatrix().getVT1A1().setMx(ws*(m1));
			
			//VT1A2
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.calculationController.getPlankForces().getWindPushOnPlank()+this.calculationController.getPlankForces().getWindPushOnA2TrafficCombination())/2;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777 + this.calculationController.getPlankForces().getWindPushOnA2TrafficCombination()*888)/2;
			this.calculationController.getMantovaLineMatrix().getVT1A2().setTy(ws*(f+r));
			this.calculationController.getMantovaLineMatrix().getVT1A2().setMx(ws*(m1));
			
			//VT1A3
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.calculationController.getPlankForces().getWindPushOnPlank()+this.calculationController.getPlankForces().getWindPushOnA3TrafficCombination())/2;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777 + this.calculationController.getPlankForces().getWindPushOnA3TrafficCombination()*888)/2;
			this.calculationController.getMantovaLineMatrix().getVT1A3().setTy(ws*(f+r));
			this.calculationController.getMantovaLineMatrix().getVT1A3().setMx(ws*(m1));
		}else if(side==1)
		{
			//MODENA
			/*##############################
			 *CHANGE 111 WITH alpha
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			if((this.calculationController.getInstrumentsData().getAne4()<(180-111)) || (this.calculationController.getInstrumentsData().getAne4()>(360-111)))
			{
				ws=1;
			}else
			{
				ws=-1;
			}
			
			//VT01
			/*##############################
			 *CHANGE 666 WITH r, 999 WITH c_span, 777 WITH e_plank
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = this.calculationController.getPlankForces().getWindPushOnPlank()/2;
			r = (this.calculationController.getPlankForces().getWindPushOnPlank()*666)/999;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777)/2;
			this.calculationController.getModenaLineMatrix().getVT0().setTy(ws*(f-r));
			this.calculationController.getModenaLineMatrix().getVT0().setMx(ws*(m1));
			
			//VT1A1
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.calculationController.getPlankForces().getWindPushOnPlank()+this.calculationController.getPlankForces().getWindPushOnA1TrafficCombination())/2;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777 + this.calculationController.getPlankForces().getWindPushOnA1TrafficCombination()*888)/2;
			this.calculationController.getModenaLineMatrix().getVT1A1().setTy(ws*(f-r));
			this.calculationController.getModenaLineMatrix().getVT1A1().setMx(ws*(m1));
			
			//VT1A2
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.calculationController.getPlankForces().getWindPushOnPlank()+this.calculationController.getPlankForces().getWindPushOnA2TrafficCombination())/2;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777 + this.calculationController.getPlankForces().getWindPushOnA2TrafficCombination()*888)/2;
			this.calculationController.getModenaLineMatrix().getVT1A2().setTy(ws*(f-r));
			this.calculationController.getModenaLineMatrix().getVT1A2().setMx(ws*(m1));
			
			//VT1A3
			/*##############################
			 *CHANGE 777 WITH e_plank, 888 WITH e_traf
			 *PARAMETERS ARE MISSING
			 *############################# 
			 */
			f = (this.calculationController.getPlankForces().getWindPushOnPlank()+this.calculationController.getPlankForces().getWindPushOnA3TrafficCombination())/2;
			m1 = (this.calculationController.getPlankForces().getWindPushOnPlank()*777 + this.calculationController.getPlankForces().getWindPushOnA3TrafficCombination()*888)/2;
			this.calculationController.getModenaLineMatrix().getVT1A3().setTy(ws*(f-r));
			this.calculationController.getModenaLineMatrix().getVT1A3().setMx(ws*(m1));
		}
		
		/*
		 * Calculate and set the Water thrust
		 */
		if(side==0)
		{
			//MANTOVA
			//AQD0
			t1 = (this.calculationController.getPlankForces().getHydrodynamicThrustWithOutDebris()/this.calculationController.getPlankForces().getHs())/2;
			this.calculationController.getMantovaLineMatrix().getAQD0().setQy(t1);
			
			//AQD1
			t1 = (this.calculationController.getPlankForces().getHydrodynamicThrustWithDebris()/this.calculationController.getPlankForces().getHs())/2;
			this.calculationController.getMantovaLineMatrix().getAQD1().setQy(t1);
		}else if(side==1)
		{
			//MODENA
			//AQD0
			t1 = (this.calculationController.getPlankForces().getHydrodynamicThrustWithOutDebris()/this.calculationController.getPlankForces().getHs())/2;
			this.calculationController.getModenaLineMatrix().getAQD0().setQy(t1);
			
			//AQD1
			t1 = (this.calculationController.getPlankForces().getHydrodynamicThrustWithDebris()/this.calculationController.getPlankForces().getHs())/2;
			this.calculationController.getModenaLineMatrix().getAQD1().setQy(t1);
		}
	}
}
