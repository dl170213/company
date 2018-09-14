package com.fims.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import com.fims.system.domain.UserWageDO;
import com.fims.system.service.UserWageService;
import com.fims.common.utils.PageUtils;
import com.fims.common.utils.Query;
import com.fims.common.utils.R;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:24
 */
 
@Controller
@RequestMapping("/system/userWage")
public class UserWageController {
	@Autowired
	private UserWageService userWageService;
	
	@GetMapping()
	@RequiresPermissions("system:userWage:userWage")
	String UserWage(){
	    return "system/userWage/userWage";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:userWage:userWage")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserWageDO> userWageList = userWageService.list(query);
		int total = userWageService.count(query);
		PageUtils pageUtils = new PageUtils(userWageList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:userWage:add")
	String add(){
	    return "system/userWage/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:userWage:edit")
	String edit(@PathVariable("id") Long id,Model model){
		UserWageDO userWage = userWageService.get(id);
		model.addAttribute("userWage", userWage);
	    return "system/userWage/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:userWage:add")
	public R save( UserWageDO userWage){
		if(userWageService.save(userWage)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:userWage:edit")
	public R update( UserWageDO userWage){
		userWageService.update(userWage);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:userWage:remove")
	public R remove( Long id){
		if(userWageService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:userWage:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userWageService.batchRemove(ids);
		return R.ok();
	}
	
}
