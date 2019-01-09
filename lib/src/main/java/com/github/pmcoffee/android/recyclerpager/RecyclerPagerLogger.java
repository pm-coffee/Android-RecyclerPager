package com.github.pmcoffee.android.recyclerpager;

import android.util.Log;

public class RecyclerPagerLogger {
	
	private static boolean enableLog = false;
	
	public static void d(String tag, String message){
		if(enableLog) {
			Log.d(tag, message);
		}
	}
	
	public static void e(String tag, String message, Throwable t){
		if(enableLog) {
			Log.e(tag, message, t);
		}
	}
	
	public static boolean isEnableLog() {
		return enableLog;
	}
	
	public static void setEnableLog(boolean enableLog) {
		RecyclerPagerLogger.enableLog = enableLog;
	}
}
