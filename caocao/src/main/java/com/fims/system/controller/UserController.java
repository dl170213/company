package com.fims.system.controller;

import com.alibaba.fastjson.JSON;
import com.fims.common.annotation.Log;
import com.fims.common.config.Constant;
import com.fims.common.controller.BaseController;
import com.fims.common.domain.MailMessage;
import com.fims.common.domain.Suggest;
import com.fims.common.domain.Tree;
import com.fims.common.service.DictService;
import com.fims.common.service.ExportService;
import com.fims.common.service.ImportService;
import com.fims.common.service.MailService;
import com.fims.common.utils.*;
import com.fims.oa.domain.Message;
import com.fims.oa.domain.Response;
import com.fims.system.domain.DeptDO;
import com.fims.system.domain.InvoiceuserDO;
import com.fims.system.domain.RoleDO;
import com.fims.system.domain.UserDO;
import com.fims.system.service.RoleService;
import com.fims.system.service.UserService;
import com.fims.system.vo.UserVO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	private String prefix="system/user"  ;
	@Autowired
	private HttpServletRequest rre;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	DictService dictService;
	@Autowired
	MailService mailService;
	@Autowired
	ExportService exportService;
	@Autowired
	ImportService importService;
	@Autowired
	SimpMessagingTemplate template;

	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		URLgetter.getRealURL(params,rre);
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model, HttpServletResponse response) {
		/*List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);*/
		/*template.convertAndSend("/topic/getResponse", new Response("欢迎体验fims,这是一个任务计划学习!" ));
		MailMessage mailMessage = new MailMessage();
		mailMessage.setCause("01");
		mailMessage.setMessageCode("02");
		mailMessage.setMessageStatus("03");
		mailService.sendMessageMail(mailMessage,"测试","mail/message.ftl");
		exportService.ProductExport(response);*/

		return prefix + "/add";
	}

	/*@RequiresPermissions("sys:user:add")
	@GetMapping("/add")
	void add(HttpServletResponse response) {
		exportService.ProductExport(response);
	}*/

	@ResponseBody
	@PostMapping("/importExcel")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		importService.importProduct(file);
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
//		List<RoleDO> roles = roleService.list(id);
//		model.addAttribute("roles", roles);
		return prefix+"/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(UserDO user) {
		user.setPassword("111111");
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	R update(UserDO user) {
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(UserDO user) {
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		if(userService.get(id)==null){
			return R.ok();
		}
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@RequiresPermissions("sys:user:resetPwd")
	@Log("请求更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(Long id) {

		UserDO userDO = userService.get(id);
		userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), "111111"));
		if(userService.update(userDO)>0){
			return	R.ok();
		}
		return R.error();
	}

	/*@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserVO userVO) {
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}
	}*/

	@Log("用户提交更改用户密码")
	@PostMapping("/userResetPwd")
	@ResponseBody
	R adminResetPwd(UserVO userVO) {
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/userTree";
	}

	@GetMapping("/deptTreeView")
	String deptTreeView() {
		return  "system/dept/deptTreeforUser";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		model.addAttribute("hobbyList",dictService.getHobbyList(userDO));
		model.addAttribute("sexList",dictService.getSexList());
		return prefix + "/personal";
	}

	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}

	/**
	 * 获取所有人员信息
	 */
	@GetMapping("/getAllUser")
	@ResponseBody
	String getAlluser(){
		Map<String,Object> map = new HashMap<>(16);
		List<UserDO> list = userService.list(map);
		Suggest suggest = new Suggest("",list==null?"":list);
		return JSON.toJSONString(suggest);
	}

	/*
	* 判断员工姓名和工号是否匹配
	* 1. 名字、工号均为空不校验 true
	* 2. 工号不存在返回 true
	* 3. 工号存在且于名字匹配 true
	* 4. 工号存在且于名字不匹配 false
	* */
	@PostMapping("/checkUser")
	@ResponseBody
	boolean checkUser(String name,String username) {
		if(name!=null&&username!=null){
			if(name.equals("")&&username.equals("")){
				return true;
			}else{
				UserDO ud = userService.getByUsername(username);
				if(ud!=null){
					if(name.equals(ud.getName())){
						return true;
					}else{
						return false;
					}
				}else{
					return true;
				}
			}
		}
		return true;
	}

	/*
	* 保存用户权限信息
	* */
	@PostMapping("/saveUserRole")
	@ResponseBody
	R saveUserRole(String id,String roles) {
		if(userService.saveUserRole(id,roles)>0){
			return R.ok();
		}
		return R.error();
	}
}
