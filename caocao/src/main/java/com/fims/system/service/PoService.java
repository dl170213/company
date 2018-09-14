package com.fims.system.service;

import com.fims.system.domain.PoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 09:48:50
 */
public interface PoService {
	
	PoDO get(Long id);
	
	List<PoDO> list(Map<String, Object> map);

	List<PoDO> listSumTotal(Map<String, Object> map);

	List<PoDO> getByNumber(String number);

	List<PoDO> getBySbi(String sbi);

	List<PoDO> getByNumberAndSBInull(Map<String, Object> map);

	int count(Map<String, Object> map);

	String getPoSum(String number);
	
	int save(PoDO po);
	
	int update(PoDO po);

	int clearSbi(String sbi);

	int updateByPo(PoDO po);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	Map poCount();
}
