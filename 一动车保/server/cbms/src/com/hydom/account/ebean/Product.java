/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hydom.core.server.ebean.Car;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 商品
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_product")
public class Product extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -859216722883218172L;

	/** 商品属性值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;

	/** 商品属性值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 全称规格前缀 */
	public static final String FULL_NAME_SPECIFICATION_PREFIX = "[";

	/** 全称规格后缀 */
	public static final String FULL_NAME_SPECIFICATION_SUFFIX = "]";

	/** 全称规格分隔符 */
	public static final String FULL_NAME_SPECIFICATION_SEPARATOR = " ";

	/**
	 * 排序类型
	 */
	public enum OrderType {

		/** 置顶降序 */
		topDesc,

		/** 价格升序 */
		priceAsc,

		/** 价格降序 */
		priceDesc,

		/** 销量降序 */
		salesDesc,

		/** 评分降序 */
		scoreDesc,

		/** 日期降序 */
		dateDesc
	}

	public Product() {
	}

	public Product(String id) {
		super(id);
	}

	/** 编号 */
	@JsonProperty
	@Column(nullable = false, unique = true, length = 100)
	private String sn;

	/**
	 * 商品系列 由规格值 分小类 但是都是一个系列
	 */
	@Column(name = "goods_numb")
	private String goods_num;

	/** 名称 */
	@JsonProperty
	@Column(nullable = false, length = 200)
	private String name;

	/** 全称 */
	@JsonProperty
	@Column(nullable = false)
	private String fullName;

	/** 销售价 */
	@JsonProperty
	@Column(columnDefinition = "decimal(20,2)")
	private Float price;

	/** 成本价 */
	@JsonProperty
	@NotNull
	@Column(columnDefinition = "decimal(20,2)")
	private Float cost;

	/** 市场价 */
	@JsonProperty
	@NotNull
	@Column(columnDefinition = "decimal(20,2)")
	private Float marketPrice;

	/** 展示图片 */
	@Column(length = 200)
	private String imgPath;

	/** 单位 */
	@Column(length = 200)
	private String unit;

	/** 重量 */
	private Integer weight;

	/** 库存 */
	private Integer stock;

	/** 已分配库存 */
	private Integer allocatedStock;

	/** 库存备注 */
	@Column(length = 2000)
	private String stockMemo;

	/** 赠送积分 */
	private Long point;

	/**
	 * 是否可以使用优惠卷 0可以使用 其他不能使用
	 */
	private Integer useCoupon;

	/**
	 * 是否可以推荐该商品 1推荐 其他 不能推荐
	 */
	private Integer recommend;

	/** 是否上架 */
	private Boolean isMarketable;

	/** 是否列出 */
	private Boolean isList;

	/** 是否置顶 */
	private Boolean isTop;

	/** 是否为赠品 */
	private Boolean isGift;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 备注 */
	@Length(max = 200)
	private String memo;

	/** 评分 */
	@Column(nullable = false, precision = 12, scale = 6)
	private Float score = 0f;

	/** 总评分 */
	@Column(nullable = false, precision = 12, scale = 6)
	private Long totalScore = 0l;

	/** 评分数 */
	@Column(nullable = false, precision = 12, scale = 6)
	private Long scoreCount = 0l;

	/** 点击数 */
	@Column(nullable = false)
	private Long hits = 0l;

	/** 周点击数 */
	@Column(nullable = false)
	private Long weekHits = 0l;

	/** 月点击数 */
	@Column(nullable = false)
	private Long monthHits = 0l;

	/** 销量 */
	@Column(nullable = false)
	private Long sales = 0l;

	/** 周销量 */
	@Column(nullable = false)
	private Long weekSales = 0l;

	/** 月销量 */
	@Column(nullable = false)
	private Long monthSales = 0l;

	/** 周点击数更新日期 */
	private Date weekHitsDate;

	/** 月点击数更新日期 */
	private Date monthHitsDate;

	/** 周销量更新日期 */
	private Date weekSalesDate;

	/** 月销量更新日期 */
	private Date monthSalesDate;

	/** 商品属性值0 */
	@Length(max = 200)
	private String attributeValue0;

	/** 商品属性值1 */
	@Length(max = 200)
	private String attributeValue1;

	/** 商品属性值2 */
	@Length(max = 200)
	private String attributeValue2;

	/** 商品属性值3 */
	@Length(max = 200)
	private String attributeValue3;

	/** 商品属性值4 */
	@Length(max = 200)
	private String attributeValue4;

	/** 商品属性值5 */
	@Length(max = 200)
	private String attributeValue5;

	/** 商品属性值6 */
	@Length(max = 200)
	private String attributeValue6;

	/** 商品属性值7 */
	@Length(max = 200)
	private String attributeValue7;

	/** 商品属性值8 */
	@Length(max = 200)
	private String attributeValue8;

	/** 商品属性值9 */
	@Length(max = 200)
	private String attributeValue9;

	/** 商品属性值10 */
	@Length(max = 200)
	private String attributeValue10;

	/** 商品属性值11 */
	@Length(max = 200)
	private String attributeValue11;

	/** 商品属性值12 */
	@Length(max = 200)
	private String attributeValue12;

	/** 商品属性值13 */
	@Length(max = 200)
	private String attributeValue13;

	/** 商品属性值14 */
	@Length(max = 200)
	private String attributeValue14;

	/** 商品属性值15 */
	@Length(max = 200)
	private String attributeValue15;

	/** 商品属性值16 */
	@Length(max = 200)
	private String attributeValue16;

	/** 商品属性值17 */
	@Length(max = 200)
	private String attributeValue17;

	/** 商品属性值18 */
	@Length(max = 200)
	private String attributeValue18;

	/** 商品属性值19 */
	@Length(max = 200)
	private String attributeValue19;

	/** 商品分类 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_category_id", nullable = false)
	private ProductCategory productCategory;

	/** 品牌 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_brand_id")
	private ProductBrand productBrand;

	/** 商品图片 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private List<ProductImage> productImages = new ArrayList<ProductImage>();

	/** 规格 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_specification", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "specification_id") })
	@OrderBy("order asc")
	@Where(clause = "visible = 1")
	private Set<Specification> specifications = new HashSet<Specification>();

	/**
	 * 规格值
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_specification_value")
	@OrderBy("specification asc")
	private Set<SpecificationValue> specificationValues = new HashSet<SpecificationValue>();

	/**
	 * 商品 绑定 车型
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_car", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "car_id") })
	private Set<Car> carSet = new HashSet<Car>();

	/** 参数值 */
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "t_product_parameter_value")
	private Map<Parameter, String> parameterValue = new HashMap<Parameter, String>();

	/**
	 * 是否适用于所有的车型 0 true 1 false
	 */
	@Column(name = "user_all_car")
	private Integer useAllCar = 0;

	/**
	 * 2、限量精品 3、天天特价 4、绿色出行
	 */
	@Column(name = "prouduct_unique_type")
	private Integer prouductUniqueType;

	/**
	 * 精品两个字段
	 */
	/**
	 * 商品总数
	 */
	@Column(name = "product_count")
	private Integer productCount;

	/**
	 * 已买数量
	 */
	@Column(name = "buy_product_count")
	private Integer buyProductCount;

	/**
	 * 天天特价
	 */
	/**
	 * 折扣
	 */
	@Column(name = "discount", columnDefinition = "decimal(20,2)")
	private Float discount;

	/**
	 * 折扣价格
	 */
	@Column(name = "discount_money", columnDefinition = "decimal(20,2)")
	private Float discountMoney;

	/**
	 * 商品标签
	 * 
	 * @return
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_product_label", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "product_label_id") })
	private Set<ProductLabel> labels = new HashSet<ProductLabel>();

	/**
	 * 商品订单
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@OrderBy("createDate desc")
	private List<ServerOrderDetail> orderProduct = new ArrayList<ServerOrderDetail>();

	private Boolean visible = true;

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public List<ServerOrderDetail> getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(List<ServerOrderDetail> orderProduct) {
		this.orderProduct = orderProduct;
	}

	public Integer getProuductUniqueType() {
		return prouductUniqueType;
	}

	public void setProuductUniqueType(Integer prouductUniqueType) {
		this.prouductUniqueType = prouductUniqueType;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Set<ProductLabel> getLabels() {
		return labels;
	}

	public void setLabels(Set<ProductLabel> labels) {
		this.labels = labels;
	}

	public Integer getUseAllCar() {
		return useAllCar;
	}

	public void setUseAllCar(Integer useAllCar) {
		this.useAllCar = useAllCar;
	}

	public Integer getAllocatedStock() {
		return allocatedStock;
	}

	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStockMemo() {
		return stockMemo;
	}

	public void setStockMemo(String stockMemo) {
		this.stockMemo = stockMemo;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	public Long getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Long scoreCount) {
		this.scoreCount = scoreCount;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}

	public Long getWeekHits() {
		return weekHits;
	}

	public void setWeekHits(Long weekHits) {
		this.weekHits = weekHits;
	}

	public Long getMonthHits() {
		return monthHits;
	}

	public void setMonthHits(Long monthHits) {
		this.monthHits = monthHits;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public Long getWeekSales() {
		return weekSales;
	}

	public void setWeekSales(Long weekSales) {
		this.weekSales = weekSales;
	}

	public Long getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(Long monthSales) {
		this.monthSales = monthSales;
	}

	public Date getWeekHitsDate() {
		return weekHitsDate;
	}

	public void setWeekHitsDate(Date weekHitsDate) {
		this.weekHitsDate = weekHitsDate;
	}

	public Date getMonthHitsDate() {
		return monthHitsDate;
	}

	public void setMonthHitsDate(Date monthHitsDate) {
		this.monthHitsDate = monthHitsDate;
	}

	public Date getWeekSalesDate() {
		return weekSalesDate;
	}

	public void setWeekSalesDate(Date weekSalesDate) {
		this.weekSalesDate = weekSalesDate;
	}

	public Date getMonthSalesDate() {
		return monthSalesDate;
	}

	public void setMonthSalesDate(Date monthSalesDate) {
		this.monthSalesDate = monthSalesDate;
	}

	public String getAttributeValue0() {
		return attributeValue0;
	}

	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	public String getAttributeValue1() {
		return attributeValue1;
	}

	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	public String getAttributeValue2() {
		return attributeValue2;
	}

	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	public String getAttributeValue3() {
		return attributeValue3;
	}

	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	public String getAttributeValue4() {
		return attributeValue4;
	}

	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	public String getAttributeValue5() {
		return attributeValue5;
	}

	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	public String getAttributeValue6() {
		return attributeValue6;
	}

	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	public String getAttributeValue7() {
		return attributeValue7;
	}

	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	public String getAttributeValue8() {
		return attributeValue8;
	}

	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	public String getAttributeValue9() {
		return attributeValue9;
	}

	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	public String getAttributeValue10() {
		return attributeValue10;
	}

	public void setAttributeValue10(String attributeValue10) {
		this.attributeValue10 = attributeValue10;
	}

	public String getAttributeValue11() {
		return attributeValue11;
	}

	public void setAttributeValue11(String attributeValue11) {
		this.attributeValue11 = attributeValue11;
	}

	public String getAttributeValue12() {
		return attributeValue12;
	}

	public void setAttributeValue12(String attributeValue12) {
		this.attributeValue12 = attributeValue12;
	}

	public String getAttributeValue13() {
		return attributeValue13;
	}

	public void setAttributeValue13(String attributeValue13) {
		this.attributeValue13 = attributeValue13;
	}

	public String getAttributeValue14() {
		return attributeValue14;
	}

	public void setAttributeValue14(String attributeValue14) {
		this.attributeValue14 = attributeValue14;
	}

	public String getAttributeValue15() {
		return attributeValue15;
	}

	public void setAttributeValue15(String attributeValue15) {
		this.attributeValue15 = attributeValue15;
	}

	public String getAttributeValue16() {
		return attributeValue16;
	}

	public void setAttributeValue16(String attributeValue16) {
		this.attributeValue16 = attributeValue16;
	}

	public String getAttributeValue17() {
		return attributeValue17;
	}

	public void setAttributeValue17(String attributeValue17) {
		this.attributeValue17 = attributeValue17;
	}

	public String getAttributeValue18() {
		return attributeValue18;
	}

	public void setAttributeValue18(String attributeValue18) {
		this.attributeValue18 = attributeValue18;
	}

	public String getAttributeValue19() {
		return attributeValue19;
	}

	public void setAttributeValue19(String attributeValue19) {
		this.attributeValue19 = attributeValue19;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductBrand getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public Set<Specification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<Specification> specifications) {
		this.specifications = specifications;
	}

	public Set<SpecificationValue> getSpecificationValues() {
		return specificationValues;
	}

	public void setSpecificationValues(
			Set<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Map<Parameter, String> getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Map<Parameter, String> parameterValue) {
		this.parameterValue = parameterValue;
	}

	public Integer getUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon(Integer useCoupon) {
		this.useCoupon = useCoupon;
	}

	public Set<Car> getCarSet() {
		return carSet;
	}

	public void setCarSet(Set<Car> carSet) {
		this.carSet = carSet;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getBuyProductCount() {
		return buyProductCount;
	}

	public void setBuyProductCount(Integer buyProductCount) {
		this.buyProductCount = buyProductCount;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Float discountMoney) {
		this.discountMoney = discountMoney;
	}

	/**
	 * 获取商品属性值
	 * 
	 * @param attribute
	 *            商品属性
	 * @return 商品属性值
	 */
	@Transient
	public String getAttributeValue(Attribute attribute) {
		if (attribute != null && attribute.getPropertyIndex() != null) {
			try {
				String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ attribute.getPropertyIndex();
				return (String) PropertyUtils.getProperty(this, propertyName);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 设置商品属性值
	 * 
	 * @param attribute
	 *            商品属性
	 * @param value
	 *            商品属性值
	 */
	@Transient
	public void setAttributeValue(Attribute attribute, String value) {
		if (attribute != null && attribute.getPropertyIndex() != null) {
			if (StringUtils.isEmpty(value)) {
				value = null;
			}
			if (value == null
					|| (attribute.getOptions() != null && attribute
							.getOptions().contains(value))) {
				try {
					String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
							+ attribute.getPropertyIndex();
					PropertyUtils.setProperty(this, propertyName, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
	}

}