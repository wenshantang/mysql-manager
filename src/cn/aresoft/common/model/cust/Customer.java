package cn.aresoft.common.model.cust;
import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
@Table("u_customer_info")
public class Customer  extends BaseModel {
	private static final long serialVersionUID = -8421150033692092723L;
	@PrimaryKey.GUID
	@Column
	private String cust_id;//主键Id
	@Column
	private String cust_name;//客户名称
	@Column
	private String short_name;//客户简称
	@Column
	private String nation;//客户国籍
	@Column
	private String cust_kind;//客户类别（1-投资客户；2-渠道客户）
	@Column
	private String cust_type;//客户类型 1:个人 2： 机构
	@Column
	private String sex;//性别（0-男；1-女）
	@Column
	private String mobile;//手机号码
	@Column
	private String telnum;//座机号码
	@Column
	private String email;//电子邮件
	@Column
	private String postcode;//邮政编码
	@Column
	private String address;//地址
	@Column
	private String cer_type;//证件类型
	@Column
	private String cer_num;//证件号码
	@Column
	private String cer_valid;//证件有效期
	@Column
	private String cust_status;//客户状态Id
	@Column
	private String cust_level;//客户等级Id
	@Column
	private String contract_num;//合同编号（N个合同号以半角逗号连接）
	@Column
	private String channel_id;//所属渠道
	@Column
	private String create_user;//创建人
	@Column
	private String create_time;//创建时间
	@Column
	private String update_user;//修改人
	@Column
	private String update_time;//修改时间
	@Column
	private String password;//登陆密码
	@Column
	private String isdelete;//是否删除（0-保留；1-删除）
	@Column
	private String crm_cust_id;//CRM客户唯一标识
	@Column
	private String faxnum; //传真号码
	@Column
	private String career; //职业
	@Column
	private String education; //学历
	@Column
	private String annual_income; //年收入描述
	@Column
	private String dividend_mode; //默认分红方式 0： 现金分红 1： 红利再投资
	@Column
	private String bank_code; //银行代码
	@Column
	private String bank_name; //银行开户人姓名
	@Column
	private String bank_num; //银行卡号
	@Column
	private String bank_detail; //详细开户信息
	@Column
	private String org_name; //机构名称
	@Column
	private String org_cer_type; 
	@Column
	private String org_license_code;
	@Column
	private String org_cer_code;
	@Column
	private String org_tax_code;
	@Column
	private String org_cer_valid;
	@Column
	private String org_oper_name;
	@Column
	private String org_oper_cer_type;
	@Column
	private String org_oper_cer_num;
	@Column
	private String org_oper_cer_valid;
	@Column
	private String org_legal_name; //法人姓名
	@Column
	private String org_legal_cer_type;
	@Column
	private String org_legal_cer_num;
	@Column
	private String org_legal_cer_valid;
	@Column
	private String org_charger_name; //控股人姓名
	@Column
	private String org_charger_cer_type; //控股人证件类型
	@Column
	private String org_charger_cer_num; //控股人证件号码
	@Column
	private String org_charger_cer_valid; //控股人证件有效期
	@Column
	private int is_first_login;// 首次登录标志位
	@Column
	private String busi_type;// 企业类型
	@Column
	private String indust_type;// 行业类型
	@Column
	private String last_login_time;//登陆时间
	
	
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public String getCust_kind() {
		return cust_kind;
	}
	public void setCust_kind(String cust_kind) {
		this.cust_kind = cust_kind;
	}
	public String getCust_type() {
		return cust_type;
	}
	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelnum() {
		return telnum;
	}
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCer_type() {
		return cer_type;
	}
	public void setCer_type(String cer_type) {
		this.cer_type = cer_type;
	}
	public String getCer_num() {
		return cer_num;
	}
	public void setCer_num(String cer_num) {
		this.cer_num = cer_num;
	}
	public String getCust_status() {
		return cust_status;
	}
	public void setCust_status(String cust_status) {
		this.cust_status = cust_status;
	}
	public String getCust_level() {
		return cust_level;
	}
	public void setCust_level(String cust_level) {
		this.cust_level = cust_level;
	}
	public String getContract_num() {
		return contract_num;
	}
	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getCrm_cust_id() {
		return crm_cust_id;
	}
	public void setCrm_cust_id(String crm_cust_id) {
		this.crm_cust_id = crm_cust_id;
	}
	public String getFaxnum() {
		return faxnum;
	}
	public void setFaxnum(String faxnum) {
		this.faxnum = faxnum;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getAnnual_income() {
		return annual_income;
	}
	public void setAnnual_income(String annual_income) {
		this.annual_income = annual_income;
	}
	public String getDividend_mode() {
		return dividend_mode;
	}
	public void setDividend_mode(String dividend_mode) {
		this.dividend_mode = dividend_mode;
	}
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_num() {
		return bank_num;
	}
	public void setBank_num(String bank_num) {
		this.bank_num = bank_num;
	}
	public String getBank_detail() {
		return bank_detail;
	}
	public void setBank_detail(String bank_detail) {
		this.bank_detail = bank_detail;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_cer_type() {
		return org_cer_type;
	}
	public void setOrg_cer_type(String org_cer_type) {
		this.org_cer_type = org_cer_type;
	}
	public String getOrg_license_code() {
		return org_license_code;
	}
	public void setOrg_license_code(String org_license_code) {
		this.org_license_code = org_license_code;
	}
	public String getOrg_cer_code() {
		return org_cer_code;
	}
	public void setOrg_cer_code(String org_cer_code) {
		this.org_cer_code = org_cer_code;
	}
	public String getOrg_tax_code() {
		return org_tax_code;
	}
	public void setOrg_tax_code(String org_tax_code) {
		this.org_tax_code = org_tax_code;
	}
	public String getOrg_cer_valid() {
		return org_cer_valid;
	}
	public void setOrg_cer_valid(String org_cer_valid) {
		this.org_cer_valid = org_cer_valid;
	}
	public String getOrg_oper_name() {
		return org_oper_name;
	}
	public void setOrg_oper_name(String org_oper_name) {
		this.org_oper_name = org_oper_name;
	}
	public String getOrg_oper_cer_type() {
		return org_oper_cer_type;
	}
	public void setOrg_oper_cer_type(String org_oper_cer_type) {
		this.org_oper_cer_type = org_oper_cer_type;
	}
	public String getOrg_oper_cer_num() {
		return org_oper_cer_num;
	}
	public void setOrg_oper_cer_num(String org_oper_cer_num) {
		this.org_oper_cer_num = org_oper_cer_num;
	}
	public String getOrg_oper_cer_valid() {
		return org_oper_cer_valid;
	}
	public void setOrg_oper_cer_valid(String org_oper_cer_valid) {
		this.org_oper_cer_valid = org_oper_cer_valid;
	}
	public String getOrg_legal_name() {
		return org_legal_name;
	}
	public void setOrg_legal_name(String org_legal_name) {
		this.org_legal_name = org_legal_name;
	}
	public String getOrg_legal_cer_type() {
		return org_legal_cer_type;
	}
	public void setOrg_legal_cer_type(String org_legal_cer_type) {
		this.org_legal_cer_type = org_legal_cer_type;
	}
	public String getOrg_legal_cer_num() {
		return org_legal_cer_num;
	}
	public void setOrg_legal_cer_num(String org_legal_cer_num) {
		this.org_legal_cer_num = org_legal_cer_num;
	}
	public String getOrg_legal_cer_valid() {
		return org_legal_cer_valid;
	}
	public void setOrg_legal_cer_valid(String org_legal_cer_valid) {
		this.org_legal_cer_valid = org_legal_cer_valid;
	}
	public String getOrg_charger_name() {
		return org_charger_name;
	}
	public void setOrg_charger_name(String org_charger_name) {
		this.org_charger_name = org_charger_name;
	}
	public String getOrg_charger_cer_type() {
		return org_charger_cer_type;
	}
	public void setOrg_charger_cer_type(String org_charger_cer_type) {
		this.org_charger_cer_type = org_charger_cer_type;
	}
	public String getOrg_charger_cer_num() {
		return org_charger_cer_num;
	}
	public void setOrg_charger_cer_num(String org_charger_cer_num) {
		this.org_charger_cer_num = org_charger_cer_num;
	}
	public String getOrg_charger_cer_valid() {
		return org_charger_cer_valid;
	}
	public void setOrg_charger_cer_valid(String org_charger_cer_valid) {
		this.org_charger_cer_valid = org_charger_cer_valid;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getCer_valid() {
		return cer_valid;
	}
	public void setCer_valid(String cer_valid) {
		this.cer_valid = cer_valid;
	}
	public int getIs_first_login() {
		return is_first_login;
	}
	public void setIs_first_login(int is_first_login) {
		this.is_first_login = is_first_login;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getIndust_type() {
		return indust_type;
	}
	public void setIndust_type(String indust_type) {
		this.indust_type = indust_type;
	}
	
	
}
