/*
 * Copyright (c) 2016 3 29.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （忘记密码重设密码Activity）
 */

package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.xiaocool.fish.R;

public class ResetPasswordActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvet(); // 初始化事件
    }

    private void initEvet() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_reset_password);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit : // 返回上一页
                finish();
                break;
            default:
                break;
        }
    }
}
