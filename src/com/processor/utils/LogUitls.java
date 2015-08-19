package com.processor.utils;

public class LogUitls {
public static final String Tag = "robert.zhang";
	
	public static boolean isLog = true;
	
	/**
	 * Error Log
	 * @param str
	 */
	public static void LogE(String str){
		if(isLog){
			android.util.Log.e(Tag, str);
		}
	}
	
	/**
	 * DeBug Log
	 * @param str
	 */
	public static void LogD(String str){
		if(isLog)
			android.util.Log.d(Tag, str);
	}
	
	/**
	 * Info Log
	 * @param str
	 */
	public static void LogI(String str){
		if(isLog)
			android.util.Log.i(Tag, str);
	}
	
	/**
	 * Warn Log
	 * @param str
	 */
	public static void LogW(String str){
		if(isLog)
			android.util.Log.w(Tag, str);
	}
}
