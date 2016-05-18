/*
 * Copyright (c) 2016 4 4.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （首页公告）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;

public class HomeNoticeActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private TextView tv_notice_title;
    private TextView tv_notice_content;
    private int position_int;
    private String position;
    private String notice_title_1;
    private String notice_title_2;
    private String notice_title_3;
    private String notice_title_4;
    private String notice_content_1;
    private String notice_content_2;
    private String notice_content_3;
    private String notice_content_4;

    private Handler h = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(HomeNoticeActivity.this, "网络连接成功", 0).show();
                    break;
                case 2:
                    Toast.makeText(HomeNoticeActivity.this, "网络问题，请稍后重试！", 0).show();
                    break;
                case 3:

                    break;
                default:
                    break;
            }
        }

    };

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
        setContentView(R.layout.activity_notice); // 主页公告1界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        tv_notice_title = (TextView) findViewById(R.id.tv_notice_title);
        tv_notice_content = (TextView) findViewById(R.id.tv_notice_content);

        getPosition();
        setContent();

    }

    private void setContent() {
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(HomeNoticeActivity.this)){
                    h.sendEmptyMessage(3);// 调用服务器登录函数
                }else {
                    h.sendEmptyMessage(2); // 输出：网络连接有问题！
                }
            }
        }.start();
        return;
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

    private void getPosition() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        position_int = bundle.getInt("position");
        position = position_int+"";
        SharedPreferences home_notice = getSharedPreferences("home_notice", MODE_PRIVATE);
        notice_title_1 = home_notice.getString("notice_title_1", "");
        notice_title_2 = home_notice.getString("notice_title_2", "");
        notice_title_3 = home_notice.getString("notice_title_3", "");
        notice_title_4 = home_notice.getString("notice_title_4", "");
        notice_content_1 = home_notice.getString("notice_content_1", "");
        notice_content_2 = home_notice.getString("notice_content_2", "");
        notice_content_3 = home_notice.getString("notice_content_3", "");
        notice_content_4 = home_notice.getString("notice_content_4", "");
        if(position.equals("0")){
            tv_notice_title.setText(notice_title_1);
            tv_notice_content.setText(notice_content_1);
        }else if (position.equals("1")){
            tv_notice_title.setText(notice_title_2);
            tv_notice_content.setText(notice_content_2);
        }else if (position.equals("2")){
            tv_notice_title.setText(notice_title_3);
            tv_notice_content.setText(notice_content_3);
        }else {
            tv_notice_title.setText(notice_title_4);
            tv_notice_content.setText(notice_content_4);
        }
    }

}

