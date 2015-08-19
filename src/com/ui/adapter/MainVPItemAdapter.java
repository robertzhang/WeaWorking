package com.ui.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.data.greendao.Task;
import com.example.weaworking.R;
import com.processor.ProcessorImpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainVPItemAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<Task> mTask;
	private LayoutInflater mInflater;
	
	public MainVPItemAdapter(Context context, List<Task> list){
		mContext = context;
		mTask = list;
		mInflater = LayoutInflater.from(context);
	}
	public int getCount() {
		return mTask.size();
	}

	public Object getItem(int arg0) {
		return mTask.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder vh;
		if (null == convertView) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.main_vp_item_layout, viewGroup, false);
			vh.main_vp_item_channel_name_tv = (TextView) convertView.findViewById(R.id.main_vp_item_channel_name_tv);
			vh.main_vp_item_time_tv = (TextView) convertView.findViewById(R.id.main_vp_item_time_tv);
			vh.main_vp_item_task_name_tv = (TextView) convertView.findViewById(R.id.main_vp_item_task_name_tv);
			vh.main_vp_item_user_name = (TextView) convertView.findViewById(R.id.main_vp_item_user_name);
			vh.main_vp_item_assigned_name = (TextView) convertView.findViewById(R.id.main_vp_item_assigned_name);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.main_vp_item_channel_name_tv.setText(ProcessorImpl.getInstance(mContext).
				mLoadManager.getChannelName(mTask.get(position).getChannel_id()));
		
		vh.main_vp_item_time_tv.setText(formatDate(mTask.get(position).getCreated_at()));
		
		vh.main_vp_item_task_name_tv.setText(mTask.get(position).getTitle());
		
		vh.main_vp_item_user_name.setText(ProcessorImpl.getInstance(mContext).
				mLoadManager.getUserName(mTask.get(position).getUser_id()));
		
		vh.main_vp_item_assigned_name.setText(ProcessorImpl.getInstance(mContext).
				mLoadManager.getUserName(mTask.get(position).getAssigned_user_id()));
		
		return convertView;
	}
	
	class ViewHolder{
		private TextView main_vp_item_channel_name_tv;
		private TextView main_vp_item_time_tv;
		private TextView main_vp_item_task_name_tv;
		private TextView main_vp_item_user_name;
		private TextView main_vp_item_assigned_name;
		
	}
	
	public String formatDate(String str) {
		Date date = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date =  df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(date);
	}
}
