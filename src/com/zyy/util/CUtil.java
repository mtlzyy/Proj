package com.zyy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyy.bean.Summands;
import com.zyy.enums.Sign;

public class CUtil {
	
	/**
	 * Separate the eqn with the '=' sign
	 * @param s
	 * @return
	 */
	public static String[] primarySeparation(String s){
		//contains right/left part of the string separated by '='
		String[] sArray = new String[2];
		String x = s.replace(" ", "").replace("*", "");
		int eqSign =x.indexOf("=");
		//erase all space in the string and insert both parts into the string array. 0--left, 1--right
		String leftEqn = x.substring(0,eqSign);
		String rightEqn = x.substring(eqSign+1);
		sArray[0] = leftEqn;
		sArray[1] = rightEqn;
		return sArray;
	}
	
	
	/*public static String removeBrackets(String s){
		
	}*/
	
	/**
	 * Separate the String into differenent summands and add it to a result list
	 * @param s
	 * @return
	 */
	public static List<Summands> secondarySeparation(String s){
		List<Summands> results = new ArrayList<Summands>();
		String[] plusSplit = s.split("\\+");
		for(int i=0;i<plusSplit.length;i++){
			String[] minusSplit = plusSplit[i].split("\\-");
			Summands posSmds = new Summands(Sign.POSITIVE, minusSplit[0]);
			results.add(posSmds);
			for(int j=1;j<minusSplit.length;j++){
				Summands minusSmds = new Summands(Sign.NEGATIVE, minusSplit[j]);
				results.add(minusSmds);
			}
		}
		return results;
	}
	
	
}

