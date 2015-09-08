package com.hydom.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydom.account.ebean.Order;
import com.hydom.account.service.OrderService;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtil {
	protected static final Logger LOG = LoggerFactory.getLogger(PushUtil.class);

	// demo App defined in resources/jpush-api.conf
	private static final String appKey = "1f6c0b912668e41d51f732b7";
	private static final String masterSecret = "000068f6abdaa7bda25f3919";

	public static final String TITLE = "Test from API example";
	public static final String ALERT = "Test from API Example - alert";
	public static final String MSG_CONTENT = "Test from API Example - msgContent";
	public static final String REGISTRATION_ID = "0900e8d85ef";
	public static final String TAG = "tag_api";

	public static void main(String[] args) throws JsonProcessingException {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		dataMap.put("orderId", "10000");
		dataMap.put("orderNum", "num121");
		dataMap.put("contact", "10000");
		dataMap.put("phone", "10000");
		dataMap.put("car", "10000");
		dataMap.put("carNum", "10000");
		dataMap.put("carColor", "10000");
		String json = mapper.writeValueAsString(dataMap);

		push("test标题", "中文内容..test...", 0, dataMap, "020557e87ac");
	}
	
	
	public static Boolean pushTechnician(String orderId){
		OrderService orderService = (OrderService) SpringUtil.getBean("orderService");
		boolean bindResult = orderService.bindTechnician(orderId); // 分配技师
		if (bindResult) {// 执行推送
			Order order = orderService.find(orderId);
			Map<String, String> dataMap = new LinkedHashMap<String, String>();
			dataMap.put("orderId", order.getId());
			dataMap.put("orderNum", order.getNum());
			dataMap.put("contact", order.getContact());
			dataMap.put("phone", order.getPhone());
			dataMap.put("car", order.getCar().getName());
			dataMap.put("carNum", order.getCarNum());
			dataMap.put("carColor", order.getCarColor());
			dataMap.put("cleanType", order.getCleanType() + "");
			dataMap.put("mlng", order.getLng() + "");
			dataMap.put("mlat", order.getLat() + "");
			try {// 保留时长根据 “几分钟用户不响应重新分配订单”来设定
				PushUtil.push("一动车保", "您有一个新的订单，请查收.", 86400, dataMap,
						order.getTechMember().getPushId());
				System.out.println("推送成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bindResult;
	}
	
	
	
	
	/**
	 * 推送
	 * 
	 * @param title
	 *            ：标题
	 * @param content
	 *            ：内容
	 * @param timeToLive
	 *            ：保留时长
	 * @param registrationId
	 *            ：用户设备ID
	 */
	public static void push(String title, String content, long timeToLive,
			Map<String, String> dataMap, String... registrationId) {
		// HttpProxy proxy = new HttpProxy("localhost", 3128);
		// Can use this https proxy: https://github.com/Exa-Networks/exaproxy
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPayload(title, content, timeToLive, dataMap,
				registrationId);
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	public static PushPayload buildPayload(String title, String content,
			long timeToLive, Map<String, String> dataMap,
			String... registrationId) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.registrationId(registrationId))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(content)
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.addExtras(dataMap)
												.setTitle(title).build())
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setBadge(1).build()).build())
				.setOptions(
						Options.newBuilder().setTimeToLive(timeToLive).build())
				.build();
	}

	public static void testSendPush() {
		// HttpProxy proxy = new HttpProxy("localhost", 3128);
		// Can use this https proxy: https://github.com/Exa-Networks/exaproxy
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_all_all_alert();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	public static PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(ALERT);
	}

	public static PushPayload buildPushObject_all_alias_alert() {
		return PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(Audience.alias("alias1"))
				.setNotification(Notification.alert(ALERT)).build();
	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.tag("tag1"))
				.setNotification(Notification.android(ALERT, TITLE, null))
				.build();
	}

	public static PushPayload buildPushObject_android_and_ios() {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.tag("tag1"))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert("alert content")
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.setTitle("Android Title")
												.build())
								.addPlatformNotification(
										IosNotification
												.newBuilder()
												.incrBadge(1)
												.addExtra("extra_key",
														"extra_value").build())
								.build()).build();
	}

	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.tag_and("tag1", "tag_all"))
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setAlert(ALERT).setBadge(5)
												.setSound("happy")
												.addExtra("from", "JPush")
												.build()).build())
				.setMessage(Message.content(MSG_CONTENT))
				.setOptions(
						Options.newBuilder().setApnsProduction(true).build())
				.build();
	}

	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(
										AudienceTarget.tag("tag1", "tag2"))
								.addAudienceTarget(
										AudienceTarget
												.alias("alias1", "alias2"))
								.build())
				.setMessage(
						Message.newBuilder().setMsgContent(MSG_CONTENT)
								.addExtra("from", "JPush").build()).build();
	}

}
