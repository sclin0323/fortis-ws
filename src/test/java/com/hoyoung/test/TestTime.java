package com.hoyoung.test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TestTime {

	public static void main(String[] args) {
		
		//LocalTime localTime = LocalTime.of(7, 0);
		
		//Time t = Time.valueOf(localTime);
		
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
		String text = date.format(formatter);
		
		System.out.println(text);
	}

}
