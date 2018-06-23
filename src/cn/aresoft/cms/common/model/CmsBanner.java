package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 网站 banner
 * @author dongchao
 *
 */

@Table("CMS_BANNER")
public class CmsBanner extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1728012713899884999L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String name;

	@Column
	private String title;

	@Column
	private String title_color;

	@Column
	private int bold;

	@Column
	private int idx;

	@Column
	private String big_picurl;

	@Column
	private String small_picurl;

	@Column
	private String href_url;

	@Column
	private String open_type;

	@Column
	private String type;

	@Column
	private int stop_time;

	@Column
	private int status;

	@Column
	private String create_time;

	@Column
	private String create_user;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_color() {
		return title_color;
	}

	public void setTitle_color(String title_color) {
		this.title_color = title_color;
	}

	public int getBold() {
		return bold;
	}

	public void setBold(int bold) {
		this.bold = bold;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getBig_picurl() {
		return big_picurl;
	}

	public void setBig_picurl(String big_picurl) {
		this.big_picurl = big_picurl;
	}

	public String getSmall_picurl() {
		return small_picurl;
	}

	public void setSmall_picurl(String small_picurl) {
		this.small_picurl = small_picurl;
	}

	public String getHref_url() {
		return href_url;
	}

	public void setHref_url(String href_url) {
		this.href_url = href_url;
	}

	public String getOpen_type() {
		return open_type;
	}

	public void setOpen_type(String open_type) {
		this.open_type = open_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStop_time() {
		return stop_time;
	}

	public void setStop_time(int stop_time) {
		this.stop_time = stop_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

}
