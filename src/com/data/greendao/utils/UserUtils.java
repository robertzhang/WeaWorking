package com.data.greendao.utils;

import android.content.Context;

import com.data.greendao.User;
import com.data.greendao.UserDao;

public class UserUtils extends GreenDaoUtilsAbs<User, UserDao>{

	private static UserUtils mInstance = null;

	public static UserUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new UserUtils(context);
		}
		return mInstance;
	}
	public UserUtils(Context context){
		setDao(context);
	}
	@Override
	protected void setDao(Context context) {
		dao = BaseApplication.getDaoSession(context).getUserDao();
	}

}

