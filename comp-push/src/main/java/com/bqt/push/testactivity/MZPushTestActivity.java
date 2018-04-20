package com.bqt.push.testactivity;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bqt.push.helper.BasePushBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

public class MZPushTestActivity extends ListActivity {
	
	public static boolean isForeground = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"",
				"",
				"",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
		}
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onPushEvent(BasePushBean bean) {
		TextView tv = new TextView(this);
		tv.setTextColor(Color.BLUE);
		tv.setText(bean.msg);
		getListView().addFooterView(tv);
	}
}