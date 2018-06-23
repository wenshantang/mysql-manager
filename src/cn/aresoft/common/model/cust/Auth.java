package cn.aresoft.common.model.cust;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 客户附件表
 * @author ylj
 *
 */
@Table("u_auth")
public class Auth extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Column
	@PrimaryKey.IDWORKER
	private String i_id;//主键Id
	@Column
	private String cust_id;//用户登录ID
	@Column
	private String auth_type;//认证类型
	@Column
	private String auth_attach;//认证附件地址
	@Column
	private String auth_desc;//认证文本描述
	
	public String getI_id() {
		return i_id;
	}
	public void setI_id(String i_id) {
		this.i_id = i_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}
	public String getAuth_attach() {
		return auth_attach;
	}
	public void setAuth_attach(String auth_attach) {
		this.auth_attach = auth_attach;
	}
	public String getAuth_desc() {
		return auth_desc;
	}
	public void setAuth_desc(String auth_desc) {
		this.auth_desc = auth_desc;
	}
	
	
}
