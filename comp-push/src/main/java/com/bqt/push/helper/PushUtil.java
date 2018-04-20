package com.bqt.push.helper;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.bqt.push.huaweiagent.HMSAgent;
import com.meizu.cloud.pushsdk.PushManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class PushUtil {
	public static final String KEY_APP_KEY = "JPUSH_APPKEY";
	
	public static String getAppKey(Context context) {
		Bundle metaData = null;
		String appKey = null;
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai)
				metaData = ai.metaData;
			if (null != metaData) {
				appKey = metaData.getString(KEY_APP_KEY);
				if ((null == appKey) || appKey.length() != 24) {
					appKey = null;
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return appKey;
	}
	
	public static String GetVersionName(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}
	
	@SuppressLint({"MissingPermission", "HardwareIds"})
	public static String getImei(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (telephonyManager != null) {
				return telephonyManager.getDeviceId();
			}
		} catch (Exception e) {
			Log.e(PushUtil.class.getSimpleName(), e.getMessage());
		}
		return "";
	}
	
	public static boolean shouldInitMiPush(Context context) {
		ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
		if (am != null) {
			List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
			String mainProcessName = context.getPackageName();
			int myPid = Process.myPid();
			for (ActivityManager.RunningAppProcessInfo info : processInfos) {
				Log.i("bqt", "pid=" + info.pid + "  processName=" + info.processName);
				if (info.pid == myPid && mainProcessName.equals(info.processName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void initPushSdk(Application context) {
		switch (SimpleDeviceUtils.getSystemType()) {
			case SYS_MIUI:
				initMiPush(context);
				break;
			case SYS_EMUI:
				initHuaweiPush(context);
				break;
			case SYS_FLYME:
				initMeizhuPush(context);
				break;
			default:
				initJPush(context);
				break;
		}
		initJPush(context);
		initMiPush(context);
		initHuaweiPush(context);
		initMeizhuPush(context);
	}
	
	public static void unInitPushSdk(Application context) {
		HMSAgent.destroy();
	}
	
	private static void initJPush(Application context) {
		JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
		JPushInterface.init(context);            // 初始化 JPush
	}
	
	private static void initMiPush(Application context) {
		String APP_ID = "1000270";
		String APP_KEY = "670100056270";
		
		// 注册push服务，注册成功后会向Receiver发送广播，可以从onCommandResult方法中的参数中获取注册信息
		if (PushUtil.shouldInitMiPush(context)) {
			MiPushClient.registerPush(context, APP_ID, APP_KEY);
		}
	}
	
	private static void initHuaweiPush(Application context) {
		HMSAgent.init(context);
	}
	
	private static void initMeizhuPush(Application context) {
		String APP_ID = "";
		String APP_KEY = "";
		PushManager.register(context, APP_ID, APP_KEY);
	}
}