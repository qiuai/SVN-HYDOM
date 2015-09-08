package com.hydom.api.service;

import com.hydom.api.ebean.ShortMessage;
import com.hydom.util.dao.DAO;

public interface ShortMessageService extends DAO<ShortMessage> {

	/**
	 * 通过手机号验证码获取短信码
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @param type
	 *            短信类型
	 * @return
	 */
	public ShortMessage findByPhoneAndCode(String phone, String code, int type);

	/**
	 * 通过手机号获取用户最近发送短信记录
	 * 
	 * @param phone
	 *            手机号
	 * @param type
	 *            短信类型
	 * @return
	 */
	public ShortMessage lastSMS(String phone, int type);

}
