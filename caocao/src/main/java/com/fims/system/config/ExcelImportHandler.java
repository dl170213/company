package com.fims.system.config;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.fims.common.domain.ProductExportDO;

/**
 * Excel导入校验
 * Created by Administrator on 2018/8/9.
 */
public class ExcelImportHandler implements IExcelVerifyHandler{
    @Override
    public ExcelVerifyHandlerResult verifyHandler(Object o) {
        System.out.print("-/-/-/-/-/-/-");
        System.out.println(((ProductExportDO)o).getRowNum());
        return new ExcelVerifyHandlerResult(true);
    }
}
