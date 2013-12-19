/*******************************************************************************
 * Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
	public static double ForceNcontributionPP(double stackWeight) {
		double contributionPP;
		contributionPP = stackWeight/3.0;
		return contributionPP;
	}
	
	/**
	 * @param n: the structure load that act on a single line of pylons
	 * @return the value of axial force N due to the external load on the single pylon
	 */
	public static double ForceNcontributionN(double n) {
		double contributionN;
		contributionN = n/3.0;
		return contributionN;
	}
	
	/**
	 * @param ty: cutting force Ty
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of Ty force at the Axial load N on a single pylon
	 */
	public static double ForceNcontributionTy(double ty, double d, double h1, double l2) {
		double contributionTy;
		
		contributionTy = (ty/d) * (h1 + (l2/2.0));
		
		return contributionTy;
	}
	
	
	/**
	 * @param mx: bending moment on x axis
	 * @return The contribution of bending moment Mx to the axial load N on a single pylon
	 */
	public static double ForceNcontributionMx(double mx) {
		double contributionMx;
		
		contributionMx = mx/3.0;
		
		return contributionMx;
	}
	
	/**
	 * @param H1: cutting force due to the level of water
	 * @param d: distance between two line of pylons
	 * @param h1: distance between the pulvino and the inferior beam
	 * @param l2: distance between inferior beam and the joint.
	 * @return The contribution of water at the axial load N on a single pylon
	 */
	public static double ForceNcontributionQy(double H1, double d, double h1, double l2) {
		double contributionQy;
		
		contributionQy = (H1 / d) * (h1 + (l2/2.0));
		
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
	public static double ForceTxContributionTx(double tx){
		double contributionTx;
		
		contributionTx = tx/3.0;
		
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
	public static double ForceTyContributionTy(double ty){
		double contributionTy;
		
		contributionTy = ty/3.0;
		
		return contributionTy;
	}
	
	/**
	 * @param H: bending moment due to the water level
	 * @return The contribution at the Qy force on each pylon
	 */
	public static double ForceTyContributionQy(double H){
		double contributionQy;
		
		contributionQy = H/3.0;
		
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
	public static double ForceMxContributionTy(double ty, double l2){
		double contributionTy;
		
		contributionTy = (ty * l2)/6.0;
		
		return contributionTy;
	}
	
	public static double ForceMxContributionQy(double H1, double l2){
		double contributionQy;
		
		contributionQy = (H1 * l2)/6.0;
		
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
	public static double ForceMyContributionTx(double tx, double l){
		double contributionTx;
		
		contributionTx = (tx * l)/3.0;
		
		return contributionTx;
	}
}
