package com.hydom.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushServer {
	protected static final Logger LOG = LoggerFactory
			.getLogger(PushServer.class);

	private static final String appKey = "9992d6c47f765cc04ec17d0c";
	// 客户提供， 原： "bb89d6c7c074f00e300152d3";
	private static final String masterSecret = "0c2af410f0d5bc831da04698";
	// 客户提供， 原："20d0a38bce9d7e10737e499d";

	public static final String TITLE = "Test from API example";
	public static final String ALERT = "Test from API Example - alert";
	public static final String MSG_CONTENT = "Test from API Example - msgContent";
	public static final String REGISTRATION_ID = "0900e8d85ef";
	public static final String TAG = "tag_api";

	public static void main(String[] args) {
		sendPush("test title", "test content", 1000l);
	}

	public static void sendPush(String title, String content, long timeToLive) {
		// HttpProxy proxy = new HttpProxy("localhost", 3128);
		// Can use this https proxy: https://github.com/Exa-Networks/exaproxy
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPayload(title, content, timeToLive);
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
			long timeToLive) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.all())
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(content)
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.setTitle(title).build())
								.addPlatformNotification(
										IosNotification.newBuilder()
												.setBadge(1).build()).build())
				.setOptions(
						Options.newBuilder().setTimeToLive(timeToLive).build())
				.build();
	}
}
