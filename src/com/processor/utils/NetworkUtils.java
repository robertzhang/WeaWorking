package com.processor.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	/**
	 * 是否有网络连接
	 * 
	 * @param paramContext
	 * @return
	 */
	public static boolean hasNetwork(Context paramContext) {
		try {
			ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
			if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
				return true;
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return false;
	}

	/**
	 * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if (info == null)
			return false;
		return info.getType() == ConnectivityManager.TYPE_WIFI;
	}
}
