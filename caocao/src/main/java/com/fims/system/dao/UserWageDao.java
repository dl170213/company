package com.fims.system.dao;

import com.fims.system.domain.UserWageDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:24
 */
@Mapper
public interface UserWageDao {

	UserWageDO get(Long id);
	
	List<UserWageDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserWageDO userWage);
	
	int update(UserWageDO userWage);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
