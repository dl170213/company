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
public class SbiDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//项目编号
	private String pmid;
	//项目
	private String pm;
	//工作类型
	private String worktype;
	//税前金额
	private String pretax;
	//税后金额
	private String posttax;
	//税收金额
	private String tax;
	//客户名称
	private String customer;
	//开票时间
	private Date ticketdate;
	//发票号
	private String invoicenumber;
	//SBI号
	private String sbi;
	//生成日期
	private Date createtime;
	//预留1
	private Date reseve1;
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
	 * 设置：项目编号
	 */
	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	/**
	 * 获取：项目编号
	 */
	public String getPmid() {
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
	 * 设置：税前金额
	 */
	public void setPretax(String pretax) {
		this.pretax = pretax;
	}
	/**
	 * 获取：税前金额
	 */
	public String getPretax() {
		return pretax;
	}
	/**
	 * 设置：税后金额
	 */
	public void setPosttax(String posttax) {
		this.posttax = posttax;
	}
	/**
	 * 获取：税后金额
	 */
	public String getPosttax() {
		return posttax;
	}
	/**
	 * 设置：税收金额
	 */
	public void setTax(String tax) {
		this.tax = tax;
	}
	/**
	 * 获取：税收金额
	 */
	public String getTax() {
		return tax;
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
	 * 设置：开票时间
	 */
	public void setTicketdate(Date ticketdate) {
		this.ticketdate = ticketdate;
	}
	/**
	 * 获取：开票时间
	 */
	public Date getTicketdate() {
		return ticketdate;
	}
	/**
	 * 设置：发票号
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}
	/**
	 * 获取：发票号
	 */
	public String getInvoicenumber() {
		return invoicenumber;
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
	 * 设置：生成日期
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：生成日期
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：预留1
	 */
	public void setReseve1(Date reseve1) {
		this.reseve1 = reseve1;
	}
	/**
	 * 获取：预留1
	 */
	public Date getReseve1() {
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
		return "SbiDO{" +
				"id=" + id +
				", pmid='" + pmid + '\'' +
				", pm='" + pm + '\'' +
				", worktype='" + worktype + '\'' +
				", pretax='" + pretax + '\'' +
				", posttax='" + posttax + '\'' +
				", tax='" + tax + '\'' +
				", customer='" + customer + '\'' +
				", ticketdate=" + ticketdate +
				", invoicenumber='" + invoicenumber + '\'' +
				", sbi='" + sbi + '\'' +
				", createtime=" + createtime +
				", reseve1='" + reseve1 + '\'' +
				", reseve2='" + reseve2 + '\'' +
				", reseve3='" + reseve3 + '\'' +
				'}';
	}
}
