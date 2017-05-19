package com.zyy.util;

import java.util.ArrayList;
import java.util.List;

import com.zyy.bean.Composite;
import com.zyy.bean.PNumber;
import com.zyy.bean.Summands;
import com.zyy.enums.Sign;
import com.zyy.exception.FormatInvalidException;


public class SMDSupport {
	
	/**
	 * Reverse all the signs in the summand list
	 * @param smds
	 * @return
	 */
	public static List<Summands> reverseSigns(List<Summands> smds){
		if(smds!=null){
			for(Summands s: smds){
				if(s.getSign()==Sign.NEGATIVE){
					s.setSign(Sign.POSITIVE);
				}
				else{
					s.setSign(Sign.NEGATIVE);
				}
			}
		}
		return smds;
	}
	
	/**
	 * Get the coefficient of the composite from the term field of the summand
	 * @param smd
	 * @return
	 */
	public static Composite parseSummands(Summands smd){
		// the coefficient of the comp
		double coefficient;
		// the term of the summand
		String term = smd.getTerm();
		Composite composite = new Composite();
		List<PNumber> pNumbers = null;
		//break the term string into chars and find the position of the first letter(variable)
		char[] chars = term.toCharArray();
		int  firstLetterPosition = -1;
		for(int i =0;i<chars.length; i++){
			if(Character.isLetter(chars[i])){
				firstLetterPosition = i;
				break;
			}
		}
		if(firstLetterPosition<0){
			//no letter, i.e no variable, it's a constant
			coefficient = Double.parseDouble(term);
		}
		else{
			//if there's at least one letter , i.e >=one variable
			if(firstLetterPosition==0){
				coefficient = 1;
			}
			else{
				coefficient = Double.parseDouble(term.substring(0,firstLetterPosition));
			}
			String compositeExpression = term.substring(firstLetterPosition);
			//parse the rest of the term string to get the power numbers of the composite
			pNumbers = parseExpressions(compositeExpression);
		}
		//set the coefficient into the composite
		composite.setCoefficient(coefficient);
		composite.setpNumbers(pNumbers);
		return composite;
	}
	/**
	 * Parse the expression String to get a list of power numbers(PNumber)
	 * @param compositeExpression
	 * @return
	 */
	public static List<PNumber> parseExpressions(String compositeExpression) {
		List<PNumber> pNumbers = new ArrayList<PNumber>();
		List<Character> variables = new ArrayList<Character>();
		List<Integer> duplicatedVars = new ArrayList<Integer>();
		char[] exprChars = compositeExpression.toCharArray();
		//create a list which contains the position of each variable in the expr array
		List<Integer> varPosition = new ArrayList<Integer>();
		//The first char in the exprChars is always a variable since it's substring of 
		//summand's term field start with the first letter's position
		varPosition.add(0);
		variables.add(exprChars[0]);
		//start with the second char in the char array
		for(int i =1;i< exprChars.length; i++){
			char c = exprChars[i];
			if(Character.isLetter(c)){
				if(variables.contains(c)){
					duplicatedVars.add(i);
				}
				else{
					variables.add(c);
				}
				varPosition.add(i);
			}
		}
		for(int i=0;i<varPosition.size();i++){
			PNumber pNum = new PNumber();
			int power;
			int powerSign = 1;
			//current variable position in the char array
			int currentLetterPos = varPosition.get(i);
			//the base(variable) of the power numbers
			char base = exprChars[currentLetterPos];
			pNum.setBase(base);
			//now need to find the power of each base
			//if this is not the last variable in the composite
			if(i!=varPosition.size()-1){	
				// the position of next variable in the char array
				int nextLetterPos = varPosition.get(i+1);
				// if the next var is just next to the current var, e.g.  xy, xz etc.
				int[] powerSet = getPower(currentLetterPos, nextLetterPos, exprChars, powerSign);
				powerSign = powerSet[1];
				power = powerSet[0];
			}
			else{
				power = getPowerForLastVar(currentLetterPos,exprChars.length-1,exprChars,powerSign);
			}
			if(duplicatedVars.contains(currentLetterPos)){
				powerCalculations(pNumbers, power,base);
			}
			else{
				pNum.setPower(power);
				pNumbers.add(pNum);
			}
		}
		return pNumbers;
	}
	
	public static int getPowerForLastVar(int curPos, int length,char[] exprChars, int powerSign) {
		int power = 0 ;
		if(length-curPos==0){
			power =  1;
		}
		else if(length-curPos>=2){
			if(exprChars[curPos+1]!='^'){
				try {
					throw new FormatInvalidException();
				} catch (FormatInvalidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				power = getPowerFromChars(exprChars, curPos, length+1);
			}
		}
		else{
			try {
				throw new FormatInvalidException();
			} catch (FormatInvalidException e) {
				e.printStackTrace();
			}
		}
		return power;
	}

	public static int[] getPower(int curPos, int nextVarPos, char[] exprChars, int nextPowerSign){
		int[] results = new int[2];
		int power = 0;
		int curPowerSign = nextPowerSign;
		char firstCharAfter = exprChars[curPos+1];
		if((nextVarPos-curPos)==1){
			power = 1;
		}
		else if(nextVarPos-curPos==2){	
			if(firstCharAfter=='*'){
				power = 1;
			}
			else if(firstCharAfter=='/'){
				power = 1;
				nextPowerSign = -1;
			}
			else{
				try {
					throw new FormatInvalidException();
				} catch (FormatInvalidException e) {
					e.printStackTrace();
				}
			}
		}
		else{
			if(firstCharAfter!='^'){
				try {
					throw new FormatInvalidException();
				} catch (FormatInvalidException e) {
					e.printStackTrace();
				}
			}
			else{
				power = getPowerFromChars(exprChars, curPos, nextVarPos);
			}
		}
		results[0] = power*curPowerSign;
		results[1] = nextPowerSign;
		return results;
	}
	
	public static int getPowerFromChars(char[] chars, int startPos, int endPos){
		char[] powerChar = new char[endPos-startPos-2];
		int power=0;
		for(int a = startPos+2,b=0; a<endPos; a++,b++){
			powerChar[b] = chars[a];
		}
		String powerStr = new String(powerChar);
		try {
			power = Integer.parseInt(powerStr);
		} catch (NumberFormatException e) {
			//**********************************to be filled
		}
		return power;
	}

	public static void powerCalculations(List<PNumber> pNums, int powerAdded, char base){
		if(pNums!=null){
			for(PNumber p:pNums){
				if(p.getBase()==base){
					int oldPower = p.getPower();
					p.setPower(oldPower+powerAdded);
					break;
				}
			}
		}
	}
}
