package com.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.data.greendao.Activity;
import com.data.greendao.utils.GreenDaoFactory;
import com.example.weaworking.R;
import com.processor.APIConstants;
import com.processor.LoadManager;
import com.processor.ProcessorImpl;
import com.processor.utils.LogUitls;
import com.ui.adapter.TaskAdapter;
import com.ui.view.PullUpAndDownRefreshListView;
import com.ui.view.PullUpAndDownRefreshListView.OnRefreshListener;

public class MainFragment extends Fragment {

	private PullUpAndDownRefreshListView feeds_lv;
	private String menu_item_content = "1";
	public List<Activity> feeds = new ArrayList<Activity>();
	public TaskAdapter mTaskItemAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null)
			menu_item_content = savedInstanceState.getString("menu_item_content");
		View view = inflater.inflate(R.layout.channel_content_layout, null);
		feeds_lv = (PullUpAndDownRefreshListView) view.findViewById(R.id.feeds_lv);
		mTaskItemAdapter = new TaskAdapter(getActivity(), feeds);
		feeds_lv.setAdapter(mTaskItemAdapter);
		feeds_lv.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh(int loadtype) {
				if (loadtype == LoadManager.LOAD_NEW) {
					LoadManager.dashboard_page = 0;
				}
				LogUitls.LogE("dashboard_page----"+LoadManager.dashboard_page);
				ProcessorImpl.getInstance(getActivity()).mLoadManager.
					getNetData(loadtype, GreenDaoFactory.ACTIVITY, 
							APIConstants.SERVER_IP+APIConstants.INIT_URL+
								"?page="+LoadManager.getDashBoardNextPage());
			}
		});
		LogUitls.LogE("oncreateView ---");
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("menu_item_content", menu_item_content);
	}
	
	public void notifyChangeData(int loadtype){
		LogUitls.LogE("notifyChangeData ---");
		feeds.clear();
		feeds.addAll(ProcessorImpl.getInstance(getActivity()).mLoadManager.getActivityList());
		mTaskItemAdapter.notifyDataSetChanged();
		if(loadtype == LoadManager.LOAD_MORE || loadtype == LoadManager.LOAD_NEW){
			feeds_lv.onRefreshComplete(loadtype);
		}
	}
}
