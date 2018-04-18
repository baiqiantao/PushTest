package com.bqt.push;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mydapter extends RecyclerView.Adapter<Mydapter.MyViewHolder> {
	
	private Context context;
	private List<ItemBean> mDatas;
	private MyOnItemClickLitener mOnItemClickLitener;
	
	public Mydapter(Context context, List<ItemBean> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
	}
	
	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.poc_four_item, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
		holder.tvMonery.setText(mDatas.get(position).monery);
		holder.tvDate.setText(mDatas.get(position).date);
		holder.tvSummary.setText(mDatas.get(position).summary);
		holder.tvBalanceMonery.setText(mDatas.get(position).balance_monery);
		holder.tvInfo.setText(mDatas.get(position).info);
		holder.root.setOnClickListener(v -> {
			if (mOnItemClickLitener != null) {
				mOnItemClickLitener.onItemClick(position);
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return mDatas.size();
	}
	
	public void setOnItemClickLitener(MyOnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	
	class MyViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.root) View root;
		@BindView(R.id.tv_monery) TextView tvMonery;
		@BindView(R.id.tv_date) TextView tvDate;
		@BindView(R.id.tv_summary) TextView tvSummary;
		@BindView(R.id.tv_balance_monery) TextView tvBalanceMonery;
		@BindView(R.id.tv_info) TextView tvInfo;
		
		public MyViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}