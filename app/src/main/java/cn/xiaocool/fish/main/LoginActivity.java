/*
 * Copyright (c) 2016 3 7.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （登录Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.bean.UserInfo;
import cn.xiaocool.fish.dao.DataCleanManager;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.utils.ToastUtils;
import cn.xiaocool.fish.view.FishApplication;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText tx_phonenumber; // 输入用户名
    private EditText tx_vertifycode; // 输入密码

    private Button btn_login; // 登录按钮
    private Button fisher_null;
    private TextView tv_forget; // 忘记密码按钮
    private TextView tv_register; // 注册按钮
    private CheckBox cb_showPass; // 是否显示密码
    private CheckBox cb_remember; // 记住账号和密码并回显

    private static String UID;
    private String result_data, token;
    private UserInfo user;
    private Context mContext;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtils.ToastShort(mContext, "手机号或密码输入错误！");
                    break;
                case 2:
                    ToastUtils.ToastShort(mContext, "网络问题，请稍后重试！");
                    break;
                case 3:
                    try {
                        JSONObject json = new JSONObject(result_data);
                        String status = json.getString("status");
                        String data = json.getString("data");
                        if (status.equals("success")) {
                            JSONObject item = new JSONObject(data);
                            FishApplication.UID = Integer.parseInt(item.getString("id"));
                            user.setUserId(item.getString("id"));
                            user.setUserName(item.getString("name"));
                            user.writeData(mContext);
                            user.setUserImg(item.getString("photo"));
                            Toast.makeText(LoginActivity.this, "登陆成功",
                                    Toast.LENGTH_SHORT).show();
                            IntentUtils.getIntent(LoginActivity.this, MainActivity.class);
                            finish();
//                        mDialog.dismiss();
                        } else {
                            Toast.makeText(LoginActivity.this, data,
                                    Toast.LENGTH_SHORT).show();
                            Log.e("hou", data);
//                        mDialog.dismiss();
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
        btn_login.setOnClickListener(this);
        fisher_null.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        isShowPassWord(); // 输入密码是否显示
        //isReTurnShowUser(); // 回显用户名和密码

        user = new UserInfo();
        user.readData(this);
        if (!user.getUserPhone().equals("")) {
            tx_phonenumber.setText(user.getUserPhone());
        }

    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_login); // 登录界面
        mContext = this;
        // 控件实例化
        tx_phonenumber = (EditText) findViewById(R.id.login_phonenum);
        tx_vertifycode = (EditText) findViewById(R.id.login_Password);
        btn_login = (Button) findViewById(R.id.btn_login);
        fisher_null = (Button) findViewById(R.id.fisher_null);
        tv_forget = (TextView) findViewById(R.id.tv_login_forget_password);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        cb_showPass = (CheckBox) findViewById(R.id.cb_showPass);
        cb_remember= (CheckBox) findViewById(R.id.cb_remember);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fisher_null: // 游客入口
                getFisher_Null();
                break;
            case R.id.btn_login : // 登录
                getLogin();
                DataCleanManager.cleanSharedPreference(this); // 清除用户信息
                break;
            case R.id.tv_login_forget_password : // 忘记密码
                IntentUtils.getIntent(LoginActivity.this, ForgetPasswordActivity.class);
                break;
            case R.id.tv_login_register : // 注册
                IntentUtils.getIntent(LoginActivity.this, RegisterActivity.class);
                break;
        }
    }

    private void getFisher_Null() {
        DataCleanManager.cleanSharedPreference(this); // 清除用户信息
        Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        IntentUtils.getIntent(LoginActivity.this, MainActivity.class); // 跳转到首页
        return;
    }

    // 登录实现的操作
    private void getLogin() {
        isEditEmpty(); //判断用户输入是否为空
    }


    private void isEditEmpty() {
        String phonenumber = tx_phonenumber.getText().toString().trim();
        String password = tx_vertifycode.getText().toString().trim();

        Context userCtx = LoginActivity.this; // 获取SharedPreferences对象
        SharedPreferences sp = userCtx.getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit(); // 存入数据
        editor.putString("userphone", phonenumber);
        editor.commit();

        user.setUserPhone(phonenumber);
        user.setUserPassword(password);
        user.writeData(mContext);
        //线程
        new Thread() {
            public void run() {
                //1、获取输入的手机号码
                if (tx_phonenumber.getText().length() ==11 && tx_vertifycode.getText().length() !=0){
                    String phoneNum = tx_phonenumber.getText().toString();
                    String password = tx_vertifycode.getText().toString();

                    if (HttpTool.isConnnected(mContext)){
                        result_data = HttpTool.Login(phoneNum,password);
                        //调用服务器登录函数
                        handler.sendEmptyMessage(3);
                    }else {
                        //输出：网络连接有问题！
                        handler.sendEmptyMessage(2);
                    }

                }else {
                    //输出：手机号或密码不正确！
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();

//                    String phonenum = tx_phonenumber.getText().toString();
//                    //2、获取收入的密码
//                    String vertifycode = tx_vertifycode.getText().toString();
//                    //------逻辑判断
//                    if(phonenum.length()==11) {
//                        if (HttpTool.isConnnected(mContext)) {
//                            result_data = HttpTool.Login(phonenum, vertifycode);
//
//                        } else {
//                            handler.sendEmptyMessage(2);
//                        }
//                        //--调用服务器登录函数
//                        handler.sendEmptyMessage(3);
//                        Log.e("wzh","enter");
//                    }
//                    else{
//                        handler.sendEmptyMessage(1);
//                    }


//        if(TextUtils.isEmpty(phonenumber)&&TextUtils.isEmpty(password)){
//            Toast.makeText(this, "请输入用户名和密码 ", Toast.LENGTH_SHORT).show();
//            return;
//        }else if(TextUtils.isEmpty(phonenumber)){
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//            return;
//        }else if(TextUtils.isEmpty(password)){
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        }else{
//
//            // 将用户名和密码保存到sava.txt文件中
////            boolean isChecked = cb_remember.isChecked();
////            if(isChecked){
////                try {
////                    File file = new File(this.getFilesDir(),"sava.txt");
////                    OutputStream out = new FileOutputStream(file);
////                    String value = phonenumber+"#XLCD#"+password;
////                    out.write(value.getBytes());
////                    out.close();
////                    //Toast.makeText(this, "勾选了, 保存成功", Toast.LENGTH_SHORT).show();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    //Toast.makeText(this, "勾选了, 没保存成功", Toast.LENGTH_SHORT).show();
////                }
////
////            }else{
////                //Toast.makeText(this, "没勾选了", Toast.LENGTH_SHORT).show();
////            }
//
//            // 将用户名保存到user.xml文件中
//

//        }

    }

    private void isShowPassWord() {
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

    private void isReTurnShowUser() {
        File file = new File(this.getFilesDir(),"sava.txt");
        if(file.exists()&&file.length()>0){
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                String num= line.split("#XLCD#")[0];
                String pwd = line.split("#XLCD#")[1];
                tx_phonenumber.setText(num);
                tx_vertifycode.setText(pwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
