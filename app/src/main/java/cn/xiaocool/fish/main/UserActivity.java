/*
 * Copyright (c) 2016 4 9.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （用户信息Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;
import cn.xiaocool.fish.ui.RoundImageView;
import cn.xiaocool.fish.utils.IntentUtils;

public class UserActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private RoundImageView iv_user_info_image;
    private TextView tv_get_user_name;
    private TextView tv_get_user_sex;
    private TextView tv_get_user_age;
    private TextView tv_get_user_city;
    private Button btn_setuserinfo;
    private SharedPreferences sharedPreferences;
    private String result_data;
    private String userName;
    private String userSex;
    private String userCity;
    private String userAge;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")){
                            String data = jsonObject.getString("data");
                            JSONObject object = new JSONObject(data);
                            userName = object.getString("user_nicename");
                            userAge = object.getString("age");
                            userSex = object.getString("sex");
                            if(userSex.equals("0")){
                                userSex="男";
                            }else{
                                userSex="女";
                            }
                            userCity = object.getString("city");
                            tv_get_user_name.setText(userName.toString());
                            tv_get_user_sex.setText(userSex.toString());
                            tv_get_user_age.setText(userAge.toString());
                            tv_get_user_city.setText(userCity.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:

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
        btn_setuserinfo.setOnClickListener(this);
        iv_user_info_image.setOnClickListener(this);
        User();
    }

    private void User() {
        //线程
        new Thread() {
            public void run() {
                sharedPreferences = getSharedPreferences("user_id", Activity.MODE_PRIVATE);
                String user_id = sharedPreferences.getString("user_id", "");
                result_data = HttpTool.GetUser(user_id, NetBaseConstant.Token);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_user); // 登录界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        tv_get_user_name = (TextView) findViewById(R.id.tv_get_user_name);
        tv_get_user_sex = (TextView) findViewById(R.id.tv_get_user_sex);
        tv_get_user_age = (TextView) findViewById(R.id.tv_get_user_age);
        tv_get_user_city = (TextView) findViewById(R.id.tv_get_user_city);
        btn_setuserinfo = (Button) findViewById(R.id.btn_setuserinfo);
        iv_user_info_image = (RoundImageView) findViewById(R.id.iv_user_info_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_setuserinfo:
                IntentUtils.getIntent(UserActivity.this, UserSetInfoActivity.class); // 跳转到编辑用户资料
                break;
            case R.id.iv_user_info_image:
                Intent intentCamera = new Intent(); // 跳转到系统拍照界面
                intentCamera.setAction("android.media.action.STILL_IMAGE_CAMERA");
                startActivity(intentCamera);
                break;
            default:
                break;
        }
    }

}
