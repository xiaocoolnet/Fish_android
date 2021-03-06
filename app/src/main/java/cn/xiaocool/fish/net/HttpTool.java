package cn.xiaocool.fish.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.xiaocool.fish.net.constant.NetBaseConstant;
import cn.xiaocool.fish.utils.LogUtils;

public class HttpTool {
//    private static String baseurl = "http://www.xiaocool.cn:8016/index.php?g=apps&m=index&";
    private static final String TAG = "NetUtil";
    /**
     * 网络连接是否可用
     */
    public static boolean isConnnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

            if (null != networkInfo) {
                for (NetworkInfo info : networkInfo) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        LogUtils.e(TAG, "the net is ok");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }
    public static String getResponse(String url,String data){
        String result = "";
        HttpURLConnection conn = null;
        try{
            // 创建一個url對象

            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获得HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");       // 设置请求方法post
            conn.setReadTimeout(5000);           // 設置请求超时
            conn.setConnectTimeout(10000);       // 設置網路連結超時
            conn.setDoInput(true);               // 開啟輸入流
            conn.setDoOutput(true);              // 開啟輸出流
            conn.setUseCaches(false);            // 使用Post方式不能使用暫存

            // post請求參數

            // 獲得一個輸出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();// 調用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                result = getStringFromInputStream(is);
                return result;
            } else {
                // Log.i(TAG, "訪問失敗" + responseCode);
                return result = "请求失败，请检查网络11111";
            }
        }catch (Exception e){
            return result = "请求失败，请检查网络2332"+e.getMessage().toString();
        }
    }
    //登录
    public static String Login(String phone, String password,String token) {
        String url = NetBaseConstant.NET_API_HOST + "a=applogin&";
        String data = "phone="+phone+"&password="+password+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return  result;
    }

    //注册
    public static String UserRegister(String phone,String password,String code,int devicestate,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=AppRegister&";
        String data = "phone="+phone+"&password="+password+"&code="+code+"&devicestate="+devicestate+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return  result;
    }
    //注册
    public static String UserVerify(String phone,String code){
        String url = NetBaseConstant.NET_API_HOST + "a=SendMobileCode&";
        String data = "phone="+phone+"&code="+code;
        String result = "";
        result = getResponse(url,data);
        return  result;
    }
    //未激活的手机号发送验证码
    public static String SendVerifyCode(String phone){
        String url = NetBaseConstant.NET_API_HOST + "a=SendMobileCode&";
        String data = "phone="+phone;
        String result = "";
        result = getResponse(url,data);
        Log.e("verify", result);
        return result;
    }

    //忘记密码（已激活手机号）写入数据库手机号和密码
    public static String ResetPassword(String code,String password,String phone,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=forgetpwd&";
        String data = "code="+code+"&password="+password+"&phone="+phone+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;

    }

    //获取用户信息http://www.xiaocool.net/index.php?g=apps&m=index&a=getuserinfo&userid=34&token=hellofish
    public static String GetUser(String userid,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=getuserinfo&";
        String data = "userid="+userid+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //上传个人头像http://www.xiaocool.net/index.php?g=apps&m=index&a=uploadavatar&upfile=34&token=hellofish
    public static String GetHeaderImage(int upfile,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=uploadavatar&";
        String data = "upfile="+upfile+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //编辑用户信息http://www.xiaocool.net/index.php?g=apps&m=index&a=savepersonalinfo&userid=2&nicename=cool&sex=1&age=19&city=烟台市&token=xiaocool
    public static String EditUserInfo(String userid,String nicename,String sex,String age,String city,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=savepersonalinfo&";
        String data = "userid="+userid+"&nicename="+nicename+"&sex="+sex+"&age="+age+"&city="+city+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //编辑用户信息http://www.xiaocool.net/index.php?g=apps&m=index&a=savepersonalinfo&userid=2&nicename=cool&sex=1&age=19&city=烟台市&token=xiaocool
    public static String PostHeadImage(String userid,String file,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=changeavatar&";
        String data = "userid="+userid+"&file="+file+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //获取个人项目列表http://www.xiaocool.net/index.php?g=apps&m=project&a=getprojectlist&userid=34&token=hellofish
    public static String GetMySelfList(String userid,String token){
        String url = NetBaseConstant.NET_API_List + "a=getprojectlist&";
        String data = "userid="+userid+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //获取系统公告列表 http://www.xiaocool.net/index.php?g=apps&m=index&a=getsystemmessage&userid=1&token=xiaocool
    public static String NewSystemNotice(String userid,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=getsystemmessage&";
        String data = "userid="+userid+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //添加系统留言／反馈 http://www.xiaocool.net/index.php?g=apps&m=index&a=addfeedback&userid=1&content=我有意见反馈&token=hellofish
    public static String AddFeedBack(String userid,String content,String token){
        String url = NetBaseConstant.NET_API_HOST + "a=addfeedback&";
        String data = "userid="+userid+"&content="+content+"&token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

    //调用和风天气API https://api.heweather.com/x3/weather?cityid=CN101010100&key=1058a995f42145d7b9b7208dafe23720
    public static String WeatherAPI(String city,String key){
        String url = NetBaseConstant.WEATHER_API + "?";
        String data = "city="+city+"&key="+key;
        String result = "";
        result = getResponse(url,data);
        return result;
    }
    //首页公告 http://www.xiaocool.net/index.php?g=apps&m=fish&a=getnoticelist&token=xiaocoolself
    //http://www.xiaocool.net/data/product_img/4.JPG
    public static String HomeNotice(String token){
        String url = "http://www.xiaocool.net/index.php?g=apps&m=fish&a=getnoticelist&";
        String data = "token="+token;
        String result = "";
        result = getResponse(url,data);
        return result;
    }

}
