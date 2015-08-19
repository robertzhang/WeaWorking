package com.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.weaworking.R;

public class SettingActivity extends Activity {
	
	private ImageButton setting_title_bar_left_ib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settting_layout);
		initView();
	}
	
	public void initView() {
		setting_title_bar_left_ib = (ImageButton)findViewById(R.id.setting_title_bar_left_ib);
	}
	
}
