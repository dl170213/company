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

import com.fims.system.domain.SocialsecurityDO;
import com.fims.system.service.SocialsecurityService;
import com.fims.common.utils.PageUtils;
import com.fims.common.utils.Query;
import com.fims.common.utils.R;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:23
 */
 
@Controller
@RequestMapping("/system/socialsecurity")
public class SocialsecurityController {
	@Autowired
	private SocialsecurityService socialsecurityService;
	
	@GetMapping()
	@RequiresPermissions("system:socialsecurity:socialsecurity")
	String Socialsecurity(){
	    return "system/socialsecurity/socialsecurity";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:socialsecurity:socialsecurity")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SocialsecurityDO> socialsecurityList = socialsecurityService.list(query);
		int total = socialsecurityService.count(query);
		PageUtils pageUtils = new PageUtils(socialsecurityList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:socialsecurity:add")
	String add(){
	    return "system/socialsecurity/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:socialsecurity:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SocialsecurityDO socialsecurity = socialsecurityService.get(id);
		model.addAttribute("socialsecurity", socialsecurity);
	    return "system/socialsecurity/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:socialsecurity:add")
	public R save( SocialsecurityDO socialsecurity){
		if(socialsecurityService.save(socialsecurity)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:socialsecurity:edit")
	public R update( SocialsecurityDO socialsecurity){
		socialsecurityService.update(socialsecurity);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:socialsecurity:remove")
	public R remove( Long id){
		if(socialsecurityService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:socialsecurity:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		socialsecurityService.batchRemove(ids);
		return R.ok();
	}
	
}
