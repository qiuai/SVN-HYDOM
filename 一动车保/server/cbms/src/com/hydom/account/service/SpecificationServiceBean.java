package com.hydom.account.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Specification;
import com.hydom.account.ebean.SpecificationValue;
import com.hydom.util.dao.DAOSupport;
import com.hydom.util.dao.PageView;

@Service
public class SpecificationServiceBean extends DAOSupport<Specification> implements SpecificationService {
	
	@Resource
	private SpecificationValueService specificationValueService;
	
	@Override
	public void deleteBySql(String... ids) {
		
		//删除规格的时候 先将规格值里面的外键设置为空
		String hql = "from com.hydom.account.ebean.SpecificationValue s where s.specification.id = '"+ids[0]+"'";
		List<SpecificationValue> values = specificationValueService.getListByHql(hql);
		for(SpecificationValue value : values){
			value.setSpecification(null);
			specificationValueService.update(value);
		}
		
		super.deleteBySql(ids);
	}
}
