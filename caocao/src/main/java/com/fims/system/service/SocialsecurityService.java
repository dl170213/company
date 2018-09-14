package com.fims.system.service;

import com.fims.system.domain.SocialsecurityDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:23
 */
public interface SocialsecurityService {
	
	SocialsecurityDO get(Long id);
	
	List<SocialsecurityDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SocialsecurityDO socialsecurity);
	
	int update(SocialsecurityDO socialsecurity);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
