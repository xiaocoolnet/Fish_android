/*
 * Copyright (c) 2016 4 22.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （初始化界面Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.Constant;
import com.easemob.chat.DemoApplication;
import com.easemob.chat.DemoHXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import cn.xiaocool.fish.R;

import com.easemob.chat.activity.*;
import com.easemob.chat.db.UserDao;
import com.easemob.chat.domain.User;
import com.easemob.chat.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** * 登陆页面 * */
public class InitDataActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    public static final int REQUEST_CODE_SETNICK = 1;

    private boolean progressShow;
    private boolean autoLogin = false;

    private String currentUsername;
    private String currentPassword;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }

        sharedPreferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        currentUsername = sharedPreferences.getString("userphone", "");
        currentPassword = sharedPreferences.getString("password", "");

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(InitDataActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (!progressShow) {
                    return;
                }
                // 登陆成功，保存用户名密码
                DemoApplication.getInstance().setUserName(currentUsername);
                DemoApplication.getInstance().setPassword(currentPassword);

                try {
                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                    // ** manually load all local groups and
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();
                    // 处理好友和群组
                    initializeContacts();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 取好友或者群聊失败，不让进入主页面
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            DemoHXSDKHelper.getInstance().logout(true, null);
                            Toast.makeText(getApplicationContext(), R.string.login_failure_failed, 1).show();
                        }
                    });
                    return;
                }
                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                        DemoApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                if (!InitDataActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                // 进入主页面
                Intent intent = new Intent(InitDataActivity.this,
                        com.easemob.chat.activity.MainActivity.class);
                startActivity(intent);
//				IntentUtils.getIntent(LoginActivity.this, InitDataActivity.class);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        setContentView(R.layout.activity_init);
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

        // 存入内存
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
        // 存入db
        UserDao dao = new UserDao(InitDataActivity.this);
        List<User> users = new ArrayList<User>(userlist.values());
        dao.saveContactList(users);
    }

    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        startActivityForResult(new Intent(this, cn.xiaocool.fish.main.RegisterActivity.class), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }
}