package com.fims.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.fims.system.dao.InvoiceuserDao;
import com.fims.system.domain.InvoiceuserDO;
import com.fims.system.service.InvoiceuserService;



@Service
public class InvoiceuserServiceImpl implements InvoiceuserService {
	@Autowired
	private InvoiceuserDao invoiceuserDao;
	
	@Override
	public InvoiceuserDO get(Long id){
		return invoiceuserDao.get(id);
	}
	
	@Override
	public List<InvoiceuserDO> list(Map<String, Object> map){
		return invoiceuserDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return invoiceuserDao.count(map);
	}
	
	@Override
	public int save(InvoiceuserDO invoiceuser){
		return invoiceuserDao.save(invoiceuser);
	}
	
	@Override
	public int update(InvoiceuserDO invoiceuser){
		return invoiceuserDao.update(invoiceuser);
	}
	
	@Override
	public int remove(Long id){
		return invoiceuserDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return invoiceuserDao.batchRemove(ids);
	}
	
}
