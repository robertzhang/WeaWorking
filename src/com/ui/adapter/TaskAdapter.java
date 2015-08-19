package com.ui.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.data.greendao.Activity;
import com.example.weaworking.R;
import com.processor.ProcessorImpl;

public class TaskAdapter extends BaseAdapter implements OnClickListener{

	private LayoutInflater mInflater;
	private Context context;
	
	public List<Activity> list;
	
	
	public TaskAdapter(Context context, List<Activity> list){
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.task_note_item_layout, viewGroup, false);
			holder.task_note_item_name = (TextView)convertView.findViewById(R.id.task_note_item_name);
			holder.task_note_item_action = (TextView)convertView.findViewById(R.id.task_note_item_action);
			holder.task_note_item_channel_name = (TextView)convertView.findViewById(R.id.task_note_item_channel_name);
			holder.task_note_item_task_name = (TextView)convertView.findViewById(R.id.task_note_item_task_name);
			holder.task_note_item_time = (TextView)convertView.findViewById(R.id.task_note_item_time);
			holder.task_note_item_progress_tv = (TextView)convertView.findViewById(R.id.task_note_item_progress_tv);
			holder.task_note_item_delete = (ImageButton)convertView.findViewById(R.id.task_note_item_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.task_note_item_name.setText(ProcessorImpl.getInstance(context).
				mLoadManager.getUserName(list.get(position).getUser_id().intValue()));
		holder.task_note_item_action.setText(getActionNameId(position));
		holder.task_note_item_time.setText(formatDate(list.get(position).getCreated_at()));//记录时间需要，重新生成activity，添加create_at参数
		if (list.get(position).getTarget_type().equals("Note")) {
			holder.task_note_item_progress_tv.setVisibility(View.VISIBLE);
			holder.task_note_item_progress_tv.setText(ProcessorImpl.getInstance(context).
				mLoadManager.getNoteAsId(list.get(position).getTarget_id().longValue()).getContent());
		} else {
			holder.task_note_item_progress_tv.setVisibility(View.GONE);
		}
		
		holder.task_note_item_delete.setOnClickListener(this);
		holder.task_note_item_delete.setTag(position);
		
		return convertView;
	}
	
	//通过activity,target_type,action等，获取task_note_item_action的内容
	public String getActionNameId(int position){
		String res = null;
		if (list.get(position).getTarget_type().equals("Task")) {
			if (list.get(position).getAction().equals("create")) {
				res = context.getResources().getString(R.string.create_new_task_string);
			} else if (list.get(position).getAction().equals("todo")) {
				res = String.format(context.getResources().getString(R.string.sign_task_string), "待处理") ;
			} else if (list.get(position).getAction().equals("doing")) {
				res = String.format(context.getResources().getString(R.string.sign_task_string), "进展中");
			} else if (list.get(position).getAction().equals("done")) {
				res = String.format(context.getResources().getString(R.string.sign_task_string), "已完成");
			}
		} else if (list.get(position).getTarget_type().equals("Note")) {
			if (list.get(position).getAction().equals("create")) {
				res = context.getResources().getString(R.string.add_task_progress_string);
			}
		}
		
		return res;
	}
	
	class ViewHolder {
		TextView task_note_item_name, task_note_item_action, 
			task_note_item_time, task_note_item_progress_tv,
			task_note_item_channel_name,task_note_item_task_name;
		ImageButton task_note_item_delete;
	}

	@Override
	public void onClick(View arg0) {
		//添加删除该条进展
		list.remove(arg0.getTag());
		notifyDataSetChanged();
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
