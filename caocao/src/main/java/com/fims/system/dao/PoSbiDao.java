package com.fims.system.dao;

import com.fims.system.domain.PoSbiDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-09-05 09:50:58
 */
@Mapper
public interface PoSbiDao {

	PoSbiDO get(Long id);
	
	List<PoSbiDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PoSbiDO poSbi);
	
	int update(PoSbiDO poSbi);
	
	int remove(Long id);

	int removeByPo(String poid);

	int removeBySbi(String sbiid);
	
	int batchRemove(Long[] ids);
}
