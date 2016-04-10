/*
 * Copyright (c) 2016 4 9.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （用户信息设置Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.bean.UserInfo;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.utils.ToastUtils;

public class UserSetInfoActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private EditText et_get_user_name;
    private EditText et_get_user_age;
    private EditText et_get_user_sex;
    private EditText et_get_user_city;
    private Button btn_setuserinfofinish;
    private Context mContext;
    private String result_data;
    private UserInfo user;
    private String user_id,user_name,user_age,user_sex,user_city;
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(UserSetInfoActivity.this,"修改资料成功", Toast.LENGTH_SHORT).show();
                            IntentUtils.getIntent(UserSetInfoActivity.this, UserActivity.class);
                        } else {
                            Toast.makeText(UserSetInfoActivity.this,"----", Toast.LENGTH_SHORT).show();
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
        btn_setuserinfofinish.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_usersetinfo); // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        et_get_user_name = (EditText) findViewById(R.id.et_get_user_name);
        et_get_user_age = (EditText) findViewById(R.id.et_get_user_age);
        et_get_user_sex = (EditText) findViewById(R.id.et_get_user_sex);
        et_get_user_city = (EditText) findViewById(R.id.et_get_user_city);
        btn_setuserinfofinish = (Button) findViewById(R.id.btn_setuserinfofinish);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_setuserinfofinish:
                Finish_SetUserInfo(); // 提交用户资料
                break;
            default:
                break;
        }
    }

    private void Finish_SetUserInfo() {
        sharedPreferences = getSharedPreferences("user_id", Activity.MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", "");
        user_name = et_get_user_name.getText().toString();
        user_age = et_get_user_age.getText().toString();
        user_sex = et_get_user_sex.getText().toString();
        user_city = et_get_user_city.getText().toString();
        //线程
        new Thread() {
            public void run() {
                        result_data = HttpTool.EditUserInfo(user_id, user_name,user_sex,user_age,user_city, NetBaseConstant.Token);
                        handler.sendEmptyMessage(1); // 调用服务器登录函数
                }
        }.start();

    }

}
