/*
 * Copyright (c) 2016 4 9.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （用户信息设置Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

public class UserSetInfoActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private EditText et_get_user_name;
    private EditText et_get_user_age;
    private EditText et_get_user_city;
    private CheckBox select_sex;
    private Button btn_setuserinfofinish;
    private String result_data;
    private UserInfo user;
    private String user_id,user_name,user_sex,user_age,user_city;
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    Toast.makeText(UserSetInfoActivity.this,"网络问题，请稍后重试！",0).show();
                    break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(UserSetInfoActivity.this,"修改资料成功", Toast.LENGTH_SHORT).show();
                            Delay(1000);
                            IntentUtils.getIntent(UserSetInfoActivity.this, UserActivity.class); // 跳转到编辑用户资料
                        } else {
                            Toast.makeText(UserSetInfoActivity.this,"修改资料失败", Toast.LENGTH_SHORT).show();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_usersetinfo); // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        et_get_user_name = (EditText) findViewById(R.id.et_get_user_name);
        et_get_user_age = (EditText) findViewById(R.id.et_get_user_age);
        et_get_user_city = (EditText) findViewById(R.id.et_get_user_city);
        btn_setuserinfofinish = (Button) findViewById(R.id.btn_setuserinfofinish);
        select_sex = (CheckBox) findViewById(R.id.select_sex);
    }

    @Override
    protected void onResume() {

        super.onResume();
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
        user_city = et_get_user_city.getText().toString();
        if (select_sex.isChecked()) {
            select_sex.setText("女");
            user_sex="1";
        }else {
            select_sex.setText("男");
            user_sex="0";
        }
        //线程
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(UserSetInfoActivity.this)){
                result_data = HttpTool.EditUserInfo(user_id, user_name,user_sex,user_age,user_city, NetBaseConstant.Token);
                handler.sendEmptyMessage(1); // 调用服务器登录函数
                }else {
                    handler.sendEmptyMessage(2); // 输出：网络连接有问题！
                }
            }
        }.start();

    }

    private void Delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
