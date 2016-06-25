package com.hoyoung.fortis.services;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import com.hoyoung.fortis.command.UserDeviceCommand;
import com.hoyoung.fortis.dao.SysSetting;
import com.hoyoung.fortis.dao.UserDevice;
import com.hoyoung.fortis.web.UserDeviceController;

public class UserDeviceServiceImpl extends BaseServiceImpl implements UserDeviceService {
	final static Logger log = Logger.getLogger(UserDeviceServiceImpl.class);

	private String searchWordSql;

	public void setSearchWordSql(String searchWordSql) {
		this.searchWordSql = searchWordSql;
	}

	@Override
	public List fetchBySearchWord(String searchWord, int page, int start, int limit) {

		String word = "%" + searchWord + "%";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getEntityClass());
		detachedCriteria.add(Restrictions.or(
				Restrictions.like("deviceName", word), 
				Restrictions.like("applicantId", word),
				Restrictions.like("macAddress", word), 
				Restrictions.like("applicantName", word)));

		List dataList = fortisDAO.findByCriteria(detachedCriteria, start, limit);

		return toMapList(dataList, true);
	}

	@Transactional(readOnly = true)
	public long fetchCountBySearchWord(String searchWord, int page, int start, int limit) {

		String word = "%" + searchWord + "%";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getEntityClass());
		detachedCriteria.add(Restrictions.or(
				Restrictions.like("deviceName", word), 
				Restrictions.like("applicantId", word),
				Restrictions.like("macAddress", word), 
				Restrictions.like("applicantName", word)));

		if (detachedCriteria == null) {
			return 0;
		}

		return fortisDAO.fetchCountByCriteria(detachedCriteria);
	}

	@Override
	public String validateCreate(Object obj) {
		UserDeviceCommand cmd = (UserDeviceCommand) obj;
		
		// 新增檢核 1. DeviceName 是否已經存在; 2. 網卡是否已經存在
		UserDevice o = (UserDevice) fortisDAO.findById(getEntityClass(), cmd.getDeviceName());
		if (o != null) {
			return "該 Device Name 已經存在!!";
		}
		
		List lists = fortisDAO.findByProperty(getEntityClass(), "macAddress", cmd.getMacAddress());
		if(lists.size() >0) {
			return "該 Mac Address 已經存在!!";
		}
		
		return null;
	}

	@Override
	public Map<String, Object> create(Object obj) {
		UserDeviceCommand cmd = (UserDeviceCommand) obj;

		UserDevice o = new UserDevice();
		o.setDeviceName(cmd.getDeviceName());
		o.setApplicantDate(new Date());
		o.setApplicantName(cmd.getApplicantName());
		o.setApplicantTime(new Time(new Date().getTime()));
		o.setApplicantId(cmd.getApplicantId());
		o.setMacAddress(cmd.getMacAddress());
		
		o.setCrtUid("sysadmin");
		o.setCrtName("syaadmin");
		o.setCrtDate(new Date());
		o.setCrtTime(new Time(new Date().getTime()));

		fortisDAO.save(o);

		return toMap(o, true);
	}
	
	@Override
	public String validateUpdate(Object obj) {
		UserDeviceCommand cmd = (UserDeviceCommand) obj;
		
		// 檢核 2. 網卡除了自己是否已經存在
		List<UserDevice> list = fortisDAO.findByProperty(getEntityClass(), "macAddress", cmd.getMacAddress());
		if(list.size() >0) {
			for(UserDevice o : list) {
				if(o.getDeviceName() != cmd.getDeviceName()) {
					return "該 Mac Address 已經存在!!";
				}
			}
		}
		
		return null;
	}

	@Override
	public Map<String, Object> update(Object obj) {
		

		UserDeviceCommand cmd = (UserDeviceCommand) obj;

		UserDevice o = (UserDevice) fortisDAO.findById(getEntityClass(), cmd.getDeviceName());

		o.setApplicantDate(new Date());
		o.setApplicantName(cmd.getApplicantName());
		o.setApplicantTime(new Time(new Date().getTime()));
		o.setApplicantId(cmd.getApplicantId());
		o.setMacAddress(cmd.getMacAddress());

		// update
		o.setUpdUid("sysadmin");
		o.setUpdName("sysadmin");
		o.setUpdDate(new Date());
		o.setUpdTime(new Time(new Date().getTime()));

		fortisDAO.attachDirty(o);

		return toMap(o, true);
	}

	@Override
	public Map<String, Object> delete(Object obj) {
		String userDeviceId = (String) obj;
		
		UserDevice o = (UserDevice) fortisDAO.findById(getEntityClass(), userDeviceId);
		fortisDAO.delete(o);

		return toMap(o, true);
	}

	@Override
	protected Map<String, Object> toMap(Object obj, boolean isFull) {
		UserDevice o = (UserDevice) obj;

		Map<String, Object> m = new HashMap<String, Object>();
		
		if (isFull) {
			m.put("deviceName", o.getDeviceName());
			m.put("applicantId", o.getApplicantId());
			m.put("applicantName", o.getApplicantName());
			m.put("applicantDate", toDateString(o.getApplicantDate()));
			m.put("applicantTime", toTimeString(o.getApplicantTime()));
			m.put("macAddress", o.getMacAddress());

			m.put("crtUid", o.getCrtUid());
			m.put("crtName", o.getCrtName());
			m.put("crtDate", toDateString(o.getCrtDate()));
			m.put("crtTime", toTimeString(o.getCrtTime()));
			m.put("updUid", o.getUpdUid());
			m.put("updName", o.getUpdName());
			m.put("updDate", toDateString(o.getUpdDate()));
			m.put("updTime", toTimeString(o.getUpdTime()));
		}

		return m;
	}

	@Override
	protected Class getEntityClass() {
		return UserDevice.class;
	}



}
