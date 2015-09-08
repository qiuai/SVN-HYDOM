/*
 * 
 * 
 * 
 */
package net.shop.controller.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import net.sf.json.JSONObject;
import net.shop.DateEditor;
import net.shop.Message;
import net.shop.Setting;
import net.shop.entity.Log;
import net.shop.template.directive.FlashMessageDirective;
import net.shop.util.SettingUtils;
import net.shop.util.SpringUtils;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 基类
 * 
 * 
 * 
 */
public class BaseController {

	/** 错误视图 */
	protected static final String ERROR_VIEW = "/admin/common/error";

	/** 错误消息 */
	protected static final Message ERROR_MESSAGE = Message.error("admin.message.error");

	/** 成功消息 */
	protected static final Message SUCCESS_MESSAGE = Message.success("admin.message.success");

	/** "验证结果"参数名称 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	public static final String CODE = "code";			
	public static final String WARN = "warn";
	public static final Integer SUCCESS = 0;			// 成功
	public static final Integer ERROR = -1;			// 错误
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	public static final String PAGENUMBER = "pageNumber";
	
	@Resource(name = "validator")
	private Validator validator;

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}

	/**
	 * 数据验证
	 * 
	 * @param target
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 货币格式化
	 * 
	 * @param amount
	 *            金额
	 * @param showSign
	 *            显示标志
	 * @param showUnit
	 *            显示单位
	 * @return 货币格式化
	 */
	protected String currency(BigDecimal amount, boolean showSign, boolean showUnit) {
		Setting setting = SettingUtils.get();
		String price = setting.setScale(amount).toString();
		if (showSign) {
			price = setting.getCurrencySign() + price;
		}
		if (showUnit) {
			price += setting.getCurrencyUnit();
		}
		return price;
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}

	/**
	 * 添加瞬时消息
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @param message
	 *            消息
	 */
	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}

	/**
	 * 添加日志
	 * 
	 * @param content
	 *            内容
	 */
	protected void addLog(String content) {
		if (content != null) {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME, content, RequestAttributes.SCOPE_REQUEST);
		}
	}
	
	/**
	 * 向客户端写出JSON数据
	 * @param json
	 * @param response
	 * @throws IOException
	 */
	public void write(JSONObject json, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	public String getRoot(HttpServletRequest request){
		return request.getScheme() + "://" + request.getHeader("host") + request.getContextPath() + "/";
	}
	
	// AJAX输出，返回null
	public String ajax(String content, String type, HttpServletResponse response) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text, HttpServletResponse response) {
		return ajax(text, "text/plain", response);
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html, HttpServletResponse response) {
		return ajax(html, "text/html", response);
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml, HttpServletResponse response) {
		return ajax(xml, "text/xml", response);
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString, HttpServletResponse response) {
		return ajax(jsonString, "text/html", response);
	}
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap, HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message, HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(CODE, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxSuccess(Object message, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(CODE, SUCCESS);
		jsonMap.put(DATA, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "application/json", response);
	}
	
	// 输出JSON错误消息，返回null
	public String ajaxError(Object message, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(CODE, ERROR);
		jsonMap.put(DATA, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "application/json", response);
	}
}