package com.fims.system.controller;

import com.fims.common.annotation.Log;
import com.fims.common.utils.R;
import com.fims.common.utils.ServerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/7/26.
 */
@Controller
@RequestMapping("/mobile")
public class MobileControll {

    @GetMapping()
    String Mobile(Model model,String code){
        model.addAttribute("sn",code.substring(0,12));
        model.addAttribute("code",code.substring(12,22));

        System.out.println("sn"+code.substring(0,12)+",code"+code.substring(12,22));
        return "mobile/index";
    }

    @Log("支付订单")
    @GetMapping("/pay")
    @ResponseBody
    R pay(String number){

        return R.ok();
    }


    @Log("获取密码")
    @GetMapping("/getPassword")
    String getPassword(Model model,String code,String sn,String number){
   //     String password = ServerUtil.SetDynamicPassword(ServerUtil.hexStringToBytes(sn),ServerUtil.hexStringToBytes(code));
        String password = ServerUtil.SetDynamicPassword(ServerUtil.hexStringToBytes(sn), ServerUtil.hexStringToBytes(String.format("%08X", Long.parseLong(code))));
        model.addAttribute("password",password);
        model.addAttribute("number",number);
        return "mobile/ok";
    }
}
