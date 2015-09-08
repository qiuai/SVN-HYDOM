/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shop.Filter;
import net.shop.Order;
import net.shop.dao.TagDao;
import net.shop.entity.Tag;
import net.shop.entity.Tag.Type;
import net.shop.service.TagService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 标签
 * 
 * 
 * 
 */
@Service("tagServiceImpl")
public class TagServiceImpl extends BaseServiceImpl<Tag, Long> implements TagService {

	@Resource(name = "tagDaoImpl")
	private TagDao tagDao;

	@Resource(name = "tagDaoImpl")
	public void setBaseDao(TagDao tagDao) {
		super.setBaseDao(tagDao);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tag> findList(Type type) {
		return tagDao.findList(type);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable("tag")
	public List<Tag> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion) {
		return tagDao.findList(null, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "tag", allEntries = true)
	public void save(Tag tag) {
		super.save(tag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "tag", allEntries = true)
	public Tag update(Tag tag) {
		return super.update(tag);
	}

	@Override
	@Transactional
	@CacheEvict(value = "tag", allEntries = true)
	public Tag update(Tag tag, String... ignoreProperties) {
		return super.update(tag, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "tag", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "tag", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "tag", allEntries = true)
	public void delete(Tag tag) {
		super.delete(tag);
	}

}