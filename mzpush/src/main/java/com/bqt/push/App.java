package com.bqt.push;

import android.app.Application;

import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

public class App extends Application {
	private static final String APP_ID = "";
	private static final String APP_KEY = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		//魅族推送只适用于Flyme系统,因此可以先行判断是否为魅族机型，再进行订阅，避免在其他机型上出现兼容性问题
		if (MzSystemUtils.isBrandMeizu()) {
			PushManager.register(this, APP_ID, APP_KEY);
		}
	}
}
