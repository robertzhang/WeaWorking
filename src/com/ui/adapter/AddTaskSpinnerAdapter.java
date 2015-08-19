package com.ui.adapter;

import java.util.List;

import com.data.greendao.Channel;
import com.data.greendao.User;
import com.processor.ProcessorImpl;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AddTaskSpinnerAdapter extends BaseAdapter{

	private Context mContext;
	private List<User> list;
	private LayoutInflater mInflater;
	
	public AddTaskSpinnerAdapter(Context pContext, List<User> list){
		this.list = list;
		mContext = pContext;
		mInflater = LayoutInflater.from(pContext);
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = mInflater.inflate(android.R.layout.simple_spinner_item, arg2, false);
		
		return view;
	}
	
}
