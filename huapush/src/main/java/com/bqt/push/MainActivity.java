package com.bqt.push;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bqt.push.hms.agent.HMSAgent;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {
	
	private TextView tv;
	private String[] array;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		tv.setTextColor(Color.BLUE);
		getListView().addFooterView(tv);
		array = new String[]{"获取token",
				"删除token",
				" 获取push状态",
				"设置是否接收普通透传消息",
				"设置接收通知消息",
				"显示push协议",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
	}
	
	private boolean b;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		tv.append("\n" + array[position] + "  ");
		b = !b;
		switch (position) {
			case 0:
				HMSAgent.Push.getToken((rst) -> tv.append("+rst"));
				break;
			case 1://token=0990005841682350300001697200CN01  belongId=1
				String token = getSharedPreferences("huawei_token", Context.MODE_PRIVATE).getString("token", null);
				HMSAgent.Push.deleteToken(token, rst -> tv.append("" + rst));
				break;
			case 2:
				HMSAgent.Push.getPushState(rst -> tv.append("" + rst));
				break;
			case 3:
				HMSAgent.Push.enableReceiveNormalMsg(b, rst -> tv.append(b + "   " + rst));
				break;
			case 4:
				HMSAgent.Push.enableReceiveNotifyMsg(b, rst -> tv.append(b + "   " + rst));
				break;
			case 5:
				HMSAgent.Push.queryAgreement(rst -> tv.append("" + rst));
				break;
		}
	}
}