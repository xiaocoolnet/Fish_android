/*
 * Copyright (c) 2016 5 02.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （我的鱼记Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import cn.xiaocool.fish.R;

public class MyMessageActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private ListView lv_fisher_message;

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
        setContentView(R.layout.activity_my_message);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        lv_fisher_message = (ListView) findViewById(R.id.lv_fisher_message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit :
                finish();
            default:
                break;
        }
    }

}
