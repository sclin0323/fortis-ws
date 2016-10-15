package com.hoyoung.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hoyoung.fortis.command.UserDeviceSyncCommand;
import com.hoyoung.fortis.dao.FortisDAO;
import com.hoyoung.fortis.dao.UserDevice;
import com.hoyoung.fortis.dao.UserDeviceLog;
import com.hoyoung.fortis.dao.UserDeviceSync;
import com.hoyoung.fortis.services.UserDeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestHibernate {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private UserDeviceService userDeviceService;

	@Autowired
	private FortisDAO fortisDAO;

	/*
	 * 基本新增
	 */
	@Test
	public void test01() {
		// start
		System.out.println(LocalDateTime.now().format(formatter));

		long timestamp = System.currentTimeMillis();
		ArrayList<UserDeviceSync> userDeviceSyncs = new ArrayList<UserDeviceSync>();
		for (int i = 100001; i <= 2000000; i++) {
			String value = String.valueOf(i);
			
			UserDeviceSync o = new UserDeviceSync();
			o.setMacAddress(String.valueOf(i));
			o.setTest(1L);
			o.setUserDevice(String.valueOf(i));
			o.setUserDeviceGroup(String.valueOf(i));
			
			fortisDAO.save(o);
		}

		// end
		System.out.println(LocalDateTime.now().format(formatter));
	}

	/*
	 * 測試 Rollback
	 */
	@Test
	@Transactional
	public void test02() {
		// start
		System.out.println(LocalDateTime.now().format(formatter));

		long timestamp = System.currentTimeMillis();
		ArrayList<UserDeviceSync> userDeviceSyncs = new ArrayList<UserDeviceSync>();
		for (int i = 1; i < 100; i++) {
			String value = String.valueOf(i);
			fortisDAO.attachDirty(new UserDeviceSync(value, value+"_update", value+"_update", timestamp));
			
			if( i== 80 ) {
				System.out.println("== RuntimeException ==");
				throw new RuntimeException();
			}
		}

		// end
		System.out.println(LocalDateTime.now().format(formatter));
	}

	@Test
	public void test03() {
		
	}

}
