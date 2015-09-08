package com.hydom.util.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.hydom.account.ebean.Specification;
import com.hydom.account.ebean.SpecificationValue;

public class SpecificationBean {
	private Specification specification;
	private List<SpecificationValue> specificationValues;

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public List<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(
			List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}
	
	public static List<SpecificationBean> conver2Bean(Set<SpecificationValue> specificationValues, Set<Specification> specifications){
		List<SpecificationBean> beans = new ArrayList<SpecificationBean>();
		
		for(Specification speci : specifications){
			SpecificationBean bean = new SpecificationBean();
			List<SpecificationValue> valueBean = new ArrayList<SpecificationValue>();
			for(SpecificationValue value : specificationValues){
				if(value.getSpecification().getId().equals(speci.getId())){
					valueBean.add(value);
				}
			}
			bean.setSpecification(speci);
			bean.setSpecificationValues(valueBean);
			beans.add(bean);
		}
		
		return beans;
	}
}
