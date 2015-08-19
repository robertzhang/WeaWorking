package com.data.greendao.utils;

import android.content.Context;

import com.data.greendao.Activity;
import com.data.greendao.ActivityDao;

public class ActivityUtils extends GreenDaoUtilsAbs<Activity, ActivityDao>{

	private static ActivityUtils mInstance = null;

	public static ActivityUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new ActivityUtils(context);
		}
		return mInstance;
	}
	public ActivityUtils(Context context){
		setDao(context);
	}
	@Override
	protected void setDao(Context context) {
		dao = BaseApplication.getDaoSession(context).getActivityDao();
	}

}
