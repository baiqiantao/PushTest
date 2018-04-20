package com.bqt.push.receiver;

import android.content.Context;
import android.util.Log;

import com.bqt.push.R;
import com.bqt.push.helper.PushMsgReceiverHelper;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

import java.util.Arrays;

public class MZPushReceiver extends MzPushMessageReceiver {
	
	@Override
	//接收服务器推送的透传消息
	public void onMessage(Context context, String s) {
		Log.i("bqt", "魅族【onMessage】" + s);
		PushMsgReceiverHelper.getInstance().onMeiZhuPushMsgReceiver(s);
	}
	
	@Override
	//注册。调用PushManager.register(context方法后，会在此回调注册状态应用在接受返回的pushid
	public void onRegister(Context context, String pushid) {
		Log.i("bqt", "魅族【onRegister】" + pushid);
	}
	
	@Override
	//取消注册。调用PushManager.unRegister(context）方法后，会在此回调反注册状态
	public void onUnRegister(Context context, boolean b) {
		Log.i("bqt", "魅族【onUnRegister】" + b);
	}
	
	@Override
	//设置通知栏小图标。重要！详情参考应用小图标自定设置
	public void onUpdateNotificationBuilder(PushNotificationBuilder builder) {
		Log.i("bqt", "魅族【onUpdateNotificationBuilder】" + builder.getmNotificationsound() + "  "
				+ builder.getmLargIcon() + "  " + builder.getmNotificationDefaults() + "  " + builder.getmNotificationFlags() + "  "
				+ builder.getmStatusbarIcon() + "  " + Arrays.toString(builder.getmVibratePattern()));
		builder.setmStatusbarIcon(R.drawable.ic_launcher);
	}
	
	@Override
	//检查通知栏和透传消息开关状态回调
	public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
		Log.i("bqt", "魅族【onPushStatus】" + pushSwitchStatus.toString());
	}
	
	@Override
	//调用新版订阅PushManager.register(context,appId,appKey)回调
	public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
		Log.i("bqt", "魅族【onRegisterStatus】" + registerStatus.toString());
	}
	
	@Override
	//新版反订阅回调
	public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
		Log.i("bqt", "魅族【onUnRegisterStatus】" + unRegisterStatus.toString());
	}
	
	@Override
	//标签回调
	public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
		Log.i("bqt", "魅族【onSubTagsStatus】" + subTagsStatus.toString());
	}
	
	@Override
	//别名回调
	public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
		Log.i("bqt", "魅族【onSubAliasStatus】" + subAliasStatus.toString());
	}
	
	@Override
	//通知栏消息到达回调，flyme6基于android6.0以上不再回调
	public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
		Log.i("bqt", "魅族【onNotificationArrived】" + mzPushMessage.toString());
	}
	
	@Override
	//通知栏消息点击回调
	public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
		Log.i("bqt", "魅族【onNotificationClicked】" + mzPushMessage.toString());
	}
	
	@Override
	//通知栏消息删除回调；flyme6基于android6.0以上不再回调
	public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
		Log.i("bqt", "魅族【onNotificationDeleted】" + mzPushMessage.toString());
	}
}