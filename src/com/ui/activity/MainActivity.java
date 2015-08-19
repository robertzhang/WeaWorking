package com.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.data.greendao.utils.GreenDaoFactory;
import com.example.weaworking.R;
import com.processor.APIConstants;
import com.processor.LoadManager;
import com.processor.ProcessorImpl;
import com.ui.fragment.MainFragment;
import com.ui.fragment.MenuFragment;
import com.ui.view.slidingmenu.SlidingMenu;

public class MainActivity extends BaseActivity {

	private Fragment mContent;
	private MenuFragment mMenuFragment = null;
	
	public MainActivity() {
		super(R.string.main_title);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ProcessorImpl.getInstance(getApplicationContext()).onLoadData(LoadManager.LOAD_INIT,  GreenDaoFactory.NONE, APIConstants.SERVER_IP+APIConstants.INIT_URL);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new MainFragment();
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		mMenuFragment = new MenuFragment(mContent);
		ProcessorImpl.getInstance(getApplicationContext()).setView(mMenuFragment);
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, mMenuFragment)
		.commit();
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}
}
