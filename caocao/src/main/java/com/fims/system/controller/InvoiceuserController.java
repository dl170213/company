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

import com.fims.system.domain.InvoiceuserDO;
import com.fims.system.service.InvoiceuserService;
import com.fims.common.utils.PageUtils;
import com.fims.common.utils.Query;
import com.fims.common.utils.R;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-14 16:25:42
 */
 
@Controller
@RequestMapping("/system/invoiceuser")
public class InvoiceuserController {
	@Autowired
	private InvoiceuserService invoiceuserService;
	
	@GetMapping()
	@RequiresPermissions("system:invoiceuser:invoiceuser")
	String Invoiceuser(){
	    return "system/invoiceuser/invoiceuser";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:invoiceuser:invoiceuser")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<InvoiceuserDO> invoiceuserList = invoiceuserService.list(query);
		int total = invoiceuserService.count(query);
		PageUtils pageUtils = new PageUtils(invoiceuserList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:invoiceuser:add")
	String add(){
	    return "system/invoiceuser/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:invoiceuser:edit")
	String edit(@PathVariable("id") Long id,Model model){
		InvoiceuserDO invoiceuser = invoiceuserService.get(id);
		model.addAttribute("invoiceuser", invoiceuser);
	    return "system/invoiceuser/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:invoiceuser:add")
	public R save( InvoiceuserDO invoiceuser){
		if(invoiceuserService.save(invoiceuser)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:invoiceuser:edit")
	public R update( InvoiceuserDO invoiceuser){
		invoiceuserService.update(invoiceuser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:invoiceuser:remove")
	public R remove( Long id){
		if(invoiceuserService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:invoiceuser:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		invoiceuserService.batchRemove(ids);
		return R.ok();
	}
	
}
