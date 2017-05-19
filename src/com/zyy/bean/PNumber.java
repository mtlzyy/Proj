package com.zyy.bean;

/**
 * Power numbers like 2.3x^4
 * @author ZYY
 *
 */
public class PNumber {
	private char base;
	private int power;

	public char getBase() {
		return base;
	}
	public void setBase(char base) {
		this.base = base;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public String toString() {
		String s = "";
		if(power!=1){
			 s=base+"^"+power;
		}
		else{
			s = base+"";
		}
		return s;
	}
}
