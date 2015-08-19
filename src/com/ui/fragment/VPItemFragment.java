package com.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.data.greendao.Task;
import com.example.weaworking.R;
import com.ui.activity.TaskActivity;
import com.ui.adapter.MainVPItemAdapter;

public class VPItemFragment extends Fragment{
	
	public List<Task> mTasks;
	public MainVPItemAdapter mainVPItemAdapter;
	public VPItemFragment(List<Task> tasks){
		mTasks = tasks;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(android.R.layout.list_content, null);
		ListView lv = (ListView)view.findViewById(android.R.id.list);
		mainVPItemAdapter = new MainVPItemAdapter(getActivity(), mTasks);
		lv.setAdapter(mainVPItemAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {//选中item，进入一个新的activity
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(getActivity(), TaskActivity.class);
				intent.putExtra("task_id", ((Task)mainVPItemAdapter.getItem(position)).getId());
				startActivity(intent);
				// 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画   
				getActivity().overridePendingTransition(R.anim.add_task_slide_in_left,  
	                    R.anim.add_task_slide_out_right); 
			}
		});
		return view;
	}
	
}
