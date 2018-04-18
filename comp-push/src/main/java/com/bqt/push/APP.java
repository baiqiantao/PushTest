package com.bqt.push;

import android.app.Application;

import com.bqt.push.helper.PushUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

import cn.jpush.android.api.JPushInterface;

public class APP extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		initJPush();
		initMiPush();
		initHuaweiPush();
		initMeizhuPush();
	}
	
	private void initJPush() {
		JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);            // 初始化 JPush
	}
	
	private void initMiPush() {
		String APP_ID = "1000270";
		String APP_KEY = "670100056270";
		
		// 注册push服务，注册成功后会向Receiver发送广播，可以从onCommandResult方法中的参数中获取注册信息
		if (PushUtil.shouldInitMiPush(this)) {
			MiPushClient.registerPush(this, APP_ID, APP_KEY);
		}
	}
	
	private void initHuaweiPush() {
	
	}
	
	private void initMeizhuPush() {
	
	}
}