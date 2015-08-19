package com.data.greendao.utils;

import android.content.Context;

/*
 * 1.Factory使用工厂模式
 * 2.Factory用来创建GreenDaoUtilsAbs的子对象，
 * 这些子对象是dao数据的实际操作对象。
 */
public class GreenDaoFactory {
	
	public static final int NONE				= 0;//全部数据
	public static final int ACTIVITY 			= 1;
	public static final int CHANNEL 			= 2;
	public static final int NOTE 				= 3;
	public static final int TASK 				= 4;
	public static final int USER 				= 5;
	public static final int ALLUSER				= 6;
	public static final int CHANNEL_GROUP		= 7;
//	public static final int CURRENT_USER		= 8;
	
	/**
	 * 创建相应的Data工具
	 * @param context
	 * @param dao
	 * @return
	 */
	public static DataUtils builder(Context context, int dao){
		DataUtils db = null;
		switch (dao) {
		case ACTIVITY:
			db = ActivityUtils.getInstance(context);
			break;
		case CHANNEL:
			db = ChannelUtils.getInstance(context);
			break;
		case NOTE:
			db = NoteUtils.getInstance(context);
			break;
		case TASK:
			db = TaskUtils.getInstance(context);
			break;
		case USER:
			db = UserUtils.getInstance(context);
			break;
		}
		return db;
	}
	
}


