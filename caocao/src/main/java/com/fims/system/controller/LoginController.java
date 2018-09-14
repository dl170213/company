package com.fims.system.controller;

import com.fims.common.annotation.Log;
import com.fims.common.controller.BaseController;
import com.fims.common.domain.FileDO;
import com.fims.common.domain.Tree;
import com.fims.common.service.FileService;
import com.fims.common.utils.MD5Utils;
import com.fims.common.utils.R;
import com.fims.common.utils.ShiroUtils;
import com.fims.system.domain.MenuDO;
import com.fims.system.service.CheckService;
import com.fims.system.service.MenuService;
import com.fims.system.service.PoService;
import com.fims.system.service.SbiService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@Autowired
	CheckService checkService;
	@Autowired
	PoService poService;
	@Autowired
	SbiService sbiService;
	@Autowired
	DefaultKaptcha defaultKaptcha;
	@GetMapping({ "/", "" })
	String welcome(Model model) {
		return "redirect:/index";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {
			model.addAttribute("picUrl","/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login(Model model) {
		System.out.println("0000000000000000000"+SecurityUtils.getSubject().getSession().getId());
		//获取登陆失败次数
		int x = Integer.parseInt(SecurityUtils.getSubject().getSession().getAttribute("userlogincount")==null?
				"0":SecurityUtils.getSubject().getSession().getAttribute("userlogincount").toString());

		model.addAttribute("logincode",x);
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password,String vrifycode) {
		System.out.println("111111111111");
		int x = Integer.parseInt(SecurityUtils.getSubject().getSession().getAttribute("userlogincount")==null?"0":
				SecurityUtils.getSubject().getSession().getAttribute("userlogincount").toString());
		//如果登录失败超过三次
		if(x>3){
			if(!vrifycode.equals(SecurityUtils.getSubject().getSession().getAttribute("vrifyCode").toString())){
				return R.error(5,"验证码错误");
			}
		}
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		}catch (AuthenticationException e) {
			SecurityUtils.getSubject().getSession().setAttribute("userlogincount",x+1);
			if((x+1)>3){
				return R.error((x+1),e.getMessage());
			}
			return R.error(1,e.getMessage());
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main(Model model) {
	//	return "redirect:/druid/index.html";

		//获取发票个数
		if(SecurityUtils.getSubject().isPermitted("system:check:check")){
			model.addAttribute("check",checkService.checkCount());
		}
		//获取PO个数
		if(SecurityUtils.getSubject().isPermitted("system:po:po")){
			model.addAttribute("po",poService.poCount());
		}
		//获取sbi个数
		if(SecurityUtils.getSubject().isPermitted("system:sbi:sbi")){
			model.addAttribute("sbi",sbiService.sbiCount());
		}
		return "main";
	}

	@GetMapping("/403")
	String error403() {
		return "403";
	}

	@RequestMapping("/defaultKaptcha")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			//生产验证码字符串并保存到session中
			String createText = defaultKaptcha.createText();
			//	SecurityUtils.getSubject().getSession();
			SecurityUtils.getSubject().getSession().setAttribute("vrifyCode", createText);
			//使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = defaultKaptcha.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream =
				httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
