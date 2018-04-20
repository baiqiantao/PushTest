package com.bqt.push;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bqt.push.testactivity.HuaweiPushTestActivity;
import com.bqt.push.testactivity.JPushTestActivity;
import com.bqt.push.testactivity.MZPushTestActivity;
import com.bqt.push.testactivity.MiPushTestActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainPushActivity extends ListActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"JPush演示", "MiPush演示", "华为Push演示", "魅族Push演示",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
				startActivity(new Intent(this, JPushTestActivity.class));
				break;
			case 1:
				startActivity(new Intent(this, MiPushTestActivity.class));
				break;
			case 2:
				startActivity(new Intent(this, HuaweiPushTestActivity.class));
				break;
			case 3:
				startActivity(new Intent(this, MZPushTestActivity.class));
				break;
		}
	}
}