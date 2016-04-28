package cn.xiaocool.fish.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemData {
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
