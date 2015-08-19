package com.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.data.greendao.Channel;
import com.data.greendao.Task;
import com.data.greendao.User;
import com.example.weaworking.R;
import com.processor.utils.LogUitls;
import com.ui.activity.AddTaskActivity;
import com.ui.activity.MainActivity;
import com.ui.adapter.MainViewPagerAdapter;
import com.ui.view.PagerSlidingTabStrip;
import com.ui.view.slidingmenu.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewPagerFragment extends Fragment implements OnClickListener{
	
	private DisplayMetrics dm;
	private PagerSlidingTabStrip main_tabs;
	private ViewPager main_pager;
	public MainViewPagerAdapter mMainViewPagerAdapter;
	
	public Object mObject;
	private ImageButton main_title_bar_left_ib;
	private ImageButton main_title_bar_right_ib;
	private TextView main_title_bar_tv;
	
	private Context mContext;
	public List<ArrayList<Task>> mList = null;
	
	public ViewPagerFragment(List<ArrayList<Task>> list, Object object){
		mObject = object;
		mList = list;
		mContext = getActivity();
	}
	public ViewPagerFragment(Context context, List<ArrayList<Task>> list, Object object){
		mObject = object;
		mContext = context;
		if(list != null && !list.isEmpty())
			mList = list;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dm = getResources().getDisplayMetrics();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		LogUitls.LogE("onCreateView");
		View view = inflater.inflate(R.layout.main_layout, null);
		
		initTitleBarLayout(view);//初始化title bar
		
		main_tabs = (PagerSlidingTabStrip)view.findViewById(R.id.main_tabs);
		main_pager = (ViewPager)view.findViewById(R.id.main_pager);
		
		mMainViewPagerAdapter = new MainViewPagerAdapter(mContext, mList, getActivity().getSupportFragmentManager());
		main_pager.setAdapter(mMainViewPagerAdapter);
		main_tabs.setViewPager(main_pager);
		setTabsValue();
		main_pager.setCurrentItem(0);
		
		//用于设置当viewpage滑动到position为0的时候，slidingmenu可以全屏滑动调出边栏，其他情况只有通过边界滑出
		main_tabs.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					((MainActivity)getActivity()).getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				} else {
					((MainActivity)getActivity()).getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				}
			}
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
			@Override
			public void onPageScrollStateChanged(int state) {}
		});
		return view;
	}

	public void initTitleBarLayout(View view){
		main_title_bar_left_ib = (ImageButton)view.findViewById(R.id.main_title_bar_left_ib);
		main_title_bar_right_ib = (ImageButton)view.findViewById(R.id.main_title_bar_right_ib);
		main_title_bar_tv = (TextView)view.findViewById(R.id.main_title_bar_tv);
		
		if (mObject != null){
			if (mObject instanceof Channel){
				main_title_bar_tv.setText(((Channel)mObject).getName());
			} else if (mObject instanceof User) {
				main_title_bar_tv.setText(((User)mObject).getName());
			}
		} else {
			main_title_bar_tv.setText("任务列表");
		}
		
		main_title_bar_left_ib.setOnClickListener(this);
		main_title_bar_right_ib.setOnClickListener(this);
	}
	
	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		main_tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		main_tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		main_tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		main_tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		main_tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		main_tabs.setIndicatorColor(getActivity().getResources().getColor(android.R.color.holo_red_dark));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		main_tabs.setSelectedTextColor(getActivity().getResources().getColor(android.R.color.holo_red_dark));
//		tabs.setSelectedTextColor(GeneralUtil.THEME_DEFAULT_COLOR);
		// 取消点击Tab时的背景色
		main_tabs.setTabBackground(0);
	}
	
	@Override
	public void onClick(View arg0) {
		
		if(arg0.getId() == R.id.main_title_bar_left_ib) {
			if (!((MainActivity)getActivity()).getSlidingMenu().isMenuShowing()) {
				((MainActivity)getActivity()).getSlidingMenu().showMenu();
			} else {
				((MainActivity)getActivity()).getSlidingMenu().showContent();
			}
		} else if (arg0.getId() == R.id.main_title_bar_right_ib) {
			Intent intent = new Intent(getActivity(), AddTaskActivity.class);
			startActivity(intent);
			// 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画   
			getActivity().overridePendingTransition(R.anim.add_task_slide_in_left,  
                    R.anim.add_task_slide_out_right);  
		}
		
	}
	
	/**
	 * 更新content ui的Task list内容UI
	 * @param list
	 */
	public void upadtaVPFragmentUI(List<ArrayList<Task>> list){
		mList = list;
		LogUitls.LogE(list.toString());
		mMainViewPagerAdapter.notifyDataChanged(mList);
	}
	
}
