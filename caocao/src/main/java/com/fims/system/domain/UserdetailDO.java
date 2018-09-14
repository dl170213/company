package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 员工详细信息表
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-07-24 11:23:24
 */
public class UserdetailDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//员工
	private Long userId;
	//工号
	private String username;
	//姓名
	private String name;
	//项目
	private Long projectId;
	//项目名称
	private String project;
	//机构
	private String deptment;
	//入职时间
	private Date entryTime;
	//转正时间
	private Date positiveTime;
	//离职时间
	private Date leaveTime;
	//职务
	private String duty;
	//岗位级别
	private String joblevel;
	//身份证号
	private String idnumber;
	//身份证地址
	private String idaddress;
	//毕业学校
	private String graduateschool;
	//专业
	private String major;
	//学历
	private String education;
	//证书编号
	private String diploma;
	//毕业年份
	private Date graduateyear;
	//合同签订日期
	private Date contracttime;
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
	 * 设置：项目
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目
	 */
	public Long getProjectId() {
		return projectId;
	}
	/**
	 * 设置：入职时间
	 */
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	/**
	 * 获取：入职时间
	 */
	public Date getEntryTime() {
		return entryTime;
	}
	/**
	 * 设置：转正时间
	 */
	public void setPositiveTime(Date positiveTime) {
		this.positiveTime = positiveTime;
	}
	/**
	 * 获取：转正时间
	 */
	public Date getPositiveTime() {
		return positiveTime;
	}
	/**
	 * 设置：离职时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	/**
	 * 获取：离职时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	/**
	 * 设置：职务
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}
	/**
	 * 获取：职务
	 */
	public String getDuty() {
		return duty;
	}
	/**
	 * 设置：岗位级别
	 */
	public void setJoblevel(String joblevel) {
		this.joblevel = joblevel;
	}
	/**
	 * 获取：岗位级别
	 */
	public String getJoblevel() {
		return joblevel;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdnumber() {
		return idnumber;
	}
	/**
	 * 设置：身份证地址
	 */
	public void setIdaddress(String idaddress) {
		this.idaddress = idaddress;
	}
	/**
	 * 获取：身份证地址
	 */
	public String getIdaddress() {
		return idaddress;
	}
	/**
	 * 设置：毕业学校
	 */
	public void setGraduateschool(String graduateschool) {
		this.graduateschool = graduateschool;
	}
	/**
	 * 获取：毕业学校
	 */
	public String getGraduateschool() {
		return graduateschool;
	}
	/**
	 * 设置：专业
	 */
	public void setMajor(String major) {
		this.major = major;
	}
	/**
	 * 获取：专业
	 */
	public String getMajor() {
		return major;
	}
	/**
	 * 设置：学历
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * 获取：学历
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * 设置：证书编号
	 */
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	/**
	 * 获取：证书编号
	 */
	public String getDiploma() {
		return diploma;
	}
	/**
	 * 设置：毕业年份
	 */
	public void setGraduateyear(Date graduateyear) {
		this.graduateyear = graduateyear;
	}
	/**
	 * 获取：毕业年份
	 */
	public Date getGraduateyear() {
		return graduateyear;
	}
	/**
	 * 设置：合同签订日期
	 */
	public void setContracttime(Date contracttime) {
		this.contracttime = contracttime;
	}
	/**
	 * 获取：合同签订日期
	 */
	public Date getContracttime() {
		return contracttime;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDeptment() {
		return deptment;
	}

	public void setDeptment(String deptment) {
		this.deptment = deptment;
	}
}
