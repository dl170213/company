package com.fims.system.service.impl;

import com.fims.common.utils.DateUtils;
import com.fims.system.dao.PoDao;
import com.fims.system.domain.PoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fims.system.dao.SbiDao;
import com.fims.system.domain.SbiDO;
import com.fims.system.service.SbiService;

import static com.fims.common.utils.DateUtils.getdep;


@Service
public class SbiServiceImpl implements SbiService {
	@Autowired
	private SbiDao sbiDao;
	@Autowired
	private PoDao poDao;
	@Override
	public SbiDO get(Long id){
		return sbiDao.get(id);
	}
	
	@Override
	public List<SbiDO> list(Map<String, Object> map){
		List<SbiDO> list = sbiDao.list(map);

		return list;
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sbiDao.count(map);
	}
	
	@Override
	public int save(SbiDO sbi){
		return sbiDao.save(sbi);
	}
	
	@Override
	public int update(SbiDO sbi){
		return sbiDao.update(sbi);
	}
	
	@Override
	public int remove(Long id){
		return sbiDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sbiDao.batchRemove(ids);
	}

	@Override
	public SbiDO getBySbi(String sbi) {
		return sbiDao.getBySbi(sbi);
	}

	@Override
	public Map sbiCount() {
		Map<String,Object> map = new HashMap<>();
		//获取上周
		int lastCount = count(DateUtils.getLastTimeInterval());
		//获取本周
		int Count = count(DateUtils.getTimeInterval(new Date()));
		if(Count==0){
			map.put("count",Count);
			map.put("dep","0%");
			map.put("code","1");//上涨
			return map;
		}else{
			if(lastCount==0){
				map.put("count",Count);
				map.put("dep","100%");
				map.put("code","1");//上涨
				return map;
			}else{
				if(Count>lastCount){
					map.put("count",Count);
					map.put("dep",getdep(Count-lastCount,lastCount));
					map.put("code","1");//上涨
					return map;
				}else{
					map.put("count",Count);
					map.put("dep",getdep(lastCount-Count,lastCount));
					map.put("code","0");//上涨
					return map;
				}
			}
		}
	}
}
