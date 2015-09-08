package com.hydom.account.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.MemberRank;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.MemberRankService;
import com.hydom.account.service.MemberService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/memberRank")
@Controller
public class MemberRankAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 7;
	
	@Resource
	private MemberRankService memberRankService;
	
	@Resource
	private MemberService memberService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model) {
		
		PageView<MemberRank> pageView = new PageView<MemberRank>(null,page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "desc");
		
		StringBuffer jpql = new StringBuffer("o.visible = ?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		
		//查询条件
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append(" and o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		pageView.setJpql(jpql.toString());
		pageView.setParams(params.toArray());
		pageView.setOrderby(orderby);
		pageView = memberRankService.getPage(pageView);
	
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		//mav.addAllObjects(model);
		return basePath+"/member_rank_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		
		model.addAttribute("m", mark);
	
		return  basePath+"/member_rank_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		
		MemberRank memberRank = memberRankService.find(id);
		model.addAttribute("entity", memberRank);
		
		model.addAttribute("m", mark);
		
		return  basePath+"/member_rank_add";
	}
	
	@RequestMapping("/save")
	public String save(ModelMap model,MemberRank entity){
		if(StringUtils.isNotEmpty(entity.getId())){
			MemberRank memberRank = memberRankService.find(entity.getId());
			memberRank.setAmount(entity.getAmount());
			memberRank.setOrder(entity.getOrder());
			memberRank.setScale(entity.getScale());
			memberRank.setName(entity.getName());
			memberRankService.update(memberRank);
		}else{
			memberRankService.save(entity);
		}
		return  "redirect:list";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(ModelMap model,@RequestParam(required=false) String[] ids) {
		
		List<MemberRank> memberRanks = memberRankService.getList(ids);

		//查找是否已被使用
		for(MemberRank memberRank:memberRanks){
			if(memberRank.getMemberSet().size()>0){
				return ajaxError("其中含有已使用的会员等级", response);
			}
		}
		
		for(MemberRank memberRank:memberRanks){
			memberRank.setVisible(false);
			memberRankService.update(memberRank);
		}
		
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String name) {
		
		MemberRank memberRank = memberRankService.getEntityByName(name);
		if(memberRank != null){
			return ajaxError("该名称已存在", response);
		}
		return ajaxSuccess("", response);
	}
	
	
}
