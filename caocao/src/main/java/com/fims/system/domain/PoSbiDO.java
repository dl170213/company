package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-09-05 09:50:58
 */
public class PoSbiDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//po编号
	private String poid;
	//sbi编号
	private String sbiid;
	//预留
	private String reseve1;

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
	 * 设置：po编号
	 */
	public void setPoid(String poid) {
		this.poid = poid;
	}
	/**
	 * 获取：po编号
	 */
	public String getPoid() {
		return poid;
	}
	/**
	 * 设置：sbi编号
	 */
	public void setSbiid(String sbiid) {
		this.sbiid = sbiid;
	}
	/**
	 * 获取：sbi编号
	 */
	public String getSbiid() {
		return sbiid;
	}
	/**
	 * 设置：预留
	 */
	public void setReseve1(String reseve1) {
		this.reseve1 = reseve1;
	}
	/**
	 * 获取：预留
	 */
	public String getReseve1() {
		return reseve1;
	}
}
