package com.fims.system.service;

import com.fims.system.domain.CheckDO;
import com.fims.system.domain.UserDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-23 11:09:40
 */
public interface CheckService {
	
	CheckDO get(Long id);

	UserDO getUserByUsername(String username);

	List<CheckDO> list(Map<String, Object> map);

	List<CheckDO> getByNumber(String id);
	
	int count(Map<String, Object> map);
	
	int save(CheckDO check);
	
	int update(CheckDO check);

	int updateRepeat(String number);

	int updateRepeat1(String number);

	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<CheckDO> listSetNames(List<CheckDO> list);

	CheckDO listSetNames(CheckDO checkDO);

	boolean exit(Map<String, Object> params);

	Map checkCount();
}
