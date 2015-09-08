package com.hydom.account.action;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Technician;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.TechnicianService;
import com.hydom.util.BaseAction;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.dao.PageView;
@Controller
@RequestMapping("/manage/technician")
public class TechnicianAction extends BaseAction{
@Resource
private TechnicianService technicianService;
@Resource
private OrderService orderService;
@Autowired
private HttpServletRequest request;
@Autowired
private HttpServletResponse response;

private int maxresult = 10;

private final static int mark = 10;

/**
 * 列表
 */
@RequestMapping("/list")
public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
	PageView<Technician> pageView = new PageView<Technician>(maxresult, page);
	LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	orderby.put("name", "desc");
	String jpql = "o.visible = 1";
	Object[] params = new Object[]{};
	if(queryContent!=null){
		jpql+=" and (o.name like ?1 or o.account like ?2 or o.phonenumber like ?3)";
		params = new Object[]{"%"+queryContent+"%",queryContent+"%",queryContent+"%"};
	}
	pageView.setQueryResult(technicianService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
	request.setAttribute("pageView", pageView);
	ModelAndView mav = new ModelAndView("/account/technician_list");
	mav.addObject("paveView", pageView);
	mav.addObject("queryContent", queryContent);
	mav.addObject("m", mark);
	return mav;
}

/**
 * 添加
 */
@RequestMapping("/add")
public ModelAndView addUI(){
	ModelAndView mav = new ModelAndView("/account/technician_add");
	mav.addObject("m", mark);
	return mav;
}

/**
 * 编辑
 */
@RequestMapping("/edit")
public ModelAndView editUI(@RequestParam String id){
	Technician technician = technicianService.find(id);
	ModelAndView mav = new ModelAndView("/account/technician_edit");
	mav.addObject("technician", technician);
	mav.addObject("m", mark);
	return mav;
	
}

/**
 * 更新
 */
@RequestMapping("/update")
public ModelAndView edit(@ModelAttribute Technician technician) {
	Technician entity = technicianService.find(technician.getId());
	entity.setAccount(technician.getAccount());
	entity.setImgPath(technician.getImgPath());
	entity.setLevel(technician.getLevel());
	entity.setName(technician.getName());
	entity.setPhonenumber(technician.getPhonenumber());
	entity.setModifyDate(new Date());
	technicianService.update(entity);
	ModelAndView mav = new ModelAndView("redirect:list");
	return mav;
}

/**
 * 删除
 */
@RequestMapping("/delete")
@ResponseBody
public String delete(String[] ids){
	
	for(String id : ids){
		Technician entity = technicianService.find(id);
		String deleteExtraName = DateTimeHelper.formatDateTimetoString(
				new Date(), "yyyyMMddHHmmss-");
		entity.setAccount(entity.getAccount() + "-DEL-" + deleteExtraName);
		entity.setJobstatus(false);
		entity.setPushId("0");
		entity.setPhonenumber(entity.getPhonenumber() + "-DEL-" + deleteExtraName);
		entity.setVisible(false);
		if(entity.getOrder()!=null){
			entity.getOrder().setTechMember(null);
			entity.getOrder().setStatus(1);
		}
		entity.setOrder(null);
		technicianService.update(entity);
	}
	return ajaxSuccess("成功", response);
}

/**
 * 保存
 */
@RequestMapping("/save")
public ModelAndView save(@ModelAttribute Technician technician) {
	technicianService.save(technician);
	ModelAndView mav = new ModelAndView("redirect:list");
	mav.addObject("m", mark);
	return mav;
}

/**
 * 验证帐号
 */
@RequestMapping("/checkAccount")
public @ResponseBody
boolean check(@RequestParam String account) {
	return technicianService.isExist(account);
}
/**
 * 验证手机
 */
@RequestMapping("/checkPhoneNumber")
public @ResponseBody
boolean checkPhoneNumber(@RequestParam String phoneNumber){
	return technicianService.isExistPhoneNumber(phoneNumber);
	}

/**
 * 重置初始密码
 */
@RequestMapping("/restPassWord")
public ModelAndView restPassWord(@RequestParam String id){
	Technician technician = technicianService.find(id);
	technician.setPassword("123456");
	technicianService.update(technician);
	ModelAndView mav = new ModelAndView("/account/technician_edit");
	/*mav.addObject("technician", technician);*/
	mav.addObject("m", mark);
	return mav;
	
}

}
