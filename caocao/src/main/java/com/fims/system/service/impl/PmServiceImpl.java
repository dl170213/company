package com.fims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fims.system.dao.PmDao;
import com.fims.system.domain.PmDO;
import com.fims.system.service.PmService;



@Service
public class PmServiceImpl implements PmService {
	@Autowired
	private PmDao pmDao;
	
	@Override
	public PmDO get(Long id){
		return pmDao.get(id);
	}
	
	@Override
	public List<PmDO> list(Map<String, Object> map){
		return pmDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return pmDao.count(map);
	}
	
	@Override
	public int save(PmDO pm){
		return pmDao.save(pm);
	}
	
	@Override
	public int update(PmDO pm){
		return pmDao.update(pm);
	}
	
	@Override
	public int remove(Long id){
		return pmDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return pmDao.batchRemove(ids);
	}
	
}
