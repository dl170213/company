package com.fims.system.dao;

import com.fims.system.domain.PoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 09:48:50
 */
@Mapper
public interface PoDao {

	PoDO get(Long id);

	String getPoSum(String number);

	List<PoDO> getByNumber(String number);

	List<PoDO> getByNumberAndSBInull(Map<String, Object> map);

	List<PoDO> getBySbi(String sbi);

	int clearSbi(String sbi);

	List<PoDO> list(Map<String, Object> map);

	List<PoDO> listSumTotal(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PoDO po);
	
	int update(PoDO po);

	int updateByPo(PoDO po);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
