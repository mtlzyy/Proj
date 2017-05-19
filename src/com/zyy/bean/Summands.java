package com.zyy.bean;

import com.zyy.enums.Sign;

public class Summands {
	private Sign sign;
	private String term;
	
	public Summands(){};
	
	public Summands(Sign sign, String term) {
		this.sign = sign;
		this.term = term;
	}

	public Sign getSign() {
		return sign;
	}
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	@Override
	public String toString() {
		String x;
		if(sign==Sign.POSITIVE){
			x = "+";
		}
		else{
			x= "-";
		}
		String s = x+term;
		return s;
	}
	
}
