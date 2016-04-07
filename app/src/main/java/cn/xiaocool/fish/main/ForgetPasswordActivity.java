/*
 * Copyright (c) 2016 3 8.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （忘记密码获取验证码Activity）
 */

package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;

public class ForgetPasswordActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private Button btn_sendVerifyCode; // 获取验证码按钮
    private Button btn_next; // 下一步
    private EditText edit_phone_number; // 输入的手机号
    private EditText edit_verifycode; // 输入的验证码
    private String phone;
    private String yzCode;
    public static int second;
    private String verifyCode;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    //设置按钮的状态不可用
                    //按钮开始倒计时
                    btn_sendVerifyCode.setClickable(false);
                    btn_sendVerifyCode.setText("请等待("+second+"s)" );
                    break;
                case 1:
                    btn_sendVerifyCode.setText("获取验证码");
                    btn_sendVerifyCode.setClickable(true);
                    break;
                case 2:
                    Toast.makeText(ForgetPasswordActivity.this, "获取验证码成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    if(yzCode.equals(verifyCode)){
                        Intent intent=new Intent();
                        intent.putExtra("phone", phone);
                        intent.putExtra("yzCode", yzCode);
                        intent.setClass(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ForgetPasswordActivity.this, "验证码输入错误", 0).show();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_forgetpass_yzcode);
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
        }
    }

    //获取验证码操作
    private void VerifyCode() {
        final String phone = edit_phone_number.getText().toString();
        if (phone.length() == 11) {
//            Toast.makeText(ForgetPasswordActivity.this, "获取成功，请稍后！", Toast.LENGTH_SHORT).show();
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
        else

        {
            Toast.makeText(ForgetPasswordActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }

    }

    //手机验证成功(下一步)操作
    private void Next() {
        phone = edit_phone_number.getText().toString(); // 获取用户输入的手机号
        yzCode = edit_verifycode.getText().toString(); // 获取用户输入的验证码
        if ((phone.length() == 11) && (yzCode.length()) == 6) {
            //启动新线程
            new Thread() {
                public void run() {
                    verifyCode = edit_verifycode.getText().toString();
                    handler.sendEmptyMessage(3);
                }

            }.start();
        }
        else{
            Toast.makeText(ForgetPasswordActivity.this,"请输入正确的验证码和手机号！！",Toast.LENGTH_SHORT).show();
        }
    }
}
