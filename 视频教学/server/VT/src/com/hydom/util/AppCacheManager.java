package com.hydom.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hydom.vt.entity.BaseEntity;

/**
 * 工具类 - 缓存管理. <br>
 * 处理缓存的添加、更新级删除.
 * 
 * @author Holen
 * @version 1.0.0 2013-1-5 新建
 */
public class AppCacheManager {

	/** LOG. */
	private final static Log log = LogFactory.getLog(AppCacheManager.class);

	/** 缓存Manager实例. */
	private static CacheManager manager;
	static {
		try {
			manager = CacheManager.getInstance();
			if (null == manager) {
				manager = CacheManager.create();
			}
		} catch (Exception e) {
			log.error("Initialize cache manager faild!", e);
		}
	}

	/**
	 * 从缓存中获取对象.
	 * 
	 * @param cache_name
	 *            缓存名称，如ehcache.xml中 <br>
	 *            产品实体配置的name：com.handou.ehcache.Product
	 * @param key
	 *            一般采用缓存对象的主键（具有唯一性的字符串）
	 * @return 缓存对象
	 */
	public static Serializable getObjectCached(String cache_name,
			Serializable key) {
		Cache cache = getCache(cache_name);
		if (cache != null) {
			try {
				Element elem = cache.get(key);
				if (elem != null && !cache.isExpired(elem))
					return elem.getValue();
			} catch (Exception e) {

				log.error("Getcache(" + cache_name + ") of " + key

				+ "failed.", e);
			}
		}

		return null;
	}

	/**
	 * 从缓存中获取对象.
	 * 
	 * @param cache_name
	 *            缓存名称，如ehcache.xml中 <br>
	 *            产品实体配置的name：com.handou.ehcache.Product
	 * @param key
	 *            一般采用缓存对象的主键（具有唯一性的字符串）
	 * @return 缓存对象
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T getObjectCached(Class<T> clazz,
			Serializable key) {
		return (T) getObjectCached(clazz.getName(), key);
	}

	/**
	 * 把对象放入缓存中
	 * 
	 * @param cache_name
	 *            缓存名称，如ehcache.xml中 <br>
	 *            产品实体配置的name：com.handou.ehcache.Product
	 * @param key
	 *            一般采用缓存对象的主键（具有唯一性的字符串）
	 * @param value
	 *            需要加入缓存的实体对象，如new <br>
	 *            一个的com.handou.ehcache.Product对象
	 */
	public synchronized static void putObjectCached(String cache_name,
			Serializable key, Serializable value) {
		Cache cache = getCache(cache_name);
		if (cache != null) {
			try {
				cache.remove(key);
				Element elem = new Element(key, value);
				cache.put(elem);
			} catch (Exception e) {
				log.error("putcache(" + cache_name + ") of " + key + "failed.",
						e);
			}
		}
	}

	/**
	 * 通过缓存名称获取缓存.
	 * 
	 * @param cacheName
	 *            - 缓存名称
	 * @return cache - 缓存
	 */
	public final static Cache getCache(Object arg) {
		if (arg instanceof BaseEntity) {
			return manager.getCache(arg.getClass().getName());
		} else if (arg instanceof String) {
			return manager.getCache(arg.toString());
		}

		return null;
	}

	/**
	 * 缓存集合里面所有的元素.
	 * 
	 * @param cacheName
	 * @param list
	 * @param fieldName
	 */
	public static void cacheAll(String cacheName, Collection<?> list,
			String fieldName) {
		Cache cache = getCache(cacheName);
		if (cache == null) {
			log.warn("Do not find cache, named by [" + cacheName + "]");
			return;
		}

		for (Object o : list) {
			Object key = ReflectionUtil.getFieldValue(o, fieldName);
			if (null == key) {
				log.warn("Could not find value [" + fieldName + "] in "
						+ o.getClass());
				continue;
			}

			Element element = new Element(key, o);
			cache.put(element);
		}
	}

	/**
	 * 缓存数据库实体对象集合.
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param list
	 *            - 待缓存集合
	 */
	public synchronized static <T extends BaseEntity> void cacheAll(
			String cacheName, Collection<T> list) {
		Cache cache = getCache(cacheName);
		if (cache == null) {
			log.warn("Do not find cache, named by [" + cacheName + "]");
			return;
		}

		Serializable key = null;
		for (T entity : list) {
			try {
				key = entity.getId();
				cache.remove(key);
				cache.put(new Element(key, entity));
			} catch (Exception e) {
				log.error("putcache(" + cacheName + ") of " + key + "failed.",
						e);
			}
		}
	}

	/**
	 * 缓存数据库实体对象集合.
	 * 
	 * @param list
	 *            - 待缓存集合
	 */
	public synchronized static <T extends BaseEntity> void cacheAll(List<T> list) {
		if (null == list || list.size() == 0) {
			return;
		}
		// 取得第一个获取它的类型
		T t = list.get(0);
		String cacheName = null;
		cacheName = t.getClass().getName();
		cacheAll(cacheName, list);
	}

	/**
	 * 缓存实体对象.
	 * 
	 * @param entity
	 *            - 待缓存实体
	 */
	public static <T extends BaseEntity> void putObjectCached(T entity) {
		String cacheName = entity.getClass().getName();
		putObjectCached(cacheName, entity.getId(), entity);
	}

	/**
	 * 获取该实体的所有缓存.
	 * 
	 * @param clazz
	 *            - 缓存的类型
	 * @return 所有该clazz类型的缓存
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseEntity> List<T> getAll(Class<T> clazz) {
		String cacheName = clazz.getName();
		Cache cache = getCache(cacheName);

		Query query = cache.createQuery();
		query.includeValues();
		List<Result> results = query.execute().all();

		List<T> list = new ArrayList<T>();
		for (Result result : results) {
			list.add((T) result.getValue());
		}
		return list;
	}

	/**
	 * 获取该该换成的所有数据.
	 * 
	 * @param cacheName
	 *            - 缓存名称
	 * @return 换成数据
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> List<T> getAll(String cacheName) {
		Cache cache = getCache(cacheName);

		Query query = cache.createQuery();
		query.includeValues();
		List<Result> results = query.execute().all();

		List<T> list = new ArrayList<T>();
		for (Result result : results) {
			list.add((T) result.getValue());
		}
		return list;
	}

	/**
	 * 从缓存中移除一个实体.
	 * 
	 * @param entity
	 *            - 待移除的实体
	 */
	public static void remove(BaseEntity entity) {
		Ehcache cache = getCache(entity.getClass().getName());
		cache.remove(entity.getId());
	}

	public static void main(String[] args) throws SecurityException,
			NoSuchFieldException {
		/*
		 * List<GoodsType> list = new ArrayList<GoodsType>(); Field f =
		 * list.getClass().getDeclaredField("elementData");
		 * System.out.println(f);
		 */

	}
}
