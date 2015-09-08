package com.hydom.extra.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.extra.ebean.ShortMessage;
import com.hydom.extra.ebean.ShortMessageRecode;
import com.hydom.util.HttpSender;

@Service
public class ShortMessageServiceBean extends DAOSupport<ShortMessage> implements
		ShortMessageService {

	@Resource
	private ShortMessageRecordService shortMessageRecordService;

	@Override
	public boolean sendCode(String phone, String code, String content) {
		// String path =
		// "http://222.76.210.200:9999/sms.aspx?action=send&userid=402&account=jyhh&
		// password=123456&mobile=13882162641&content=111111";

		String path = "http://222.76.210.200:9999/sms.aspx";// 地址
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "send");
		params.put("userid", "402");
		params.put("account", "jyhh");
		params.put("password", "zxyx4000336603");
		params.put("mobile", phone);// 接受人电话
		params.put("content", content);// 短信内容
		boolean sendResult = false;
		Date sendTime = new Date();
		try {
			String result = HttpSender.sendGetRequest(path, params, "UTF-8");
			if (result.toString().contains("<returnstatus>Success</returnstatus>")) {
				sendResult = true;
			} else {
				sendResult = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendResult = false;
		}
		/** *************保存短信发送记录START************* */
		ShortMessageRecode smr = new ShortMessageRecode();
		smr.setContent(content);
		smr.setCode(code);
		smr.setPhone(phone);
		smr.setSendTime(sendTime);
		smr.setResult(sendResult);
		shortMessageRecordService.save(smr);
		/** *************保存短信发送记录END*************** */
		if (sendResult) {
			ShortMessage sm = this.find(phone);
			if (sm == null) {// 插入新记录
				sm = new ShortMessage();
				sm.setPhone(phone);
				sm.setContent(content);
				sm.setCode(code);
				sm.setSendTime(sendTime);
				this.save(sm);
			} else {// 更新发送内容与发送时间
				sm.setContent(code);
				sm.setCode(code);
				sm.setSendTime(sendTime);
				this.update(sm);
			}
		}
		return sendResult;
	}

	@Override
	public String findCode(String phone) {
		try {
			ShortMessage shotMessage = this.find(phone);
			if (System.currentTimeMillis() - shotMessage.getSendTime().getTime() > 3 * 60 * 1000) {// 超时
				return "CODETIMEOUT";
			}
			return shotMessage.getCode();
		} catch (Exception e) {
			return null;
		}
	}

}
