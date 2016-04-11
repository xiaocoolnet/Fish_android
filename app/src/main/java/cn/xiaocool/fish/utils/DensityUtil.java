/*
 * Copyright (c) 2016 4 10.
 * 版权所有 Hello Fish工作室
 * 用途：社交软件
 */

package cn.xiaocool.fish.utils;
import android.content.Context;

public class DensityUtil
{
	 /*** 根据手机的分辨率从 dip 的单位 转成为 px(像素)*/
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }

    /*** 根据手机的分辨率从 px(像素) 的单位 转成为 dp*/
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
