package com.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.data.greendao.Task;
import com.ui.fragment.VPItemFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter{
	
	public List<ArrayList<Task>> mList = new ArrayList<ArrayList<Task>>();
	private ArrayList<VPItemFragment> mFragments = null;
	private final String[] titles = { "待处理", "进展中", "已完成"};
	
	public MainViewPagerAdapter(FragmentManager fm){
		super(fm);
		mFragments = new ArrayList<VPItemFragment>();
	}
	public MainViewPagerAdapter(Context context, List<ArrayList<Task>> list, FragmentManager fm) {
		super(fm);
		mFragments = new ArrayList<VPItemFragment>();
		
		for (int i=0;i<titles.length;i++)
			mList.add(new ArrayList<Task>());
		for (int i=0;i<titles.length;i++) {
			mFragments.add(new VPItemFragment(mList.get(i)));
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Fragment getItem(int position) {
		// 返回要显示的Fragment
		return mFragments.get(position);
		
	}
	
	public void notifyDataChanged(List<ArrayList<Task>> list) {
		if (list != null) {
			for (int i=0;i<3;i++) {
				mList.get(i).clear();
				if (list.get(i) != null && list.get(i).size() > 0) {
					mList.get(i).addAll(list.get(i));
					if (null != mFragments.get(i).mainVPItemAdapter)
						mFragments.get(i).mainVPItemAdapter.notifyDataSetChanged();
				}
			}
		}
	}
	
}
