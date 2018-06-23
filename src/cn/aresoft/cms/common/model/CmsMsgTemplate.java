package cn.aresoft.cms.common.model;

import java.util.Map;

import org.beetl.core.Template;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import com.puff.framework.utils.StringUtil;
import com.puff.log.Log;
import com.puff.log.LogFactory;

import cn.aresoft.cms.common.StringTemplate;
import cn.aresoft.common.model.BaseModel;

/**
 * cms 消息模板(短信、邮件、微信)
 * 
 * @author dongchao
 *
 */
@Table("cms_msg_template")
public class CmsMsgTemplate extends BaseModel {

	private static final Log log = LogFactory.get();

	/**
	 * 
	 */
	private static final long serialVersionUID = -8528266345673079478L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String name;// 名称

	@Column
	private String content;// 内容

	@Column
	private String type;// 类型1.短信 2邮件

	@Column
	private String memo;// 描述

	@Column
	private String create_time;// 创建时间

	@Column
	private String email_subject;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getEmail_subject() {
		return email_subject;
	}

	public void setEmail_subject(String email_subject) {
		this.email_subject = email_subject;
	}

	/**
	 * 替换邮件主题中的变量生成真实内容
	 * 
	 * @param param
	 * @return
	 */
	public String parserSubject(Map<String, Object> param) {
		if (StringUtil.empty(email_subject)) {
			return null;
		}
		try {
			Template template = StringTemplate.getTemplate(email_subject);
			template.binding(param);
			return template.render();
		} catch (Exception e) {
			log.error("解析邮件主题出错\n模板内容{0}}", e, email_subject);
			return null;
		}
	}

	/**
	 * 替换模板中的变量生成真实内容
	 * 
	 * @param param
	 * @return
	 */
	public String parser(Map<String, Object> param) {
		try {
			Template template = StringTemplate.getTemplate(content);
			template.binding(param);
			return template.render();
		} catch (Exception e) {
			log.error("解析模板出错\n模板名称{0}\n模板内容{1}}", e, name, content);
			return null;
		}
	}

}
