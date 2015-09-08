/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 商品品牌
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_product_brand")
public class ProductBrand extends BaseEntity {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4644392686461379593L;
	
	
	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;
	
	/** logo */
	@Length(max = 200)
	@Column(name="logo")
	private String imgPath;

	/** 官方网址 */
	@Length(max = 200)
	private String url;
	
	/**
	 * 是否可以利用
	 */
	private Boolean visible = true;
	
	/**
	 * 排序
	 */
	@Column(name="orders")
	private Integer order;
	
	/**
	 * 是否推荐  0 不推荐  1 推荐
	 */
	@Column(name="command_brand")
	private Integer commandBrand;
	
	/**
	 * 介绍
	 */
	private String remark;
	
	
	/** 介绍 *//*
	@Lob
	private String introduction;*/
	/**
	 * 商品类型
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_category_brand",joinColumns={@JoinColumn(name="product_brand_id")},inverseJoinColumns={@JoinColumn(name="product_category_id")})
	@OrderBy("order asc")
	private List<ProductCategory> productCategorySet = new ArrayList<ProductCategory>();
	
	/**
	 * 商品
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="productBrand")
	@Where(clause="visible = 1")
	private List<Product> productSet = new ArrayList<Product>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public List<ProductCategory> getProductCategorySet() {
		return productCategorySet;
	}

	public void setProductCategorySet(List<ProductCategory> productCategorySet) {
		this.productCategorySet = productCategorySet;
	}

	public Integer getCommandBrand() {
		return commandBrand;
	}

	public void setCommandBrand(Integer commandBrand) {
		this.commandBrand = commandBrand;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(List<Product> productSet) {
		this.productSet = productSet;
	}

	@Transient
	public String getFullUrl(){
		if(StringUtils.isEmpty(this.url)){
			return "";
		}
		Pattern pattern = Pattern.compile("^(http:\\/\\/)(www.)?(\\w+\\.)+\\w{2,4}(\\/)?$");
		Matcher matcher = pattern.matcher(this.url);
		if(matcher.matches()){
			return this.url;
		}
	//	http://www.baidu.com
		return "http://"+this.url;
	}
}