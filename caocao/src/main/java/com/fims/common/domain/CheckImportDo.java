package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

import javax.validation.constraints.NotNull;

/**
 * 发票信息导入操作
 * Created by Administrator on 2018/8/9.
 */
@ExcelTarget(value="productExportDO")
public class CheckImportDo implements IExcelDataModel,IExcelModel {

    @Excel(name = "月份",orderNum = "0")
    private String month;

    @Excel(name = "发票号",orderNum = "1")
    @NotNull(message = "发票号不能为空")
    private String number;

    @Excel(name = "姓名",orderNum = "2")
    private String name;

    @Excel(name = "工号",orderNum = "3")
    private String username;

    private int rowNum;
    private String errorMsg;

    public CheckImportDo() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int i) {
        this.rowNum = i;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg = s;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
