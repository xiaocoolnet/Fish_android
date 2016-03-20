/*
 * Copyright (c) 2016 3 8.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （忘记密码获取验证码Activity）
 */

package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.adapter.YzCodeAdapter;

public class ForgetPasswordActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private Button btn_sendVerifyCode; // 获取验证码按钮
    private Button btn_next; // 下一步
    private EditText edit_phone_number; // 输入的手机号
    private EditText edit_verifycode; // 输入的验证码

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
        if(phone.length() == 11) {
            YzCodeAdapter time = new YzCodeAdapter(btn_sendVerifyCode, "验证码", 30, 1);
            time.start();
        }else{
            Toast.makeText(ForgetPasswordActivity.this,"手机号输入不合法",Toast.LENGTH_SHORT).show();
        }
    }

    //手机验证成功(下一步)操作
    private void Next() {

        String phone = edit_phone_number.getText().toString();
        String yzCode = edit_verifycode.getText().toString();

        if(phone.equals("18653503680") && yzCode.equals("123456") && phone.length()==11 && yzCode.length()==6){ // 成功验证
            Toast.makeText(ForgetPasswordActivity.this, "登录成功，开始重新设置密码", Toast.LENGTH_SHORT).show();
        }else if(phone.length()!=11 || yzCode.length()!=6){ // 输入不合法
            Toast.makeText(ForgetPasswordActivity.this,"手机号或验证码输入不合法",Toast.LENGTH_SHORT).show();
        }else { //验证失败
            Toast.makeText(ForgetPasswordActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
        }
    }

}
