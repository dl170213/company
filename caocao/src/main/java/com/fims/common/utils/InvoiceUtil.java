package com.fims.common.utils;

import com.fims.system.domain.InvoiceDO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
public class InvoiceUtil {
    /**
     * 判断是不是符合格式的发票代码
     * 1. 发票编号>=8
     * 2. 增值税扫码格式：01，04，032001800104，75067954，734.95，20180623，74218221204242281269，4E90
     * */
    public static InvoiceDO isInvoiceNumber(String code){
        InvoiceDO invoiceDO  = new InvoiceDO();
        invoiceDO.setInvoiceNumber(code);
        if(code!=null&&!"".equals(code)){
            String [] invoices = code.split("，");
            if(invoices!=null&&invoices.length>=4&&invoices[3]!=null&&!"".equals(invoices[3])&&invoices[3].length()>=8){
                invoiceDO.setInvoiceNumber(invoices[3]);
                if(invoices.length>5&&invoices[4]!=null&&!"".equals(invoices[4])){
                    invoiceDO.setNumber(invoices[4]);
                }
                if(invoices.length>6&&invoices[5]!=null&&!"".equals(invoices[5])&&invoices[5].length()==8){
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        Date date = sdf.parse(invoices[5]);
                        invoiceDO.setInvoicedate(DateUtils.format(date));
                    }catch (Exception ex){

                    }
                }
            }
        }else{
            invoiceDO.setInvoiceNumber("");
        }
        return invoiceDO;
    }
}
