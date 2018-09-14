package com.fims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fims.system.dao.UserdetailDao;
import com.fims.system.domain.UserdetailDO;
import com.fims.system.service.UserdetailService;



@Service
public class UserdetailServiceImpl implements UserdetailService {
	@Autowired
	private UserdetailDao userdetailDao;
	
	@Override
	public UserdetailDO get(Long id){
		return userdetailDao.get(id);
	}
	
	@Override
	public List<UserdetailDO> list(Map<String, Object> map){
		return userdetailDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userdetailDao.count(map);
	}
	
	@Override
	public int save(UserdetailDO userdetail){
		return userdetailDao.save(userdetail);
	}
	
	@Override
	public int update(UserdetailDO userdetail){
		return userdetailDao.update(userdetail);
	}
	
	@Override
	public int remove(Long id){
		return userdetailDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userdetailDao.batchRemove(ids);
	}
	
}
