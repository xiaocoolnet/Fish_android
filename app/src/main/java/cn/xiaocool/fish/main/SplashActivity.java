/*
 * Copyright (c) 2016 4 10.
 * 版权所有 Hello Fish工作室
 * 用途：社交软件
 */

package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.utils.MyConstants;
import cn.xiaocool.fish.utils.SpTools;

public class SplashActivity extends Activity {

    private ImageView iv_splash;
    private AnimationSet as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        startAnimation(); // 开始动画
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        isAnimationEnd();
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        iv_splash= (ImageView) findViewById(R.id.iv_splash);
    }

    private void isAnimationEnd() {

        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            // 监听动画播完
            @Override
            public void onAnimationEnd(Animation animation) {
                // 判断进入向导界面还是主界面
                if (SpTools.getBoolean(getApplicationContext(), MyConstants.ISSETUP, false)){
                    // true，设置过 ，直接进入主界面
                    IntentUtils.getIntent(SplashActivity.this, LoginActivity.class);
                } else {
                    // false 没设置过，进入设置向导界面
                    IntentUtils.getIntent(SplashActivity.this, GuideActivity.class);
                }
                finish(); // 关闭自己
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startAnimation() {
        as=new AnimationSet(false);

//        RotateAnimation ra=new RotateAnimation(0,360,
//                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f); // 设置锚点为图片的中心点
//        ra.setDuration(1000); // 设置动画播放时间
//        ra.setFillAfter(true); // 动画播放完之后，停留在当前状态
//        as.addAnimation(ra); // 添加到动画集

        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(1000);
        aa.setFillAfter(true);
        as.addAnimation(aa);

        ScaleAnimation sa=new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(1000);
        sa.setFillAfter(true);
        as.addAnimation(sa);

        iv_splash.startAnimation(as); // 播放动画
        // 动画播完进入下一个界面 向导界面和主界面(调用onAnimationEnd)
    }

}
