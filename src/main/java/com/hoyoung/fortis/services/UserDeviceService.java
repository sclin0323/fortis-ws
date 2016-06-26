package com.hoyoung.fortis.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hoyoung.fortis.command.UserDeviceCommand;

public interface UserDeviceService extends BaseService{

		// 查詢
		public List fetchBySearchWord(String searchWord,int page,int start,int limit);
		
		public long fetchCountBySearchWord(String searchWord, int page, int start, int limit);

		// 檢查是否有異動網卡
		public boolean isUpdateMacAddress(UserDeviceCommand cmd);
}
