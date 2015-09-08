package com.hydom.vt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.security.krb5.internal.tools.Ktab;

import com.hydom.common.AbstractController;
import com.hydom.common.bean.Pager;
import com.hydom.common.service.CourseService;
import com.hydom.vt.entity.Course;
import com.hydom.vt.entity.User;

/**
 * 课程处理Handler
 * 
 * @author WY
 * @version 1.0.0 2015.6.5 新建
 */
@Controller
@RequestMapping("/course")
public class CourseController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4875133640231469559L;
	/**
	 * serialVersionUID
	 */
	@Resource
	private CourseService courseService;

	// 用户列表
	@RequestMapping("/list.do")
	public String list(@RequestParam(defaultValue = "true") Boolean isteacher, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Course.class);
		detachedCriteria.add(Restrictions.eq("status", 0));
		if(pager==null){
			pager=new Pager();
		}
		detachedCriteria.createAlias("user", "user");
		String keyword = pager.getKeyword();
		if (StringUtils.isNotEmpty(keyword)) {
			// 模糊查询条件
			SimpleExpression[] pathSEs = new SimpleExpression[3];
			pathSEs[0] = Restrictions.like("name", "%" + keyword + "%");
			pathSEs[1] = Restrictions.like("mainContent", "%" + keyword + "%");
			pathSEs[2] = Restrictions.like("user.nickname", "%" + keyword + "%");
			
//			int i=4;
//			Pattern pattern = Pattern.compile("[0-9].?[0-9]?");
//			Matcher matcher = pattern.matcher(keyword);
//			boolean flag= matcher.matches();
//			if(flag){
//				pathSEs[i] = Restrictions.like("money", Float.parseFloat(keyword));
//				i++;
//			}
//			
//			pattern = Pattern.compile("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]");
//			matcher = pattern.matcher(keyword);
//			flag= matcher.matches();
//			if(flag){
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date d = null;
//				try {
//					d = sdf.parse(keyword+" 00:00:00");
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				pathSEs[i] = Restrictions.like("startDate", d);
//				i++;
//			}
			detachedCriteria.add(Restrictions.or(pathSEs));
		}
		courseService.findByPager(pager, detachedCriteria);
		request.setAttribute("keyword", keyword);
		return "course_list";
	}

	// 用户详情
	@RequestMapping("/view.do")
	public String view(String id) {
		Course c = courseService.load(id);
		c.setScore(courseService.getCourseScore(c.getId()));
		getRequest().setAttribute("course", c);
		return "course_view";
	}

	// 删除
	@ResponseBody
	@RequestMapping("/del.do")
	public void delete(String id) {
		try {
			Course c = courseService.load(id);
			c.setStatus(1);
			courseService.update(c);
		} catch (Exception e) {
			ajaxJsonSuccessMessage("网络异常,操作失败");
		}
		ajaxJsonSuccessMessage("操作成功");
	}
}
