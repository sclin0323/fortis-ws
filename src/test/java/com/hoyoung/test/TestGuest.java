package com.hoyoung.test;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoyoung.fortis.services.GuestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGuest {

	@Autowired
	private GuestService guestService;
	
	@Test
	public void test01() {
		
		System.out.println("Hello");
		
		//Map map = guestService.fetchById("123");
		
		
		
	}
}
