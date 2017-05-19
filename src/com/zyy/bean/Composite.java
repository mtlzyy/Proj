package com.zyy.bean;

import java.util.List;
/**
 * Represents all composites like 2.33*x^4*y^3*z^2 etc;
 * @author ZYY
 *
 */
public class Composite {
	private List<PNumber> pNumbers;
	private double coefficient;
	public List<PNumber> getpNumbers() {
		return pNumbers;
	}
	public void setpNumbers(List<PNumber> pNumbers) {
		this.pNumbers = pNumbers;
	}
	public double getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	@Override
	public String toString() {
		String powers = "";
		if(pNumbers==null){
			powers = "";
		}
		else{
			for(int i =0;i<pNumbers.size();i++){
				powers+=(pNumbers.get(i).toString());
			}
		}
		return coefficient+powers;
	}
	
	
	
}
