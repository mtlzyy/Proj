package com.zyy.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.zyy.bean.Composite;
import com.zyy.bean.Summands;
import com.zyy.enums.Sign;
import com.zyy.util.CUtil;
import com.zyy.util.SMDSupport;

public class UtilTest {

	@Test
	public void testPrimarySeparation() {
		String s = "dawdea+dasd dwqdwq = sdad";
		String[] ss = CUtil.primarySeparation(s);
		/*System.out.println(ss[0]+"\r"+ss[1]);
		if(s.contains("+")){
			String[] sc = ss[1].split("\\+");
			System.out.println(sc.length);
			System.out.println(sc[0]);
		}*/
	}
	
	/*@Test
	public void testRemoveBrackets(){
		
	}*/
	
	@Test
	public void testSecondarySeparation(){
		String s = "das+ d1212e+432 4-321-dasd122 -dawdw ko+d qw-223";
		String x = s.replace(" ", "");
		List<Summands> ss = CUtil.secondarySeparation(x);
		for(Summands smd:ss){
			System.out.print(smd);
		}
		System.out.println("\r*****************");
	}
	
	@Test
	public void testReverseSign(){
		System.out.println("test ReverseSign");
		String s = "dawdea+dasd dwqdwq = sdad";
		String[] primaryRes = CUtil.primarySeparation(s);
		List<Summands> leftRes = CUtil.secondarySeparation(primaryRes[0]);
		List<Summands> rightRes = SMDSupport.reverseSigns(CUtil.secondarySeparation(primaryRes[1]));
		leftRes.addAll(rightRes);
		if(leftRes!=null){
			for(Summands x:leftRes){
				System.out.print(x);
			}
		}
		System.out.println("\r*****************");
	}
	
	@Test
	public void testGeneral(){
		System.out.println("test General");
		String s = "S1";
		String s2 = "k/s*d";
		char[] chars = s2.toCharArray();
		/*System.out.println(Character.isLetter(chars[0]));
		System.out.println(Character.isLetter(chars[1]));*/
		System.out.println(chars[1]=='/');
		System.out.println(chars[3]=='*');
		char[] c = new char[]{'a','b','d','3','e'};
		String ss = new String(c);
		System.out.println(ss);
		System.out.println(Character.isDigit('0'));
		System.out.println("\r**************");
	}
	
	@Test
	public void testParseSummands(){
		System.out.println("test ParseSmds");
		String eqn = "43.232f^3r^3";
		String eqn2 = "2.4xyxy";
		String eqn3 = "2.5xasqwe";   
		String eqn4 = "2x^5y^3zd";   
		String eqn5 = "2.5";   
		String eqn6 = "1";   
		String eqn7 = "x";   
		String eqn8 = "y^2x^2y"; 
		String[] sx = new String[]{eqn,eqn2,eqn3,eqn4,eqn5,eqn6,eqn7,eqn8};
		for(int i=6; i<sx.length;i++){
			Composite c = new Composite();
			Summands smd = new Summands(Sign.POSITIVE, sx[i]);
			c = SMDSupport.parseSummands(smd);
			System.out.println("eqn"+(i+1)+" is\t"+c);
		}
		/*String eqn2 = "2.4xyxy";
		Composite c = new Composite();
		Summands smd = new Summands(Sign.POSITIVE, eqn2);
		c = SMDSupport.parseSummands(smd);
		System.out.println(c);*/
	}
}
