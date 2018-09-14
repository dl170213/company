package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:24
 */
public class UserWageDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//员工
	private Long userId;
	//基本工资
	private String basewage;
	//报销标准
	private String reimbursement;
	//试用期工资
	private String probationwage;
	//试用期报销标准
	private String probursement;
	//工资卡号
	private String wagecardnumber;
	//联行号
	private String coupletnumber;
	//开户行名称
	private String openbank;
	//第二银行卡号
	private String wagecardnumber1;
	//第二联行号
	private String coupletnumber1;
	//第二开户行名称
	private String openbank1;
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
	 * 设置：基本工资
	 */
	public void setBasewage(String basewage) {
		this.basewage = basewage;
	}
	/**
	 * 获取：基本工资
	 */
	public String getBasewage() {
		return basewage;
	}
	/**
	 * 设置：报销标准
	 */
	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}
	/**
	 * 获取：报销标准
	 */
	public String getReimbursement() {
		return reimbursement;
	}
	/**
	 * 设置：试用期工资
	 */
	public void setProbationwage(String probationwage) {
		this.probationwage = probationwage;
	}
	/**
	 * 获取：试用期工资
	 */
	public String getProbationwage() {
		return probationwage;
	}
	/**
	 * 设置：试用期报销标准
	 */
	public void setProbursement(String probursement) {
		this.probursement = probursement;
	}
	/**
	 * 获取：试用期报销标准
	 */
	public String getProbursement() {
		return probursement;
	}
	/**
	 * 设置：工资卡号
	 */
	public void setWagecardnumber(String wagecardnumber) {
		this.wagecardnumber = wagecardnumber;
	}
	/**
	 * 获取：工资卡号
	 */
	public String getWagecardnumber() {
		return wagecardnumber;
	}
	/**
	 * 设置：联行号
	 */
	public void setCoupletnumber(String coupletnumber) {
		this.coupletnumber = coupletnumber;
	}
	/**
	 * 获取：联行号
	 */
	public String getCoupletnumber() {
		return coupletnumber;
	}
	/**
	 * 设置：开户行名称
	 */
	public void setOpenbank(String openbank) {
		this.openbank = openbank;
	}
	/**
	 * 获取：开户行名称
	 */
	public String getOpenbank() {
		return openbank;
	}
	/**
	 * 设置：第二银行卡号
	 */
	public void setWagecardnumber1(String wagecardnumber1) {
		this.wagecardnumber1 = wagecardnumber1;
	}
	/**
	 * 获取：第二银行卡号
	 */
	public String getWagecardnumber1() {
		return wagecardnumber1;
	}
	/**
	 * 设置：第二联行号
	 */
	public void setCoupletnumber1(String coupletnumber1) {
		this.coupletnumber1 = coupletnumber1;
	}
	/**
	 * 获取：第二联行号
	 */
	public String getCoupletnumber1() {
		return coupletnumber1;
	}
	/**
	 * 设置：第二开户行名称
	 */
	public void setOpenbank1(String openbank1) {
		this.openbank1 = openbank1;
	}
	/**
	 * 获取：第二开户行名称
	 */
	public String getOpenbank1() {
		return openbank1;
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
