/*
 * Copyright (c) 2016 3 26.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （天气Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xiaocool.fish.R;

public class WeatherActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private TextView txt_weather_city, txt_weather_temp, txt_weather_detail;

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
        setContentView(R.layout.activity_weather); // 登录界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        txt_weather_city = (TextView) findViewById(R.id.txt_weather_city);
        txt_weather_temp = (TextView) findViewById(R.id.txt_weather_temp);
        txt_weather_detail = (TextView) findViewById(R.id.txt_weather_detail);
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
