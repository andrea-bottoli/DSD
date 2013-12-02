package dsd.model;

import java.util.List;

public class MNDomain {
	
	private List<Float> m;
	private List<Float> n;
	
	public MNDomain(){
		
	}
	
	public MNDomain(List<Float> m, List<Float> n){
		this.m = m;
		this.n = n;
	}

	/**
	 * @return the m
	 */
	public List<Float> getM() {
		return m;
	}

	/**
	 * @return the n
	 */
	public List<Float> getN() {
		return n;
	}
}
