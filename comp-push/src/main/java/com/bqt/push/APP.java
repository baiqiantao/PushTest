package com.bqt.push;

import android.app.Application;

import com.bqt.push.helper.PushUtil;

public class APP extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		PushUtil.initPushSdk(this);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		PushUtil.unInitPushSdk(this);
	}
}