package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * PO数据导入实体
 * Created by Administrator on 2018/8/10.
 */
@ExcelTarget(value="poImportDO")
public class POImportDO implements IExcelDataModel,IExcelModel{

    @Excel(name="'Delivery Date",orderNum = "20")
    @NotNull(message = "收到日期不能为空")
    private String receiveddate;

    @Excel(name="项目名称",orderNum = "13")
    @NotNull(message = "项目名称不能为空")
    private String pm;

    @Excel(name="'Purchase Doc Number",orderNum = "11")
    @NotNull(message = "合同号不能为空")
    private String contractid;

    @Excel(name="SP",orderNum = "12")
    private String sp_;

    @Excel(name="'WBS",orderNum = "27")
    private String wbs_;

    @Excel(name="'Description",orderNum = "17")
    @NotNull(message = "工作内容不能为空")
    private String workbody;

    @Excel(name="'Net Price",orderNum = "23")
    @NotNull(message = "单价不能为空")
    private String pice;

    @Excel(name="'Unconfirmed",orderNum = "5")
    @NotNull(message = "数量不能为空")
    private String count;

    @Excel(name="'Plant",orderNum = "19")
    @NotNull(message = "客户名称不能为空")
    private String customer_;

    @Excel(name="工作类型",orderNum = "14")
    private String worktype;
  //  private String sbi;
    private int rowNum;
    private String errorMsg;

    public POImportDO(){

    }

    public POImportDO(String receiveddate, String pm, String contractid, String sp, String wbs, String workbody, String pice, String count, String customer, String worktype, int rowNum, String errorMsg) {
        this.receiveddate = receiveddate;
        this.pm = pm;
        this.contractid = contractid;
        this.sp_ = sp;
        this.wbs_ = wbs;
        this.workbody = workbody;
        this.pice = pice;
        this.count = count;
        this.customer_ = customer;
        this.worktype = worktype;
        this.rowNum = rowNum;
        this.errorMsg = errorMsg;
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

    public String getSp_() {
        return sp_;
    }

    public void setSp_(String sp_) {
        this.sp_ = sp_;
    }

    public String getWbs_() {
        return wbs_;
    }

    public void setWbs_(String wbs) {
        this.wbs_ = wbs;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCustomer_() {
        return customer_;
    }

    public void setCustomer_(String customer) {
        this.customer_ = customer;
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
        rowNum = i;
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
        return "POImportDO{" +
                "receiveddate='" + receiveddate + '\'' +
                ", pm='" + pm + '\'' +
                ", contractid='" + contractid + '\'' +
                ", sp='" + sp_ + '\'' +
                ", wbs='" + wbs_ + '\'' +
                ", workbody='" + workbody + '\'' +
                ", pice='" + pice + '\'' +
                ", count='" + count + '\'' +
                ", customer='" + customer_ + '\'' +
                ", worktype='" + worktype + '\'' +
                ", rowNum=" + rowNum +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
