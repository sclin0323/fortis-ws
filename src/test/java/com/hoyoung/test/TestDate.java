package com.hoyoung.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) throws Exception {
		String str1 = "1471714605109";
		String str2 = "1471714605110";
		
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH mm ss sss");
		Date date1 = new Date(Long.parseLong(str1));
		System.out.println(sf1.format(date1));
		
		
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH mm ss sss");
		Date date2 = new Date(Long.parseLong(str2));
		System.out.println(sf2.format(date2));
		
		
		
		
	}

}
