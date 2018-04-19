package com.bqt.push;

import android.app.Application;
import android.util.Log;

import com.bqt.push.hms.agent.HMSAgent;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		boolean success = HMSAgent.init(this);
		Log.i("bqt", ""+success);
		
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		HMSAgent.destroy();
	}
}
