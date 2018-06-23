package cn.aresoft.manager.model.sys;

import com.puff.jdbc.core.Record;

public class SysDeptRecord extends Record {
 @Override
public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Record other = (Record) obj;
		if (this.getRecord() == null) {
			if (other.getRecord() != null) {
				return false;
			}
		} else if (!getRecord().equals(other.getRecord()))
			return false;
		//根据部门id来判断
		 if(!this.getRecord().get("id").equals(other.getRecord().get("id"))) 
             return false; 
		return true;
}
}
