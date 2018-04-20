package com.bqt.push.receiver;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bqt.push.helper.PushMsgReceiverHelper;
import com.huawei.hms.support.api.push.PushReceiver;

public class HuaweiPushRevicer extends PushReceiver {
	
	/**
	 * 这里接收到穿透消息，自定义通知栏，和操作
	 *
	 * @param context 上下文
	 * @param msg     消息
	 * @param bundle  bundle
	 * @return 不清楚返回真假的作用
	 */
	@Override
	public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
		try {
			String content = new String(msg, "UTF-8");
			Log.i("bqt", "华为【onPushMsg】" + content);
			PushMsgReceiverHelper.getInstance().onHuaweiPushMsgReceiver(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void onToken(Context context, String token, Bundle extras) {
		Log.i("bqt", "华为【onToken】token=" + token + "  belongId=" + extras.getString("belongId"));
		//保存在本地备用
		context.getSharedPreferences("huawei_token", Context.MODE_PRIVATE).edit().putString("token", token).apply();
		Toast.makeText(context, token, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 这里接收到的是通知栏消息，无法自定义操作，默认打开应用
	 *
	 * @param context 上下文
	 * @param event   事件
	 * @param extras  额外
	 */
	public void onEvent(Context context, Event event, Bundle extras) {
		int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
		String message = extras.getString(BOUND_KEY.pushMsgKey);
		Log.i("bqt", "华为【onEvent】event=" + event + "  notifyId=" + notifyId + "  message=" + message);
		
		if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
			NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			if (0 != notifyId && manager != null) {
				manager.cancel(notifyId);
			}
		}
		super.onEvent(context, event, extras);
	}
	
	/**
	 * 得到华为推送的连接状态
	 *
	 * @param context   上下文
	 * @param pushState 连接状态
	 */
	@Override
	public void onPushState(Context context, boolean pushState) {
		Log.i("bqt", "华为【onPushState】连接状态：" + pushState);
	}
}