package com.hoyoung.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMatch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println("abc-123".matches("[-a-zA-Z0-9|\\.]*"));
		
		System.out.println("abc -123".matches("[-a-zA-Z0-9|\\.]*"));
		
		System.out.println("AAA-123".matches("[-a-zA-Z0-9|\\.]*"));
		System.out.println("AAA-123中文".matches("[-a-zA-Z0-9|\\.]*"));
		
		/*
		System.out.println("abc-def".matches("^[a-z\\-]+$"));
		
		System.out.println("abc-123".matches("[-a-zA-Z0-9|\\-]*"));
		
		Pattern p = Pattern.compile("^[a-z\\-]+$");
		   String line = "abc-def";
		   Matcher matcher = p.matcher(line);
		   System.out.println(matcher.matches());  // true
		   */
	}

}
