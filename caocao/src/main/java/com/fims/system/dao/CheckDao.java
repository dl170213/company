package com.fims.system.dao;

import com.fims.system.domain.CheckDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-23 11:09:40
 */
@Mapper
public interface CheckDao {

	CheckDO get(Long id);
	
	List<CheckDO> list(Map<String, Object> map);

	List<CheckDO> getByNumber(String invoicenumber);

	int count(Map<String, Object> map);
	
	int save(CheckDO check);
	
	int update(CheckDO check);

	int updateRepeat(String number);

	int updateRepeat1(String number);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
