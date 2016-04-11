/*
 * Copyright (c) 2016 3 1.
 * 版权所有 Hello Fish工作室
 * 用途：社交软件
 */

package cn.xiaocool.fish.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpTools
{
	public static void setBoolean(Context context,String key,boolean value){
		SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();//提交保存键值对
	}

	public static boolean getBoolean(Context context,String key,boolean defValue){
		SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
}
