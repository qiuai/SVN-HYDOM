package com.hydom.util.bean;

import com.hydom.account.ebean.ProductBrand;

public class BrandBean {
	private ProductBrand brand;
	private Boolean isSelected;

	public ProductBrand getBrand() {
		return brand;
	}

	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

}
