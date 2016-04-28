package cn.xiaocool.fish.net.constant;

import cn.xiaocool.fish.dao.SystemData;

public interface NetBaseConstant {
    /** * 网络请求IP */
    public final static String NET_BASE_HOST = "http://www.xiaocool.net/index.php?g=apps&m=index&";

    /** *接口号 */
    public final static String Token = "hellofish";
    public final static String APPID = "b08fef069f4f90c8";
    public final static String Private_Key = "274b25_SmartWeatherAPI_9236094";
    public final static String SYSTEM_DATA = SystemData.getStringDate();
    public final static String NET_API_HOST = "http://www.xiaocool.net/index.php?g=apps&m=index&";

    public final static String NET_API_List = "http://www.xiaocool.net/index.php?g=apps&m=project&";
    /** * 头像图片地址 * */
    public final static String NET_AVATAR_HOST = "http://www.xiaocool.net/uploads/avatar/";
    /** * 和风天气 * */
    public final static String WEATHER_API = "https://api.heweather.com/x3/weather";
    /** * 中国气象局 * */
    public final static String WEATHER_WEA_API = "http://open.weather.com.cn/data/";

}
