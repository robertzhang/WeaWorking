package com.data.greendao.utils;

import android.content.Context;

import com.data.greendao.Note;
import com.data.greendao.NoteDao;

public class NoteUtils extends GreenDaoUtilsAbs<Note, NoteDao>{
	private static NoteUtils mInstance = null;

	public static NoteUtils getInstance(Context context){
		if(null == mInstance){
			mInstance = new NoteUtils(context);
		}
		return mInstance;
	}
	public NoteUtils(Context context){
		setDao(context);
	}
	@Override
	protected void setDao(Context context) {
		dao = BaseApplication.getDaoSession(context).getNoteDao();
	}
}
