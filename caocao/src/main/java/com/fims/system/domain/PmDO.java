package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-03 15:39:34
 */
public class PmDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//项目名称
	private String pmname;
	//备注
	private String remark;
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
	 * 设置：项目名称
	 */
	public void setPmname(String pmname) {
		this.pmname = pmname;
	}
	/**
	 * 获取：项目名称
	 */
	public String getPmname() {
		return pmname;
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
