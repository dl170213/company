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

import com.fims.system.domain.PmDO;
import com.fims.system.service.PmService;
import com.fims.common.utils.PageUtils;
import com.fims.common.utils.Query;
import com.fims.common.utils.R;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 15:39:34
 */
 
@Controller
@RequestMapping("/system/pm")
public class PmController {
	@Autowired
	private PmService pmService;
	
	@GetMapping()
	@RequiresPermissions("system:pm:pm")
	String Pm(){
	    return "system/pm/pm";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:pm:pm")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PmDO> pmList = pmService.list(query);
		int total = pmService.count(query);
		PageUtils pageUtils = new PageUtils(pmList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:pm:add")
	String add(){
	    return "system/pm/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:pm:edit")
	String edit(@PathVariable("id") Long id,Model model){
		PmDO pm = pmService.get(id);
		model.addAttribute("pm", pm);
	    return "system/pm/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:pm:add")
	public R save( PmDO pm){
		if(pmService.save(pm)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:pm:edit")
	public R update( PmDO pm){
		pmService.update(pm);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:pm:remove")
	public R remove( Long id){
		if(pmService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:pm:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		pmService.batchRemove(ids);
		return R.ok();
	}
	
}
