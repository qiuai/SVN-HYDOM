package com.hydom.vt.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.common.AbstractController;
import com.hydom.common.bean.Pager;
import com.hydom.common.service.UserService;
import com.hydom.vt.entity.User;
import com.hydom.vt.util.Constant;

/**
 * 用户处理Handler
 * 
 * @author rl
 * @version 1.0.0 2015.6.4 新建
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1718786787833928397L;
	@Resource
	private UserService userService;

	// 用户列表
	@RequestMapping("/list.do")
	public String list(@RequestParam(defaultValue = "true") Boolean isteacher,
			Pager pager) {
		User u=(User) getSession().getAttribute(
				Constant.SESSION_USER);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("isteacher", isteacher));
		detachedCriteria.add(Restrictions.eq("status", 0));
		detachedCriteria.add(Restrictions.ne("id", u.getId()));
		if(pager==null){
			pager=new Pager();
		}
		String keyword = pager.getKeyword();
		if (StringUtils.isNotEmpty(keyword)) {
			// 模糊查询条件
			SimpleExpression[] pathSEs = new SimpleExpression[7];
			pathSEs[0] = Restrictions.like("nickname", "%" + keyword + "%");
			pathSEs[1] = Restrictions.like("username", "%" + keyword + "%");
			pathSEs[2] = Restrictions.like("school", "%" + keyword + "%");
			pathSEs[3] = Restrictions.like("city", "%" + keyword + "%");
			pathSEs[4] = Restrictions.like("tel", "%" + keyword + "%");
			pathSEs[5] = Restrictions.like("email", "%" + keyword + "%");
			pathSEs[6] = Restrictions.like("address", "%" + keyword + "%");
			detachedCriteria.add(Restrictions.or(pathSEs));
		}
		userService.findByPager(pager, detachedCriteria);
	//	getRequest().setAttribute("pager", pager);
		getRequest().setAttribute("isteacher", isteacher);
		return "user_list";
	}

	// 用户详情
	@RequestMapping("/view.do")
	public String view(String id) {
		User u = userService.load(id);
		getRequest().setAttribute("user", u);
		return "user_view";
	}

	// 删除
	@ResponseBody
	@RequestMapping("/del.do")
	public void delete(String id) {
		try {
			User u = userService.load(id);
			u.setStatus(1);
			userService.update(u);
		} catch (Exception e) {
			ajaxJsonSuccessMessage("网络异常,操作失败");
		}
		ajaxJsonSuccessMessage("操作成功");
	}
	
}
