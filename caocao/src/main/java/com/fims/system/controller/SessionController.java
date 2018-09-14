package com.fims.system.controller;

import java.util.Collection;
import java.util.List;

import com.fims.common.utils.Memcach;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fims.common.utils.R;
import com.fims.system.domain.UserOnline;
import com.fims.system.service.SessionService;

@RequestMapping("/sys/online")
@Controller
public class SessionController {
	@Autowired
	SessionService sessionService;

	@GetMapping()
	public String online() {
		return "system/online/online";
	}

	@ResponseBody
	@RequestMapping("/list")
	public List<UserOnline> list() {
		return sessionService.list();
	}

	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	public R forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			sessionService.forceLogout(sessionId);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

	}

	@ResponseBody
	@RequestMapping("/sessionList")
	public Collection<Session> sessionList() {
		return sessionService.sessionList();
	}


	/**
	 * 根据导入excel的索引，获取当前导入进度
	 * **/
	@ResponseBody
	@GetMapping("/importProcess")
	public R importProcess(String importId) {
		R r = new R();
		System.out.println("----"+importId+">>>>"+Memcach.EXCEL_STATUS.get(importId)+","+Memcach.EXCEL_PROCESS.get(importId));
		r.put("code", Memcach.EXCEL_STATUS.get(importId)==null?1:Memcach.EXCEL_STATUS.get(importId));//当前导入阶段
		r.put("process", Memcach.EXCEL_PROCESS.get(importId)==null?"":Memcach.EXCEL_PROCESS.get(importId));//当前进度
		if(Memcach.EXCEL_STATUS.get(importId)!=null&&(((int)Memcach.EXCEL_STATUS.get(importId)==4)||((int)Memcach.EXCEL_STATUS.get(importId)==-1))){
			Memcach.EXCEL_STATUS.remove(importId);
		}
		return r;
	}

	/**
	 * 删除缓存中的excel的索引
	 * **/
	@ResponseBody
	@PostMapping("/removeProcess")
	public R removeProcess(String importId) {
		System.out.println("removeProcess:"+importId);
		Memcach.EXCEL_STATUS.remove(importId);
		Memcach.EXCEL_PROCESS.remove(importId);
		return R.ok();
	}

}
