package com.fims.system.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author dl
 * @email dl@163.com
 * @date 2018-08-02 11:09:13
 */
public class UserBaseDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//员工
	private Long userId;
	//工号
	private String username;
	//员工姓名
	private String name;
	//部门
	private Long deptid;
	//部门名称
	private String deptname;
	//项目
	private Long projectId;
	//项目名称
	private String project;
	//状态
	private String status;
	//邮箱
	private String mail;
	//手机号
	private String tel;
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
	 * 设置：工号
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：工号
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：员工姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：员工姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：部门
	 */
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	/**
	 * 获取：部门
	 */
	public Long getDeptid() {
		return deptid;
	}
	/**
	 * 设置：部门名称
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptname() {
		return deptname;
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
	 * 设置：项目名称
	 */
	public void setProject(String project) {
		this.project = project;
	}
	/**
	 * 获取：项目名称
	 */
	public String getProject() {
		return project;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：邮箱
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * 获取：邮箱
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * 设置：手机号
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取：手机号
	 */
	public String getTel() {
		return tel;
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
}
