package com.meida.cosmeticsshopuser.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.meida.cosmeticsshopuser.Activity.MessageActivity;
import com.meida.cosmeticsshopuser.Activity.MessageDetailActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.jpush.Logger;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.DefaultPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Logger.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				/*App.jpushToken = regId;
				PreferencesUtils.putString(context, Contants.JPUSH_ID, regId);*/

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

				showNotifycation(context,bundle.getString(JPushInterface.EXTRA_MESSAGE));
				/*DefaultPushNotificationBuilder builder = new DefaultPushNotificationBuilder();
				Map map = new HashMap();
				map.put("cn.jpush.android.ALERT", bundle.getString(JPushInterface.EXTRA_MESSAGE));
				map.put("cn.jpush.android.NOTIFICATION_CONTENT_TITLE",context.getResources().getString(R.string.app_name));
				builder.buildNotification(map);
				JPushInterface.setDefaultPushNotificationBuilder(builder);*/

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

				/*DefaultPushNotificationBuilder builder = new DefaultPushNotificationBuilder();
				Map map = new HashMap();
				map.put("cn.jpush.android.ALERT", JPushInterface.EXTRA_ALERT);
				map.put("cn.jpush.android.NOTIFICATION_CONTENT_TITLE",context.getResources().getString(R.string.app_name));
				builder.buildNotification(map);
				JPushInterface.setDefaultPushNotificationBuilder(builder);*/

				//showNotifycation(context,bundle.getString(JPushInterface.EXTRA_ALERT));

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");/*user_id_30*/

				try{
					/*{"message_id":1,"message_type":1}*/
					String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
					LoggerUtil.e("extra",extra);
					JSONObject jsonObject = new JSONObject(extra);
					String msgId = jsonObject.optString("message_id");
					Intent i = new Intent(context, MessageDetailActivity.class);
					i.putExtra("id",msgId);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				}catch (Exception e){
					e.printStackTrace();
					//TODO 打开自定义的Activity
					Intent i = new Intent(context, MessageActivity.class);
					i.putExtras(bundle);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				}

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Logger.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}


	/*private void showNotifycation2(Context context, String string){
		NotificationManager mNotificationManager =
				(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle(context.getResources().getString(R.string.app_name))
				.setContentText(string)
				*//*TODO DEBUG*//*
				.setSmallIcon(R.mipmap.logo)
				.setContentIntent(getDefalutIntent(context, Notification.FLAG_AUTO_CANCEL))
				.setDefaults(Notification.DEFAULT_VIBRATE)
				.setAutoCancel(true);
		mNotificationManager.notify(0,mBuilder.build());
	}*/

	private void showNotifycation(Context context, String string){

		PendingIntent pendingIntent;
		try{
			LoggerUtil.e("extra",string);
			JSONObject jsonObject = new JSONObject(string);
			String msgId = jsonObject.optString("message_id");
			pendingIntent = getDefalutIntent(context,Notification.FLAG_AUTO_CANCEL,msgId);
		}catch (Exception e){
			pendingIntent = getDefalutIntent(context,Notification.FLAG_AUTO_CANCEL);
		}

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		String channelId = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			channelId = "1";
			NotificationChannel channel = new NotificationChannel(channelId, "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
			channel.enableLights(true); //是否在桌面icon右上角展示小红点   
			channel.setLightColor(Color.RED); //小红点颜色   
			channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知 

			notificationManager.createNotificationChannel(channel);
		}
		NotificationCompat.Builder build = new NotificationCompat.Builder(context, channelId);
		build.setContentText(string);
		build.setContentTitle(context.getResources().getString(R.string.app_name));
		build.setSmallIcon(R.mipmap.logo);
		build.setContentIntent(pendingIntent);
		notificationManager.notify(0,build.build());


	}

	public PendingIntent getDefalutIntent(Context context, int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity
				(context, 1, new Intent(context, MessageActivity.class), flags);
		return pendingIntent;
	}


	public PendingIntent getDefalutIntent(Context context, int flags,String msgid){
		PendingIntent pendingIntent= PendingIntent.getActivity
				(context, 1, new Intent(context, MessageActivity.class).putExtra("id",msgid), flags);
		return pendingIntent;
	}




}
