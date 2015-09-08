package com.hydom.util.cache;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 工具类 - 反射
 */

public class ReflectionUtil {

	/**
	 * 读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 * 
	 * @return
	 */
	public static Object getFieldValue(final Object object,
			final String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 * 
	 * @return1...
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		try {
			if (field.getGenericType().equals(java.lang.Boolean.class)
					&& value instanceof Integer) {
				if ((Integer) value == 0)
					field.set(object, false);
				else if ((Integer) value == 1)
					field.set(object, true);
			} else if (field.getGenericType().equals(boolean.class)) {
				field.set(object, Boolean.valueOf(object.toString()));
			} else {
				field.set(object, value);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 调用对象方法,无视private/protected修饰符.
	 * 
	 * @return
	 */
	public static Object invokeMethod(final Object object,
			final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) throws InvocationTargetException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + object + "]");
		}
		method.setAccessible(true);
		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @return DeclaredField
	 */
	protected static Field getDeclaredField(final Object object,
			final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @return DeclaredField
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredMethod.
	 * 
	 * @return DeclaredMethod
	 */
	protected static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获得Class定义中声明的父类的泛型参数的类型.
	 * 
	 * @return 泛型参数的类型
	 */
	@SuppressWarnings({ "unchecked"})
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 获得定义Class时声明的父类的泛型参数的类型.
	 * 
	 * @return 泛型参数的类型
	 */

	@SuppressWarnings({"unchecked" })
	public static Class getSuperClassGenricType(final Class clazz,
			final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

}