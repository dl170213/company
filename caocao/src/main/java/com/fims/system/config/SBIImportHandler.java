package com.fims.system.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.fims.common.domain.SBIImportDO;

/**
 * Created by Administrator on 2018/8/17.
 */
public class SBIImportHandler implements IExcelVerifyHandler {
    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object o) {

        SBIImportDO sbiImportDO  = (SBIImportDO)o;

        System.out.println("----+/"+sbiImportDO.toString());
        return new ExcelVerifyHandlerResult(true);
    }
}
