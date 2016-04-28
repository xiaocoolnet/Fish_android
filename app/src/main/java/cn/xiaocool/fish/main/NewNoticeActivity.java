/*
 * Copyright (c) 2016 4 19.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （系统公告）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;

public class NewNoticeActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private String result_data;
    private String title,content,time;
    private TextView tv_new_notice_title,tv_new_notice_time,tv_new_notice_content;
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Toast.makeText(NewNoticeActivity.this, "网络问题，请稍后重试！", 0).show();
                    break;
                case 0:
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String dataoj = json.getString("data");

						JSONArray jsonArray = new JSONArray(dataoj);
						JSONObject ojArrayNotice = jsonArray.getJSONObject(0);
                        title = ojArrayNotice.getString("title");
                        content = ojArrayNotice.getString("content");
                        time = ojArrayNotice.getString("create_time");

                        sharedPreferences = getSharedPreferences("new_notice_content", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("title", title);
                        editor.putString("content", content);
                        editor.putString("time", time);
                        editor.commit();// 提交修改

                        tv_new_notice_title.setText("标题："+title.toString());
                        tv_new_notice_time.setText("时间："+time.toString());
                        tv_new_notice_content.setText("内容："+content.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        getNotice();
        getNewNotice();
        initEvent(); // 初始化事件
    }

    private void getNewNotice() {
        SharedPreferences locate = getSharedPreferences("new_notice_content", MODE_PRIVATE);
        String getTitle = locate.getString("title", "");
        String getTime = locate.getString("time", "");
        String getContent = locate.getString("content", "");
        tv_new_notice_title.setText("标题："+getTitle.toString());
        tv_new_notice_time.setText("时间："+getTime.toString());
        tv_new_notice_content.setText("内容：" + getContent.toString());
    }

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
    }

    private void getNotice() {
        //线程
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(NewNoticeActivity.this)){
                result_data = HttpTool.NewSystemNotice("1", NetBaseConstant.Token);
                handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(2); // 输出：网络连接有问题！
                }
            }
        }.start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_new_system_notice); // 控件实例化

        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        tv_new_notice_title = (TextView) findViewById(R.id.tv_new_notice_title);
        tv_new_notice_time = (TextView) findViewById(R.id.tv_new_notice_time);
        tv_new_notice_content = (TextView) findViewById(R.id.tv_new_notice_content);
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
