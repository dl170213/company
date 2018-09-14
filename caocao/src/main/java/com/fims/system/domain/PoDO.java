package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 09:48:50
 */
public class PoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//项目id
	private Long pmid;
	//项目
	private String pm;
	//SP序列号
	private String sp;
	//WBS编号
	private String wbs;
	//数量
	private String count;
	//工作内容
	private String workbody;
	//单价
	private String pice;
	//总价
	private String total;
	//客户名称
	private String customer;
	//工作类型
	private String worktype;
	//SBI号
	private String sbi;
	//收到日期
	private Date receiveddate;
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
	 * 设置：项目id
	 */
	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}
	/**
	 * 获取：项目id
	 */
	public Long getPmid() {
		return pmid;
	}
	/**
	 * 设置：项目
	 */
	public void setPm(String pm) {
		this.pm = pm;
	}
	/**
	 * 获取：项目
	 */
	public String getPm() {
		return pm;
	}
	/**
	 * 设置：SP序列号
	 */
	public void setSp(String sp) {
		this.sp = sp;
	}
	/**
	 * 获取：SP序列号
	 */
	public String getSp() {
		return sp;
	}
	/**
	 * 设置：WBS编号
	 */
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
	/**
	 * 获取：WBS编号
	 */
	public String getWbs() {
		return wbs;
	}
	/**
	 * 设置：数量
	 */
	public void setCount(String count) {
		this.count = count;
	}
	/**
	 * 获取：数量
	 */
	public String getCount() {
		return count;
	}
	/**
	 * 设置：工作内容
	 */
	public void setWorkbody(String workbody) {
		this.workbody = workbody;
	}
	/**
	 * 获取：工作内容
	 */
	public String getWorkbody() {
		return workbody;
	}
	/**
	 * 设置：单价
	 */
	public void setPice(String pice) {
		this.pice = pice;
	}
	/**
	 * 获取：单价
	 */
	public String getPice() {
		return pice;
	}
	/**
	 * 设置：总价
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * 获取：总价
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * 设置：客户名称
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	/**
	 * 获取：客户名称
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * 设置：工作类型
	 */
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	/**
	 * 获取：工作类型
	 */
	public String getWorktype() {
		return worktype;
	}
	/**
	 * 设置：SBI号
	 */
	public void setSbi(String sbi) {
		this.sbi = sbi;
	}
	/**
	 * 获取：SBI号
	 */
	public String getSbi() {
		return sbi;
	}
	/**
	 * 设置：收到日期
	 */
	public void setReceiveddate(Date receiveddate) {
		this.receiveddate = receiveddate;
	}
	/**
	 * 获取：收到日期
	 */
	public Date getReceiveddate() {
		return receiveddate;
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

	@Override
	public String toString() {
		return "PoDO{" +
				"id=" + id +
				", pmid=" + pmid +
				", pm='" + pm + '\'' +
				", sp='" + sp + '\'' +
				", wbs='" + wbs + '\'' +
				", count='" + count + '\'' +
				", workbody='" + workbody + '\'' +
				", pice='" + pice + '\'' +
				", total='" + total + '\'' +
				", customer='" + customer + '\'' +
				", worktype='" + worktype + '\'' +
				", sbi='" + sbi + '\'' +
				", receiveddate=" + receiveddate +
				", reseve1='" + reseve1 + '\'' +
				", reseve2='" + reseve2 + '\'' +
				", reseve3='" + reseve3 + '\'' +
				'}';
	}
}
