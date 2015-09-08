package com.hydom.zxy.api;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.common.AbstractController;
import com.hydom.zxy.entity.Version;
import com.hydom.zxy.service.VersionService;

/**
 * 版本控制Api
 * @author Holen
 * @version 1.0.0 2015.3.11 新建
 */
@Controller()
@RequestMapping("/api/version")
public class VersionApi extends AbstractController {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6238799705946000807L;

	@Resource
	private VersionService versionService;
	
	/**
	 * 检测版本更新.
	 * @param type - 类型
	 * @param code - 当前编号
	 * @return version 新版本
	 */
	@RequestMapping("/checkUpdate")
	public @ResponseBody Version checkUpdate(@RequestParam(defaultValue = "0") int type, @RequestParam(defaultValue = "0") int code) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Version.class);
		criteria.add(Restrictions.eq("devicetype", type));
		criteria.add(Restrictions.gt("code", code));
		
		List<Version> list = versionService.getList(criteria, 1);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
}
