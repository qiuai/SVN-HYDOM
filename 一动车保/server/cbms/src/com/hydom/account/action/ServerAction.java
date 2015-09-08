package com.hydom.account.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.News;
import com.hydom.account.ebean.Server;
import com.hydom.account.service.ServerService;
import com.hydom.util.BaseAction;

@RequestMapping("/manage/server")
@Controller
public class ServerAction extends BaseAction {

	@Resource
	private ServerService serverService;
	

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表
	 * 
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, ModelMap model) {

		List<Server> serverList = serverService.getListByHql("FROM Server ORDER BY type ASC");
		ModelAndView mv = new ModelAndView("/server/server_list");
		mv.addObject("m", 12);
		mv.addObject("serverList", serverList);
		return mv;

		// 带分页
		// PageView<Server> pageView = new PageView<Server>(null,page);
		//
		// // StringBuffer jpql = new StringBuffer();
		// // List<Object> params = new ArrayList<Object>();
		// LinkedHashMap<String, String> orderby = new LinkedHashMap<String,
		// String>();
		// orderby.put("createDate", "desc");
		// pageView.setOrderby(orderby);
		// pageView = serverService.getPage(pageView);
		// model.addAttribute("pageView", pageView);
		// ModelAndView mv = new ModelAndView("/server/server_list");
		// mv.addObject("m",12);
		// return mv;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @param response
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids) {
		for (String id : ids) {
			Server entity = serverService.find(id);
			serverService.remove(entity);
		}
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/detail")
	public String detail(String id,ModelMap model){
		Server server=serverService.find(id);
		model.put("server", server);
		return "/server/service";
	}
	@RequestMapping("/service")
	public String service(){
		return "/server/service";
	}
	@RequestMapping("/edit")
	public ModelAndView editUI(String id) {
		Server server = serverService.find(id);
		ModelAndView mav = new ModelAndView("/server/server_add");
		mav.addObject("server", server);
		return mav;
	}
	@RequestMapping("/save")
	public ModelAndView save(Server server) {
		if(StringUtils.isNotEmpty(server.getId())){
			serverService.update(server);
		}else{
			serverService.save(server);
		}
		ModelAndView mav = new ModelAndView("redirect:/manage/server/list");
		return mav;
	}
	
	@RequestMapping("/appdown")
	public String appDownlod(){
		return "/server/appDown";
	}
}
