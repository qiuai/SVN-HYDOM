package com.hydom.account.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.naming.CommunicationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberRank;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.MemberRankService;
import com.hydom.account.service.MemberService;
import com.hydom.api.service.TokenService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonUtil;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.dao.PageView;
import com.hydom.util.dao.QueryResult;

@RequestMapping("/manage/member")
@Controller
public class MemberAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 7;
	
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private MemberService memberService;
	@Resource
	private AreaService areaService;
	@Resource
	private TokenService tokenService;
	
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model) {
		
		PageView<Member> pageView = new PageView<Member>(null,page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		
		StringBuffer jpql = new StringBuffer("o.visible = ?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		//查询条件
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append(" and (o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
			
			jpql.append(" or o.email like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
			
			jpql.append(" or o.mobile like ?"+(params.size()+1)+")");
			params.add("%"+queryContent+"%");
			
			//jpql.append(" or o.area.name like ?"+(params.size()+1)+")");
			//params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		pageView.setJpql(jpql.toString());
		pageView.setParams(params.toArray());
		pageView.setOrderby(orderby);
		pageView = memberService.getPage(pageView);
	
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		//mav.addAllObjects(model);
		return basePath+"/member_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		//会员等级
		StringBuffer jpql = new StringBuffer("o.visible = ?1");
		Object[] params = {true};
		List<MemberRank> memberRanks = memberRankService.getList(jpql.toString(), params, orderby);
		model.addAttribute("memberRanks", memberRanks);

		//顶级地区
		jpql = new StringBuffer("o.visible=?1 and o.parent is null");
		List<Area> areas = areaService.getList(jpql.toString(), params, orderby);
		model.addAttribute("areas", areas);
		
		
		model.addAttribute("m", mark);
		
		return  basePath+"/member_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		
		Member member = memberService.find(id);
		model.addAttribute("entity", member);
		
		model.addAttribute("m", mark);
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		//会员等级
		StringBuffer jpql = new StringBuffer("o.visible = ?1");
		Object[] params = {true};
		List<MemberRank> memberRanks = memberRankService.getList(jpql.toString(), params, orderby);
		model.addAttribute("memberRanks", memberRanks);
		
		return  basePath+"/member_edit";
	}
	
	@RequestMapping("/save")
	public String save(ModelMap model,Member entity){
		if(StringUtils.isNotEmpty(entity.getId())){
			
		}else{
			if(StringUtils.isEmpty(entity.getMemberRank().getId())){
				entity.setMemberRank(null);
			}
			if(StringUtils.isEmpty(entity.getArea().getId())){
				entity.setArea(null);
			}
			memberService.save(entity);
		}
		return  "redirect:list";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(Member entity,String adjustAmount,String adjustMoney,String adjustRemark){
		try{
			Member member = memberService.find(entity.getId());
			
			if(StringUtils.isNotEmpty(entity.getPassword())){
				member.setPassword(entity.getPassword());
			}
			
			if(StringUtils.isNotEmpty(entity.getMemberRank().getId())){
				member.setMemberRank(entity.getMemberRank());
			}else{
				member.setMemberRank(null);
			}
			
			member.setStatus(entity.getStatus());
			
			Float amount = member.getAmount();//积分
			Float adAmount = CommonUtil.add(amount+"",adjustAmount);
			
			Float adMoney = CommonUtil.add(member.getMoney()+"",adjustMoney);
			
			member.setAmount(adAmount);
			member.setMoney(adMoney);
			memberService.update(member);
			
			if(member.getStatus() == 0){
				tokenService.deletAllTokenByUID(member.getId());
			}
			
			return ajaxSuccess("成功", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("失败",	response);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			Member member = memberService.find(id);
			member.setMobile(member.getMobile() + "-" + CommonUtil.getOrderNum());
			member.setVisible(false);
			memberService.update(member);
		}
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String mobile) {
		
		Member member = memberService.findByMobile(mobile);
		if(member != null){
			return ajaxError("该手机号已被注册", response);
		}
		return ajaxSuccess("", response);
	}
	
	@RequestMapping("/deleteView")
	@ResponseBody
	public String deleteView(){
		List<Member> memberList = memberService.getListByHql("select o from Member o where o.visible = false");
		for(Member member : memberList){
			member.setMobile(member.getMobile() + "-" + CommonUtil.getOrderNum());
			memberService.update(member);
		}
		return ajaxSuccess("成功", response);
	}
}
