package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-23 11:09:40
 */
public class CheckDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//发票编号
	private String invoicenumber;
	//开票时间
	private Date invoicetime;
	//发票金额
	private String number;
	//发票税率
	private String tax;
	//税后金额
	private String total;
	//实报金额
	private String realnumber;
	//报销时间
	private Date createtime;
	//报销时间1
	private String createtime_a;
	//创建人
	private String userIdCreate;
	//创建人
	private String userNameCreate;
	//报销人Ids
	private String userIdExpense;
	//报销人
	private String userNames;
	//报销人工号
	private String username;
	//备注
	private String remark;
	//0：未删除；1：已删除
	private String status;
	//0：未重复；1：已重复
	private String repeat;

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：发票编号
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}
	/**
	 * 获取：发票编号
	 */
	public String getInvoicenumber() {
		return invoicenumber;
	}
	/**
	 * 设置：开票时间
	 */
	public void setInvoicetime(Date invoicetime) {
		this.invoicetime = invoicetime;
	}
	/**
	 * 获取：开票时间
	 */
	public Date getInvoicetime() {
		return invoicetime;
	}
	/**
	 * 设置：发票金额
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：发票金额
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：实报金额
	 */
	public void setRealnumber(String realnumber) {
		this.realnumber = realnumber;
	}
	/**
	 * 获取：实报金额
	 */
	public String getRealnumber() {
		return realnumber;
	}
	/**
	 * 设置：报销时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：报销时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：创建人
	 */
	public void setUserIdCreate(String userIdCreate) {
		this.userIdCreate = userIdCreate;
	}
	/**
	 * 获取：创建人
	 */
	public String getUserIdCreate() {
		return userIdCreate;
	}
	/**
	 * 设置：报销人
	 */
	public void setUserIdExpense(String userIdExpense) {
		this.userIdExpense = userIdExpense;
	}
	/**
	 * 获取：报销人
	 */
	public String getUserIdExpense() {
		return userIdExpense;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：预留1
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：预留1
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：预留2
	 */
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	/**
	 * 获取：预留2
	 */
	public String getRepeat() {
		return repeat;
	}
	/**
	 * 获取：报销人
	 */
	public String getUserNames() {
		return userNames;
	}
	/**
	 * 设置：报销人
	 */
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	/**
	 * 获取：创建人名字
	 */
	public String getUserNameCreate() {
		return userNameCreate;
	}
	/**
	 * 设置：创建人名字
	 */
	public void setUserNameCreate(String userNameCreate) {
		this.userNameCreate = userNameCreate;
	}

	public String getCreatetime_a() {
		return createtime_a;
	}

	public void setCreatetime_a(String createtime_a) {
		this.createtime_a = createtime_a;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "CheckDO{" +
				"id=" + id +
				", invoicenumber='" + invoicenumber + '\'' +
				", invoicetime=" + invoicetime +
				", number='" + number + '\'' +
				", tax='" + tax + '\'' +
				", total='" + total + '\'' +
				", realnumber='" + realnumber + '\'' +
				", createtime=" + createtime +
				", createtime_a='" + createtime_a + '\'' +
				", userIdCreate='" + userIdCreate + '\'' +
				", userNameCreate='" + userNameCreate + '\'' +
				", userIdExpense='" + userIdExpense + '\'' +
				", userNames='" + userNames + '\'' +
				", username='" + username + '\'' +
				", remark='" + remark + '\'' +
				", status='" + status + '\'' +
				", repeat='" + repeat + '\'' +
				'}';
	}
}
