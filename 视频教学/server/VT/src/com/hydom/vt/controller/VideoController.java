package com.hydom.vt.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.common.AbstractController;
import com.hydom.common.bean.Pager;
import com.hydom.common.service.VideoService;
import com.hydom.vt.entity.Video;

/**
 * 课程处理Handler
 * 
 * @author WY
 * @version 1.0.0 2015.6.5 新建
 */
@Controller
@RequestMapping("/video")
public class VideoController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4875133640231469559L;
	/**
	 * serialVersionUID
	 */
	@Resource
	private VideoService videoService;

	// 用户列表
	@RequestMapping("/list.do")
	public String list(@RequestParam(defaultValue = "true") Boolean isteacher, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Video.class);
		if(pager==null){
			pager=new Pager();
		}
		detachedCriteria.createAlias("course", "course");
		detachedCriteria.createAlias("user", "user");
		String keyword = pager.getKeyword();
		if (StringUtils.isNotEmpty(keyword)) {
			// 模糊查询条件
			SimpleExpression[] pathSEs = new SimpleExpression[3];
			pathSEs[0] = Restrictions.like("name", "%" + keyword + "%");
			pathSEs[1] = Restrictions.like("course.name", "%" + keyword + "%");
			pathSEs[2] = Restrictions.like("user.nickname", "%" + keyword + "%");
			detachedCriteria.add(Restrictions.or(pathSEs));
		}
		videoService.findByPager(pager, detachedCriteria);
		request.setAttribute("keyword", keyword);
		return "video_list";
	}

	// 播放视频
	@RequestMapping("/view.do")
	public String view(String id) {
		Video v = videoService.load(id);
		getRequest().setAttribute("video", v);
		return "video_view";
	}

	// 删除
	@ResponseBody
	@RequestMapping("/del.do")
	public void delete(String id) {
		try {
			Video v = videoService.load(id);
			videoService.delete(v);
		} catch (Exception e) {
			ajaxJsonSuccessMessage("网络异常,操作失败");
		}
		ajaxJsonSuccessMessage("操作成功");
	}
}
