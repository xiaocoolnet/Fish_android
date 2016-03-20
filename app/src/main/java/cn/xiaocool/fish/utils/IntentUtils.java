/*
 * Copyright (c) 2016 3 8.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP
 */

package cn.xiaocool.fish.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentUtils {

    public static Intent getIntent(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        return intent;
    }

    public static Intent getIntents(Context mContext, Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
        return intent;
    }

}