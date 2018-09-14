package com.fims.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fims.system.domain.UserBaseDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fims.system.domain.UserdetailDO;
import com.fims.system.service.UserdetailService;
import com.fims.common.utils.PageUtils;
import com.fims.common.utils.Query;
import com.fims.common.utils.R;

/**
 * 员工详细信息表
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:24
 */
 
@Controller
@RequestMapping("/system/userdetail")
public class UserdetailController {
	@Autowired
	private UserdetailService userdetailService;
	
	@GetMapping()
	@RequiresPermissions("system:userdetail:userdetail")
	String Userdetail(){
	    return "system/userdetail/userdetail";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:userdetail:userdetail")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserdetailDO> userdetailList = userdetailService.list(query);

		UserdetailDO userdetailDO = new UserdetailDO();
		userdetailDO.setId(Long.parseLong("1"));
		userdetailDO.setUsername("1011");
		userdetailDO.setName("测试");
		userdetailDO.setDeptment("立业/人事部");
		userdetailDO.setEntryTime(new Date());
		userdetailDO.setProject("浙江电信-金华");
		userdetailDO.setDuty("人事");
		userdetailDO.setRemark("测试用例");
		userdetailDO.setReseve1("在职");
		userdetailList.add(userdetailDO);

		UserdetailDO userdetailDO1 = new UserdetailDO();
		userdetailDO1.setId(Long.parseLong("2"));
		userdetailDO1.setUsername("1012");
		userdetailDO1.setName("张三");
		userdetailDO1.setDeptment("立业/网优部");
		userdetailDO1.setEntryTime(new Date());
		userdetailDO1.setProject("浙江电信-金华");
		userdetailDO1.setDuty("普通员工");
		userdetailDO1.setRemark("测试用例");
		userdetailDO1.setReseve1("调整");
		userdetailList.add(userdetailDO1);

		UserdetailDO userdetailDO3 = new UserdetailDO();
		userdetailDO3.setId(Long.parseLong("4"));
		userdetailDO3.setUsername("1014");
		userdetailDO3.setName("王二");
		userdetailDO3.setDeptment("立业/人事部");
		userdetailDO3.setEntryTime(new Date());
		userdetailDO3.setProject("浙江电信-金华");
		userdetailDO3.setDuty("人事");
		userdetailDO3.setRemark("测试用例");
		userdetailDO3.setReseve1("试用");
		userdetailList.add(userdetailDO3);

		UserdetailDO userdetailDO2 = new UserdetailDO();
		userdetailDO2.setId(Long.parseLong("3"));
		userdetailDO2.setUsername("1013");
		userdetailDO2.setName("李四");
		userdetailDO2.setDeptment("立业/人事部");
		userdetailDO2.setEntryTime(new Date());
		userdetailDO2.setProject("浙江电信-金华");
		userdetailDO2.setDuty("人事");
		userdetailDO2.setRemark("测试用例");
		userdetailDO2.setReseve1("离职");
		userdetailList.add(userdetailDO2);
		int total = userdetailService.count(query);
		PageUtils pageUtils = new PageUtils(userdetailList, total+4);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:userdetail:add")
	String add(){
	    return "system/userdetail/add";
	}

	@GetMapping("/remind/setting")
	@RequiresPermissions("system:userdetail:remind")
	String remindSetting(){
		return "system/userdetail/remindSetting";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:userdetail:edit")
	String edit(@PathVariable("id") Long id,Model model){
	//	UserdetailDO userdetail = userdetailService.get(id);
		UserBaseDO userbase = new UserBaseDO();
		userbase.setId(Long.parseLong("1"));
		userbase.setUsername("1011");
		userbase.setName("测试");
		userbase.setDeptid(Long.parseLong("1"));
		userbase.setDeptname("立业/人事部");
		userbase.setProjectId(Long.parseLong("1"));
		userbase.setProject("浙江电信-杭州");
		userbase.setStatus("1");
		userbase.setMail("test@jesoncom.com");
		userbase.setTel("13300752146");
		userbase.setEntryTime(new Date());
		userbase.setPositiveTime(new Date());
		userbase.setLeaveTime(new Date());
		userbase.setDuty("人事");
		userbase.setJoblevel("");
		userbase.setIdnumber("310312198912134455");
		userbase.setIdaddress("上海市静安区柳营路515弄1-17号");
		userbase.setGraduateschool("北京大学");
		userbase.setMajor("行政管理");
		userbase.setEducation("研究生");
		userbase.setDiploma("12345678912543364");
		userbase.setGraduateyear(new Date());
		userbase.setContracttime(new Date());
		userbase.setBasewage("6000");
		userbase.setReimbursement("3000");
		userbase.setProbationwage("4000");
		userbase.setProbursement("1000");
		userbase.setWagecardnumber("8586476123456987");
		userbase.setCoupletnumber("400231546");
		userbase.setOpenbank("中国农业银行上海静安支行");
		userbase.setRemark("测试用户");
		userbase.setWagecardnumber1("8586476123456987");
		userbase.setCoupletnumber("400231546");
		userbase.setOpenbank1("中国农业银行上海静安支行");

		model.addAttribute("userdetail", userbase);
	    return "system/userdetail/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:userdetail:add")
	public R save( UserdetailDO userdetail){
		if(userdetailService.save(userdetail)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:userdetail:edit")
	public R update( UserdetailDO userdetail){
		userdetailService.update(userdetail);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:userdetail:remove")
	public R remove( Long id){
		if(userdetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:userdetail:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userdetailService.batchRemove(ids);
		return R.ok();
	}
	
}
