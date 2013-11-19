package dsd.model.calculation;

import java.util.ArrayList;

public class LineForcesMatrix {
	
	private Force ps;
	private Force a110;
	private Force a120;
	private Force a210;
	private Force a220;
	private Force a311;
	private Force a312;
	private Force a321;
	private Force a322;
	private Force fr01;
	private Force fr02;
	private Force vt0;
	private Force vt1a1;
	private Force vt1a2;
	private Force vt1a3;
	private Force aqd0;
	private Force aqd1;
	
	private ArrayList<Force> forcesList = null;
	
	public LineForcesMatrix()
	{
		super();
		ps = new Force();
		a110 = new Force();
		a120 = new Force();
		a210 = new Force();
		a220 = new Force();
		a311 = new Force();
		a312 = new Force();
		a321 = new Force();
		a322 = new Force();
		fr01 = new Force();
		fr02 = new Force();
		vt0 = new Force();
		vt1a1 = new Force();
		vt1a2 = new Force();
		vt1a3 = new Force();
		aqd0 = new Force();
		aqd1 = new Force();
		
		forcesList = new ArrayList<Force>();
		forcesList.add(ps);
		forcesList.add(a110);
		forcesList.add(a120);
		forcesList.add(a210);
		forcesList.add(a220);
		forcesList.add(a311);
		forcesList.add(a312);
		forcesList.add(a321);
		forcesList.add(a322);
		forcesList.add(fr01);
		forcesList.add(fr02);
		forcesList.add(vt0);
		forcesList.add(vt1a1);
		forcesList.add(vt1a2);
		forcesList.add(vt1a3);
		forcesList.add(aqd0);
		forcesList.add(aqd1);		
	}

	/**
	 * @return the ps
	 */
	public Force getPs() {
		return ps;
	}

	/**
	 * @param ps the ps to set
	 */
	public void setPs(Force ps) {
		this.ps = ps;
	}

	/**
	 * @return the a110
	 */
	public Force getA110() {
		return a110;
	}

	/**
	 * @param a110 the a110 to set
	 */
	public void setA110(Force a110) {
		this.a110 = a110;
	}

	/**
	 * @return the a120
	 */
	public Force getA120() {
		return a120;
	}

	/**
	 * @param a120 the a120 to set
	 */
	public void setA120(Force a120) {
		this.a120 = a120;
	}

	/**
	 * @return the a210
	 */
	public Force getA210() {
		return a210;
	}

	/**
	 * @param a210 the a210 to set
	 */
	public void setA210(Force a210) {
		this.a210 = a210;
	}

	/**
	 * @return the a220
	 */
	public Force getA220() {
		return a220;
	}

	/**
	 * @param a220 the a220 to set
	 */
	public void setA220(Force a220) {
		this.a220 = a220;
	}

	/**
	 * @return the a311
	 */
	public Force getA311() {
		return a311;
	}

	/**
	 * @param a311 the a311 to set
	 */
	public void setA311(Force a311) {
		this.a311 = a311;
	}

	/**
	 * @return the a312
	 */
	public Force getA312() {
		return a312;
	}

	/**
	 * @param a312 the a312 to set
	 */
	public void setA312(Force a312) {
		this.a312 = a312;
	}

	/**
	 * @return the a321
	 */
	public Force getA321() {
		return a321;
	}

	/**
	 * @param a321 the a321 to set
	 */
	public void setA321(Force a321) {
		this.a321 = a321;
	}

	/**
	 * @return the a322
	 */
	public Force getA322() {
		return a322;
	}

	/**
	 * @param a322 the a322 to set
	 */
	public void setA322(Force a322) {
		this.a322 = a322;
	}

	/**
	 * @return the fr01
	 */
	public Force getFr01() {
		return fr01;
	}

	/**
	 * @param fr01 the fr01 to set
	 */
	public void setFr01(Force fr01) {
		this.fr01 = fr01;
	}

	/**
	 * @return the fr02
	 */
	public Force getFr02() {
		return fr02;
	}

	/**
	 * @param fr02 the fr02 to set
	 */
	public void setFr02(Force fr02) {
		this.fr02 = fr02;
	}

	/**
	 * @return the vt0
	 */
	public Force getVt0() {
		return vt0;
	}

	/**
	 * @param vt0 the vt0 to set
	 */
	public void setVt0(Force vt0) {
		this.vt0 = vt0;
	}

	/**
	 * @return the vt1a1
	 */
	public Force getVt1a1() {
		return vt1a1;
	}

	/**
	 * @param vt1a1 the vt1a1 to set
	 */
	public void setVt1a1(Force vt1a1) {
		this.vt1a1 = vt1a1;
	}

	/**
	 * @return the vt1a2
	 */
	public Force getVt1a2() {
		return vt1a2;
	}

	/**
	 * @param vt1a2 the vt1a2 to set
	 */
	public void setVt1a2(Force vt1a2) {
		this.vt1a2 = vt1a2;
	}

	/**
	 * @return the vt1a3
	 */
	public Force getVt1a3() {
		return vt1a3;
	}

	/**
	 * @param vt1a3 the vt1a3 to set
	 */
	public void setVt1a3(Force vt1a3) {
		this.vt1a3 = vt1a3;
	}

	/**
	 * @return the aqd0
	 */
	public Force getAqd0() {
		return aqd0;
	}

	/**
	 * @param aqd0 the aqd0 to set
	 */
	public void setAqd0(Force aqd0) {
		this.aqd0 = aqd0;
	}

	/**
	 * @return the aqd1
	 */
	public Force getAqd1() {
		return aqd1;
	}

	/**
	 * @param aqd1 the aqd1 to set
	 */
	public void setAqd1(Force aqd1) {
		this.aqd1 = aqd1;
	}

	/**
	 * @return the forcesList
	 */
	public ArrayList<Force> getForcesList() {
		return forcesList;
	}
}
