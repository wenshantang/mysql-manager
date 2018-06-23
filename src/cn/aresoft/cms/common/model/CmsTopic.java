package cn.aresoft.cms.common.model;

import java.util.Arrays;
import java.util.List;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 栏目基本信息
 * 
 * @author dongchao
 *
 */
@Table("cms_topic")
public class CmsTopic extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3167583700744871062L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;// 栏目ID

	@Column
	private String menu_name;// 左侧菜单显示名称

	@Column
	private String name;// 栏目名称

	@Column
	private String simple_name;// 栏目简称

	@Column
	private String code;// 栏目代码(创建不许修改)

	@Column
	private String type_id;// 栏目类型ID

	@Column
	private String location;// 访问路径

	@Column(processor = ArrayFieldProcessor.class)
	private String[] access_permission;// 浏览权限

	@Column
	private String is_show;// 是否显示1.显示 0不显示

	@Column
	private int idx;// 栏目排序

	@Column
	private String parent_id;// 上级栏目ID

	@Column
	private String model_id;// 栏目模型ID

	@Column
	private String create_time;// 创建时间

	@Column
	private String create_user;// 创建人

	@Column
	private String last_modify_time;// 最后修改时间

	@Column
	private String last_modify_user;// 最后修改人

	@Column
	private String meta_title;// html meta title

	@Column
	private String meta_keywords;// html meta keywords

	@Column
	private String meta_desc;// html meta description

	@Column
	private String allow_contribute;// 是否允许投稿1.允许 0.不允许

	@Column(processor = ArrayFieldProcessor.class)
	private String[] contribute_permission;// 投稿权限

	@Column
	private String template_id;// 模板ID

	@Column
	private String title_img;// title 图片

	@Column
	private int title_img_width;// title 图片宽度

	@Column
	private int title_img_heigth;// title 图片高度

	@Column
	private String content_img;// content图片

	@Column
	private int content_img_width;// content 图片宽度

	@Column
	private int content_img_heigth;// content 图片高度

	@Column
	private String target = "_blank";// 打开方式

	@Column
	private int page_size = 20;// 分页大小

	private boolean has_child;// 是否有子节点
	
	@Column
	private String article_column;//是否是文章栏目 0-否  1-是

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimple_name() {
		return simple_name;
	}

	public void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[] getAccess_permission() {
		return access_permission;
	}

	public void setAccess_permission(String[] access_permission) {
		this.access_permission = access_permission;
	}

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
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

	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public String getLast_modify_user() {
		return last_modify_user;
	}

	public void setLast_modify_user(String last_modify_user) {
		this.last_modify_user = last_modify_user;
	}

	public String getMeta_title() {
		return meta_title;
	}

	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}

	public String getMeta_keywords() {
		return meta_keywords;
	}

	public void setMeta_keywords(String meta_keywords) {
		this.meta_keywords = meta_keywords;
	}

	public String getMeta_desc() {
		return meta_desc;
	}

	public void setMeta_desc(String meta_desc) {
		this.meta_desc = meta_desc;
	}

	public String getAllow_contribute() {
		return allow_contribute;
	}

	public void setAllow_contribute(String allow_contribute) {
		this.allow_contribute = allow_contribute;
	}

	public String[] getContribute_permission() {
		return contribute_permission;
	}

	public void setContribute_permission(String[] contribute_permission) {
		this.contribute_permission = contribute_permission;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTitle_img() {
		return title_img;
	}

	public void setTitle_img(String title_img) {
		this.title_img = title_img;
	}

	public int getTitle_img_width() {
		return title_img_width;
	}

	public void setTitle_img_width(int title_img_width) {
		this.title_img_width = title_img_width;
	}

	public int getTitle_img_heigth() {
		return title_img_heigth;
	}

	public void setTitle_img_heigth(int title_img_heigth) {
		this.title_img_heigth = title_img_heigth;
	}

	public String getContent_img() {
		return content_img;
	}

	public void setContent_img(String content_img) {
		this.content_img = content_img;
	}

	public int getContent_img_width() {
		return content_img_width;
	}

	public void setContent_img_width(int content_img_width) {
		this.content_img_width = content_img_width;
	}

	public int getContent_img_heigth() {
		return content_img_heigth;
	}

	public void setContent_img_heigth(int content_img_heigth) {
		this.content_img_heigth = content_img_heigth;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public boolean isHas_child() {
		return has_child;
	}

	public void setHas_child(boolean has_child) {
		this.has_child = has_child;
	}

	@Override
	public String toString() {
		return "CmsTopic [id=" + id + ", name=" + name + ", simple_name=" + simple_name + ", code=" + code + ", type_id=" + type_id + ", location=" + location
				+ ", access_permission=" + Arrays.toString(access_permission) + ", is_show=" + is_show + ", idx=" + idx + ", parent_id=" + parent_id + ", model_id=" + model_id
				+ ", create_time=" + create_time + ", create_user=" + create_user + ", last_modify_time=" + last_modify_time + ", last_modify_user=" + last_modify_user
				+ ", meta_title=" + meta_title + ", meta_keywords=" + meta_keywords + ", meta_desc=" + meta_desc + ", allow_contribute=" + allow_contribute
				+ ", contribute_permission=" + Arrays.toString(contribute_permission) + ", template_id=" + template_id + ", title_img=" + title_img + ", title_img_width="
				+ title_img_width + ", title_img_heigth=" + title_img_heigth + ", content_img=" + content_img + ", content_img_width=" + content_img_width + ", content_img_heigth="
				+ content_img_heigth + ", target=" + target + ", page_size=" + page_size + ", has_child=" + has_child + "]";
	}

	public String getArticle_column() {
		return article_column;
	}

	public void setArticle_column(String article_column) {
		this.article_column = article_column;
	}

	//为栏目权限管理新增
	private List<CmsTopic> sons;
	
	public List<CmsTopic> getSons() {
		return this.sons;
	}

	public void setSons(List<CmsTopic> sons) {
		this.sons = sons;
	}
}
