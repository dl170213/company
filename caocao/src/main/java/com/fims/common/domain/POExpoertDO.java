package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/8/16.
 */
@ExcelTarget(value="poExpoertDO")
public class POExpoertDO extends ExcelExportEntity implements IExcelExportStyler
{
    @Excel(name="收到日期",needMerge = true,mergeVertical = true,mergeRely = {2},height = 15.75)
    private String receiveddate;

    @Excel(name="项目名称",orderNum = "1",width = 20,needMerge = true,mergeVertical = true,mergeRely = {2})
    private String pm;

    @Excel(name="合同号",orderNum = "2",width = 15,needMerge = true,mergeVertical = true,mergeRely = {2})
    private String contractid;

    @Excel(name="SP序列",orderNum = "3",width = 15,needMerge = true,mergeVertical = true,mergeRely = {2})
    private String sp_;

    @Excel(name="WBS编号",orderNum = "4" ,width = 40,needMerge = true,mergeVertical = true,mergeRely = {2})
    private String wbs_;

    @Excel(name="数量",orderNum = "5")
    private String count;

    @Excel(name="工作内容",orderNum = "6" ,width = 40)
    private String workbody;

    @Excel(name="单价",orderNum = "7")
    private String pice;

    @Excel(name="总价",orderNum = "8",needMerge = true,mergeVertical = true,mergeRely = {2})
    private String total;

    @Excel(name="客户名称",orderNum = "9",needMerge = true,mergeVertical = true,mergeRely = {2},width = 20)
    private String customer_;

    @Excel(name="工作类型",orderNum = "10",needMerge = true,mergeVertical = true,mergeRely = {2})
    private String worktype;

    @Excel(name="SBI号",orderNum = "11",needMerge = true,mergeVertical = true,mergeRely = {2,11})
    private String sbi;

    public POExpoertDO(){

    }

    public POExpoertDO(String receiveddate, String pm, String contractid, String sp, String wbs, String workbody, String pice, String count, String customer, String worktype,String total,String sbi) {
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
        this.total = total;
        this.sbi = sbi;
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

    public void setWbs_(String wbs_) {
        this.wbs_ = wbs_;
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

    public void setCustomer_(String customer_) {
        this.customer_ = customer_;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSbi() {
        return sbi;
    }

    public void setSbi(String sbi) {
        this.sbi = sbi;
    }

    @Override
    public CellStyle getStyles(Cell cell, int i, ExcelExportEntity excelExportEntity, Object o, Object o1) {
        System.out.println("//////////////////////////////////");
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cs = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short)10);
        cs.setFont(font);
        cs.setBorderTop(BorderStyle.MEDIUM);
        cs.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cs.setBorderRight(BorderStyle.MEDIUM);
        cs.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs.setBorderBottom(BorderStyle.MEDIUM);
        cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs.setBorderLeft(BorderStyle.MEDIUM);
        cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cell.setCellStyle(cs);
        cell.setCellType(CellType.NUMERIC);
        if(i == 5||i == 7||i == 8){
            cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        }
        return cs;
    }

    @Override
    public CellStyle getHeaderStyle(short i) {
        System.out.println("*********************************");
        return null;
    }

    @Override
    public CellStyle getTitleStyle(short i) {
        return null;
    }

    @Override
    public CellStyle getStyles(boolean b, ExcelExportEntity excelExportEntity) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        excelExportEntity.setHeight(15.75);
        excelExportEntity.setNumFormat("0.00");
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cs = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short)10);
        cs.setFont(font);
        cs.setBorderTop(BorderStyle.MEDIUM);
        cs.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cs.setBorderRight(BorderStyle.MEDIUM);
        cs.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cs.setBorderBottom(BorderStyle.MEDIUM);
        cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cs.setBorderLeft(BorderStyle.MEDIUM);
        cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        return cs;
    }

    @Override
    public CellStyle getTemplateStyles(boolean b, ExcelForEachParams excelForEachParams) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return null;
    }
}
