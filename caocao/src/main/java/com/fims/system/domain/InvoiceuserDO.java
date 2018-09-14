package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-14 16:25:42
 */
public class InvoiceuserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//报销用户名
	private String username;

	public InvoiceuserDO(Long id,String username) {
		this.id = id;
		this.username = username;
	}

	public InvoiceuserDO(String username) {
		this.username = username;
	}

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
	 * 设置：报销用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：报销用户名
	 */
	public String getUsername() {
		return username;
	}
}
