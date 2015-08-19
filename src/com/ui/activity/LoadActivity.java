package com.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LoadActivity extends Activity{
	/*
	 * 可以在此类中做登陆操作。
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
	
}
