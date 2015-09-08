package com.hydom.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

/**
 * Entity保存更新拦截器,自动填充创建、更新时间.
 * @author Holen
 * @version 1.0.0 2012.05.05 新建
 */
@Component
public class EntityInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 7319416231145791577L;

	private static final String CREATE_DATE = "createDate";// "创建日期"属性名称
	private static final String MODIFY_DATE = "lastupdate";// "修改日期"属性名称
	
	// 保存数据时回调此方法
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		Date createDate = new Date();
		for (int i = 0; i < propertyNames.length; i++) {
			if (CREATE_DATE.equals(propertyNames[i]) || MODIFY_DATE.equals(propertyNames[i])) {
				state[i] = createDate;
			}
		}
		return true;
	}
	
	/**
	 * 实体过滤
	 * @param entity 需要过滤的实体
	 * @param classes 实体集
	 * @return 是否在范围之类 true 即不用过滤，false 即要过滤
	 */
	@SuppressWarnings({ "rawtypes" })
	public boolean entityFilter(Object entity, List<Class> classes) {
		boolean flag = false;
		for(Class<?> c : classes) {
			if(entity.getClass().getName().equals(c.getName())) {
				flag = true;
			}
		}
		return flag;
	}

	// 更新数据时回调此方法
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		Date updateDate = new Date();
		for (int i = 0; i < propertyNames.length; i++) {
			if (MODIFY_DATE.equals(propertyNames[i])) {
				currentState[i] = updateDate;
			}
		}
		return true;
	}

}