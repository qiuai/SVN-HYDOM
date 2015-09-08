package com.carinsurance.receiver;

import cn.jpush.android.api.JPushInterface;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private int notifyId = 1;
	private String extras;
	private String massage;

	// private NotificationManager mNotificationManager;
	// private NotificationCompat.Builder mBuilder;
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		Log.d("TAG", "[MyReceiver] onReceive - " + intent.getAction()
				+ ", extras: " + printBundle(bundle));

		System.out.println("得到sx========" + extras);

		System.out.println("得到========"
				+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
		massage = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		Log.v("MyReceiver", "wwwww======extras=>" + extras);
		// if (extras != null) {
		// System.out.println("55555555555555555555555555555");
		// FinalDb db = FinalDb.create(context, "mytest.db", true);
		// User user = null;
		// try {
		// user = JsonUtil.getEntityByJsonString(extras, User.class);
		// Log.d(TAG, "user.get" + user.getPhoneNumber());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// // Log.d(TAG, "user为" + user.toString());
		// if (db != null){
		// db.save(user);
		// }
		//
		//
		// if (Utils.hadn != null) {
		// Utils.hadn.sendEmptyMessage(2);
		// } else {
		// System.out.println("传送的handler为空");
		// }
		//
		// }

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 接收到推送下来的自定义消息: "
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//			int notifactionId = bundle
//					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//			
//			
//			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
//			Log.v(TAG,"extras="+extras);
//			if (extras != null) {
//				System.out.println("55555555555555555555555555555");
//			
//				DbUtils db = DbUtils.create(context, "mytest.db");
//				User_Message user = null;
//				try {
//					
//					user = JsonUtil.getEntityByJsonString(extras,
//							User_Message.class);
////					Log.d(TAG, "user.get" + user.getKey());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if (("" + user.getIs_Online_news()).equals("-1")
//						|| ("" + user.getIs_Online_news()).equals("null")) {
//					// Log.d(TAG, "user为" + user.toString());
//					if (db != null) {
//						try {
//							db.save(user);
//						} catch (DbException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//					if (Utils.hadn != null) {
//						Utils.hadn.sendEmptyMessage(2);
//					} else {
//						System.out.println("传送的handler为空");
//					}
//				} else if (("" + user.getIs_Online_news()).equals("1")) {
//					List<MyFriends> myFriends = Utils.getAllFriends(context);
//					for (int i = 0; i < myFriends.size(); i++) {
//						if (myFriends.get(i).getName()
//								.equals("" + user.getName())) {
//							MyFriends ff = myFriends.get(i);
//							ff.setState("1");
//							Utils.ModifyFriends(context, ff);
//							break;
//						}
//					}
//					if (Utils.main_active_handle != null) {
//						Utils.main_active_handle.sendEmptyMessage(2);
//					} else {
//						System.out.println("传送的handler为空");
//					}
//
//				} else if (("" + user.getIs_Online_news()).equals("0")) {
//					List<MyFriends> myFriends = Utils.getAllFriends(context);
//					for (int i = 0; i < myFriends.size(); i++) {
//						if (myFriends.get(i).getName()
//								.equals("" + user.getName())) {
//							MyFriends ff = myFriends.get(i);
//							ff.setState("0");
//							Utils.ModifyFriends(context, ff);
//							break;
//						}
//					}
//					if (Utils.main_active_handle != null) {
//						Utils.main_active_handle.sendEmptyMessage(2);
//					} else {
//						System.out.println("传送的handler为空");
//					}
//
//				}
//
//			}

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//			if (Utils.active == null) {
//
//				Intent intent2 = new Intent(context, MainActivity.class);
//				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				// intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//				// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent2);
//			} else {
//				// Message message=new Message();
//				// Bundle bundle2=new Bundle();
//				// message.what=1;
//				if (Utils.hadn != null) {
//					Utils.hadn.sendEmptyMessage(2);
//				} else {
//					System.out.println("传送的handler为空");
//				}
//
//			}

			// 充值 = 1, 订单延时 = 2, 会员催菜提醒 = 3, 订单取消 = 4, 收到订单 = 5
			// //打开自定义的Activity
			// Intent i = new Intent(context, TestActivity.class);
			// i.putExtras(bundle);
			// //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
			// Intent.FLAG_ACTIVITY_CLEAR_TOP );
			// context.startActivity(i);

			// ((Activity) (context)).finish();

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.e(TAG, "[MyReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	private NotificationManager getSystemService(String notificationService) {
		// TODO Auto-generated method stub
		return null;
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	// send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		// if (MainActivity.isForeground) {
		// String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		// String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		// Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
		// msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
		// if (!ExampleUtil.isEmpty(extras)) {
		// try {
		// JSONObject extraJson = new JSONObject(extras);
		// if (null != extraJson && extraJson.length() > 0) {
		// msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
		// }
		// } catch (JSONException e) {
		//
		// }
		//
		// }
		// context.sendBroadcast(msgIntent);
		// }
	}

	public PendingIntent getDefalutIntent(int flags, Context context) {
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
				new Intent(), flags);
		return pendingIntent;
	}
	// NotificationManager mNotificationManager = (NotificationManager)
	// getSystemService(Context.NOTIFICATION_SERVICE);
	// NotificationCompat.Builder mBuilder = new
	// NotificationCompat.Builder(context);
	// if (extras != null && !extras.equals("") && !extras.equals("{}")) {
	// PendingIntent pendingIntent1= PendingIntent.getActivity(context, 1, new
	// Intent(), Notification.FLAG_AUTO_CANCEL);
	// mBuilder.setContentTitle("通知")
	// .setContentText(massage + "")
	//
	// .setContentIntent(pendingIntent1)
	// // .setNumber(number)//显示数量
	// .setTicker(massage + "")//通知首次出现在通知栏，带上升动画效果的
	// .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
	// .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
	// // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
	// .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
	// .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
	// //Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 // requires
	// VIBRATE permission
	// .setSmallIcon(R.drawable.ic_launcher);
	//
	// mBuilder.setAutoCancel(true)
	// // 点击后让通知将消失
	// .setContentTitle("通知")
	// .setContentText(massage + "")
	// .setTicker(massage + "");
	// // 点击的意图ACTION是跳转到Intent
	// Intent resultIntent = new Intent(context, SMSActivity.class);
	// resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	// PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
	// resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	// mBuilder.setContentIntent(pendingIntent);
	// mNotificationManager.notify(notifyId, mBuilder.build());
	// }
}