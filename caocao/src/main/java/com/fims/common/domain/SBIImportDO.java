package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

/**
 * Created by Administrator on 2018/8/17.
 */
@ExcelTarget(value="SBIImportDO")
public class SBIImportDO implements IExcelDataModel,IExcelModel {

    @Excel(name = "A",orderNum = "1")
    private String SBI;

    @Excel(name = "B",orderNum = "8")
    private String PO;

    @Excel(name = "C",orderNum = "11")
    private String count;

    @Excel(name = "D",orderNum = "13")
    private String pice;

    @Excel(name = "E",orderNum = "15")
    private String total;

    private int rowNum;
    private String errorMsg;

    public SBIImportDO() {

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

    public String getSBI() {
        return SBI;
    }

    public void setSBI(String SBI) {
        this.SBI = SBI;
    }

    public String getPO() {
        return PO;
    }

    public void setPO(String PO) {
        this.PO = PO;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    @Override
    public String toString() {
        return "SBIImportDO{" +
                "SBI='" + SBI + '\'' +
                ", PO='" + PO + '\'' +
                ", count='" + count + '\'' +
                ", pice='" + pice + '\'' +
                ", total='" + total + '\'' +
                ", rowNum=" + rowNum +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
