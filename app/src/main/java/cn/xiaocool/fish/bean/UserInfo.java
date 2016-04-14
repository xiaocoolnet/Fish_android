package cn.xiaocool.fish.bean;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import cn.xiaocool.fish.db.sp.UserSp;
import cn.xiaocool.fish.main.LoginActivity;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId; // 用户ID
    private String userName; // 姓名
    private String userAge; // 用户年龄
    private String userSex; // 用户性别
    private String userImg; // 用户头像
    private String userIdTemp;
    private String userPassword; // 用户密码
    private String userPhone; // 用户手机号
    private String userCode; // 验证码
    private String userGender; // 性别
    private String userCompany; // 公司
    private String userPosition; // 职务
    private String userCityId; // 城市ID
    private String userCity; // 城市名
    private String userEase; // 环信名
    private String userEasePassword; // 环信密码
    private ArrayList<TagInfo> userTags; // 用户的标签
    private ArrayList<TagInfo> userCategoryTags; // 用户的业态标签
    private ArrayList<TagInfo> userSkillsTags; // 用户的技能标签
    private ArrayList<TagInfo> userPersonalTags; // 用户的行业标签
    private String isKa; // 0不是，1是

    public UserInfo() {

    }

    public UserInfo(Context context) {
        readData(context);
    }

    public void readData(Context context) {
        UserSp sp = new UserSp(context);
        sp.read(this);
    }

    public void writeData(Context context) {
        UserSp sp = new UserSp(context);
        sp.write(this);
    }

    public void clearData(Context context) {
        UserSp sp = new UserSp(context);
        sp.clear();
    }

    public void clearDataExceptPhone(Context mContext) {
        // TODO Auto-generated method stub
        UserSp sp = new UserSp(mContext);
        UserInfo user = new UserInfo();
        user.setUserPhone(sp.read().getUserPhone());
        clearData(mContext);
        user.writeData(mContext);
    }

    /**
     * 已登录过得，自动进入
     *
     * @return
     */
    public boolean isLogined() {
        if (this.getUserId().equals("")) {
            return false;
        }
        return true;
    }


    public String getUserId() {
        if (userId == null) {
            return "";
        } else if (userId.equals("null")) {
            return "";
        }
        return userId;
    }

    public String setUserId(String userId) {
        this.userId = userId;
        return userId;
    }

    public String getUserName() {
        if (userName == null) {
            return "null";
        } else if (userName.equals("null")) {
            return "null";
        }
        return userName;
    }

    public String setUserName(String userName) {
        Log.e("setname","begin");
        Log.e("setname",userName);
        this.userName = userName;
        return userName;
    }

    public String getUserAge() {
        if (userAge == null) {
            return "";
        } else if (userAge.equals("null")) {
            return "";
        }
        return userAge;
    }

    public String setUserAge(String userage) {
        this.userAge = userage;
        return userage;
    }


    public String getUserSex() {
        if (userSex == null) {
            return "";
        } else if (userSex.equals("null")) {
            return "";
        }
        return userSex;
    }

    public String setUserSex(String userSex) {
        this.userSex = userSex;
        return userSex;
    }


    public String getUserImg() {
        if (userImg == null) {
            return "";
        } else if (userImg.equals("null")) {
            return "";
        }
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


    public String getUserPhone() {
        if (userPhone == null) {
            return "";
        } else if (userPhone.equals("null")) {
            return "";
        }
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getUserPassword() {
        if (userPassword == null) {
            return "";
        } else if (userPassword.equals("null")) {
            return "";
        }
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ArrayList<TagInfo> getUserTags() {
        if (userTags == null) {
            return new ArrayList<TagInfo>();
        } else if (userTags.equals("null")) {
            return new ArrayList<TagInfo>();
        }
        return userTags;
    }

    public void setUserTags(ArrayList<TagInfo> userTags) {
        this.userTags = userTags;
    }

    public ArrayList<TagInfo> getUserCategoryTags() {
        if (userCategoryTags == null) {
            return new ArrayList<TagInfo>();
        } else if (userCategoryTags.equals("null")) {
            return new ArrayList<TagInfo>();
        }
        return userCategoryTags;
    }

    public void setUserCategoryTags(ArrayList<TagInfo> userCategoryTags) {
        this.userCategoryTags = userCategoryTags;
    }

    public ArrayList<TagInfo> getUserSkillsTags() {
        if (userSkillsTags == null) {
            return new ArrayList<TagInfo>();
        } else if (userSkillsTags.equals("null")) {
            return new ArrayList<TagInfo>();
        }
        return userSkillsTags;
    }

    public void setUserSkillsTags(ArrayList<TagInfo> userSkillsTags) {
        this.userSkillsTags = userSkillsTags;
    }

    public ArrayList<TagInfo> getUserPersonalTags() {
        if (userPersonalTags == null) {
            return new ArrayList<TagInfo>();
        } else if (userPersonalTags.equals("null")) {
            return new ArrayList<TagInfo>();
        }
        return userPersonalTags;
    }

    public void setUserPersonalTags(ArrayList<TagInfo> userPersonalTags) {
        this.userPersonalTags = userPersonalTags;
    }


    @Override
    public String toString() {
        return "UserInfo [userId=" + userId +", userName=" + userName +", userImg=" + userImg +", userAge=" + userAge +", userSex=" + userSex + ", userPassword=" + userPassword +", userPhone=" + userPhone +
                ", userTags=" + userTags + ", userCategoryTags=" + userCategoryTags + ", userSkillsTags=" + userSkillsTags
                + ", userPersonalTags=" + userPersonalTags + "]";
    }

}