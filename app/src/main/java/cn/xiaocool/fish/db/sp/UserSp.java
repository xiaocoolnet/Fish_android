package cn.xiaocool.fish.db.sp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import cn.xiaocool.fish.bean.TagInfo;
import cn.xiaocool.fish.bean.UserInfo;

public class UserSp extends BaseSp<UserInfo> {

    public UserSp(Context context) {
        super(context, "user_sp");
    }

    @Override
    public void read(UserInfo user) {
        // 安全检查
        if (user == null) {
            user = new UserInfo();
        }
        if (getSP().contains("userId")) {
            user.setUserId(getSP().getString("userId", ""));
        }
        if (getSP().contains("userAge")) {
            user.setUserId(getSP().getString("userAge", ""));
        }
        if (getSP().contains("userSex")) {
            user.setUserId(getSP().getString("userSex", ""));
        }
        if (getSP().contains("userName")) {
            user.setUserName(getSP().getString("userName", ""));
        }
        if (getSP().contains("userPhone")) {
            user.setUserPhone(getSP().getString("userPhone", ""));
        }
        if (getSP().contains("userPassword")) {
            user.setUserPassword(getSP().getString("userPassword", ""));
        }
        if (getSP().contains("userTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("tag_id_" + i, ""));
                tag.setTagName(getSP().getString("tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserTags(tags);
        }
        if (getSP().contains("userCategoryTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userCategoryTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("category_tag_id_" + i, ""));
                tag.setTagName(getSP().getString("category_tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserCategoryTags(tags);
        }
        if (getSP().contains("userSkillsTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userSkillsTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("skill_tag_id_" + i, ""));
                tag.setTagName(getSP().getString("skill_tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserSkillsTags(tags);
        }
        if (getSP().contains("userPersonalTag_size")) {
            ArrayList<TagInfo> tags = new ArrayList<TagInfo>();
            tags.clear();
            int size = getSP().getInt("userPersonalTag_size", 0);
            for (int i = 0; i < size; i++) {
                TagInfo tag = new TagInfo();
                tag.setTagId(getSP().getString("personal_tag_id_" + i, ""));
                tag.setTagName(getSP().getString("personal_tag_name_" + i, ""));
                tags.add(tag);
            }
            user.setUserPersonalTags(tags);
        }
    }

    @Override
    public UserInfo read() {
        UserInfo result = null;
        result = new UserInfo();
        read(result);
        return result;
    }

    @Override
    public void write(UserInfo user) {
        SharedPreferences.Editor editor = getSP().edit();
        if (!user.getUserId().equals("")) {
            editor.putString("userId", user.getUserId());
        }
        if (!user.getUserId().equals("")) {
            editor.putString("userAge", user.getUserAge());
        }
        if (!user.getUserName().equals("")) {
            editor.putString("userSex", user.getUserSex());
        }
        if (!user.getUserName().equals("")) {
            editor.putString("userName", user.getUserName());
        }
        if (!user.getUserPhone().equals("")) {
            editor.putString("userPhone", user.getUserPhone());
        }
        if (!user.getUserPassword().equals("")) {
            editor.putString("userPassword", user.getUserPassword());
        }
        if (user.getUserTags().size() > 0) {
            editor.putInt("userTag_size", user.getUserTags().size());
            for (int i = 0; i < user.getUserTags().size(); i++) {
                editor.putString("tag_id_" + i, user.getUserTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserTags().size(); i++) {
                editor.putString("tag_name_" + i, user.getUserTags().get(i).getTagName());
            }
        }
        if (user.getUserCategoryTags().size()>0) {
            editor.putInt("userCategoryTag_size", user.getUserCategoryTags().size());
            for (int i = 0; i < user.getUserCategoryTags().size(); i++) {
                editor.putString("category_tag_id_" + i, user.getUserCategoryTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserCategoryTags().size(); i++) {
                editor.putString("category_tag_name_" + i, user.getUserCategoryTags().get(i).getTagName());
            }
        }
        if (user.getUserSkillsTags().size() > 0) {
            editor.putInt("userSkillsTag_size", user.getUserSkillsTags().size());
            for (int i = 0; i < user.getUserSkillsTags().size(); i++) {
                editor.putString("skill_tag_id_" + i, user.getUserSkillsTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserSkillsTags().size(); i++) {
                editor.putString("skill_tag_name_" + i, user.getUserSkillsTags().get(i).getTagName());
            }
        }
        if (user.getUserPersonalTags().size() > 0) {
            editor.putInt("userPersonalTag_size", user.getUserPersonalTags().size());
            for (int i = 0; i < user.getUserPersonalTags().size(); i++) {
                editor.putString("personal_tag_id_" + i, user.getUserPersonalTags().get(i).getTagId());
            }
            for (int i = 0; i < user.getUserPersonalTags().size(); i++) {
                editor.putString("personal_tag_name_" + i, user.getUserPersonalTags().get(i).getTagName());
            }
        }
        editor.commit();
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = getSP().edit();
        editor.clear();
        editor.commit();
    }
}