package com.data.greendao.utils;

import android.content.Context;

import com.data.greendao.Task;
import com.data.greendao.TaskDao;

public class TaskUtils extends GreenDaoUtilsAbs<Task, TaskDao>{

	private static TaskUtils mInstance = null;

	public static TaskUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new TaskUtils(context);
		}
		return mInstance;
	}
	public TaskUtils(Context context){
		setDao(context);
	}
	@Override
	protected void setDao(Context context) {
		dao = BaseApplication.getDaoSession(context).getTaskDao();
	}

}