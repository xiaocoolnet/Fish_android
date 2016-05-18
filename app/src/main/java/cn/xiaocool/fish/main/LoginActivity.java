/*
 * Copyright (c) 2016 3 7.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （登录Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.easemob.EMCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.Constant;
import com.easemob.chat.DemoApplication;
import com.easemob.chat.DemoHXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.db.UserDao;
import com.easemob.chat.domain.User;
import com.easemob.chat.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.bean.UserInfo;
import cn.xiaocool.fish.dao.DataCleanManager;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;
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
    private SharedPreferences sharedPreferences;
    private boolean autoLogin = false;
    private String currentUsername;
    private String currentPassword;
    private boolean progressShow;

    private static String UID;
    private String result_data;
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
                            String user_id = user.setUserId(item.getString("id"));

                            sharedPreferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_id", user_id);
                            editor.commit();// 提交修改

                            String user_nicename=user.setUserName(item.getString("user_nicename"));
                            user.setUserSex(item.getString("sex"));
                            user.setUserAge(item.getString("age"));
                            user.writeData(mContext);
                            Toast.makeText(LoginActivity.this, "你好："+user_nicename,
                                    Toast.LENGTH_SHORT).show();
                            IntentUtils.getIntent(LoginActivity.this, com.easemob.chat.activity.MainActivity.class);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT).show();
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
        isLogin();

        // 控件实例化
        tx_phonenumber = (EditText) findViewById(R.id.login_phonenum);
        tx_vertifycode = (EditText) findViewById(R.id.login_Password);
        btn_login = (Button) findViewById(R.id.btn_login);
        fisher_null = (Button) findViewById(R.id.fisher_null);
        tv_forget = (TextView) findViewById(R.id.tv_login_forget_password);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        cb_showPass = (CheckBox) findViewById(R.id.cb_showPass);
        cb_remember= (CheckBox) findViewById(R.id.cb_remember);

        // 如果用户名改变，清空密码
        tx_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tx_vertifycode.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (DemoApplication.getInstance().getUserName() != null) {
            tx_phonenumber.setText(DemoApplication.getInstance().getUserName());
        }

    }

    public void isLogin(){
        sharedPreferences = getSharedPreferences("user_id", Activity.MODE_PRIVATE);
        String user_id = sharedPreferences.getString("user_id", "");
        if (user_id != ""){ // 登录过
//          Toast.makeText(LoginActivity.this,"已经登录过了",0).show();
            IntentUtils.getIntent(LoginActivity.this, com.easemob.chat.activity.MainActivity.class);
        }

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
        IntentUtils.getIntent(LoginActivity.this, com.easemob.chat.activity.MainActivity.class); // 跳转到首页
        return;
    }

    // 登录实现的操作
    private void getLogin() {
        isEditEmpty(); // 判断用户输入是否为空
//        if (!CommonUtils.isNetWorkConnected(this)) {
//            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        currentUsername = tx_phonenumber.getText().toString().trim();
//        currentPassword = tx_vertifycode.getText().toString().trim();
//
//        if (TextUtils.isEmpty(currentUsername)) {
//            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(currentPassword)) {
//            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        progressShow = true;
//        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
//        pd.setCanceledOnTouchOutside(false);
//        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                progressShow = false;
//            }
//        });
//        pd.setMessage(getString(R.string.Is_landing));
//        pd.show();
//
//        final long start = System.currentTimeMillis();
//        // 调用sdk登陆方法登陆聊天服务器
//        EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {
//
//            @Override
//            public void onSuccess() {
//                if (!progressShow) {
//                    return;
//                }
//                // 登陆成功，保存用户名密码
//                DemoApplication.getInstance().setUserName(currentUsername);
//                DemoApplication.getInstance().setPassword(currentPassword);
//
//                try {
//                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                    // ** manually load all local groups and
//                    EMGroupManager.getInstance().loadAllGroups();
//                    EMChatManager.getInstance().loadAllConversations();
//                    // 处理好友和群组
//                    initializeContacts();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    // 取好友或者群聊失败，不让进入主页面
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            pd.dismiss();
//                            DemoHXSDKHelper.getInstance().logout(true, null);
//                            Toast.makeText(getApplicationContext(), R.string.login_failure_failed, 1).show();
//                        }
//                    });
//                    return;
//                }
//                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
//                boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
//                        DemoApplication.currentUserNick.trim());
//                if (!updatenick) {
//                    Log.e("LoginActivity", "update current user nick fail");
//                }
//                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
//                    pd.dismiss();
//                }
//
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//            }
//
//            @Override
//            public void onError(final int code, final String message) {
//                if (!progressShow) {
//                    return;
//                }
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        pd.dismiss();
//                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
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
                        result_data = HttpTool.Login(phoneNum,password, NetBaseConstant.Token);
                        handler.sendEmptyMessage(3);// 调用服务器登录函数
                    }else {
                        handler.sendEmptyMessage(2); // 输出：网络连接有问题！
                    }

                }else {
                    handler.sendEmptyMessage(1); // 输出：手机号或密码不正确！
                }
            }
        }.start();

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

    private void initializeContacts() {
        Map<String, User> userlist = new HashMap<String, User>();
        // 添加user"申请与通知"
        User newFriends = new User();
        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
        String strChat = getResources().getString(
                R.string.Application_and_notify);
        newFriends.setNick(strChat);

        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        User groupUser = new User();
        String strGroup = getResources().getString(R.string.group_chat);
        groupUser.setUsername(Constant.GROUP_USERNAME);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
        userlist.put(Constant.GROUP_USERNAME, groupUser);

        // 添加"Robot"
        User robotUser = new User();
        String strRobot = getResources().getString(R.string.robot_chat);
        robotUser.setUsername(Constant.CHAT_ROBOT);
        robotUser.setNick(strRobot);
        robotUser.setHeader("");
        userlist.put(Constant.CHAT_ROBOT, robotUser);

        // 存入内存
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
        // 存入db
        UserDao dao = new UserDao(LoginActivity.this);
        List<User> users = new ArrayList<User>(userlist.values());
        dao.saveContactList(users);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }

}
