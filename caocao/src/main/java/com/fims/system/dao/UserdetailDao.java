package com.fims.system.dao;

import com.fims.system.domain.UserdetailDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 员工详细信息表
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:24
 */
@Mapper
public interface UserdetailDao {

	UserdetailDO get(Long id);
	
	List<UserdetailDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserdetailDO userdetail);
	
	int update(UserdetailDO userdetail);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
