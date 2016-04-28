/*
 * Copyright (c) 2016 4 20.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （用户反馈Activity）
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;

public class FeedBackActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private TextView tv_feedback_name;
    private SharedPreferences sharedPreferences;
    private EditText tv_feedback_content;
    private Button btn_feedback;
    private String result_data;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Toast.makeText(FeedBackActivity.this,"网络问题，请稍后重试！",0).show();
                    break;
                case 0:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")){
                            Toast.makeText(FeedBackActivity.this,"提交成功",0).show();
                        }
                        else {
                            Toast.makeText(FeedBackActivity.this,"提交失败",0).show();
                        }
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
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
        btn_feedback.setOnClickListener(this);
        getUserName();
    }

    private void getUserName() {
        sharedPreferences = getSharedPreferences("user_info", Activity.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        tv_feedback_name.setText(userName.toString());
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_feedback); // 用户反馈界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        tv_feedback_name = (TextView) findViewById(R.id.tv_feedback_name);
        tv_feedback_content = (EditText) findViewById(R.id.tv_feedback_content);
        btn_feedback = (Button) findViewById(R.id.btn_feedback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit :
                finish();
                break;
            case R.id.btn_feedback :
                GitFeedBack();
                break;
            default:
                break;
        }
    }

    private void GitFeedBack() {
        //线程
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(FeedBackActivity.this)){
                sharedPreferences = getSharedPreferences("user_id", Activity.MODE_PRIVATE);
                String user_id = sharedPreferences.getString("user_id", "");
                result_data = HttpTool.AddFeedBack(user_id, String.valueOf(tv_feedback_content.getText()), NetBaseConstant.Token);
                handler.sendEmptyMessage(0);
                }else {
                    handler.sendEmptyMessage(2); // 输出：网络连接有问题！
                }
            }
        }.start();
    }

}
