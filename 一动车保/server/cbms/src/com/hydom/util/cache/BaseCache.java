package com.hydom.util.cache;

import java.io.Serializable;

public interface BaseCache extends Serializable {

	/**
	 * 取得实体缓存Key.
	 * @return key of cache
	 */
	Serializable getCacheKey();
}
