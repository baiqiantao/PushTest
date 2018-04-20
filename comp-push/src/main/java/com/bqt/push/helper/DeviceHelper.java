package com.bqt.push.helper;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//判断系统厂商，里面的内容基本都来自QMUI库
public class DeviceHelper {
	private final static String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static final String KEY_FLYME_VERSION_NAME = "ro.build.display.id";
	private final static String FLYME = "flyme";
	private final static String MEIZUBOARD[] = {"m9", "M9", "mx", "MX"};
	private static String sMiuiVersionName;
	private static String sFlymeVersionName;
	
	static {
		Properties properties = new Properties();
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {// android 8.0，读取 /system/build.prop 会报 permission denied
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
				properties.load(fileInputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		try {
			Class<?> clzSystemProperties = Class.forName("android.os.SystemProperties");
			Method getMethod = clzSystemProperties.getDeclaredMethod("get", String.class);
			sMiuiVersionName = getLowerCaseName(properties, getMethod, KEY_MIUI_VERSION_NAME);
			sFlymeVersionName = getLowerCaseName(properties, getMethod, KEY_FLYME_VERSION_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getLowerCaseName(Properties p, Method get, String key) {
		String name = p.getProperty(key);
		if (name == null) {
			try {
				name = (String) get.invoke(null, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (name != null) name = name.toLowerCase();
		return name;
	}
	
	private static boolean sIsTabletChecked = false;
	private static boolean sIsTabletValue = false;
	
	/**
	 * 判断是否为平板设备
	 */
	public static boolean isTablet(Context context) {
		if (sIsTabletChecked) {
			return sIsTabletValue;
		} else {
			sIsTabletChecked = true;
			sIsTabletValue = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
					Configuration.SCREENLAYOUT_SIZE_LARGE;
			return sIsTabletValue;
		}
	}
	
	/**
	 * 判断是否是flyme系统
	 */
	public static boolean isFlyme() {
		return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains(FLYME);
	}
	
	/**
	 * 判断是否是MIUI系统
	 */
	public static boolean isMIUI() {
		return !TextUtils.isEmpty(sMiuiVersionName);
	}
	
	public static boolean isMIUIV5() {
		return "v5".equals(sMiuiVersionName);
	}
	
	public static boolean isMIUIV6() {
		return "v6".equals(sMiuiVersionName);
	}
	
	public static boolean isMIUIV7() {
		return "v7".equals(sMiuiVersionName);
	}
	
	public static boolean isMIUIV8() {
		return "v8".equals(sMiuiVersionName);
	}
	
	public static boolean isMIUIV9() {
		return "v9".equals(sMiuiVersionName);
	}
	
	public static boolean isFlymeVersionHigher5_2_4() {
		//查不到默认高于5.2.4
		boolean isHigher = true;
		if (sFlymeVersionName != null && !sFlymeVersionName.equals("")) {
			Pattern pattern = Pattern.compile("(\\d+\\.){2}\\d");
			Matcher matcher = pattern.matcher(sFlymeVersionName);
			if (matcher.find()) {
				String versionString = matcher.group();
				if (versionString != null && !versionString.equals("")) {
					String[] version = versionString.split("\\.");
					if (version.length == 3) {
						if (Integer.valueOf(version[0]) < 5) {
							isHigher = false;
						} else if (Integer.valueOf(version[0]) > 5) {
							isHigher = true;
						} else {
							if (Integer.valueOf(version[1]) < 2) {
								isHigher = false;
							} else if (Integer.valueOf(version[1]) > 2) {
								isHigher = true;
							} else {
								if (Integer.valueOf(version[2]) < 4) {
									isHigher = false;
								} else if (Integer.valueOf(version[2]) >= 5) {
									isHigher = true;
								}
							}
						}
					}
					
				}
			}
		}
		return isMeizu() && isHigher;
	}
	
	/**
	 * 判断是否为魅族
	 */
	public static boolean isMeizu() {
		return isSpecialBoardPhone(MEIZUBOARD) || isFlyme();
	}
	
	/**
	 * 判断是否为小米，详见https://dev.mi.com/doc/?p=254
	 */
	public static boolean isXiaomi() {
		return Build.MANUFACTURER.toLowerCase().equals("xiaomi");
	}
	
	/**
	 * 是否是指定型号的手机
	 */
	private static boolean isSpecialBoardPhone(String[] boards) {
		String board = android.os.Build.BOARD;
		if (board != null) {
			for (String b : boards) {
				if (board.equals(b)) {
					return true;
				}
			}
		}
		return false;
	}
}