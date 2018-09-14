package com.fims.system.service.impl;

import com.fims.common.utils.DateUtils;
import com.fims.common.utils.InvoiceUtil;
import com.fims.system.dao.InvoiceuserDao;
import com.fims.system.dao.UserDao;
import com.fims.system.domain.InvoiceDO;
import com.fims.system.domain.InvoiceuserDO;
import com.fims.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fims.system.dao.CheckDao;
import com.fims.system.domain.CheckDO;
import com.fims.system.service.CheckService;

import static com.fims.common.utils.DateUtils.getdep;


@Service
public class CheckServiceImpl implements CheckService {
	@Autowired
	private CheckDao checkDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private InvoiceuserDao invoiceuserDao;
	@Override
	public CheckDO get(Long id){
		return checkDao.get(id);
	}

	@Override
	public List<CheckDO> getByNumber(String id){
		return checkDao.getByNumber(id);
	}
	
	@Override
	public List<CheckDO> list(Map<String, Object> map){
		//判断searchName获取发票编号
		if(map.get("searchName")!=null&&!"".equals(map.get("searchName"))){
			try {
				map.put("invoicenumber", InvoiceUtil.isInvoiceNumber(map.get("searchName").toString().replace(",","，")
						.replace("。",".")).getInvoiceNumber());
				map.remove("repeat");
			}catch (Exception pe){

			}
		}

		//判断是否拥有管理权限
		if("true".equals(map.get("isPermitted").toString())){
			System.out.println("=====拥有管理权限====");
			map.remove("userIdCreate");//管理员查询全部数据

			if("true".equals(map.get("rerepeat").toString())){
				map.put("repeat","1");//管理员查询全部数据
			}
			if("true".equals(map.get("restatus").toString())){
				map.put("status","1");//管理员查询全部数据
			}
		}
		System.out.println(map.toString());
		List<CheckDO> list = checkDao.list(map);

		return list;
	}
	
	@Override
	public int count(Map<String, Object> map){
		return checkDao.count(map);
	}
	
	@Override
	public int save(CheckDO check){
		int count = checkDao.save(check);
		if(count>0&&check.getInvoicenumber()!=null&&!"".equals(check.getInvoicenumber())){
			if(check.getRepeat().equals("1")){//更新所有重复发票状态
				checkDao.updateRepeat1(check.getInvoicenumber());
			}
		}
		return count;
	}
	
	@Override
	public int update(CheckDO check){
		int cunt = checkDao.update(check);
		///暂未修改
		return cunt;
	}
	
	@Override
	public int remove(Long id){
		return checkDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return checkDao.batchRemove(ids);
	}
	/*
	 * 设置报销人姓名
	 * */
	@Override
	public List<CheckDO> listSetNames(List<CheckDO> list){
		for (CheckDO checkDO:list){
			checkDO = listSetNames(checkDO);
		}
		return list;
	}
	/*
     * 设置报销人姓名
     * */
	@Override
	public CheckDO listSetNames(CheckDO checkDO){
		if (checkDO.getUserIdExpense()!=null&&!"".equals(checkDO.getUserIdExpense())) {
			String[] ids = checkDO.getUserIdExpense().split(",");
			String idnames = "";
			for (String id : ids) {
				if (id != null && !"".equals(id)) {
					idnames += userDao.get(Long.parseLong(id)).getName() + ",";
				}
			}
			checkDO.setUserNames(idnames);
			//设置创建人名字
			checkDO.setUserNameCreate(checkDO.getUserIdCreate());
		}
		return checkDO;
	}

	@Override
	public UserDO getUserByUsername(String username) {
		UserDO user = userDao.getByUsername(username);
		if(user!=null&&user.getDeptId()==0){
			user.setDeptName("总机构");
		}
		return user;
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = checkDao.list(params).size() > 0;
		return exit;
	}

	@Override
	public Map checkCount() {
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> map1 = new HashMap<>();
		//获取上个月
		map1 = DateUtils.getMonthInterval(2);
		map1.put("status","0");
		int lastCount = count(map1);
		//获取这个月
		map1 = DateUtils.getMonthInterval(1);
		map1.put("status","0");
		int Count = count(map1);
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
	public int updateRepeat(String number) {
		return checkDao.updateRepeat(number);
	}

	@Override
	public int updateRepeat1(String number) {
		return checkDao.updateRepeat1(number);
	}
}
