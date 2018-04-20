package com.bqt.push.helper;

import android.os.Bundle;
import android.util.Log;

import com.bqt.push.testactivity.HuaweiPushTestActivity;
import com.bqt.push.testactivity.JPushTestActivity;
import com.bqt.push.testactivity.MZPushTestActivity;
import com.bqt.push.testactivity.MiPushTestActivity;
import com.xiaomi.mipush.sdk.MiPushMessage;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * 处理推送SDK推过来的自定义消息（又叫应用内消息，或者透传消息）
 */
public class PushMsgReceiverHelper {
	private static PushMsgReceiverHelper instance = new PushMsgReceiverHelper();
	
	private PushMsgReceiverHelper() {
	}
	
	public static PushMsgReceiverHelper getInstance() {
		return instance;
	}
	
	/**
	 * 处理极光推送推过来的自定义消息
	 */
	public void onJPushMsgReceiver(Bundle bundle) {
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);//必填，消息内容本身
		String title = bundle.getString(JPushInterface.EXTRA_TITLE);//可选，消息标题
		String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);//可选，消息内容类型
		String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);//可选，JSON 格式的可选参数
		String content = "msg:" + message + "\t title:" + title + "\t type:" + type + "\t extra:" + extra;
		Log.i("bqt", "【极光推送】" + content);
		if (JPushTestActivity.isForeground) {
			EventBus.getDefault().post(new BasePushBean(content, BasePushBean.TYPE_STRING));
		}
	}
	
	/**
	 * 处理小米推送推过来的自定义消息
	 */
	public void onMiPushMsgReceiver(MiPushMessage message) {
		Log.i("bqt", "【小米推送】" + message);
		if (MiPushTestActivity.isForeground) {
			EventBus.getDefault().post(new BasePushBean(message.getContent(), BasePushBean.TYPE_STRING));
		}
	}
	
	/**
	 * 处理华为光推送推过来的自定义消息
	 */
	public void onHuaweiPushMsgReceiver(String message) {
		Log.i("bqt", "【华为推送】" + message);
		if (HuaweiPushTestActivity.isForeground) {
			EventBus.getDefault().post(new BasePushBean(message, BasePushBean.TYPE_STRING));
		}
	}
	
	/**
	 * 处理魅族推送推过来的自定义消息
	 */
	public void onMeiZhuPushMsgReceiver(String message) {
		Log.i("bqt", "【魅族推送】" + message);
		if (MZPushTestActivity.isForeground) {
			EventBus.getDefault().post(new BasePushBean(message, BasePushBean.TYPE_STRING));
		}
	}
}