/*
 * Copyright (c) 2016 3 9.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （注册设置密码Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.xiaocool.fish.R;

public class SetPasswordActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private EditText ed_get_phone_number; // 获得用户的手机号
    private Button btn_finish; // 完成注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        getPhoneNum(); // 获得上个Activity传送来的手机号

        // 添加点击事件
        btn_exit.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_set_password);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        ed_get_phone_number = (EditText) findViewById(R.id.ed_get_phone_number);
        btn_finish = (Button) findViewById(R.id.btn_finish);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit : // 返回上一页
                finish();
                break;
            case R.id.btn_finish : // 完成注册
        }
    }

    private void getPhoneNum() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ed_get_phone_number.setText(bundle.getString("phone"));
    }

}
