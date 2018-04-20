package com.bqt.push.helper;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.meizu.cloud.pushsdk.base.SystemProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class SimpleDeviceUtils {
	
	public enum SystemType {
		/**
		 * 小米手机（MIUI系统）
		 */
		SYS_MIUI,
		/**
		 * 华为手机（EMUI系统）
		 */
		SYS_EMUI,
		/**
		 * 魅族手机，FLYME系统
		 */
		SYS_FLYME,
		/**
		 * 其他系统
		 */
		SYS_OTHER
	}
	
	private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
	private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
	
	private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
	private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
	private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";
	
	/**
	 * 8.0之后有些系统信息获取不到，没有在各种版本手机上逐一测试
	 */
	public static SystemType getSystemType() {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
			if (Build.MANUFACTURER.toLowerCase().equals("xiaomi")//官方提供的判断是否为小米手机（而非MIUI系统）的方法
					|| prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null//QMUI提供的判断是否是MIUI的方法
					|| prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null//下面两个是网上补充的方法，感觉没必要的
					|| prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
				return SystemType.SYS_MIUI;
			} else if (isEMUI()//华为
					|| prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
					|| prop.getProperty(KEY_EMUI_VERSION, null) != null
					|| prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
				return SystemType.SYS_EMUI;
			} else if (isMeizu()//魅族推送SDK中提供的判断是否是魅族的方法
					|| DeviceHelper.isMeizu()) {//QMUI提供的判断是否是魅族的方法
				return SystemType.SYS_FLYME;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SystemType.SYS_OTHER;
	}
	
	@SuppressLint("PrivateApi")
	private static boolean isEMUI() {
		Class<?>[] clsArray = new Class<?>[]{String.class};
		Object[] objArray = new Object[]{"ro.build.version.emui"};
		try {
			Class<?> SystemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method get = SystemPropertiesClass.getDeclaredMethod("get", clsArray);
			String version = (String) get.invoke(SystemPropertiesClass, objArray);
			Log.i("bqt", "EMUI version is:" + version);
			return !TextUtils.isEmpty(version);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否为魅族设备
	 */
	private static boolean isMeizu() {
		String model = SystemProperties.get("ro.meizu.product.model");
		return (!TextUtils.isEmpty(model)) || "meizu".equalsIgnoreCase(Build.BRAND) || "22c4185e".equalsIgnoreCase(Build.BRAND);
	}
}