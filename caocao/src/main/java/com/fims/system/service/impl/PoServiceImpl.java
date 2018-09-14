package com.fims.system.service.impl;

import com.fims.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fims.system.dao.PoDao;
import com.fims.system.domain.PoDO;
import com.fims.system.service.PoService;

import static com.fims.common.utils.DateUtils.getdep;


@Service
public class PoServiceImpl implements PoService {
	@Autowired
	private PoDao poDao;
	
	@Override
	public PoDO get(Long id){
		return poDao.get(id);
	}
	
	@Override
	public List<PoDO> list(Map<String, Object> map){
		return poDao.list(map);
	}

	@Override
	public List<PoDO> listSumTotal(Map<String, Object> map) {
		return poDao.listSumTotal(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return poDao.count(map);
	}
	
	@Override
	public int save(PoDO po){
		return poDao.save(po);
	}
	
	@Override
	public int update(PoDO po){
		return poDao.update(po);
	}

	@Override
	public int updateByPo(PoDO po){
		return poDao.updateByPo(po);
	}
	
	@Override
	public int remove(Long id){
		return poDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return poDao.batchRemove(ids);
	}

	@Override
	public List<PoDO> getByNumber(String number) {
		return poDao.getByNumber(number);
	}

	@Override
	public List<PoDO> getBySbi(String sbi) {
		return poDao.getBySbi(sbi);
	}

	@Override
	public int clearSbi(String sbi) {
		return poDao.clearSbi(sbi);
	}

	@Override
	public Map poCount() {
		Map<String,Object> map = new HashMap<>();
		//获取上周
		int lastCount = count(DateUtils.getLastTimeInterval());
		//获取本周
		int Count = count(DateUtils.getTimeInterval(new Date()));
		System.out.println("lastCount:"+lastCount+",Count:"+Count);
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

	@Override
	public String getPoSum(String number) {
		return poDao.getPoSum(number);
	}

	@Override
	public List<PoDO> getByNumberAndSBInull(Map<String, Object> map)  {
		return poDao.getByNumberAndSBInull(map);
	}
}
