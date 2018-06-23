package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * @author DC
 * @E-mail:dongchaoforever@Gmail.com
 * @version 创建时间：2012-7-15 下午01:31:02
 */

@Table("SYS_WORKDAY")
public class WorkDay extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3292858683539965048L;

	@Column
	@PrimaryKey.CUSTOM
	private String day;

	@Column
	private String flag;

	@Column
	private String startDate;

	@Column
	private String endDate;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
