/*
 * Copyright (c) 2016 3 9.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （注册设置密码Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.DemoApplication;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.view.FishApplication;

public class SetPasswordActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private EditText ed_get_phone_number; // 获得用户的手机号
    private Button btn_finish; // 完成注册
    private EditText edit_password1;
    private EditText edit_password2;
    private String code;
    private TextView yzcode;
    private String setPassword;
    private String reSetPassword;
    private int deviceState;
    private String result_data;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){

                case 0:
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            //实力化缓存类
//                            JSONObject item = new JSONObject(data);
//                            FishApplication.UID = Integer.parseInt(item.getString("id"));
                            IntentUtils.getIntent(SetPasswordActivity.this, LoginActivity.class);
                            Toast.makeText(SetPasswordActivity.this,"注册成功",0).show();
                        } else {
                            Toast.makeText(SetPasswordActivity.this,"注册失败，重新注册",0).show();
                            IntentUtils.getIntent(SetPasswordActivity.this, LoginActivity.class);

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


    public void back(View view) {
        finish();
    }

    private void initEvent() {
        getPhoneNum(); // 获得上个Activity传送来的手机号

        // 添加点击事件
        btn_exit.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_set_password);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        ed_get_phone_number = (EditText) findViewById(R.id.ed_get_phone_number);
        yzcode = (TextView) findViewById(R.id.code);
        edit_password1 = (EditText) findViewById(R.id.edit_password1);
        edit_password2 = (EditText) findViewById(R.id.edit_password2);
        btn_finish = (Button) findViewById(R.id.btn_finish);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit : // 返回上一页
                finish();
                break;
            case R.id.btn_finish : // 完成注册
                RegisterFinish();
                break;
        }
    }

    private void RegisterFinish() {
        setPassword = edit_password1.getText().toString();
        reSetPassword = edit_password2.getText().toString();
        if (setPassword.equals(reSetPassword)){
            if (edit_password1.length()>=8 && edit_password1.length()<=12) {
                //启动新线程
                new Thread() {
                    public void run() {
                        String phoneNum = ed_get_phone_number.getText().toString(); // 获取用户输入的手机号
                        String setPassword = edit_password1.getText().toString();
                        code = yzcode.getText().toString();
                        deviceState =1;
                        Toast.makeText(SetPasswordActivity.this,phoneNum,0).show();
                        result_data = HttpTool.UserRegister(phoneNum,setPassword,code,deviceState, NetBaseConstant.Token);
                        handler.sendEmptyMessage(0);
                    }
                }.start();
            }
            else{
                Toast.makeText(SetPasswordActivity.this,"请输入合法(8-12位)的密码",0).show();
            }
        }else {
            Toast.makeText(SetPasswordActivity.this,"输入的密码不一致",0).show();
        }

        final String username = ed_get_phone_number.getText().toString().trim();
        final String pwd = edit_password1.getText().toString().trim();
        String confirm_pwd = edit_password2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
            ed_get_phone_number.requestFocus();
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            edit_password1.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
            edit_password2.requestFocus();
            return;
        } else if (!pwd.equals(confirm_pwd)) {
            Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage(getResources().getString(R.string.Is_the_registered));
            pd.show();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        // 调用sdk注册方法
                        EMChatManager.getInstance().createAccountOnServer(username, pwd);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!SetPasswordActivity.this.isFinishing())
                                    pd.dismiss();
                                // 保存用户名
                                DemoApplication.getInstance().setUserName(username);
                                finish();
                            }
                        });
                    } catch (final EaseMobException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!SetPasswordActivity.this.isFinishing())
                                    pd.dismiss();
                            }
                        });
                    }
                }
            }).start();

        }

    }

    private void getPhoneNum() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ed_get_phone_number.setText(bundle.getString("phone"));
        yzcode.setText(bundle.getString("yzCode"));
    }

}
