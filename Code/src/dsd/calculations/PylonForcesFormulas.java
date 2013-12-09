package dsd.calculations;

public class PylonForcesFormulas {

	/* #######################
	 * ##	AXIAL LOAD N	##
	 * #######################
	 */
	/**
	 * @param stackWaight: the portion of stack waight on the single line of pylons
	 * @return the value of axial force N due to the stack waight on the single pylon
	 */
	public static float ForceNcontributionPP(float stackWeight) {
		float contributionPP;
		contributionPP = stackWeight / 3;
		return contributionPP;
	}
	
	/**
	 * @param n: the structure load that act on a single line of pylons
	 * @return the value of axial force N due to the external load on the single pylon
	 */
	public static float ForceNcontributionN(float n) {
		float contributionN;
		contributionN = n / 3;
		return contributionN;
	}
	
	/**
	 * @param ty: cutting force Ty
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of Ty force at the Axial load N on a single pylon
	 */
	public static float ForceNcontributionTy(float ty, float d, float h1, float l2) {
		float contributionTy;
		
		contributionTy = (ty / d) * (h1 + (l2/2));
		
		return contributionTy;
	}
	
	
	/**
	 * @param mx: bending moment on x axis
	 * @return The contribution of bending moment Mx to the axial load N on a single pylon
	 */
	public static float ForceNcontributionMx(float mx) {
		float contributionMx;
		
		contributionMx = mx / 3;
		
		return contributionMx;
	}
	
	/**
	 * @param H1: cutting force due to the level of water
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of water at the axial load N on a single pylon
	 */
	public static float ForceNcontributionQy(float H1, float d, float h1, float l2) {
		float contributionQy;
		
		contributionQy = (H1 / d) * (h1 + (l2/2));
		
		return contributionQy;
	}
	
	
	/* ###########################
	 * ##	CUTTING FORCE TX	##
	 * ###########################
	 */
	/**
	 * @param tx: cutting force Tx acting on the line
	 * @return The contribution at the Tx force on each pylon
	 */
	public static float ForceTxContributionTx(float tx){
		float contributionTx;
		
		contributionTx = tx / 3;
		
		return contributionTx;
	}
	
	
	
	/* ###########################
	 * ##	CUTTING FORCE TY	##
	 * ###########################
	 */
	/**
	 * @param ty: cutting force Ty acting on the line
	 * @return The contribution at the Ty force on each pylon
	 */
	public static float ForceTyContributionTy(float ty){
		float contributionTy;
		
		contributionTy = ty / 3;
		
		return contributionTy;
	}
	
	/**
	 * @param H: bending moment due to the water level
	 * @return The contribution at the Qy force on each pylon
	 */
	public static float ForceTyContributionQy(float H){
		float contributionQy;
		
		contributionQy = H / 3;
		
		return contributionQy;
	}
	
	/* ###########################
	 * ##	BENDING MOMENT Mx	##
	 * ###########################
	 */
	/**
	 * @param ty: cutting force Ty acting on the line
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution at the Ty force on each pylon
	 */
	public static float ForceMxContributionTy(float ty, float l2){
		float contributionTy;
		
		contributionTy = (ty * l2) / 6;
		
		return contributionTy;
	}
	
	public static float ForceMxContributionQy(float H1, float l2){
		float contributionQy;
		
		contributionQy = (H1 * l2) / 6;
		
		return contributionQy;
	}
	
	
	/* ###########################
	 * ##	BENDING MOMENT My	##
	 * ###########################
	 */
	/**
	 * @param tx: cutting force Tx acting on the line
	 * @param l: distance between pulvino and the joint (l2 + h1)
	 * @return The contribution at the Tx force on each pylon
	 */
	public static float ForceMyContributionTx(float tx, float l){
		float contributionTx;
		
		contributionTx = (tx * l) / 3;
		
		return contributionTx;
	}
}
