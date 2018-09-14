package com.fims.system.controller;

import com.fims.common.annotation.Log;
import com.fims.common.config.Constant;
import com.fims.common.controller.BaseController;
import com.fims.common.utils.R;
import com.fims.system.dao.UserRoleDao;
import com.fims.system.domain.RoleDO;
import com.fims.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {
	String prefix = "system/role";
	@Autowired
	RoleService roleService;
	@Autowired
	UserRoleDao userRoleDao;
	@RequiresPermissions("sys:role:role")
	@GetMapping()
	String role() {
		return prefix + "/role";
	}

	@RequiresPermissions("sys:role:role")
	@GetMapping("/list")
	@ResponseBody()
	List<RoleDO> list() {
		List<RoleDO> roles = roleService.list();
		return roles;
	}

	@Log("添加角色")
	@RequiresPermissions("sys:role:add")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}

	@Log("编辑角色")
	@RequiresPermissions("sys:role:edit")
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		RoleDO roleDO = roleService.get(id);
		model.addAttribute("role", roleDO);
		return prefix + "/edit";
	}

	@Log("保存角色")
	@RequiresPermissions("sys:role:add")
	@PostMapping("/save")
	@ResponseBody()
	R save(RoleDO role) {
		if (roleService.save(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@Log("更新角色")
	@RequiresPermissions("sys:role:edit")
	@PostMapping("/update")
	@ResponseBody()
	R update(RoleDO role) {
		if (roleService.update(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@Log("删除角色")
	@RequiresPermissions("sys:role:remove")
	@PostMapping("/remove")
	@ResponseBody()
	R save(Long id) {
		//判断角色是否已在使用
		Map<String,Object> map1 = new HashMap<>(16);
		map1.put("roleId",id);
		if(userRoleDao.list(map1).size()>0){
			return R.error(1, "删除失败,该角色 '"+roleService.get(id).getRoleName()+"' 已绑定账号");
		}
		if (roleService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	@RequiresPermissions("sys:role:batchRemove")
	@Log("批量删除角色")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		for(Long id:ids){
			//判断角色是否已在使用
			Map<String,Object> map1 = new HashMap<>(16);
			map1.put("roleId",id);
			if(userRoleDao.list(map1).size()>0){
				return R.error(1, "删除失败,该角色 '"+roleService.get(id).getRoleName()+"' 已绑定账号");
			}
		}

		int r = roleService.batchremove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exitOne")
	@ResponseBody
	boolean exitOne(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !roleService.exitOne(params);
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !roleService.exit(params);
	}

	/*
	*
	* 获取用户角色信息
	* **/
	@GetMapping("/roleselect/{id}")
	String roleselect(@PathVariable("id") String id, Model model) {
		//新增、没有用户id

		if("-1".equals(id)){
			model.addAttribute("noselect",roleService.list());
			model.addAttribute("select","");
		}else{
			List<RoleDO> lists = roleService.list(Long.parseLong(id));
			List<RoleDO> noselect = new ArrayList<>();
			List<RoleDO> select = new ArrayList<>();
			for(RoleDO rd : lists){
				if(rd.getRoleSign().equals("true")){
					select.add(rd);
				}else{
					noselect.add(rd);
				}
			}
			model.addAttribute("noselect",noselect);
			model.addAttribute("select",select);
			System.out.println(noselect.toString());
		}
		model.addAttribute("userId",id);
		return prefix + "/roleselect";
	}
}
