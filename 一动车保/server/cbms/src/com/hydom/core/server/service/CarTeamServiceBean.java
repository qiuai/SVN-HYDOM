package com.hydom.core.server.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Area;
import com.hydom.account.service.AreaService;
import com.hydom.core.server.ebean.CarTeam;
import com.hydom.util.dao.DAOSupport;
import com.hydom.util.dao.PageView;

/**
 * @Description 车队业务层实现
 * @author WY
 * @date 2015年7月7日 下午5:39:59
 */

@Service
public class CarTeamServiceBean extends DAOSupport<CarTeam> implements CarTeamService{

	@Resource
	private AreaService areaService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageView<CarTeam> getPage(Area area, Integer page) {
		
		PageView<CarTeam> pageView = new PageView<CarTeam>(null,page);
		
		String baseSql = "from CarTeam o ";
		
		if(area != null){//( or a is null) 
			baseSql += "left join o.area a where a in (:areas) and o.visible = :visible";
		}else{
			baseSql += "where o.visible = :visible";
		}
		
		//String sql = "select o "+baseSql +" order by o.createDate desc";
		
		String sql = "select c from CarTeam c where c.id in (select o.id "+baseSql +" group by o.id) order by c.createDate desc";
		
		
		Query query = em.createQuery(sql);
		
		query.setFirstResult(pageView.getFirstResult());
		query.setMaxResults(pageView.getMaxresult());
		query.setParameter("visible", true);
		if(area != null){
			List<Area> areas = areaService.getAreaByParentId(area);
			query.setParameter("areas", areas);
		}
		
		List<CarTeam> list = query.getResultList();
		pageView.setRecords(list);
		
		String countSql = "select count(c.id) from CarTeam c where c.id in (select o.id "+baseSql +" group by o.id)";
		Query countQuery = em.createQuery(countSql);
		
		if(area != null){
			List<Area> areas = areaService.getAreaByParentId(area);
			countQuery.setParameter("areas", areas);
		}
		countQuery.setParameter("visible", true);
		
		pageView.setTotalrecord((Long) countQuery.getSingleResult());
		return pageView;
	}
}
