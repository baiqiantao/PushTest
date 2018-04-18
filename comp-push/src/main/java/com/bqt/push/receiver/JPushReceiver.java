package com.bqt.push.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bqt.push.helper.PushMsgReceiverHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器。如果不定义这个 Receiver，则默认用户会打开主界面，接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent == null || intent.getAction() == null) return;
		
		Bundle bundle = intent.getExtras();
		Log.i("bqt", "【JPushReceiver】Action：" + intent.getAction() + "\nextras：" + printBundle(bundle));
		
		switch (intent.getAction()) {
			case JPushInterface.ACTION_MESSAGE_RECEIVED://将自定义消息转发到需要的地方
				Log.i("bqt", "【JPushReceiver】接收到推送下来的自定义消息");
				if (bundle != null) {
					PushMsgReceiverHelper.getInstance().onJPushMsgReceiver(bundle);
				}
				break;
			case JPushInterface.ACTION_REGISTRATION_ID:
				String regId = bundle == null ? "" : bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Log.i("bqt", "【JPushReceiver】接收Registration Id : " + regId);
				break;
			case JPushInterface.ACTION_NOTIFICATION_RECEIVED:
				Log.i("bqt", "【JPushReceiver】接收到推送下来的通知");
				int notifactionId = bundle == null ? -1 : bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Log.i("bqt", "【JPushReceiver】接收到推送下来的通知的ID: " + notifactionId);
				break;
			case JPushInterface.ACTION_NOTIFICATION_OPENED:
				Log.i("bqt", "【JPushReceiver】用户点击打开了通知");
				break;
			case JPushInterface.ACTION_RICHPUSH_CALLBACK:  // 根据 JPushInterface.EXTRA_EXTRA 的内容处理代码
				String extra = bundle == null ? "" : bundle.getString(JPushInterface.EXTRA_EXTRA);
				Log.i("bqt", "【JPushReceiver】用户收到到RICH PUSH CALLBACK: " + extra);
				break;
			case JPushInterface.ACTION_CONNECTION_CHANGE:
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.i("bqt", "【JPushReceiver】" + intent.getAction() + " connected state change to " + connected);
				break;
			default:
				Log.i("bqt", "【JPushReceiver】Unhandled intent - " + intent.getAction());
				break;
		}
	}
	
	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		if (bundle == null) return "";
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			switch (key) {
				case JPushInterface.EXTRA_NOTIFICATION_ID:
					sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
					break;
				case JPushInterface.EXTRA_CONNECTION_CHANGE:
					sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
					break;
				case JPushInterface.EXTRA_EXTRA:
					if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
						Log.i("bqt", "This message has no Extra data");
						continue;
					}
					try {
						JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
						Iterator<String> it = json.keys();
						while (it.hasNext()) {
							String myKey = it.next();
							sb.append("\nkey:").append(key)
									.append(", value: [").append(myKey)
									.append(" - ").append(json.optString(myKey)).append("]");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					break;
				default:
					sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
					break;
			}
		}
		return sb.toString();
	}
}