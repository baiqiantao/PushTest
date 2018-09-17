package com.xiaomi.mipushdemo;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * 1、设置 topic 和 alias。 服务器端使用 appsecret 即可以向demo发送广播和单点的消息。<br/>
 * 2、为了修改本 demo 为使用你自己的 appid，你需要修改几个地方：DemoApplication.java 中的 APP_ID,
 * APP_KEY，AndroidManifest.xml 中的 packagename，和权限 permission.MIPUSH_RECEIVE 的前缀为你的 packagename。
 */
public class MainActivity extends ListActivity {
	
	@Override
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
				"重新开始推送",
				"再打开一个页面：" + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss SSS", Locale.getDefault()).format(new Date()),
				"",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
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
				MiPushClient.setAcceptTime(MainActivity.this, startHour, startMin, endHour, endMin, null);
				break;
			case 7:
				MiPushClient.pausePush(MainActivity.this, null);
				break;
			case 8:
				MiPushClient.resumePush(MainActivity.this, null);
				break;
			default:
				startActivity(new Intent(this, MainActivity.class));
				break;
		}
	}
	
	public void set_alias() {
		final EditText editText = new EditText(MainActivity.this);
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("设置别名")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String alias = editText.getText().toString();
					MiPushClient.setAlias(MainActivity.this, alias, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	public void unset_alias() {
		final EditText editText = new EditText(MainActivity.this);
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("撤销别名")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String alias = editText.getText().toString();
					MiPushClient.unsetAlias(MainActivity.this, alias, null);
				})
				.setNegativeButton("取消", null)
				.show();
		
	}
	
	public void set_account() {
		final EditText editText = new EditText(MainActivity.this);
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("设置帐号")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String account = editText.getText().toString();
					MiPushClient.setUserAccount(MainActivity.this, account, null);
				})
				.setNegativeButton("取消", null)
				.show();
		
	}
	
	public void unset_account() {
		final EditText editText = new EditText(MainActivity.this);
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("撤销帐号")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String account = editText.getText().toString();
					MiPushClient.unsetUserAccount(MainActivity.this, account, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	public void subscribe_topic() {
		final EditText editText = new EditText(MainActivity.this);
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("设置标签")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String topic = editText.getText().toString();
					MiPushClient.subscribe(MainActivity.this, topic, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
	
	public void unsubscribe_topic() {
		final EditText editText = new EditText(MainActivity.this);
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("撤销标签")
				.setView(editText)
				.setPositiveButton("确认", (dialog, which) -> {
					String topic = editText.getText().toString();
					MiPushClient.unsubscribe(MainActivity.this, topic, null);
				})
				.setNegativeButton("取消", null)
				.show();
	}
}
