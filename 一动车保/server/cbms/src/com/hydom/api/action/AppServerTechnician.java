package com.hydom.api.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.Technician;
import com.hydom.account.ebean.WorkLog;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.TechnicianService;
import com.hydom.account.service.WorkLogService;
import com.hydom.core.order.ebean.ServiceImage;
import com.hydom.core.order.ebean.TechnicianBindRecord;
import com.hydom.core.order.service.ServiceImageService;
import com.hydom.core.order.service.TechnicianBindRecordService;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.FirstSpendSendCoupon;
import com.hydom.core.server.service.CouponPackageRecordService;
import com.hydom.core.server.service.FirstSpendSendCouponService;
import com.hydom.util.UploadImageUtil;
import com.hydom.util.dao.QueryResult;

/**
 * @Description 移动端技师相关接口
 * @author WY
 * @date 2015年7月27日 下午3:22:16
 */

@RequestMapping("/api/technician")
@Controller
public class AppServerTechnician {

	@Resource
	private TechnicianService technicianService;
	@Resource
	private OrderService orderService;
	@Resource
	private TechnicianBindRecordService technicianBindRecordService;
	@Resource
	private ServiceImageService serviceImageService;
	@Resource
	private WorkLogService workLogService;
	@Resource
	private FirstSpendSendCouponService firstSpendSendCouponService;
	@Autowired
	private HttpServletRequest request;

	private Log log = LogFactory.getLog("dataServerLog");
	private static final double EARTH_RADIUS = 6378137;

	/**
	 * 技师登录
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param pushId
	 *            推送id
	 * @return
	 */
	@RequestMapping(value = "login", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String login(String account, String password, String pushId) {
		try {
			log.info("App【技师登录】：" + "账号=" + account + " 推送id=" + pushId);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			Technician technician = technicianService.findTechnician(account,
					password);
			if (technician == null) {
				dataMap.put("result", "701"); // 用户名或密码错误
				dataMap.put("id", "");
				dataMap.put("jobStatus", 0);
				dataMap.put("stats", 0);
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			if (StringUtils.isEmpty(pushId)) {
				dataMap.put("result", "702"); // 推送id为空
				dataMap.put("id", "");
				dataMap.put("jobStatus", false);
				dataMap.put("stats", 0);
				String json = mapper.writeValueAsString(dataMap);
				return json;
			}
			StringBuffer jpql = new StringBuffer(
					"o.visible = ?1 and o.pushId = ?2");
			Object[] params = new Object[] { true, pushId };
			QueryResult<Technician> tech = technicianService.getScrollData(-1,
					-1, jpql.toString(), params);
			List<Technician> list = tech.getResultList();
			for (Technician t : list) {
				t.setPushId("0");
				technicianService.update(t);
			}

			technician.setPushId(pushId);
			technicianService.update(technician);
			dataMap.put("result", "001");
			dataMap.put("id", technician.getId());
			dataMap.put("jobStatus", technician.isJobstatus());
			dataMap.put("stats", technician.getStats());
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 返回技师当前的订单
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param pushId
	 *            推送id
	 * @return
	 */
	@RequestMapping(value = "CurrentOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String CurrentOrder(String techId) {
		try {
			log.info("App【返回技师当前的订单】：" + "账号=" + techId);
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			Technician technician = technicianService.find(techId);
			dataMap.put("id", technician.getId());
			dataMap.put("name", technician.getName());
			dataMap.put("phoneNumber", technician.getPhonenumber());
			dataMap.put("jobStatus", technician.isJobstatus());
			Order order = technician.getOrder();
			if (technician.isJobstatus() && order == null) {
				order = orderService.matchOrder(techId, technician.getLatitude(), technician.getLongitude());
			}
			if (order != null && (order.getStatus() >= 31 && order.getStatus() <= 39)) {
				technician.setOrder(null);
				order.setTechMember(null);
				technicianService.update(technician);
				orderService.update(order);
				return "{\"result\":\"1002\"}";
			}
			if (technician.getOrder() != null) {
				dataMap.put("result", "001");
				dataMap.put("stats", order.getStatus()-1);
				dataMap.put("hasOrder", true);
				dataMap.put("orderId", order.getId());// 订单id
				dataMap.put("orderNum", order.getNum());// 订单编号
				dataMap.put("contact", order.getContact());// 联系人
				dataMap.put("phone", order.getPhone());// 联系电话
				dataMap.put("car", order.getCar().getName());// 车型
				dataMap.put("carNum", order.getCarNum());// 车牌号
				dataMap.put("carColor", order.getCarColor());// 车辆颜色
				dataMap.put("cleanType", order.getCleanType());// 清洗方式 1内部清洗
																// 2内外清洗
				dataMap.put("mlng", order.getLng());// 用户经度
				dataMap.put("mlat", order.getLat());// 用户纬度
				dataMap.put("distance", order.getDistance());// 距离
			} else {
				dataMap.put("result", "001");
				dataMap.put("stats", 0);
				dataMap.put("hasOrder", false);
				dataMap.put("orderId", "");// 订单id
				dataMap.put("orderNum", "");// 订单编号
				dataMap.put("contact", "");// 联系人
				dataMap.put("phone", "");// 联系电话
				dataMap.put("car", "");// 车型
				dataMap.put("carNum", "");// 车牌号
				dataMap.put("carColor", "");// 车辆颜色
				dataMap.put("cleanType", 0);// 清洗方式 1内部清洗 2内外清洗
				dataMap.put("mlng", 0);// 用户经度
				dataMap.put("mlat", 0);// 用户纬度
				dataMap.put("distance", 0);// 距离
			}
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 技师上下班
	 * 
	 * @param techId
	 *            技师id
	 * @param lng
	 *            技师经度
	 * @param lat
	 *            技师纬度
	 * @param jobStatus
	 *            上下班状态
	 * @return
	 */
	@RequestMapping(value = "work", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String work(String techId, Double lng, Double lat, Boolean jobStatus) {
		try {
			log.info("App【技师上下班】：" + "技师id=" + techId + " 上下班状态=" + jobStatus
					+ " 技师经度=" + lng + "技师纬度=" + lat);
			Technician tech = technicianService.find(techId);
			JSONObject obj = new JSONObject();

			// 暂时存储当前上下班状态
			boolean js = tech.isJobstatus();

			// 更改上班状态
			tech.setJobstatus(jobStatus);
			tech.setLongitude(lng);
			tech.setLatitude(lat);
			technicianService.update(tech);

			// 记录上下班日志
			if (js != jobStatus) {
				WorkLog workLog = new WorkLog();
				workLog.setTechnician(technicianService.find(techId));
				workLog.setJobstatus(jobStatus);
				workLogService.save(workLog);
			}

			Order order = tech.getOrder();
			if (jobStatus && order == null) {
				order = orderService.matchOrder(techId, lat, lng);
			}
			if (jobStatus && order != null) {
				obj.put("result", "001");
				obj.put("hasOrder", true);
				obj.put("stats", tech.getStats());// 技师工作状态
				obj.put("orderId", order.getId());// 订单id
				obj.put("orderNum", order.getNum());// 订单编号
				obj.put("contact", order.getContact());// 联系人
				obj.put("phone", order.getPhone());// 联系电话
				obj.put("car", order.getCar().getName());// 车型
				obj.put("carNum", order.getCarNum());// 车牌号
				obj.put("carColor", order.getCarColor());// 车辆颜色
				obj.put("cleanType", order.getCleanType());// 清洗方式 1内部清洗 2内外清洗
				obj.put("mlng", order.getLng());// 用户经度
				obj.put("mlat", order.getLat());// 用户纬度
				obj.put("distance", order.getDistance());// 距离
				return obj.toString();
			} else {
				obj.put("result", "001");
				obj.put("hasOrder", false);
				obj.put("stats", 0);// 技师工作状态
				obj.put("orderId", "");// 订单id
				obj.put("orderNum", "");// 订单编号
				obj.put("contact", "");// 联系人
				obj.put("phone", "");// 联系电话
				obj.put("car", "");// 车型
				obj.put("carNum", "");// 车牌号
				obj.put("carColor", "");// 车辆颜色
				obj.put("cleanType", 0);// 清洗方式 1内部清洗 2内外清洗
				obj.put("mlng", 0);// 用户经度
				obj.put("mlat", 0);// 用户纬度
				obj.put("distance", 0);// 距离
				return obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获得技师上下班状态
	 * 
	 * @param techId
	 *            技师id
	 * @return
	 */
	@RequestMapping(value = "getJobStatus", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getJobStatus(String techId) {
		try {
			log.info("App【获得技师上下班状态】：" + "技师id=" + techId);
			JSONObject obj = new JSONObject();
			obj.put("result", "001");
			obj.put("jobStatus", technicianService.find(techId).isJobstatus());
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 技师接受订单
	 * 
	 * @param techId
	 *            技师id
	 * @param orderId
	 *            订单id
	 * @param tlng
	 *            技师经度
	 * @param tlat
	 *            技师纬度
	 * @return
	 */
	@RequestMapping(value = "acceptOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String acceptOrder(String techId, String orderId, Double tlng, Double tlat) {
		try {
			log.info("App【技师接受订单】：" + "技师id=" + techId + " 订单id=" + orderId
					+ " 技师经度=" + tlng + " 技师纬度=" + tlat);
			Technician tech = technicianService.find(techId);
			Order order = orderService.find(orderId);
			if (order.getStatus() >= 31 && order.getStatus() <= 39) {
				tech.setOrder(null);
				order.setTechMember(null);
				technicianService.update(tech);
				orderService.update(order);
				return "{\"result\":\"1002\"}";
			}
			if (tech.getOrder() == null || order.getStatus() != 1)
				return "{\"result\":\"1003\"}";
			if (!order.getTechMember().getId().equals(techId))
				return "{\"result\":\"1005\"}";
			if (tlng == 0 && tlat == 0)
				return "{\"result\":\"1001\"}";
			tech.setLongitude(tlng);
			tech.setLatitude(tlat);
			tech.setStats(1);
			technicianService.update(tech);

			// 计算两点间的距离
			double radLat1 = rad(tlat);
			double radLat2 = rad(order.getLat());
			double a = radLat1 - radLat2;
			double b = rad(tlng) - rad(order.getLng());
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
					+ Math.cos(radLat1) * Math.cos(radLat2)
					* Math.pow(Math.sin(b / 2), 2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;
			order.setDistance(s / 1000);

			order.setStatus(2);
			order.getTechnicianBindRecord().setState(2);
			order.setOrdersDate(new Date());
			orderService.update(order);

			JSONObject obj = new JSONObject();
			obj.put("result", "001");
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 拒绝订单
	 * 
	 * @param techId
	 *            技师id
	 * @param tlng
	 *            技师经度
	 * @param tlat
	 *            技师纬度
	 * @param refuseCause
	 *            拒绝原因
	 * @return
	 */
	@RequestMapping(value = "refuseOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String refuseOrder(String techId, Double tlng, Double tlat,
			String refuseCause) {
		try {
			log.info("App【技师拒绝订单】：" + "技师id=" + techId + " 技师经度=" + tlng
					+ " 技师纬度=" + tlat + "原因=" + refuseCause);
			JSONObject obj = new JSONObject();
			Technician tech = technicianService.find(techId);
			Order order = tech.getOrder();
			if (order.getStatus() >= 31 && order.getStatus() <= 39) {
				tech.setOrder(null);
				order.setTechMember(null);
				technicianService.update(tech);
				orderService.update(order);
				return "{\"result\":\"1002\"}";
			}
			if (order == null || order.getStatus() != 1)
				return "{\"result\":\"1003\"}";
			if (!order.getTechMember().getId().equals(techId))
				return "{\"result\":\"1005\"}";
			if (tlng == 0 || tlat == 0)
				return "{\"result\":\"1001\"}";

			tech.setLongitude(tlng);
			tech.setLatitude(tlat);

			// 计算两点间的距离
			double radLat1 = rad(tlat);
			double radLat2 = rad(order.getLat());
			double a = radLat1 - radLat2;
			double b = rad(tlng) - rad(order.getLng());
			double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
					+ Math.cos(radLat1) * Math.cos(radLat2)
					* Math.pow(Math.sin(b / 2), 2)));
			s = s * EARTH_RADIUS;
			s = Math.round(s * 10000) / 10000;

			order.getTechnicianBindRecord().setRefuseDistance(s / 1000);
			order.getTechnicianBindRecord().setRefuseDate(new Date());
			order.getTechnicianBindRecord().setRefuseCause(refuseCause);
			order.getTechnicianBindRecord().setState(3);
			order.setTechMember(null);
			orderService.update(order);

			// 查找未绑定技师的订单
			order = orderService.matchOrder(techId, tlat, tlng);
			// orderService.find("29381b50-14e8-4448-8acb-71389656dd8f");///////////////////////////////////////////////////////////////////////////////////////////
			if (order != null) {
				tech.setOrder(order);
				tech.setStats(0);
				technicianService.update(tech);

				obj.put("result", "001");
				obj.put("hasOrder", true);
				obj.put("orderId", order.getId());// 订单id
				obj.put("orderNum", order.getNum());// 订单编号
				obj.put("contact", order.getContact());// 联系人
				obj.put("phone", order.getPhone());// 联系电话
				obj.put("car", order.getCar().getName());// 车型
				obj.put("carNum", order.getCarNum());// 车牌号
				obj.put("carColor", order.getCarColor());// 车辆颜色
				obj.put("cleanType", order.getCleanType());// 清洗方式 1内部清洗 2内外清洗
				obj.put("mlng", order.getLng());// 用户经度
				obj.put("mlat", order.getLat());// 用户纬度
				obj.put("distance", order.getDistance());// 距离
				return obj.toString();
			} else {
				// 设技师状态为空闲
				tech.setOrder(null);
				tech.setStats(0);
				technicianService.update(tech);
				obj.put("result", "001");
				obj.put("hasOrder", false);
				obj.put("orderId", "");// 订单id
				obj.put("orderNum", "");// 订单编号
				obj.put("contact", "");// 联系人
				obj.put("phone", "");// 联系电话
				obj.put("car", "");// 车型
				obj.put("carNum", "");// 车牌号
				obj.put("carColor", "");// 车辆颜色
				obj.put("cleanType", 0);// 清洗方式 1内部清洗 2内外清洗
				obj.put("mlng", 0);// 用户经度
				obj.put("mlat", 0);// 用户纬度
				obj.put("distance", 0);// 距离
				return obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param files
	 *            MultipartFile类型的图片数组
	 * @return
	 */
	@RequestMapping(value = "imageUpload", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String imageUpload(String techId, @RequestParam MultipartFile imageFile) {
		try {
			log.info("App【上传图片】：" + "技师id=" + techId);
			Map<String, String> map = UploadImageUtil.uploadFileApp(imageFile,
					request);
			String url = map.get("source");
			JSONObject obj = new JSONObject();
			obj.put("result", "001");
			obj.put("imageUrl", url);
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 开始服务
	 * 
	 * @param techId
	 *            技师id
	 * @param imageUrl
	 *            服务图片
	 * @return
	 */
	@RequestMapping(value = "serviceStart", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serviceStart(String techId, String[] imageUrl) {
		try {
			log.info("App【服务开始】：" + "技师id=" + techId);
			Technician tech = technicianService.find(techId);
			Order order = tech.getOrder();
			if (order == null || order.getStatus() != 2)
				return "{\"result\":\"1003\"}";
			if (!order.getTechMember().getId().equals(techId))
				return "{\"result\":\"1005\"}";
			if (imageUrl != null) {
				for (String img : imageUrl) {
					if (imageUrl == null || imageUrl.equals("null"))
						continue;
					ServiceImage serviceImage = new ServiceImage();
					serviceImage.setOrder(tech.getOrder());
					serviceImage.setImg(img);
					serviceImage.setProperty(1);
					serviceImageService.save(serviceImage);
				}
			}

			tech.setStats(2);
			technicianService.update(tech);
			order.setMakeStartDate(new Date());
			order.setStatus(3);
			orderService.update(order);
			JSONObject obj = new JSONObject();
			obj.put("result", "001");
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 结束服务
	 * 
	 * @param techId
	 *            技师id
	 * @param files
	 *            MultipartFile类型的图片数组
	 * @param tlng
	 *            技师经度
	 * @param tlat
	 *            技师纬度
	 * @return
	 */
	@RequestMapping(value = "serviceEnd", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String serviceEnd(String techId, @RequestParam String[] imageUrl,
			Double tlng, Double tlat) {
		try {
			log.info("App【服务结束】：" + "技师id=" + techId + " 技师经度=" + tlng
					+ " 技师纬度=" + tlat);
			Technician tech = technicianService.find(techId);
			Order order = tech.getOrder();
			if (order == null || order.getStatus() != 3)
				return "{\"result\":\"1003\"}";
			if (!order.getTechMember().getId().equals(techId))
				return "{\"result\":\"1005\"}";
			JSONObject obj = new JSONObject();
			for (String img : imageUrl) {
				if (imageUrl == null || imageUrl.equals("null"))
					continue;
				ServiceImage serviceImage = new ServiceImage();
				serviceImage.setOrder(tech.getOrder());
				serviceImage.setImg(img);
				serviceImage.setProperty(2);
				serviceImageService.save(serviceImage);
			}

			// 更改订单状态为完成
			order.setMakeEndDate(new Date());
			order.setStatus(0);
			orderService.update(order);
			
			//首次消费送优惠券
			firstSpendSendCouponService.gainCoupon(order);
			// 设技师目前服务的订单
			tech.setOrder(null);// //////////////////////////////////////////////////////////////////////////////////////

			// 查找未绑定技师的订单
			order = orderService.matchOrder(techId, tlat, tlng);
			// orderService.find("29381b50-14e8-4448-8acb-71389656dd8f");//////////////////////////////////////////////////////////////////////
			if (order != null) {
				obj.put("result", "001");
				obj.put("hasOrder", true);
				obj.put("orderId", order.getId());// 订单id
				obj.put("orderNum", order.getNum());// 订单编号
				obj.put("contact", order.getContact());// 联系人
				obj.put("phone", order.getPhone());// 联系电话
				obj.put("car", order.getCar().getName());// 车型
				obj.put("carNum", order.getCarNum());// 车牌号
				obj.put("carColor", order.getCarColor());// 车辆颜色
				obj.put("cleanType", order.getCleanType());// 清洗方式 1内部清洗 2内外清洗
				obj.put("mlng", order.getLng());// 用户经度
				obj.put("mlat", order.getLat());// 用户纬度
				obj.put("distance", order.getDistance());// 距离

				// 设技师目前服务的订单
				tech.setStats(1);
				tech.setOrder(order);// //////////////////////////////////////////////////////////////////////////////////////
				technicianService.update(tech);

				return obj.toString();
			} else {
				// 设技师状态为空闲
				tech.setStats(0);
				technicianService.update(tech);
				obj.put("result", "001");
				obj.put("hasOrder", false);
				obj.put("orderId", "");// 订单id
				obj.put("orderNum", "");// 订单编号
				obj.put("contact", "");// 联系人
				obj.put("phone", "");// 联系电话
				obj.put("car", "");// 车型
				obj.put("carNum", "");// 车牌号
				obj.put("carColor", "");// 车辆颜色
				obj.put("cleanType", 0);// 清洗方式 1内部清洗 2内外清洗
				obj.put("mlng", 0);// 用户经度
				obj.put("mlat", 0);// 用户纬度
				obj.put("distance", 0);// 距离
				return obj.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 技师经纬度更新
	 * 
	 * @param techId
	 *            技师id
	 * @param lng
	 *            技师经度
	 * @param lat
	 *            技师纬度
	 * @return
	 */
	@RequestMapping(value = "setLngAndLat", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String setLngAndLat(String techId, Double tlng, Double tlat, String area) {
		try {
			log.info("App【技师经纬度更新】：" + "技师id=" + techId + " 技师经度=" + tlng
					+ " 技师纬度=" + tlat + " 所在地=" + area);
			Technician technician = technicianService.find(techId);
			if ((tlat == 0 || tlat == null) && (tlng == 0 || tlng == null))
				return "{\"result\":\"1001\"}";
			technician.setLongitude(tlng);
			technician.setLatitude(tlat);
			if (StringUtils.isEmpty(area))
				area = "获得地区失败";
			technician.setArea(area);
			technicianService.update(technician);
			JSONObject obj = new JSONObject();

			// Date d2=new Date();
			// System.out.println("经纬度更新接口调用时间间隔（s）："+(Double.parseDouble((d2.getTime()-d1.getTime())+"")/1000));
			// d1=new Date();

			obj.put("result", "001");
			obj.put("jobStatus", technicianService.find(techId).isJobstatus());
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	// static private Date d1=new Date();

	/**
	 * 原密码验证与修改密码
	 * 
	 * @param techId
	 *            技师id
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "modifyPWD", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String modifyPWD(String techId, String oldPassword, String newPassword) {
		try {
			log.info("App【修改密码】：" + "技师id=" + techId + " oldPassword="
					+ oldPassword + " newPassword=" + newPassword);
			JSONObject obj = new JSONObject();
			Technician technician = technicianService.find(techId);
			if (!technician.getPassword().equals(oldPassword)) {
				obj.put("result", "901");
				return obj.toString();
			}
			technician.setPassword(newPassword);
			technicianService.update(technician);
			obj.put("result", "001");
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 订单查询
	 * 
	 * @param techId
	 *            技师id
	 * @return
	 */
	@RequestMapping(value = "selectOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String selectOrder(String techId, Integer year, Integer month,
			Integer orderState) {
		try {
			log.info("App【订单查询】：" + "技师id=" + techId + " year=" + year
					+ " month=" + month + " orderState=" + orderState);
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();

			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("modifyDate", "desc");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Integer nowDate = Integer.parseInt(sdf.format(new Date()));
			String jpql = "o.technician = ?1 and o.state IN (2,3) and YEAR(o.order.modifyDate) IN ("
					+ (nowDate - 1) + "," + nowDate + ")";
			Object[] params = new Object[] { technicianService.find(techId) };
			if (year != null && orderState == null) {
				jpql = "o.technician = ?1 and YEAR(o.order.modifyDate) = ?2 and o.state IN (2,3)";
				params = new Object[] { technicianService.find(techId), year };
				if (month != null) {
					jpql = "o.technician = ?1 and YEAR(o.order.modifyDate) = ?2 and MONTH(o.order.modifyDate) = ?3 and o.state IN (2,3)";
					params = new Object[] { technicianService.find(techId),
							year, month };
				}
			} else if (orderState != null && year == null) {
				jpql = "o.technician = ?1 and o.state = ?2 and YEAR(o.order.modifyDate) IN ("
						+ (nowDate - 1) + "," + nowDate + ")";
				params = new Object[] { technicianService.find(techId),
						orderState };
			} else if (year != null && orderState != null) {
				jpql = "o.technician = ?1 and YEAR(o.order.modifyDate) = ?2 and o.state = ?3";
				params = new Object[] { technicianService.find(techId), year,
						orderState };
				if (month != null) {
					jpql = "o.technician = ?1 and YEAR(o.order.modifyDate) = ?2 and MONTH(o.order.modifyDate) = ?3 and o.state = ?4";
					params = new Object[] { technicianService.find(techId),
							year, month, orderState };
				}
			}
			QueryResult<TechnicianBindRecord> TechnicianBindRecords = technicianBindRecordService
					.getScrollData(-1, -1, jpql, params, orderby);
			List<TechnicianBindRecord> list = TechnicianBindRecords
					.getResultList();

			for (TechnicianBindRecord tbr : list) {
				obj.put("orderId", tbr.getOrder().getId());// 订单id
				obj.put("orderNum", tbr.getOrder().getNum() == null ? "" : tbr
						.getOrder().getNum());// 订单编号
				obj.put("cleanType", tbr.getOrder().getCleanType());// 清洗方式
																	// 1内部清洗
																	// 2内外清洗
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = tbr.getOrder().getMakeStartDate();
				if (date != null) {
					obj.put("startDate", sdf.format(date));// 服务时间
				} else {
					obj.put("startDate", "");
				}

				obj.put("orderState", tbr.getState());// 接受=2，拒绝=3
				array.add(obj);
				obj.clear();
			}

			obj.clear();
			obj.put("result", "001");
			obj.put("orders", array);

			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 查看订单详情
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 */
	@RequestMapping(value = "getOrderInfo", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getOrderInfo(String orderId, String techId) {
		try {
			log.info("App【获得订单详情】" + "订单id=" + orderId);
			Order order = orderService.find(orderId);
			if (!order.getTechMember().getId().equals(techId))
				return "{\"result\":\"1005\"}";
			JSONObject obj = new JSONObject();
			obj.put("result", "001");
			obj.put("orderId", order.getId());// 订单id
			obj.put("orderNum", order.getNum());// 订单编号
			obj.put("contact", order.getContact());// 联系人
			obj.put("phone", order.getPhone());// 联系电话
			obj.put("car", order.getCar().getName());// 车型
			obj.put("carNum", order.getCarNum());// 车牌号
			obj.put("carColor", order.getCarColor());// 车辆颜色
			obj.put("cleanType", order.getCleanType());// 清洗方式 1内部清洗 2内外清洗
			obj.put("mlng", order.getLat());// 用户经度
			obj.put("mlat", order.getLng());// 用户纬度
			obj.put("distance", order.getDistance());// 距离
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = order.getMakeStartDate();
			if (date != null) {
				obj.put("startDate", sdf.format(date));// 服务时间
			} else {
				obj.put("startDate", "");
			}
			JSONObject imgs = new JSONObject();
			List<ServiceImage> imgUrl = serviceImageService
					.getAfterImageByOrderId(orderId);
			for (int i = 0; i < 3; i++) {
				if (i < imgUrl.size()) {
					if (imgUrl.get(i).getImg().equals("null")
							|| imgUrl.get(i).getImg() == null) {
						imgs.put("imgUrl" + i, "");
					} else {
						imgs.put("imgUrl" + i, imgUrl.get(i).getImg());
					}
				} else {
					imgs.put("imgUrl" + i, "");
				}
			}
			obj.put("afterImgs", imgs);
			imgs.clear();
			imgUrl = serviceImageService.getBeforeImageByOrderId(orderId);
			for (int i = 0; i < 3; i++) {
				if (i < imgUrl.size()) {
					if (imgUrl.get(i).getImg().equals("null")
							|| imgUrl.get(i).getImg() == null) {
						imgs.put("imgUrl" + i, "");
					} else {
						imgs.put("imgUrl" + i, imgUrl.get(i).getImg());
					}
				} else {
					imgs.put("imgUrl" + i, "");
				}
			}
			obj.put("beforeImgs", imgs);
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 获得服务器日期
	 * 
	 * @return
	 */
	@RequestMapping(value = "getDate", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getDate() {
		try {
			log.info("App【获得服务器时间】");
			JSONObject obj = new JSONObject();
			Date date = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
			SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
			obj.put("result", "001");
			obj.put("year", sdf1.format(date));
			obj.put("month", sdf2.format(date));
			obj.put("day", sdf3.format(date));
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}

	/**
	 * 推送订单判断
	 * 
	 * @param techid
	 *            技师ID
	 * @param oId
	 *            订单ID
	 * @return
	 */
	@RequestMapping(value = "/order/status", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String orderStatus(String techId, String oId) {
		try {
			log.info("App【订单状态判断】" + " techId=" + techId + " oId=" + oId);
			JSONObject obj = new JSONObject();
			Order order = orderService.find(oId);
			obj.put("result", "001");

			if (order.getTechMember() != null
					&& techId.equals(order.getTechMember().getId())
					&& order.getStatus() == 1) { // 当前订单属于该技师、并且订单状态为派单中
				obj.put("ostatus", 1);
			} else {
				obj.put("ostatus", 0); // 订单失效
			}
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"000\"}";
		}
	}
}
