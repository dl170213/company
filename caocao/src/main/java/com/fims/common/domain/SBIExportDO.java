package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/21.
 */
@ExcelTarget(value="sbiExpoertDO")
public class SBIExportDO
{
    @Excel(name = "项目名称",orderNum = "0",width = 30)
    private String pm;

    @Excel(name = "工作类型",orderNum = "1",width = 30)
    private String worktype;

    @Excel(name = "税前金额",orderNum = "2",width = 10)
    private String pretax;

    @Excel(name = "税后金额",orderNum = "4",width = 10)
    private String posttax;

    @Excel(name = "税收金额",orderNum = "3",width = 10)
    private String tax;

    @Excel(name = "客户名称",orderNum = "5",width = 30)
    private String customer;

    @Excel(name = "开票日期",orderNum = "7",width = 20)
    private String ticketdate;

    @Excel(name = "发票号",orderNum = "6",width = 20)
    private String invoicenumber;

    @Excel(name = "SBI",orderNum = "8",width = 10)
    private String sbi;

    @Excel(name = "生成日期",orderNum = "9",width = 20)
    private String reseve1;

    @Excel(name = "导入日期",orderNum = "10",width = 20)
    private String createtime;

    public SBIExportDO() {

    }

    public SBIExportDO(String pm, String worktype, String pretax, String posttax, String tax, String customer, String ticketdate, String invoicenumber, String sbi, String createtime) {
        this.pm = pm;
        this.worktype = worktype;
        this.pretax = pretax;
        this.posttax = posttax;
        this.tax = tax;
        this.customer = customer;
        this.ticketdate = ticketdate;
        this.invoicenumber = invoicenumber;
        this.sbi = sbi;
        this.createtime = createtime;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getPretax() {
        return pretax;
    }

    public void setPretax(String pretax) {
        this.pretax = pretax;
    }

    public String getPosttax() {
        return posttax;
    }

    public void setPosttax(String posttax) {
        this.posttax = posttax;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTicketdate() {
        return ticketdate;
    }

    public void setTicketdate(String ticketdate) {
        this.ticketdate = ticketdate;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }

    public String getSbi() {
        return sbi;
    }

    public void setSbi(String sbi) {
        this.sbi = sbi;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReseve1() {
        return reseve1;
    }

    public void setReseve1(String reseve1) {
        this.reseve1 = reseve1;
    }

    @Override
    public String toString() {
        return "SBIExportDO{" +
                "pm='" + pm + '\'' +
                ", worktype='" + worktype + '\'' +
                ", pretax='" + pretax + '\'' +
                ", posttax='" + posttax + '\'' +
                ", tax='" + tax + '\'' +
                ", customer='" + customer + '\'' +
                ", ticketdate='" + ticketdate + '\'' +
                ", invoicenumber='" + invoicenumber + '\'' +
                ", sbi='" + sbi + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
