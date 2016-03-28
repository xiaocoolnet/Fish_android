package cn.xiaocool.fish.bean;

import java.io.Serializable;

public class TagInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String tagName;
    private String tagId;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "TagInfo [tagName=" + tagName + ", tagId=" + tagId + "]";
    }

}
