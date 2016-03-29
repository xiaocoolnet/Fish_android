/*
 * Copyright (c) 2016 3 8.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （注册手机验证Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.view.FishApplication;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private Button btn_sendVerifyCode; // 获取验证码按钮
    private Button btn_next; // 下一步
    private EditText edit_phone_number; // 输入的手机号
    private EditText edit_verifycode; // 输入的验证码
    private String result_data;
    public static int second;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    btn_sendVerifyCode.setText("请等待("+second+"s)" );
                    btn_sendVerifyCode.setClickable(false); // 设置按钮的状态不可用
                    break;
                case 1:
                    btn_sendVerifyCode.setText("获取验证码");
                    btn_sendVerifyCode.setClickable(true); // 设置按钮的状态可用
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "获取验证码成功", 0).show();
                    break;
                case 3:
                    //判断与服务器的验证码是否一致，如果一致则提示注册成功，跳转到主界面；否则提示验证码错误
                    //   Intent intent_is = new Intent(RegisterActivity.this, MainActivity.class);
                    //  startActivity(intent_is);
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            JSONObject item = new JSONObject(data);
                            //实力化缓存类
                            FishApplication.UID = Integer.parseInt(item.getString("id"));
                            Toast.makeText(RegisterActivity.this, "注册成功！",0).show();
                            IntentUtils.getIntent(RegisterActivity.this, SetPasswordActivity.class);
                        } else {
                            Toast.makeText(RegisterActivity.this, data,0).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
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
        btn_sendVerifyCode.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_register);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        btn_sendVerifyCode = (Button) findViewById(R.id.yanzhengma);
        btn_next = (Button) findViewById(R.id.btn_next);
        edit_phone_number = (EditText) findViewById(R.id.edit_phone_number);
        edit_verifycode = (EditText) findViewById(R.id.edit_verifycode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit : // 返回上一页
                finish();
                break;
            case R.id.yanzhengma : // 验证码控件
                VerifyCode();
                break;
            case R.id.btn_next : // 手机验证码验证成功(下一步)
                Next();
                break;
            default:
                break;
        }
    }

    //获取验证码操作
    private void VerifyCode() {
        final String phone = edit_phone_number.getText().toString();
        if (phone.length() == 11) {
            new Thread() {
                @Override
                public void run() {
                    HttpTool.SendVerifyCode(phone);  //让服务器发送验证码到手机
                    handler.sendEmptyMessage(2);
                    second =30;
                    for (int i = 0; i < 30; i++) {
                        try {
                            handler.sendEmptyMessage(0);
                            Thread.sleep(1000);
                            second--;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(1);
                }
            }.start();
        }
        else {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号码！",0).show();
        }
    }

    //手机验证成功(下一步)操作
    private void Next() {
        String phone = edit_phone_number.getText().toString(); // 获取用户输入的手机号
        String yzCode = edit_verifycode.getText().toString(); // 获取用户输入的验证码
        if ((phone.length() == 11) && (yzCode.length()) == 6) {
            //启动新线程
            new Thread() {
                public void run() {
                    String phoneNum = edit_phone_number.getText().toString();
                    String verifyCode = edit_verifycode.getText().toString();
                    result_data = HttpTool.UserVerify(phoneNum, verifyCode);
                    handler.sendEmptyMessage(3);
                }
            }.start();
        }
        else{
            Toast.makeText(RegisterActivity.this,"请输入正确的验证码和手机号！！",0).show();
        }
    }

}
