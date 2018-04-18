package com.bqt.push.jpush;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

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
}
