package com.fims.system.dao;

import com.fims.system.domain.SbiDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 09:48:50
 */
@Mapper
public interface SbiDao {

	SbiDO get(Long id);

	SbiDO getBySbi(String sbi);

	List<SbiDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SbiDO sbi);
	
	int update(SbiDO sbi);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
