package com.fims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fims.system.dao.PoSbiDao;
import com.fims.system.domain.PoSbiDO;
import com.fims.system.service.PoSbiService;



@Service
public class PoSbiServiceImpl implements PoSbiService {
	@Autowired
	private PoSbiDao poSbiDao;
	
	@Override
	public PoSbiDO get(Long id){
		return poSbiDao.get(id);
	}
	
	@Override
	public List<PoSbiDO> list(Map<String, Object> map){
		return poSbiDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return poSbiDao.count(map);
	}
	
	@Override
	public int save(PoSbiDO poSbi){
		return poSbiDao.save(poSbi);
	}
	
	@Override
	public int update(PoSbiDO poSbi){
		return poSbiDao.update(poSbi);
	}
	
	@Override
	public int remove(Long id){
		return poSbiDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return poSbiDao.batchRemove(ids);
	}

	@Override
	public int removeByPo(String poid) {
		return poSbiDao.removeByPo(poid);
	}

	@Override
	public int removeBySbi(String sbiid) {
		return poSbiDao.removeBySbi(sbiid);
	}
}
