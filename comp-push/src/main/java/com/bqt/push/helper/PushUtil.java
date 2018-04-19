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
import android.os.Environment;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.bqt.push.APP;
import com.bqt.push.huaweiagent.HMSAgent;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

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
	
	public static String getImei(Context context, String imei) {
		String ret = null;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			ret = telephonyManager.getDeviceId();
		} catch (Exception e) {
			Log.e(PushUtil.class.getSimpleName(), e.getMessage());
		}
		if (isReadableASCII(ret)) {
			return ret;
		} else {
			return imei;
		}
	}
	
	private static boolean isReadableASCII(CharSequence string) {
		if (TextUtils.isEmpty(string)) return false;
		try {
			Pattern p = Pattern.compile("[\\x20-\\x7E]+");
			return p.matcher(string).matches();
		} catch (Throwable e) {
			return true;
		}
	}
	
	public static String getDeviceId(Context context) {
		return JPushInterface.getUdid(context);
	}
	
	public static boolean shouldInitMiPush(Context context) {
		ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
		if (am != null) {
			List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
			String mainProcessName = context.getPackageName();
			int myPid = Process.myPid();
			for (ActivityManager.RunningAppProcessInfo info : processInfos) {
				if (info.pid == myPid && mainProcessName.equals(info.processName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isEMUI2() {
		Class<?>[] clsArray = new Class<?>[]{String.class};
		Object[] objArray = new Object[]{"ro.build.version.emui"};
		try {
			@SuppressLint("PrivateApi") Class<?> SystemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method get = SystemPropertiesClass.getDeclaredMethod("get", clsArray);
			String version = (String) get.invoke(SystemPropertiesClass, objArray);
			Log.i("bqt", "EMUI version is:" + version);
			return !TextUtils.isEmpty(version);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isEMUI() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return prop.getProperty("ro.build.hw_emui_api_level", null) != null;
	}
	
	public static boolean isMIUI() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return prop.getProperty("ro.miui.ui.version.code", null) != null
				|| prop.getProperty("ro.miui.ui.version.name", null) != null
				|| prop.getProperty("ro.miui.internal.storage", null) != null;
	}
	
	public static void initPushSdk(Application context) {
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
	
	}
	
}