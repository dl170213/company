package com.fims.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fims.common.service.ExportService;
import com.fims.common.service.ImportService;
import com.fims.common.utils.Memcach;
import com.fims.system.service.PoSbiService;
import com.fims.system.service.PoService;
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

import com.fims.system.domain.SbiDO;
import com.fims.system.service.SbiService;
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
@RequestMapping("/system/sbi")
public class SbiController {
	@Autowired
	private SbiService sbiService;

	@Autowired
	private PoService poService;

	@Autowired
	private ExportService exportService;

	@Autowired
	private ImportService importService;
	
	@GetMapping()
	@RequiresPermissions("system:sbi:sbi")
	String Sbi(){
	    return "system/sbi/sbi";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sbi:sbi")
	public PageUtils list(@RequestParam Map<String, Object> params){
		if(params.get("searchtime")!=null&&!"".equals(params.get("searchtime"))){
			String [] dates = params.get("searchtime").toString().split("~");
			params.put("starttime",dates[0]);
			params.put("endtime",dates[1]);
		}
		//查询列表数据
        Query query = new Query(params);
		List<SbiDO> sbiList = sbiService.list(query);

		int total = sbiService.count(query);
		PageUtils pageUtils = new PageUtils(sbiList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:sbi:add")
	String add(){
	    return "system/sbi/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("system:sbi:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SbiDO sbi = sbiService.get(id);
		model.addAttribute("sbi", sbi);
	    return "system/sbi/edit";
	}

	@GetMapping("/openExcelupload")
	@RequiresPermissions("system:sbi:import")
	String openExcelupload(){
		return "system/sbi/import";
	}

	/**
	 * 导入Excel SBI
	 * **/
	@ResponseBody
	@PostMapping("/importExcel")
	R upload(@RequestParam("file") MultipartFile[] file, HttpServletRequest request, String seachname) {
		System.out.println("seachname:"+seachname);
		R rs = new R();
		List<Map<String,Object>> list = new ArrayList<>();
		if(file.length>0){
			for(MultipartFile mf :file){
				System.out.println(mf.getOriginalFilename());
				R r = importService.importSBI2(mf,seachname);
				Map<String,Object> map = new HashMap<>();
				map.put("name",mf.getOriginalFilename());
				map.put("info",r);
				list.add(map);
			}
			rs.put("code",2);
			rs.put("msg","操作完成");
			rs.put("infos",list);
		}else{
			System.out.println("空文件，无文件");
			rs.put("code",1);
			rs.put("msg","请选择上传文件");
		}
		Memcach.EXCEL_STATUS.put(seachname,4);//校验失败
		return rs;
	}

	/**
	 * 导出Excel SBI
	 * **/
	@GetMapping("/sbiexportdata")
	@RequiresPermissions("system:sbi:export")
	void poexportdata(HttpServletResponse response, String searchName, String searchtime){
		Map<String, Object> params = new HashMap<>();
		params.put("searchName",searchName);
		params.put("searchtime",searchtime);
		if(searchtime!=null&&!"".equals(searchtime)) {
			String[] dates = searchtime.split("~");
			params.put("starttime", dates[0]);
			params.put("endtime", dates[1]);
		}
		exportService.sbiExport(response,params);
	}

	/**
	 * 导出Excel SBI
	 * **/
	@GetMapping("/sbiexportdetail")
	@RequiresPermissions("system:sbi:export")
	void sbiexportdetail(HttpServletResponse response, String searchName, String searchtime){
		Map<String, Object> params = new HashMap<>();
		params.put("searchName",searchName);
		params.put("searchtime",searchtime);
		if(searchtime!=null&&!"".equals(searchtime)) {
			String[] dates = searchtime.split("~");
			params.put("starttime", dates[0]);
			params.put("endtime", dates[1]);
		}
		exportService.sbiDetailExport(response,params);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sbi:add")
	public R save( SbiDO sbi){
		if(sbiService.save(sbi)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sbi:edit")
	public R update( SbiDO sbi){
		sbiService.update(sbi);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("system:sbi:remove")
	public R remove( Long id){
		//清除已关联的po中的sbi数据
		SbiDO sd = sbiService.get(id);
		if(sd == null){
			return R.ok();
		}
		poService.clearSbi(sd.getSbi());
	//	posbiService.removeBySbi(sbiService.get(id).getSbi());
		if(sbiService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sbi:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		for(Long id:ids){
			SbiDO sd = sbiService.get(id);
			if(sd == null){
				continue;
			}
			poService.clearSbi(sd.getSbi());
		}
		sbiService.batchRemove(ids);
		return R.ok();
	}
}
