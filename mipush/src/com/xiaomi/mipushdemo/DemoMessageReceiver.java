package com.xiaomi.mipushdemo;

import android.content.Context;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 注意，重写的这些方法都运行在非 UI 线程中
 */
public class DemoMessageReceiver extends PushMessageReceiver {
	
	@Override
	/*用来接收服务器向客户端发送的透传消息*/
	public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
		Log.i("bqt", "【onReceivePassThroughMessage】" + message.toString());
		printMsg(message);
	}
	
	private void printMsg(MiPushMessage message) {
		String topic = message.getTopic();
		String alias = message.getAlias();
		String content = message.getContent();
		Log.i("bqt", "topic=" + topic + "  alias=" + alias + "  content=" + content);
	}
	
	@Override
	/*用来接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发*/
	public void onNotificationMessageClicked(Context context, MiPushMessage message) {
		Log.i("bqt", "【onNotificationMessageClicked】" + message.toString());
		printMsg(message);
	}
	
	@Override
	/*用来接收服务器向客户端发送的通知消息，这个回调方法是在通知消息到达客户端时触发。
	另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。*/
	public void onNotificationMessageArrived(Context context, MiPushMessage message) {
		Log.i("bqt", "【onNotificationMessageArrived】" + message.toString());
		printMsg(message);
	}
	
	@Override
	/*用来接收客户端向服务器发送命令后的响应结果*/
	public void onCommandResult(Context context, MiPushCommandMessage message) {
		Log.i("bqt", "【onCommandResult】" + message.toString());
		printCmdMsg(message);
	}
	
	private void printCmdMsg(MiPushCommandMessage message) {
		String command = message.getCommand();
		List<String> arguments = message.getCommandArguments();
		String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
		String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
		Log.i("bqt", "command=" + command + "  " + cmdArg1 + "  " + cmdArg2);
		
		switch (command) {
			case MiPushClient.COMMAND_REGISTER://注册
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "注册成功 mRegId =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_SET_ALIAS://设置别名
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "设置别名成功 mAlias =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_UNSET_ALIAS://取消设置别名
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "取消设置别名成功 mAlias =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_SET_ACCOUNT://设置账户
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "设置账户成功 mAccount =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_UNSET_ACCOUNT://撤销账户
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "撤销账户成功 mAccount =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_SUBSCRIBE_TOPIC://订阅标签
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "订阅标签成功 mTopic =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC://撤销标签
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "撤销标签成功 mTopic =" + cmdArg1);
				}
				break;
			case MiPushClient.COMMAND_SET_ACCEPT_TIME://设置接收推送时间
				if (message.getResultCode() == ErrorCode.SUCCESS) {
					Log.i("bqt", "设置接收推送时间成功 mStartTime =" + cmdArg1 + "  mEndTime=" + cmdArg2);
				}
				break;
			default:
				Log.i("bqt", "未知命令，Reason=" + message.getReason());
				break;
		}
	}
	
	@Override
	/*用来接收客户端向服务器发送注册命令后的响应结果*/
	public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
		Log.i("bqt", "【onReceiveRegisterResult】" + message.toString());
		String command = message.getCommand();
		List<String> arguments = message.getCommandArguments();
		String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
		
		Log.i("bqt", "command=" + command + "  " + cmdArg1);
	}
}