package com.carinsurance.utils;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class JPushTools {

	
	final static String MASTER_SRCRET = "2fe2e8e0391baaa961ce3bf4";// md5验证key

	final static String APP_KEY = "f3df77495287d10f247e53b9";// 极光推送申请的appkey
//	/**
//	 * String tag1,String ALERT,String TITLE,Map<String,String> map
//	 * @param tongzhi
//	 */
//	/**
//	 * 	 平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE。
//	 * @param tag1        目标是 tag
//	 * @param ALERT 通知 内容
//	 * @param TITLE 标题
//	 * @param map  自定义数据
//	 */
//	public static void PushAndroid(String tag1,String ALERT,String TITLE,Map<String,String> map)
//	{
//		
//		JPushClient jpushClient=new JPushClient(MASTER_SRCRET, APP_KEY);
//		
//		PushPayload payload=buildPushObject_android_tag_alertWithTitle(tag1, ALERT, TITLE, map);
////		jpushClient
//		 try {
//	            PushResult result = jpushClient.sendPush(payload);
//	            Log.v("aaa","Got result - " + result);
//	            
//	        } catch (APIConnectionException e) {
//	            // Connection error, should retry later
////	            LOG.error("Connection error, should retry later", e);
//	            
//	        } catch (APIRequestException e) {
//	            // Should review the error, and fix the request
//	            Log.v("aaa","Should review the error, and fix the request");
//	            Log.v("aaa","HTTP Status: " + e.getStatus());
//	            Log.v("aaa","Error Code: " + e.getErrorCode());
//	            Log.v("aaa","Error Message: " + e.getErrorMessage());
//	        }
//		
//	}
//	/**
//	 *构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。
//	 * @return
//	 */
//	public static PushPayload buildPushObject_all_alias_alert() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias("alias1"))
//                .setNotification(Notification.alert(ALERT))
//                .build();
//    }
// /**
//  * 	 构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE。
//  * @param tag1 目标是tag
//  * @param ALERT   
//  * @param TITLE 标题
//  * @return
//  */
//	 public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag1,String ALERT,String TITLE,Map<String,String> map) {
//		 return PushPayload.newBuilder()
//	                .setPlatform(Platform.android())
//	                .setAudience(Audience.tag(tag1))
//	                .setNotification(Notification.android(ALERT, TITLE, map))
//	                .build();
//	    }
//	/**
//	 *  构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的并集，推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
//	 * @return
//	 */
//	   public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
//	        return PushPayload.newBuilder()
//	                .setPlatform(Platform.ios())
//	                .setAudience(Audience.tag_and("tag1", "tag_all"))
//	                .setNotification(Notification.newBuilder()
//	                        .addPlatformNotification(IosNotification.newBuilder()
//	                                .setAlert(ALERT)
//	                                .setBadge(5)
//	                                .setSound("happy")
//	                                .addExtra("from", "JPush")
//	                                .build())
//	                        .build())
//	                 .setMessage(Message.content(MSG_CONTENT))
//	                 .setOptions(Options.newBuilder()
//	                         .setApnsProduction(true)
//	                         .build())
//	                 .build();
//	    }
	 
	 
//	 /**
//	  * * 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的交集）并（"alias1" 与 "alias2" 的交集），推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
//	  * @return
//	  */
//	   public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//	        return PushPayload.newBuilder()
//	                .setPlatform(Platform.android_ios())
//	                .setAudience(Audience.newBuilder()
//	                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
//	                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
//	                        .build())
//	                .setMessage(Message.newBuilder()
//	                        .setMsgContent(MSG_CONTENT)
//	                        .addExtra("from", "JPush")
//	                        .build())
//	                .build();
//	    }
//	   /**
//	    * 获取统计样列
//	    */
//	   public void String tongji()
//	   {
//		   JPushClient jpushClient = new JPushClient(masterSecret, appKey);
//			try {
//		            ReceivedsResult result = jpushClient.getReportReceiveds("1942377665");
//		            LOG.debug("Got result - " + result);
//		            
//		        } catch (APIConnectionException e) {
//		            // Connection error, should retry later
//		            LOG.error("Connection error, should retry later", e);
//		            
//		        } catch (APIRequestException e) {
//		            // Should review the error, and fix the request
//		            LOG.error("Should review the error, and fix the request", e);
//		            LOG.info("HTTP Status: " + e.getStatus());
//		            LOG.info("Error Code: " + e.getErrorCode());
//		            LOG.info("Error Message: " + e.getErrorMessage());
//		        }
//	   }
//	   /**
//	    * # Tag/Alias 样例
//	    */
//	   public void tagAlias()
//	   {
//		   try {
//				TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
//				
//				LOG.info(result.alias);
//				LOG.info(result.tags.toString());
//				
//			} catch (APIConnectionException e) {
//				LOG.error("Connection error. Should retry later. ", e);
//				
//			} catch (APIRequestException e) {
//				LOG.error("Error response from JPush server. Should review and fix it. ", e);
//	           		LOG.info("HTTP Status: " + e.getStatus());
//	           		LOG.info("Error Code: " + e.getErrorCode());
//	           		LOG.info("Error Message: " + e.getErrorMessage());
//			}
//	   }
	  
	   
	/**
	 * 进行推送的关键在于构建一个 PushPayload 对象。以下示例一般的构建对象的用法。
     * 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
	 * @return
	 */
//	public static PushPayload buildPushObject_all_all_alert(String ALERT) {
//	    return PushPayload.alertAll(ALERT);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//初始化消息推送
	
	public static void initJPushInterface(Context context)
	{
		try {
			JPushInterface.setDebugMode(true);
			JPushInterface.init(context);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
//	
//	
//	
	// 设置消息推送服务
		public static void setJPushInterfaceTuisong(Context context,String uid) {
			// TODO Auto-generated method stub
			Set<String> set = new HashSet<String>();
			set.add(uid);
			JPushInterface.setAliasAndTags(context, "users", set,
					new TagAliasCallback() {
						@Override
						public void gotResult(int arg0, String arg1,
								Set<String> arg2) {
							// TODO Auto-generated method stub
//							Log.v("aaa", "arg0==>" + arg0 + "/" + "===arg1==>"
//									+ arg1 + "/" + "===>arg2===" + arg2);
						}
					});
		}
}
