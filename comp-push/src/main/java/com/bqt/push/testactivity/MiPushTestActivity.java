package com.bqt.push.testactivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bqt.push.helper.BasePushBean;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 1、设置 topic 和 alias。 服务器端使用 appsecret 即可以向demo发送广播和单点的消息。<br/>
 * 2、为了修改本 demo 为使用你自己的 appid，你需要修改几个地方：Application中的 APP_ID 和 APP_KEY
 *      AndroidManifest.xml 中的 packagename，和权限 permission.MIPUSH_RECEIVE 的前缀为你的 packagename。
 */
public class MiPushTestActivity extends ListActivity {
	
	public static boolean isForeground = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"设置别名",
				"撤销别名",
				"设置帐号",
				"撤销帐号",
				"设置标签",
				
				"撤销标签",
				"设置接收消息时间",
				"暂停推送",
				"重新开始推送",};
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
				set_alias();
				break;
			case 1:
				unset_alias();
				break;
			case 2:
				set_account();
				break;
			case 3:
				unset_account();
				break;
			case 4:
				subscribe_topic();
				break;
			case 5:
				unsubscribe_topic();
				break;
			case 6:
				int startHour = 10;
				int startMin = 0;
				int endHour = 23;
				int endMin = 0;
				MiPushClient.setAcceptTime(this, startHour, startMin, endHour, endMin, null);
				break;
			case 7:
				MiPushClient.pausePush(this, null);
				break;
			case 8:
				MiPushClient.resumePush(this, null);
				break;
		}
	}
	
	public void set_alias() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("设置别名")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String alias = editText.getText().toString();
					MiPushClient.setAlias(this, alias, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	public void unset_alias() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("撤销别名")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String alias = editText.getText().toString();
					MiPushClient.unsetAlias(this, alias, null);
				})
				.setNegativeButton("取消", null)
				.show();
		
	}
	
	public void set_account() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("设置帐号")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String account = editText.getText().toString();
					MiPushClient.setUserAccount(this, account, null);
				})
				.setNegativeButton("取消", null)
				.show();
		
	}
	
	public void unset_account() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("撤销帐号")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String account = editText.getText().toString();
					MiPushClient.unsetUserAccount(this, account, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	public void subscribe_topic() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("设置标签")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String topic = editText.getText().toString();
					MiPushClient.subscribe(this, topic, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	public void unsubscribe_topic() {
		final EditText editText = new EditText(this);
		new AlertDialog.Builder(this)
				.setTitle("撤销标签")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String topic = editText.getText().toString();
					MiPushClient.unsubscribe(this, topic, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onPushEvent(BasePushBean bean) {
		TextView tv = new TextView(this);
		tv.setTextColor(Color.BLUE);
		tv.setText(bean.msg);
		getListView().addFooterView(tv);
	}
}