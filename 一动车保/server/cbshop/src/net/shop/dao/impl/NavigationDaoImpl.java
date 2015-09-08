/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.shop.dao.NavigationDao;
import net.shop.entity.Navigation;
import net.shop.entity.Navigation.Position;

import org.springframework.stereotype.Repository;

/**
 * Dao - 导航
 * 
 * 
 * 
 */
@Repository("navigationDaoImpl")
public class NavigationDaoImpl extends BaseDaoImpl<Navigation, Long> implements NavigationDao {

	@Override
	public List<Navigation> findList(Position position) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Navigation> criteriaQuery = criteriaBuilder.createQuery(Navigation.class);
		Root<Navigation> root = criteriaQuery.from(Navigation.class);
		criteriaQuery.select(root);
		if (position != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("position"), position));
		}
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("order")));
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

}