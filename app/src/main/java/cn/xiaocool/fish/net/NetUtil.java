package cn.xiaocool.fish.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.xiaocool.fish.utils.LogUtils;

public class NetUtil {
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
    /***
     * @param is
     * @return
     * @throws IOException
     */
    private static String getStringFromInputStream(InputStream is)
            throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        // 一定要寫len=is.read(buffer)
        // 如果while((is.read(buffer))!=-1)則無法將數據寫入buffer中
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 將流中的數據轉換成字串,採用的編碼是utf-8(模擬器默認編碼)
        os.close();
        return state;
    }
    public static String getResponse(String url,String data){
        String result = "";
        HttpURLConnection conn = null;
        try{
            // 创建一个url对象

            URL mURL = new URL(url);
            // 调用URL的openConnection()方法,获得HttpURLConnection对象
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("POST");       // 设置请求方法post
            conn.setReadTimeout(5000);           // 设置获取超时
            conn.setConnectTimeout(10000);       // 設置網路連結超時
            conn.setDoInput(true);               // 开启输入流
            conn.setDoOutput(true);              // 开启输出流
            conn.setUseCaches(false);            // 使用Post方式不能使用暂存

            // post请求参数

            // 获得一個输出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方法
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                result = getStringFromInputStream(is);
                return result;
            } else {
                // Log.i(TAG, "訪問失敗" + responseCode);
                return result = "请调求失败，请检查网络";
            }
        }catch (Exception e){
            return result = "请求失败，请检查网络"+e.getMessage().toString();
        }
    }
}
