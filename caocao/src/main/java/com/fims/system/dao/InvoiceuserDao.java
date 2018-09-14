package com.fims.system.dao;

import com.fims.system.domain.InvoiceuserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-14 16:25:42
 */
@Mapper
public interface InvoiceuserDao {

	InvoiceuserDO get(Long id);

	InvoiceuserDO getByUsername(String usernme);
	
	List<InvoiceuserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(InvoiceuserDO invoiceuser);
	
	int update(InvoiceuserDO invoiceuser);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
