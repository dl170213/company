package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:23
 */
public class SocialsecurityDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//员工
	private Long userId;
	//养老金
	private String pension;
	//医疗保险
	private String medical;
	//失业保险
	private String unemployment;
	//三金合计
	private String pmuTotal;
	//大病保险
	private String serious;
	//公积金
	private String provident;
	//合计
	private String total;
	//社保类型
	private String type;
	//社保缴纳地
	private String payland;
	//社保卡号
	private String idnumber;
	//代缴公司
	private String paycompany;
	//缴纳金额
	private String pay;
	//津贴发放
	private Date allowance;
	//预留1
	private String reseve1;
	//预留2
	private String reseve2;
	//预留3
	private String reseve3;

	/**
	 * 设置：编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：员工
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：员工
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：养老金
	 */
	public void setPension(String pension) {
		this.pension = pension;
	}
	/**
	 * 获取：养老金
	 */
	public String getPension() {
		return pension;
	}
	/**
	 * 设置：医疗保险
	 */
	public void setMedical(String medical) {
		this.medical = medical;
	}
	/**
	 * 获取：医疗保险
	 */
	public String getMedical() {
		return medical;
	}
	/**
	 * 设置：失业保险
	 */
	public void setUnemployment(String unemployment) {
		this.unemployment = unemployment;
	}
	/**
	 * 获取：失业保险
	 */
	public String getUnemployment() {
		return unemployment;
	}
	/**
	 * 设置：三金合计
	 */
	public void setPmuTotal(String pmuTotal) {
		this.pmuTotal = pmuTotal;
	}
	/**
	 * 获取：三金合计
	 */
	public String getPmuTotal() {
		return pmuTotal;
	}
	/**
	 * 设置：大病保险
	 */
	public void setSerious(String serious) {
		this.serious = serious;
	}
	/**
	 * 获取：大病保险
	 */
	public String getSerious() {
		return serious;
	}
	/**
	 * 设置：公积金
	 */
	public void setProvident(String provident) {
		this.provident = provident;
	}
	/**
	 * 获取：公积金
	 */
	public String getProvident() {
		return provident;
	}
	/**
	 * 设置：合计
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * 获取：合计
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * 设置：社保类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：社保类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：社保缴纳地
	 */
	public void setPayland(String payland) {
		this.payland = payland;
	}
	/**
	 * 获取：社保缴纳地
	 */
	public String getPayland() {
		return payland;
	}
	/**
	 * 设置：社保卡号
	 */
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	/**
	 * 获取：社保卡号
	 */
	public String getIdnumber() {
		return idnumber;
	}
	/**
	 * 设置：代缴公司
	 */
	public void setPaycompany(String paycompany) {
		this.paycompany = paycompany;
	}
	/**
	 * 获取：代缴公司
	 */
	public String getPaycompany() {
		return paycompany;
	}
	/**
	 * 设置：缴纳金额
	 */
	public void setPay(String pay) {
		this.pay = pay;
	}
	/**
	 * 获取：缴纳金额
	 */
	public String getPay() {
		return pay;
	}
	/**
	 * 设置：津贴发放
	 */
	public void setAllowance(Date allowance) {
		this.allowance = allowance;
	}
	/**
	 * 获取：津贴发放
	 */
	public Date getAllowance() {
		return allowance;
	}
	/**
	 * 设置：预留1
	 */
	public void setReseve1(String reseve1) {
		this.reseve1 = reseve1;
	}
	/**
	 * 获取：预留1
	 */
	public String getReseve1() {
		return reseve1;
	}
	/**
	 * 设置：预留2
	 */
	public void setReseve2(String reseve2) {
		this.reseve2 = reseve2;
	}
	/**
	 * 获取：预留2
	 */
	public String getReseve2() {
		return reseve2;
	}
	/**
	 * 设置：预留3
	 */
	public void setReseve3(String reseve3) {
		this.reseve3 = reseve3;
	}
	/**
	 * 获取：预留3
	 */
	public String getReseve3() {
		return reseve3;
	}
}
