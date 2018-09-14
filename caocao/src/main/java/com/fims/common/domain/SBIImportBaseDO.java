package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/8/21.
 */
@ExcelTarget(value="SBIImportBaseDO")
public class SBIImportBaseDO implements IExcelDataModel,IExcelModel {

    @Excel(name = "收到日期",orderNum = "0")
    @NotNull
    private String recivedate;
    @Excel(name = "项目名称",orderNum = "1")
    @NotNull
    private String pm;
    @Excel(name = "合同号",orderNum = "2")
    @NotNull
    private String contract;
    @Excel(name = "含税价",orderNum = "3")
    @NotNull
    private String posttax;
    @Excel(name = "工作类型",orderNum = "4")
    @NotNull
    private String worktype;
    @Excel(name = "SBI号",orderNum = "5")
    private String sbi;
    @Excel(name = "开票日期",orderNum = "6")
    private String ticketdate;
    @Excel(name = "SBI生成日期",orderNum = "7")
    private String generate;

    private int rowNum;
    private String errorMsg;

    public SBIImportBaseDO() {

    }

    public String getRecivedate() {
        return recivedate;
    }

    public void setRecivedate(String recivedate) {
        this.recivedate = recivedate;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getPosttax() {
        return posttax;
    }

    public void setPosttax(String posttax) {
        this.posttax = posttax;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getSbi() {
        return sbi;
    }

    public void setSbi(String sbi) {
        this.sbi = sbi;
    }

    public String getTicketdate() {
        return ticketdate;
    }

    public void setTicketdate(String ticketdate) {
        this.ticketdate = ticketdate;
    }

    public String getGenerate() {
        return generate;
    }

    public void setGenerate(String generate) {
        this.generate = generate;
    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int i) {
        this.rowNum  =i;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg = s;
    }

    @Override
    public String toString() {
        return "SBIImportBaseDO{" +
                "recivedate='" + recivedate + '\'' +
                ", pm='" + pm + '\'' +
                ", contract='" + contract + '\'' +
                ", posttax='" + posttax + '\'' +
                ", worktype='" + worktype + '\'' +
                ", sbi='" + sbi + '\'' +
                ", ticketdate='" + ticketdate + '\'' +
                ", generate='" + generate + '\'' +
                ", rowNum=" + rowNum +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
