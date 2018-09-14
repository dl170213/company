package com.fims.system.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.fims.common.domain.CheckImportDo;
import com.fims.system.domain.CheckDO;
import com.fims.system.domain.UserDO;
import com.fims.system.service.CheckService;
import com.fims.system.service.PoService;
import com.fims.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Component
public class CheckImportHandler implements IExcelVerifyHandler {

    @Autowired
    private CheckService service ;


    public static CheckService checkservice;

    @PostConstruct
    public void init(){
        checkservice = service;
    }

    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object o) {
        CheckImportDo cid = (CheckImportDo)o;
        if(cid==null){
            return new ExcelVerifyHandlerResult(true);
        }
        if(cid.getMonth()!=null&&!"".equals(cid.getMonth())){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                System.out.println(df.parse(cid.getMonth().replace(".","-")+"-15"));
            }catch (Exception ex){
                return new ExcelVerifyHandlerResult(false,"时间格式不正确");
            }
        }else{
            return new ExcelVerifyHandlerResult(false,"报销月份不能为空");
        }
        System.out.println("111111111111111111111111111111");
        if(cid.getNumber()==null||"".equals(cid.getNumber())){
            return new ExcelVerifyHandlerResult(false,"发票号不能为空");
        }else{
            if(cid.getNumber().length()>12){
                return new ExcelVerifyHandlerResult(false,"发票号不能大于12位");
            }else if(cid.getNumber().length()<8){
                return new ExcelVerifyHandlerResult(false,"发票号不能小于8位");
            }else{
                List<CheckDO> list = checkservice.getByNumber(cid.getNumber());
                if(list!=null&&list.size()>0){
                    return new ExcelVerifyHandlerResult(false,"该发票已存在");
                }
            }
        }
        System.out.println("2222222222222222222222");
        if(cid.getName()!=null&&!"".equals(cid.getName())){
            if(cid.getName().length()>20){
                return new ExcelVerifyHandlerResult(false,"报销人姓名过长,姓名长度大于20");
            }else{
                if(cid.getName().length()<2){
                    return new ExcelVerifyHandlerResult(false,"报销人姓名太短,姓名长度小于2");
                }

            }
            if(cid.getUsername()==null||"".equals(cid.getUsername().replace(" ",""))){
                return new ExcelVerifyHandlerResult(false,"报销人对应的工号不能为空");
            }
        }
        System.out.println("-/-/-/-/-//-/-/-/-/-/-/-/-/");
        if(cid.getUsername()!=null&&!"".equals(cid.getUsername())){
            if(cid.getUsername().length()>20){
                return new ExcelVerifyHandlerResult(false,"员工工号过长,工号长度大于20");
            }else{
                if(cid.getUsername().length()<4){
                    return new ExcelVerifyHandlerResult(false,"员工工号太短,工号长度小于4");
                }else{
                    if(cid.getName()==null||"".equals(cid.getName())){
                        return new ExcelVerifyHandlerResult(false,"报销人姓名不能为空");
                    }
                    UserDO ud = checkservice.getUserByUsername(cid.getUsername());
                    if(ud!=null){
                        if(!ud.getName().equals(cid.getName())){
                            return new ExcelVerifyHandlerResult(false,"报销人姓名与员工工号不匹配");
                        }
                    }else{//员工工号可以不存在，不存在的新增
                    //    return new ExcelVerifyHandlerResult(false,"员工工号不存在");
                    }
                }
            }
        }
        return new ExcelVerifyHandlerResult(true);
    }
}
