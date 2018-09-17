package com.xiaomi.mipushdemo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class DemoApplication extends Application {
	
	private static final String APP_ID = "2882303761517864915";//1000270
	private static final String APP_KEY = "5221786477915";//670100056270
	
	// 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep com.xiaomi.mipushdemo
	public static final String TAG = "com.xiaomi.mipushdemo";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// 注册push服务，注册成功后会向DemoMessageReceiver发送广播
		// 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
		if (shouldInit()) {
			MiPushClient.registerPush(this, APP_ID, APP_KEY);
		}
		
		Logger.setLogger(this, new LoggerInterface() {
			@Override
			public void setTag(String tag) {
			}
			
			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}
			
			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		});
	}
	
	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		if (am != null) {
			List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
			String mainProcessName = getPackageName();
			int myPid = Process.myPid();
			for (RunningAppProcessInfo info : processInfos) {
				if (info.pid == myPid && mainProcessName.equals(info.processName)) {
					return true;
				}
			}
		}
		return false;
	}
}