package com.hydom.core.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.WorkLog;
import com.hydom.account.service.WorkLogService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 考勤记录控制层
 * @author WY
 * @date 2015年8月15日 下午6:30:05
 */

@RequestMapping("/manage/workLog")
@Controller
public class WorkLogAction extends BaseAction{
	
	@Resource
	private WorkLogService workLogService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private int maxresult = 10;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent, Date opDate, Date edDate) {
		PageView<WorkLog> pageView = new PageView<WorkLog>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("1=1");
		
		List<Object> params = new ArrayList<Object>();
		if(queryContent!=null){
			jpql.append(" and o.technician.phonenumber like ?"+(params.size()+1)+" or o.technician.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		if(opDate!=null){
			jpql.append(" and o.createDate >= ?"+(params.size()+1));
			params.add(opDate);
		}
		if(edDate!=null){
			jpql.append(" and o.createDate <= ?"+(params.size()+1));
			params.add(edDate);
		}
		
		pageView.setQueryResult(workLogService.getScrollData(pageView.getFirstResult(), maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/workLog/workLog_list");
		mav.addObject("paveView", pageView);
		mav.addObject("queryContent", queryContent);
		mav.addObject("opDate", opDate);
		mav.addObject("edDate", edDate);
		mav.addObject("m", 10);
		return mav;
	}
}
