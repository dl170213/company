package com.fims.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fims.common.service.ExportService;
import com.fims.common.service.ImportService;
import com.fims.system.service.SbiService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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

import com.fims.system.domain.PoDO;
import com.fims.system.service.PoService;
import com.fims.common.utils.PageUtils;
import com.fims.common.utils.Query;
import com.fims.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 09:48:50
 */
 
@Controller
@RequestMapping("/system/po")
public class PoController {
	@Autowired
	private PoService poService;
	@Autowired
	private SbiService sbiService;
	@Autowired
	private ImportService importService;
	@Autowired
	private ExportService exportService;
	
	@GetMapping()
	@RequiresPermissions("system:po:po")
	String Po(){
	    return "system/po/po";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:po:po")
	public PageUtils list(@RequestParam Map<String, Object> params){

		if(params.get("searchtime")!=null&&!"".equals(params.get("searchtime"))){
			String [] dates = params.get("searchtime").toString().split("~");
			params.put("starttime",dates[0]);
			params.put("endtime",dates[1]);
		}
		//查询列表数据
        Query query = new Query(params);
		List<PoDO> poList = poService.list(query);
		int total = poService.count(query);
		PageUtils pageUtils = new PageUtils(poList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("system:po:add")
	String add(){
		return "system/po/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:po:edit")
	String edit(@PathVariable("id") Long id,Model model){
		PoDO po = poService.get(id);
		model.addAttribute("po", po);
	    return "system/po/edit";
	}

	@GetMapping("/openExcelupload")
	@RequiresPermissions("system:po:import")
	String openExcelupload(){
		return "system/po/import";
	}

	@GetMapping("/poexport")
	@RequiresPermissions("system:po:export")
	String poexport(){
		return "system/po/export";
	}

	@GetMapping("/poexportdata")
	@RequiresPermissions("system:po:export")
	void poexportdata(HttpServletResponse response,String searchName,String searchtime){
		Map<String, Object> params = new HashMap<>();
		params.put("searchName",searchName);
		params.put("searchtime",searchtime);
		if(searchtime!=null&&!"".equals(searchtime)) {
			String[] dates = searchtime.split("~");
			params.put("starttime", dates[0]);
			params.put("endtime", dates[1]);
		}
		exportService.poExportOld(response,params);
	}

	/**
	 * 导入Excel PO
	 * **/
	@ResponseBody
	@PostMapping("/importExcel")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,String seachname) {
		System.out.println("seachname:"+seachname);
		return importService.importPO(file,seachname);
	}


	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:po:add")
	public R save( PoDO po){
		if(poService.save(po)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:po:edit")
	public R update( PoDO po){
		poService.update(po);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:po:remove")
	public R remove( Long id){
		PoDO pd = poService.get(id);
		if(pd==null){
			return R.ok();
		}
		if(pd.getSbi()!=null&&!"".equals(pd.getSbi())){
			return R.error(2,"已生成SBI无法删除!");
		}
		if(poService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:po:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){

		for(Long id:ids){
			PoDO pd  = poService.get(id);
			if(pd==null){
				continue;
			}
			if(pd.getSbi()!=null&&!"".equals(pd.getSbi())){
				return R.error(2,pd.getReseve1()+" 已生成SBI无法删除!");
			}
		}

		poService.batchRemove(ids);
		return R.ok();
	}

	@GetMapping("/openExceluploadBase")
	@RequiresPermissions("system:po:importbase")
	String openExceluploadBase(){
		return "system/po/importbase";
	}

	/**
	 * 导入Excel PO基础数据
	 * **/
	@ResponseBody
	@PostMapping("/importExcelBase")
	R importExcelBase(@RequestParam("file") MultipartFile file, HttpServletRequest request, String seachname) {
		System.out.println("seachname:"+seachname);
		return importService.importPOBase(file,seachname);
	}
}
