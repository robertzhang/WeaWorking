package com.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.data.greendao.Activity;
import com.example.weaworking.R;
import com.processor.ProcessorImpl;
import com.processor.utils.LogUitls;
import com.ui.adapter.TaskAdapter;

public class MainFragment extends Fragment {

	private ListView feeds_lv;
	private String menu_item_content = "1";
	public List<Activity> feeds = new ArrayList<Activity>();
	public TaskAdapter mTaskItemAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null)
			menu_item_content = savedInstanceState.getString("menu_item_content");
		View view = inflater.inflate(R.layout.channel_content_layout, null);
		feeds_lv = (ListView) view.findViewById(R.id.feeds_lv);
		mTaskItemAdapter = new TaskAdapter(getActivity(), feeds);
		feeds_lv.setAdapter(mTaskItemAdapter);
		LogUitls.LogE("oncreateView ---");
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("menu_item_content", menu_item_content);
	}
	
	public void notifyChangeData(){
		LogUitls.LogE("notifyChangeData ---");
		feeds.clear();
		feeds.addAll(ProcessorImpl.getInstance(getActivity()).mLoadManager.getActivityList());
		mTaskItemAdapter.notifyDataSetChanged();
	}
}
