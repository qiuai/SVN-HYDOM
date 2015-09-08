package com.hydom.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Area;
import com.hydom.util.dao.DAOSupport;

@Service
public class AreaServiceBean extends DAOSupport<Area> implements AreaService {

	public String fullAreaName(String aid) {
		Area area = this.find(aid);
		Area parentArea = area.getParent();
		StringBuffer fullname = new StringBuffer(area.getName());
		while (parentArea != null) {
			fullname.insert(0, parentArea.getName());
			parentArea = parentArea.getParent();
		}
		return fullname.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getRootArea() {
		String hql = "select o from Area o where o.visible=:visible and o.parent is null";
		Query query = em.createQuery(hql);
		query.setParameter("visible", true);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Area getEntitybyNameAndPrantId(String parentId, String content) {
		String hql = "select o from Area o where o.visible=:visible and o.name = '"
				+ content + "' ";
		if (StringUtils.isEmpty(parentId)) {
			hql += "and o.parent is null";
		} else {
			hql += "and o.parent = '" + parentId + "'";
		}
		try {
			Query query = em.createQuery(hql);
			query.setParameter("visible", true);

			List<Area> areas = query.getResultList();
			if (areas.size() > 0) {
				return areas.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Area> getAreaByParentId(Area area) {

		List<Area> list = getSubArea(area, new ArrayList<Area>());

		return list;
	}

	public List<Area> getSubArea(Area area, List<Area> result) {
		if (area.getChildren().size() > 0) {
			for (Area sbArea : area.getChildren()) {
				getSubArea(sbArea, result);
			}
		}
		result.add(area);
		return result;
	}

}
