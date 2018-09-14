package com.fims.system.service;

import com.fims.system.domain.PmDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 15:39:34
 */
public interface PmService {
	
	PmDO get(Long id);
	
	List<PmDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PmDO pm);
	
	int update(PmDO pm);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
