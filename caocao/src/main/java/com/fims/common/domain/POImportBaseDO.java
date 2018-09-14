package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/9/7.
 */
@ExcelTarget(value="POImportBaseDO")
public class POImportBaseDO implements IExcelDataModel,IExcelModel {

    @Excel(name = "收到日期",orderNum = "0")
    @NotNull(message = "收到日期不能为空")
    private String receiveddate;

    @Excel(name = "项目名称",orderNum = "1")
    @NotNull(message = "项目名称不能为空")
    private String pm;

    @Excel(name = "合同号",orderNum = "2")
    @NotNull(message = "合同号不能为空")
    private String contractid;

    @Excel(name = "SP序列",orderNum = "3")
    private String sp;

    @Excel(name="WBS编号",orderNum = "4")
    private String wbs_;

    @Excel(name="数量",orderNum = "5")
    @NotNull(message = "数量不能为空")
    private String count;

    @Excel(name="工作内容",orderNum = "6")
    @NotNull(message = "工作内容不能为空")
    private String workbody;

    @Excel(name="单价",orderNum = "7")
    @NotNull(message = "单价不能为空")
    private String pice;

    @Excel(name="总价",orderNum = "8")
    @NotNull(message = "总价不能为空")
    private String total;

    @Excel(name="客户名称",orderNum = "9")
    @NotNull(message = "客户名称不能为空")
    private String customer_;

    @Excel(name="工作类型",orderNum = "10")
    @NotNull(message = "工作类型不能为空")
    private String worktype;


    private int rowNum;
    private String errorMsg;

    public POImportBaseDO() {

    }

    public String getReceiveddate() {
        return receiveddate;
    }

    public void setReceiveddate(String receiveddate) {
        this.receiveddate = receiveddate;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getContractid() {
        return contractid;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getWbs_() {
        return wbs_;
    }

    public void setWbs_(String wbs_) {
        this.wbs_ = wbs_;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWorkbody() {
        return workbody;
    }

    public void setWorkbody(String workbody) {
        this.workbody = workbody;
    }

    public String getPice() {
        return pice;
    }

    public void setPice(String pice) {
        this.pice = pice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCustomer_() {
        return customer_;
    }

    public void setCustomer_(String customer_) {
        this.customer_ = customer_;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
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

    @Override
    public String toString() {
        return "POImportBaseDO{" +
                "receiveddate='" + receiveddate + '\'' +
                ", pm='" + pm + '\'' +
                ", contractid='" + contractid + '\'' +
                ", sp='" + sp + '\'' +
                ", wbs_='" + wbs_ + '\'' +
                ", count='" + count + '\'' +
                ", workbody='" + workbody + '\'' +
                ", pice='" + pice + '\'' +
                ", total='" + total + '\'' +
                ", customer_='" + customer_ + '\'' +
                ", worktype='" + worktype + '\'' +
                ", rowNum=" + rowNum +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
