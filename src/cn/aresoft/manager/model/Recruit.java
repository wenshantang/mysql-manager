package cn.aresoft.manager.model;

import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

@Table("tb_recruit_info")
public class Recruit extends BaseModel {
		/**
		 *招聘信息
		 */
		private static final long serialVersionUID = 614175932606410339L;
		@PrimaryKey.IDWORKER
		@Column
		private String id;//id
		@Column
		private String messagesTitle;//应聘岗位
		@Column
		private String content;//申请描述
		@Column
		private String author;//申请人
		@Column
		private String phone;//联系电话
		@Column
		private String address;//联系地址
		@Column
		private String sex;//性别
		@Column
		private String file_url;//文件附件
		@Column
		private String create_time;//创建时间
        @Column
        private String status;//是否有意向
        @Column
        private String remark;//备注
        @Column
        private String update_time;//修改时间		
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getMessagesTitle() {
            return messagesTitle;
        }
        
        public void setMessagesTitle(String messagesTitle) {
            this.messagesTitle = messagesTitle;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        
        public String getAuthor() {
            return author;
        }
        
        public void setAuthor(String author) {
            this.author = author;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public void setPhone(String phone) {
            this.phone = phone;
        }
        
        public String getAddress() {
            return address;
        }
        
        public void setAddress(String address) {
            this.address = address;
        }
        
        public String getSex() {
            return sex;
        }
        
        public void setSex(String sex) {
            this.sex = sex;
        }
        
        public String getFile_url() {
            return file_url;
        }
        
        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }
        
        public String getCreate_time() {
            return create_time;
        }
        
        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        
        public String getStatus() {
            return status;
        }

        
        public void setStatus(String status) {
            this.status = status;
        }

        
        public String getRemark() {
            return remark;
        }

        
        public void setRemark(String remark) {
            this.remark = remark;
        }

        
        public String getUpdate_time() {
            return update_time;
        }

        
        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
	
		
		
}
