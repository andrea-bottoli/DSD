package dsd.controller.mathEngineTask;

import dsd.calculations.MathEngine;
import dsd.controller.ParametersController;
import dsd.model.calculation.Combination;
import dsd.model.calculation.InstrumentsData;
import dsd.model.calculation.LineForces;
import dsd.model.calculation.PylonCombination;
import dsd.model.calculation.PylonForces;
import dsd.model.enums.eParameter;


public class PylonCombinationTask implements Runnable {

	private LineForces lineForces;
	private PylonForces pylonForces;
	private InstrumentsData instrumetnsData;
	
	private float l;
	private float l2;
	private float ds;
	private float hi;
	private float H;
	private float H1;
	private float H2;
	
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
		int i = 0;
		
		cleanVariables();
		
		for (Combination c : this.lineForces.getComboList())
		{
			//Store the reference to the relative combination into the pylonCombination variable.
			this.pylonForces.getPylonComboList().get(i).setCombination(c);
			setUpVariables(c);
			/*
			 * Start the calculation for the forces at each pylon
			 */
			//Vertical load N
			axialStressN(c, this.pylonForces.getPylonComboList().get(i));

			//Cutting Force Tx
			cuttingForceTx(c, this.pylonForces.getPylonComboList().get(i));
			
			//Cutting Force Ty
			cuttingForceTy(c, this.pylonForces.getPylonComboList().get(i));
			
			//Bending Stress Mx
			bendingStressMx(c, this.pylonForces.getPylonComboList().get(i));
			
			//Bending Stress My
			bendingStressMy(c, this.pylonForces.getPylonComboList().get(i));			
			
			i++;
		}
	}	
	
	/**
	 * This method resets the variables used during the calculations.
	 */
	private void cleanVariables()
	{
		this.l = 0;
		this.l2 = 0;
		this.ds = 0;
		this.hi = 0;
		this.H = 0;
		this.H1 = 0;
		this.H2 = 0;
	}
	
	/**
	 * This method sets up some variables utilized in the calculations
	 * @param c: the combination considered
	 */
	private void setUpVariables(Combination c)
	{
		float mh=0;
		
		this.ds = ParametersController.getParameter(eParameter.HeightOfTheReferenceOfTheBottomOfTheRiver).getValue() - this.instrumetnsData.getSonar1();
		if(this.ds<0){
			this.ds=0;
		}
		
		this.l2 = ParametersController.getParameter(eParameter.DistanceBetweenTheInferiorBeamAndTheBottom_ref).getValue() +
					this.ds +
					ParametersController.getParameter(eParameter.SinkingOfTheJointsOverTheGround).getValue();
		
		this.l = this.l2 + ParametersController.getParameter(eParameter.DistanceBetweenThePulvinoAndTheInferiorBeam).getValue();
		
		this.hi = this.instrumetnsData.getIdro1() - ParametersController.getParameter(eParameter.HeightOfTheReferenceOfTheBottomOfTheRiver).getValue();
		if(this.hi < 0){
			this.hi = 0;
		}
		
		
		this.H = this.hi * c.getQy();
		mh =  this.H * ((this.hi/2) + this.ds + ParametersController.getParameter(eParameter.SinkingOfTheJointsOverTheGround).getValue());
		
		if(this.hi > ParametersController.getParameter(eParameter.DistanceBetweenTheInferiorBeamAndTheBottom_ref).getValue()){
			//CASE A: hi > h2
			this.H1 = mh/(this.hi + this.l2);
		}else{
			//CASE B: hi<=h2
			this.H1 = mh/this.l2;
		}
		
		this.H2 = this.H - this.H1;
	}
	
	
	/**
	 * This method calculates the value of the axial load N
	 * on the three pylons
	 * @param c: the combination considered 
	 * @param pylonCombination: the pylon combination considered
	 */
	private void axialStressN(Combination c, PylonCombination pylonCombination) {
		float p1=0, p2=0, p3=0, x=0;
		
		/* ###########
		 * ##	PP	##
		 * ###########
		 */
		x = MathEngine.ForceNcontributionPP(c.getStackWeight());
		
		//Pylon1
		p1 = x;
		//Pylon2
		p2 = x;
		//Pylon3
		p3 = x;
		
		/* ###########
		 * ##	N	##
		 * ###########
		 */
		x = MathEngine.ForceNcontributionN(c.getN());
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		/* ###########
		 * ##	Ty	##
		 * ###########
		 */
		x = MathEngine.ForceNcontributionTy(c.getTy(), ParametersController.getParameter(eParameter.WidthOfTheChassis).getValue(),
											ParametersController.getParameter(eParameter.DistanceBetweenThePulvinoAndTheInferiorBeam).getValue(), this.l2);
		
		//Pylon1
		p1 += -x;
		//Pylon2
		//no contributions for p2
		//Pylon3
		p3 += x;
		
		/* ###########
		 * ##	Mx	##
		 * ###########
		 */
		x = MathEngine.ForceNcontributionMx(c.getMx());
		
		//Pylon1
		p1 += -x;
		//Pylon2
		//no contributions for p2
		//Pylon3
		p1 += x;
		
		/* ###########
		 * ##	qy	##
		 * ###########
		 */
		/*
		 * 2 cases
		 */
		if(this.hi > ParametersController.getParameter(eParameter.DistanceBetweenTheInferiorBeamAndTheBottom_ref).getValue()){
			//CASE A: hi > h2
			x = MathEngine.ForceNcontributionQyCaseA(this.H1, ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue(),
														ParametersController.getParameter(eParameter.DistanceBetweenThePulvinoAndTheInferiorBeam).getValue(), this.l2);
		}else{
			//CASE B: hi<=h2
			x = MathEngine.ForceNcontributionQyCaseB(this.H1, ParametersController.getParameter(eParameter.DistanceBetweenTwoLineOfPylon).getValue(), this.l2);
		}
		
		//Pylon1
		p1 += -x;
		//Pylon2
		//no contributions for p2
		//Pylon3
		p3 += +x;
		
		pylonCombination.getPylon1().setN(p1);
		pylonCombination.getPylon2().setN(p2);
		pylonCombination.getPylon3().setN(p3);
	}
	
	/**
	 * This method calculates the value of the cutting force Tx
	 * on the three pylons
	 * @param c: the combination considered 
	 * @param pylonCombination: the pylon combination considered
	 */
	private void cuttingForceTx(Combination c, PylonCombination pylonCombination) {
		float p1=0, p2=0, p3=0, x=0;
		
		/* ###########
		 * ##	Tx	##
		 * ###########
		 */
		x = MathEngine.ForceTxContributionTx(c.getTx());
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		pylonCombination.getPylon1().setTx(p1);
		pylonCombination.getPylon2().setTx(p2);
		pylonCombination.getPylon3().setTx(p3);
	}
	
	/**
	 * This method calculates the value of the cutting force Ty
	 * on the three pylons
	 * @param c: the combination considered 
	 * @param pylonCombination: the pylon combination considered
	 */
	private void cuttingForceTy(Combination c, PylonCombination pylonCombination) {
		float p1=0, p2=0, p3=0, x=0;
		
		/* ###########
		 * ##	Ty	##
		 * ###########
		 */
		x = MathEngine.ForceTyContributionTy(c.getTy());
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		/* ###########
		 * ##	Qy	##
		 * ###########
		 */
		x = MathEngine.ForceTyContributionQy(this.H);
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		pylonCombination.getPylon1().setTy(p1);
		pylonCombination.getPylon2().setTy(p2);
		pylonCombination.getPylon3().setTy(p3);
	}
	
	/**
	 * This method calculates the value of the bending force Mx
	 * on the three pylons
	 * @param c: the combination considered 
	 * @param pylonCombination: the pylon combination considered
	 */
	private void bendingStressMx(Combination c, PylonCombination pylonCombination) {
		float p1=0, p2=0, p3=0, x=0;
		
		/* ###########
		 * ##	Ty	##
		 * ###########
		 */
		x = MathEngine.ForceMxContributionTy(c.getTy(), this.l2);
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		/* ###########
		 * ##	Qy	##
		 * ###########
		 */
		x = MathEngine.ForceMxContributionQy(this.H1, this.l2);
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		pylonCombination.getPylon1().setMx(p1);
		pylonCombination.getPylon2().setMx(p2);
		pylonCombination.getPylon3().setMx(p3);
	}
	
	/**
	 * This method calculates the value of the bending force My
	 * on the three pylons
	 * @param c: the combination considered 
	 * @param pylonCombination: the pylon combination considered
	 */
	private void bendingStressMy(Combination c, PylonCombination pylonCombination) {
		float p1=0, p2=0, p3=0, x=0;
		
		/* ###########
		 * ##	Tx	##
		 * ###########
		 */
		x = MathEngine.ForceMyContributionTx(c.getTx(), this.l);
		
		//Pylon1
		p1 += x;
		//Pylon2
		p2 += x;
		//Pylon3
		p3 += x;
		
		pylonCombination.getPylon1().setMy(p1);
		pylonCombination.getPylon2().setMy(p2);
		pylonCombination.getPylon3().setMy(p3);
	}
}
