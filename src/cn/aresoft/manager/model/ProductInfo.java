package cn.aresoft.manager.model;

import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

@Table("tb_product_info")
public class ProductInfo extends BaseModel {

    private static final long serialVersionUID = 3446621210954808561L;
        /**
		 * 产品信息表
		 */
		@PrimaryKey.AUTO
		@Column
		private int id;//'主键',
		@Column
		private String code;//产品代码
		@Column
		private String name;//产品名称
        @Column
        private String item;//产品简介、摘要
        @Column
        private String isHot;//产品热度
		@Column
		private String status;//'开放状态：0：在售 1：即将开放 2：已售罄 '
		@Column
		private String type;//产品类型
		@Column
		private String content;//产品内容
        @Column
        private String img_big;//产品大图片地址
        @Column
        private String img_small;//产品小图片地址
		@Column
		private String i_createuser;//建立人
		@Column
		private String d_lastmodtime;//最后修改时间
		@Column
		private String i_moduser;//修改人
		@Column
		private String public_time;//发布时间
		@Column
		private String i_createtime;//创建时间
        @Column
        private String sort_num;//排列序号
        @Column
        private String file_url;//附件上传地址
        
        
        public String getFile_url() {
            return file_url;
        }

        
        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getSort_num() {
			return sort_num;
		}

		public void setSort_num(String sort_num) {
			this.sort_num = sort_num;
		}

		public String getI_createtime() {
			return i_createtime;
		}

		public void setI_createtime(String i_createtime) {
			this.i_createtime = i_createtime;
		}

		
        
        public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCode() {
            return code;
        }
        
        public void setCode(String code) {
            this.code = code;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getItem() {
            return item;
        }
        
        public void setItem(String item) {
            this.item = item;
        }
        
        public String getIsHot() {
            return isHot;
        }
        
        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        public String getImg_big() {
			return img_big;
		}
		public void setImg_big(String img_big) {
			this.img_big = img_big;
		}

		public String getImg_small() {
			return img_small;
		}

		public void setImg_small(String img_small) {
			this.img_small = img_small;
		}

		public String getI_createuser() {
            return i_createuser;
        }
        
        public void setI_createuser(String i_createuser) {
            this.i_createuser = i_createuser;
        }
        
        public String getD_lastmodtime() {
            return d_lastmodtime;
        }
        
        public void setD_lastmodtime(String d_lastmodtime) {
            this.d_lastmodtime = d_lastmodtime;
        }
        
        public String getI_moduser() {
            return i_moduser;
        }
        
        public void setI_moduser(String i_moduser) {
            this.i_moduser = i_moduser;
        }
        
        public String getPublic_time() {
            return public_time;
        }
        
        public void setPublic_time(String public_time) {
            this.public_time = public_time;
        }
		
		
	
}
