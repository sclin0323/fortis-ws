package com.hoyoung.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import groovy.util.logging.Log4j;

public class TestLocalDate {

	@Test
	public void test01(){
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
		String text = date.format(formatter);
		
		System.out.println(text);
	}
	
}
