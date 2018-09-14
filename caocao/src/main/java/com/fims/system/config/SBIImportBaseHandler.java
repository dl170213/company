package com.fims.system.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.fims.common.domain.SBIImportBaseDO;
import com.fims.system.domain.PoDO;
import com.fims.system.service.CheckService;
import com.fims.system.service.PoService;
import com.fims.system.service.SbiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Component
public class SBIImportBaseHandler implements IExcelVerifyHandler {

    @Autowired
    private SbiService service ;
    @Autowired
    private PoService service1 ;

    public static SbiService sbiservice;
    public static PoService poservice;

    @PostConstruct
    public void init(){
        sbiservice = service;
        poservice = service1;
    }
    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object o) {
        SBIImportBaseDO sibd = (SBIImportBaseDO)o;
        System.out.println(sibd.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(df.parse(sibd.getRecivedate()));
        }catch (Exception ex){
            return new ExcelVerifyHandlerResult(false,"收到日期时间格式错误");
        }
        if(sibd.getPm().length()>20){
            return new ExcelVerifyHandlerResult(false,"项目名称过长，超过20位");
        }
        if(sibd.getContract().length()>20){
            return new ExcelVerifyHandlerResult(false,"合同号过长，超过20位");
        }else if(sibd.getContract().length()< 7){
            return new ExcelVerifyHandlerResult(false,"合同号太短，小于7位");
        }else{
            List<PoDO> list = poservice.getByNumber(sibd.getContract());
            if(list!=null&&list.size()>0){
                return new ExcelVerifyHandlerResult(false,"该合同号已存在");
            }
        }

        if(sibd.getPosttax().length()>20){
            return new ExcelVerifyHandlerResult(false,"含税价格过长，超过20位");
        }else{
            try {
                System.out.println(Double.parseDouble(sibd.getPosttax()));
            }catch (Exception ex){
                return new ExcelVerifyHandlerResult(false,"含税价格格式错误");
            }
        }
        if(sibd.getWorktype()!=null&&!"".equals(sibd.getWorktype())&&sibd.getWorktype().length()>20){
            return new ExcelVerifyHandlerResult(false,"工作类型过长，超过20位");
        }
        if(sibd.getTicketdate()!=null&&!"".equals(sibd.getTicketdate())){
            try {
                System.out.println(df.parse(sibd.getRecivedate()));
            }catch (Exception ex){
                return new ExcelVerifyHandlerResult(false,"开票日期时间格式错误");
            }
        }
        if(sibd.getSbi()!=null&&!"".equals(sibd.getSbi())){
            if(sibd.getSbi().length()>20){
                return new ExcelVerifyHandlerResult(false,"SBI号过长，超过20位");
            }else  if(sibd.getSbi().length()<6){
                return new ExcelVerifyHandlerResult(false,"SBI号太短，小于6位");
            }else{
                if(sbiservice.getBySbi(sibd.getSbi())!=null){
                    return new ExcelVerifyHandlerResult(false,"SBI号已存在");
                }
            }
        }
        if(sibd.getGenerate()!=null&&!"".equals(sibd.getGenerate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            try {
                System.out.println(sdf.parse(sibd.getGenerate()));
            }catch (Exception ex){
                return new ExcelVerifyHandlerResult(false,"SBI生成日期时间格式错误");
            }
        }
        return  new ExcelVerifyHandlerResult(true);
    }
}
