/*
 * Copyright (c) 2016 4 22.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （发布渔获Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import cn.xiaocool.fish.R;

public class PublishActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_publish); // 添加好友界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit :
                finish();
                break;
            default:
                break;
        }
    }

}
