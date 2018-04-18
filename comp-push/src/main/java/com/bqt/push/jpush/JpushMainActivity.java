package com.bqt.push.jpush;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bqt.push.BasePushBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

import cn.jpush.android.api.JPushInterface;

public class JpushMainActivity extends ListActivity {
	
	public static boolean isForeground = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setTextColor(Color.BLUE);
		String string = "AppKey: " + PushUtil.getAppKey(this) + "\n" +
				"IMEI: " + PushUtil.getImei(this, "") + "\n" +
				"RegId:" + JPushInterface.getRegistrationID(this) + "\n" +
				"PackageName: " + getPackageName() + "\n" +
				"deviceId:" + PushUtil.getDeviceId(this) + "\n" +
				"Version: " + PushUtil.GetVersionName(this);
		tv.setText(string);
		getListView().addHeaderView(tv);
		
		String[] array = {"initPush", "stopPush", "resumePush",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
		EventBus.getDefault().register(this);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position - 1) {
			case 0:
				JPushInterface.init(this);// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
				break;
			case 1:
				JPushInterface.stopPush(this);
				break;
			case 2:
				JPushInterface.resumePush(this);
				break;
		}
	}
	
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onPushEvent(BasePushBean bean) {
		TextView tv = new TextView(this);
		tv.setTextColor(Color.BLUE);
		tv.setText(bean.msg);
		getListView().addFooterView(tv);
	}
}