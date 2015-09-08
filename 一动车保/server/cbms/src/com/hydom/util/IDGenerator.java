package com.hydom.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

public class IDGenerator implements IdentifierGenerator, Configurable {

	/**
	 * 返回标准的UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 返回时间戳相关的ID <br>
	 * 示例：20150506-210335-ABCDUXYZ[8位随机串]
	 * 
	 * @return
	 */
	public static String timeId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss-");
		Date date = new Date();
		String dateStr = sdf.format(date);
		String id = dateStr + getRandomString(8,0);
		return id;

	}

	/**
	 * 获取指定长度的随机串
	 * 
	 * @param length
	 * @param model  model=0 数字和字母随机 ； model=1 纯数字随机；model=2纯字母随机
	 * @return
	 */
	public static String getRandomString(int length,int model) {
		String base = "ABCDEFGHIJKLMNOPQURSTUVWXYZ0123456789";
		if(model==1){
			base = "0123456789";
		}else if(model==2){
			base = "ABCDEFGHIJKLMNOPQURSTUVWXYZ";
		}
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	


	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1)
			throws HibernateException {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	@Override
	public void configure(Type arg0, Properties arg1, Dialect arg2)
			throws MappingException {
		// TODO Auto-generated method stub
	}
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
	}
}
