/*
 * Copyright (c) 2016 3 7.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （登录Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.utils.IntentUtils;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText tx_phonenumber; // 输入用户名
    private EditText tx_vertifycode; // 输入密码

    private Button btn_login; // 登录按钮
    private TextView tv_forget; // 忘记密码按钮
    private TextView tv_register; // 注册按钮
    private CheckBox cb_showPass; // 是否显示密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        // 添加点击事件
        btn_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        isShowPassWord(); //输入密码是否显示
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_login); // 登录界面
        // 控件实例化
        tx_phonenumber = (EditText) findViewById(R.id.login_phonenum);
        tx_vertifycode = (EditText) findViewById(R.id.login_Password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_forget = (TextView) findViewById(R.id.tv_login_forget_password);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        cb_showPass = (CheckBox) findViewById(R.id.cb_showPass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login : // 登录
                //DataCleanManager.cleanExternalCache(this); // 清除本应用内部缓存
                //DataCleanManager.cleanInternalCache(this); // 清除外部cache下的内容
                getLogin();
                break;
            case R.id.tv_login_forget_password : // 忘记密码
                IntentUtils.getIntent(LoginActivity.this, ForgetPasswordActivity.class);
                break;
            case R.id.tv_login_register : // 注册
                IntentUtils.getIntent(LoginActivity.this, RegisterActivity.class);
                break;
        }
    }

    // 登录实现的操作
    private void getLogin() {
        //isEditEmpty();
        IntentUtils.getIntent(LoginActivity.this, MainActivity.class); // 跳转到首页
    }

    //判断用户输入是否为空
    private void isEditEmpty() {
        String phonenumber = tx_phonenumber.getText().toString().trim();
        String password = tx_vertifycode.getText().toString().trim();

        if(TextUtils.isEmpty(phonenumber)&&TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入用户名和密码 ", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(phonenumber)){
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this, phonenumber+"登录成功", Toast.LENGTH_SHORT).show();
            IntentUtils.getIntent(LoginActivity.this, MainActivity.class); // 跳转到首页
            return;
        }
    }

    private void isShowPassWord() {
        // 是否显示密码
        cb_showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_showPass.isChecked()){
                    tx_vertifycode.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Editable et_able = tx_vertifycode.getText();
                    Selection.setSelection(et_able, et_able.length());
                }else{
                    tx_vertifycode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Editable et_able = tx_vertifycode.getText();
                    Selection.setSelection(et_able, et_able.length());
                }
            }
        });
    }

}
