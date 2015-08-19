package com.data.greendao.utils;

import android.content.Context;

import com.data.greendao.Channel;
import com.data.greendao.ChannelDao;

public class ChannelUtils extends GreenDaoUtilsAbs<Channel, ChannelDao>{

	private static ChannelUtils mInstance = null;

	public static ChannelUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new ChannelUtils(context);
		}
		return mInstance;
	}
	public ChannelUtils(Context context){
		setDao(context);
	}
	@Override
	protected void setDao(Context context) {
		dao = BaseApplication.getDaoSession(context).getChannelDao();
	}

}
