package com.fims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fims.system.dao.SocialsecurityDao;
import com.fims.system.domain.SocialsecurityDO;
import com.fims.system.service.SocialsecurityService;



@Service
public class SocialsecurityServiceImpl implements SocialsecurityService {
	@Autowired
	private SocialsecurityDao socialsecurityDao;
	
	@Override
	public SocialsecurityDO get(Long id){
		return socialsecurityDao.get(id);
	}
	
	@Override
	public List<SocialsecurityDO> list(Map<String, Object> map){
		return socialsecurityDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return socialsecurityDao.count(map);
	}
	
	@Override
	public int save(SocialsecurityDO socialsecurity){
		return socialsecurityDao.save(socialsecurity);
	}
	
	@Override
	public int update(SocialsecurityDO socialsecurity){
		return socialsecurityDao.update(socialsecurity);
	}
	
	@Override
	public int remove(Long id){
		return socialsecurityDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return socialsecurityDao.batchRemove(ids);
	}
	
}
