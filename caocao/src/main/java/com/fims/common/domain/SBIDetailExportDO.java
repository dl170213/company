package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * Created by Administrator on 2018/9/9.
 */
@ExcelTarget(value="sbiDetailExpoertDO")
public class SBIDetailExportDO {

    @Excel(name = "SBI",orderNum = "0",width = 10,needMerge = true,mergeVertical = true,mergeRely = {0})
    private String sbi;

    @Excel(name = "生成日期",orderNum = "1",width = 20,needMerge = true,mergeVertical = true,mergeRely = {0})
    private String reseve1;

    @Excel(name = "总金额",orderNum = "2",width = 10,needMerge = true,mergeVertical = true,mergeRely = {0})
    private String posttax;

    @Excel(name = "客户名称",orderNum = "3",width = 30,needMerge = true,mergeVertical = true,mergeRely = {0})
    private String customer;

    @Excel(name = "PO合同号",orderNum = "4",width = 10)
    private String po_contractid;

    @Excel(name = "PO项目名称",orderNum = "5",width = 20)
    private String po_pm;

    @Excel(name = "PO工作类型",orderNum = "6",width = 10)
    private String po_worktype;

    @Excel(name = "PO数量",orderNum = "7",width = 10)
    private String po_count;

    @Excel(name = "PO单价",orderNum = "8",width = 10)
    private String po_pice;

    @Excel(name = "PO总价",orderNum = "9",width = 10)
    private String po_total;

    public SBIDetailExportDO() {

    }

    public SBIDetailExportDO(String sbi, String reseve1, String posttax, String customer, String po_contractid, String po_pm, String po_worktype, String po_count, String po_pice, String po_total) {
        this.sbi = sbi;
        this.reseve1 = reseve1;
        this.posttax = posttax;
        this.customer = customer;
        this.po_contractid = po_contractid;
        this.po_pm = po_pm;
        this.po_worktype = po_worktype;
        this.po_count = po_count;
        this.po_pice = po_pice;
        this.po_total = po_total;
    }

    public String getSbi() {
        return sbi;
    }

    public void setSbi(String sbi) {
        this.sbi = sbi;
    }

    public String getReseve1() {
        return reseve1;
    }

    public void setReseve1(String reseve1) {
        this.reseve1 = reseve1;
    }

    public String getPosttax() {
        return posttax;
    }

    public void setPosttax(String posttax) {
        this.posttax = posttax;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPo_contractid() {
        return po_contractid;
    }

    public void setPo_contractid(String po_contractid) {
        this.po_contractid = po_contractid;
    }

    public String getPo_pm() {
        return po_pm;
    }

    public void setPo_pm(String po_pm) {
        this.po_pm = po_pm;
    }

    public String getPo_worktype() {
        return po_worktype;
    }

    public void setPo_worktype(String po_worktype) {
        this.po_worktype = po_worktype;
    }

    public String getPo_count() {
        return po_count;
    }

    public void setPo_count(String po_count) {
        this.po_count = po_count;
    }

    public String getPo_pice() {
        return po_pice;
    }

    public void setPo_pice(String po_pice) {
        this.po_pice = po_pice;
    }

    public String getPo_total() {
        return po_total;
    }

    public void setPo_total(String po_total) {
        this.po_total = po_total;
    }

    @Override
    public String toString() {
        return "SBIDetailExportDO{" +
                "sbi='" + sbi + '\'' +
                ", reseve1='" + reseve1 + '\'' +
                ", posttax='" + posttax + '\'' +
                ", customer='" + customer + '\'' +
                ", po_contractid='" + po_contractid + '\'' +
                ", po_pm='" + po_pm + '\'' +
                ", po_worktype='" + po_worktype + '\'' +
                ", po_count='" + po_count + '\'' +
                ", po_pice='" + po_pice + '\'' +
                ", po_total='" + po_total + '\'' +
                '}';
    }
}
