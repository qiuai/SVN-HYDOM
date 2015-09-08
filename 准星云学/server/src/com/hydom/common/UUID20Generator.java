package com.hydom.common;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

/**
 * 自定义主键生成器.
 * <pre>根据UUID去掉“-”，取前面20位作为id</pre>
 * @author Holen
 *
 */
public class UUID20Generator implements IdentifierGenerator, Configurable {

	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString().replaceAll("-", "").substring(0, 20).toUpperCase();
		return id;
	}

	@Override
	public void configure(Type arg0, Properties arg1, Dialect arg2)
			throws MappingException {
		
	}
}
