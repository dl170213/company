package com.fims.system.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.fims.common.dao.FileDao;
import com.fims.common.domain.POImportDO;
import com.fims.system.domain.PoDO;
import com.fims.system.service.PoService;
import com.fims.system.service.impl.PoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 校验PO导入数据
 * Created by Administrator on 2018/8/10.
 */
@Component
public class POImportHandler implements IExcelVerifyHandler {

    @Autowired
    private PoService service ;

    public static PoService poservice;

    @PostConstruct
    public void init(){
        poservice = service;
    }

    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object o) {

        System.out.println("+++++++"+o.toString());
        POImportDO podo = (POImportDO)o;
        if(podo==null){
            return new ExcelVerifyHandlerResult(false,"空行");
        }
        if(!"".equals(podo.getReceiveddate().replace("'",""))){//收到日期校验
            SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
            String timeStr = podo.getReceiveddate().replace("'","");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                System.out.println(df.format(format.parse(podo.getReceiveddate().replace("'",""))));
            }catch (Exception ex){
                return new ExcelVerifyHandlerResult(false,ex.getMessage());
            }
        }

        //判断PO是否已存在
        //po可能存在多种
        //1. 正常PO
        //2. 负PO红冲
        //3. 再次下单正常PO
        //4. 可能存在一半PO生成SBI，另一半PO再次生成SBI
        if(!podo.getContractid().equals("")){
            List<PoDO> list = poservice.getByNumber(Long.parseLong(podo.getContractid().replace("'",""))+"");
            if(list!=null&&list.size()>0){
                //日期、合同号、数量、价格均一致时，才能确定两条数据一致
                return new ExcelVerifyHandlerResult(false,"PO数据已存在");
            }
        }else{
            return new ExcelVerifyHandlerResult(false,"PO合同号格式错误");
        }

        if(podo.getCount()!=null&&!"".equals(podo.getCount())){

            try{
                System.out.println(Double.parseDouble(getRepaceString1(podo.getCount())));
            }catch(Exception ex){
                return new ExcelVerifyHandlerResult(false,"数量转格式失败");
            }
        }
        if(podo.getPice()!=null&&!"".equals(podo.getPice())){

            try{
                System.out.println(Double.parseDouble(getRepaceString1(podo.getPice())));
            }catch(Exception ex){
                return new ExcelVerifyHandlerResult(false,"单价转格式失败");
            }
        }
        //
    //    PoDO podo1 =  poservice.get(Long.parseLong("1"));

        return new ExcelVerifyHandlerResult(true);
    }

    public String getRepaceString1(String rs){
        return rs.replace("'","").replace(".","").replace(",",".").replace("，",".");
    }

}
