package com.fims.common.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 仓库物品实体类，用于导入导出
 * Created by Administrator on 2018/7/27.
 */
@ExcelTarget(value="productExportDO")
public class ProductExportDO implements IExcelDataModel,IExcelModel{

    @Excel(name="设备分类",orderNum = "0")
    private String equipmentType;

    @Excel(name="具体名称及版本",orderNum = "1")
    private String name;

    @Excel(name="公司编号",orderNum = "2")
    private String companyNumber;

    @Excel(name="设备S/N号",orderNum = "3",width = 20)
    @NotNull(message = "D列 不能为空")
    private String SN;

    @Excel(name="设备IMEI号",orderNum = "4",width = 50)
    private String IMEI;

    @Excel(name="使用项目",orderNum = "5")
    private String PM;

    @Excel(name="负责人",orderNum = "6")
    private String person;

    @Excel(name="设备是否正常",replace ={"正常_1","损坏_2"},orderNum = "7")
    @Max(value = 2,message = "H列 max 最大值不能超过2")
    private String isOK;

    @Excel(name="购买时间", exportFormat = "yyyy-MM-dd",orderNum = "8")
    private Date time;

    @Excel(name="单价（元/个）",orderNum = "9")
    private String pice;

    @Excel(name="备注",orderNum = "10")
    private String remark;

    @Excel(name="链接",orderNum = "11",width = 50)
    private String link;

    @Excel(name="图片",orderNum = "12",width = 50,type = 2,imageType = 1,height = 40)
    private String pic;

    private String errorMsg;

    private int rowNum;

    public ProductExportDO(){

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public ProductExportDO(String equipmentType, String name, String companyNumber, String SN, String IMEI, String PM,
                           String person, String isOK, Date time, String pice, String remark, String link, String pic) {
        this.equipmentType = equipmentType;
        this.name = name;
        this.companyNumber = companyNumber;
        this.SN = SN;
        this.IMEI = IMEI;
        this.PM = PM;
        this.person = person;
        this.isOK = isOK;
        this.time = time;
        this.pice = pice;
        this.remark = remark;
        this.link = link;
        this.pic = pic;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getPM() {
        return PM;
    }

    public void setPM(String PM) {
        this.PM = PM;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getIsOK() {
        return isOK;
    }

    public void setIsOK(String isOK) {
        this.isOK = isOK;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPice() {
        return pice;
    }

    public void setPice(String pice) {
        this.pice = pice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
        return "ProductExportDO{" +
                "equipmentType='" + equipmentType + '\'' +
                ", name='" + name + '\'' +
                ", companyNumber='" + companyNumber + '\'' +
                ", SN='" + SN + '\'' +
                ", IMEI='" + IMEI + '\'' +
                ", PM='" + PM + '\'' +
                ", person='" + person + '\'' +
                ", isOK='" + isOK + '\'' +
                ", time=" + time +
                ", pice='" + pice + '\'' +
                ", remark='" + remark + '\'' +
                ", link='" + link + '\'' +
                ", pic='" + pic + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", rowNum=" + rowNum +
                '}';
    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int i) {
        this.rowNum = i;
    }
}
