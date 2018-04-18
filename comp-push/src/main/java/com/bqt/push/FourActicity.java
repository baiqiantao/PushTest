package com.bqt.push;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FourActicity extends Activity implements MyOnItemClickLitener {
	private RecyclerView mRecyclerView;
	private Mydapter mRecyclerViewAdapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.poc_four);
		mRecyclerView = findViewById(R.id.rv);
		init();
	}
	
	protected void init() {
		List<ItemBean> mDatas = new ArrayList<ItemBean>();
		String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss SSS", Locale.getDefault()).format(new Date());
		for (int i = 0; i < 3; i++) {
			mDatas.add(new ItemBean(158 + new Random().nextInt(100) * 25.48 + i + "",
					"" + date,
					"摘要摘要",
					158 + new Random().nextInt(100) * 15.06 + "",
					"不懂左右逢源，不喜趋炎附势，不会随波逐流，不狡辩，不恭维，不把妹，我就是你心目中最想要的那位 Android 工程师"));
		}
		mRecyclerViewAdapter = new Mydapter(this, mDatas);
		mRecyclerViewAdapter.setOnItemClickLitener(this);
		
		mRecyclerView.setAdapter(mRecyclerViewAdapter);//设置adapter
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
	}
	
	@Override
	public void onItemClick(int position) {
		Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
	}
}