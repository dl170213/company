package com.fims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fims.system.dao.UserWageDao;
import com.fims.system.domain.UserWageDO;
import com.fims.system.service.UserWageService;



@Service
public class UserWageServiceImpl implements UserWageService {
	@Autowired
	private UserWageDao userWageDao;
	
	@Override
	public UserWageDO get(Long id){
		return userWageDao.get(id);
	}
	
	@Override
	public List<UserWageDO> list(Map<String, Object> map){
		return userWageDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userWageDao.count(map);
	}
	
	@Override
	public int save(UserWageDO userWage){
		return userWageDao.save(userWage);
	}
	
	@Override
	public int update(UserWageDO userWage){
		return userWageDao.update(userWage);
	}
	
	@Override
	public int remove(Long id){
		return userWageDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userWageDao.batchRemove(ids);
	}
	
}
