/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 商品分类
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_product_category")
public class ProductCategory extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5968795398480810880L;
	
	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";
	
	/** 名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/** 树路径 */
	@Column(name="tree_path",length=2000)
	private String treePath;
	
	@Column(name="img_path",length=1000)
	private String imgPath;
	/**
	 * 层级
	 */
	@Column(nullable = false)
	private Integer grade;
	
	/**
	 * 排序
	 */
	@Column(name="orders")
	private Integer order;
	
	
	/**
	 * 热卖分类 0 非热卖   1 热卖
	 */
	@Column(name="hot_productcategory")
	private Integer hotProductCategory = 0;
	
	/** 上级分类 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private ProductCategory parent;

	/** 下级分类 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@Where(clause="visible = 1")
	@OrderBy("order asc")
	private Set<ProductCategory> children = new HashSet<ProductCategory>();
	
	/**
	 * 服务分类
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="serviceType_id")
	private ServiceType serviceType;
	
	/**
	 * 商品品牌
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_category_brand",joinColumns={@JoinColumn(name="product_category_id")},inverseJoinColumns={@JoinColumn(name="product_brand_id")})
	@OrderBy("order asc")
	private List<ProductBrand> productBrandSet = new ArrayList<ProductBrand>();
	
	/**
	 * 该分类下的筛选条件
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy="productCategory")
	private Set<Attribute> attributeSet = new HashSet<Attribute>();
	
	/**
	 * 该分类下的 规格 
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="productCategory")
	@Where(clause="visible = 1")
	private Set<Specification> specificationSet = new HashSet<Specification>();
	
	
	private Boolean visible = true;
	
	public Set<Specification> getSpecificationSet() {
		return specificationSet;
	}

	public void setSpecificationSet(Set<Specification> specificationSet) {
		this.specificationSet = specificationSet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}

	public Set<ProductCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<ProductCategory> children) {
		this.children = children;
	}

	public List<ProductBrand> getProductBrandSet() {
		return productBrandSet;
	}

	public void setProductBrandSet(List<ProductBrand> productBrandSet) {
		this.productBrandSet = productBrandSet;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getHotProductCategory() {
		return hotProductCategory;
	}

	public void setHotProductCategory(Integer hotProductCategory) {
		this.hotProductCategory = hotProductCategory;
	}

	public Set<Attribute> getAttributeSet() {
		return attributeSet;
	}

	public void setAttributeSet(Set<Attribute> attributeSet) {
		this.attributeSet = attributeSet;
	}
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * 获取树路径
	 * 
	 * @return 树路径
	 */
	@Transient
	public List<String> getTreePaths() {
		List<String> treePaths = new ArrayList<String>();
		String[] ids = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		if (ids != null) {
			for (String id : ids) {
				treePaths.add(id);
			}
		}
		return treePaths;
	}

	
}