package com.fims.system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.fims.common.controller.BaseController;
import com.fims.common.domain.Suggest;
import com.fims.common.service.ImportService;
import com.fims.common.utils.*;
import com.fims.system.domain.InvoiceDO;
import com.fims.system.domain.InvoiceuserDO;
import com.fims.system.domain.UserDO;
import com.fims.system.service.InvoiceuserService;
import com.fims.system.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fims.system.domain.CheckDO;
import com.fims.system.service.CheckService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-23 11:09:40
 */
 
@Controller
@RequestMapping("/system/check")
public class CheckController extends BaseController{
	@Autowired
	private CheckService checkService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImportService importService;
	@Autowired
	private InvoiceuserService invoiceuserService;

	@GetMapping()
	@RequiresPermissions("system:check:check")
	String Check(){
	    return "system/check/check";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:check:check")
	public PageUtils list(@RequestParam Map<String, Object> params){

		//设置默认查询
		params.put("userIdCreate",getUsername());//默认只能查询自己的记录
		params.put("status","0");//默认查询未删除记录
		params.put("repeat","0");//默认查询未重复记录
		//查询列表数据
		Subject subject = SecurityUtils.getSubject();
		//查询是否拥有查询删除权限
		params.put("isPermitted",subject.isPermitted("system:check:repeatAndDelete"));
		if(params.get("searchtime")!=null&&!"".equals(params.get("searchtime"))){
			String [] dates = params.get("searchtime").toString().split("~");
			params.put("starttime",dates[0]);
			params.put("endtime",dates[1]);
		}
        Query query = new Query(params);
		List<CheckDO> checkList = checkService.list(query);

		//发票编号不为空且查询到数据，更新新的数据到数据库，改为有员工确认是否新增
		/*if(checkList.size()>0&&params.get("searchName")!=null&&!"".equals(params.get("searchName"))){
			CheckDO checkDO = new CheckDO();
			checkDO.setInvoicenumber(checkList.get(0).getInvoicenumber());
			checkDO.setUserIdCreate(getUserId());
			checkDO.setCreatetime(new Date());
			checkDO.setRepeat("1");
			checkDO.setStatus("0");
			checkService.save(checkDO);
		}*/
		int total = checkService.count(query);
		PageUtils pageUtils = new PageUtils(checkList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:check:add")
	String add(String code,String status,Model model){
		InvoiceDO invoiceDO = new InvoiceDO();
		if(!"-1".equals(code)){
			try {
				invoiceDO = InvoiceUtil.isInvoiceNumber(code.replace(",","，").replace("。","."));
			}catch (Exception pe){

			}
		}
		//设置时间格式yyyy-MM
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH,-1);
		model.addAttribute("createtime_a",adf.format(cal.getTime()).substring(0,7));

		model.addAttribute("status",status);
		model.addAttribute("invoice",invoiceDO);
		System.out.println(invoiceDO.toString());
	    return "system/check/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:check:edit")
	String edit(@PathVariable("id") Long id,Model model){
		CheckDO check = checkService.get(id);
		if(check.getCreatetime()!=null){
			check.setCreatetime_a(check.getCreatetime().toString().substring(0,7));
		}
		check.setUserNameCreate(check.getUserIdCreate());
		model.addAttribute("check", check);
	    return "system/check/edit";
	}

	@GetMapping("/openExcelupload")
	@RequiresPermissions("system:check:import")
	String openExcelupload(){
		return "system/check/import";
	}

	/**
	 * 导入Excel 发票
	 * **/
	@ResponseBody
	@PostMapping("/importExcel")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, String seachname) {
		System.out.println("seachname:"+seachname);
		return importService.importCheck(file,seachname,getUsername());
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:check:add")
	public R save( CheckDO check){

		Map params = new HashMap(16);
		params.put("invoicenumber",check.getInvoicenumber());
		params.put("status","0");//未删除
		if(!checkService.exit(params)){//不存在
			check.setRepeat("0");//未重复
		}else{
			check.setRepeat("1");//未重复
		}

		if(check.getCreatetime_a()!=null){
			//设置时间格式yyyy-MM
			SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				check.setCreatetime(adf.parse(check.getCreatetime_a()+"-15"));
			}catch (ParseException pe){

			}
		}
		//设置操作员
		check.setUserIdCreate(getUsername());
		check.setStatus("0");//未删除
	//	check.setRepeat("0");//未重复
		System.out.println(check.toString());
		if(checkService.save(check)>0){
			if(check.getUsername()!=null&&!"".equals(check.getUsername())&&userService.getByUsername(check.getUsername())==null){
				UserDO ud = new UserDO();
				ud.setName(check.getUserIdExpense());
				ud.setUsername(check.getUsername());
				ud.setDeptId(Long.parseLong("0"));
				ud.setStatus(0);
				ud.setPassword("111111");
				ud.setPassword(MD5Utils.encrypt(ud.getUsername(), ud.getPassword()));
				if(userService.save(ud)<=0){
					checkService.remove(check.getId());
					return R.error("新增账号失败！");
				}
			}
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:check:edit")
	public R update( CheckDO check){
		checkService.update(check);
		if(check.getUsername()!=null&&!"".equals(check.getUsername())&&userService.getByUsername(check.getUsername())==null){
			UserDO ud = new UserDO();
			ud.setName(check.getUserIdExpense());
			ud.setUsername(check.getUsername());
			ud.setDeptId(Long.parseLong("0"));
			ud.setStatus(0);
			ud.setPassword("111111");
			ud.setPassword(MD5Utils.encrypt(ud.getUsername(), ud.getPassword()));
			if(userService.save(ud)<=0){
				checkService.remove(check.getId());
				return R.error("新增账号失败！");
			}
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:check:remove")
	public R remove( Long id){
		CheckDO checkDO = checkService.get(id);
		if(checkDO==null){
			return R.ok();
		}
		checkDO.setStatus("1");
		if(checkService.update(checkDO)>0){
			Map<String,Object> map = new HashMap<>();
			map.put("status","0");
			map.put("invoicenumber",checkDO.getInvoicenumber());
			if(checkService.count(map) == 1){//更新所有重复发票状态
				checkService.updateRepeat(checkDO.getInvoicenumber());
			}
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:check:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		for(Long id:ids){
			CheckDO checkDO = checkService.get(id);
			if(checkDO==null){
				continue;
			}
			checkDO.setStatus("1");
			if(checkService.update(checkDO)<=0){
				return R.error();
			}
			Map<String,Object> map = new HashMap<>();
			map.put("status","0");
			map.put("invoicenumber",checkDO.getInvoicenumber());
			if(checkService.count(map) == 1){//更新所有重复发票状态
				checkService.updateRepeat(checkDO.getInvoicenumber());
			}
		}
		return R.ok();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !checkService.exit(params);
	}

	/**
	 * 获取所有人员信息
	 */
	@GetMapping("/getAllUser")
	@ResponseBody
	String getequipment(){
		Map<String,Object> map = new HashMap<>(16);
		List<InvoiceuserDO> list = invoiceuserService.list(map);
		Suggest suggest = new Suggest("",list==null?"":list);
		return JSON.toJSONString(suggest);
	}
}
