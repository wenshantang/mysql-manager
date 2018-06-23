package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.ListUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.cms.common.model.CmsModel;
import cn.aresoft.cms.common.model.CmsModelAttr;
import cn.aresoft.cms.common.service.CmsModelService;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsModelService")
public class CmsModelServiceImpl extends CenterServiceImpl<CmsModel>implements CmsModelService {

	@Override
	public PageRecord<CmsModel> paging(CmsModel t, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(CmsModel.class);
		List<Object> condition = new ArrayList<Object>();
		return paging(sql, condition, param);
	}

	@Override
	@Transaction
	public void addModel(CmsModel model, List<CmsModelAttr> attrs) {
		insert(model);
		if (ListUtil.notEmpty(attrs)) {
			for (CmsModelAttr cma : attrs) {
				executor.save(cma);
			}
		}
	}

	@Override
	@Transaction
	public void updateModel(CmsModel model, List<CmsModelAttr> attrs) {
		update(model);
		executor.delete("delete from cms_model_attr where model_code = ?", model.getCode());
		if (ListUtil.notEmpty(attrs)) {
			for (CmsModelAttr cma : attrs) {
				executor.save(cma);
			}
		}
	}

	@Override
	public List<CmsModelAttr> queryModelAttr(String code) {
		return executor.queryList(CmsModelAttr.class, SqlBuilder.buildQuerySQL(CmsModelAttr.class) + " where model_code = ?", code);
	}

	@Override
	public void deleteModelAttr(List<String> ids) {
		deleteInSql("delete from cms_model_attr where model_code ", ids);
	}

}
