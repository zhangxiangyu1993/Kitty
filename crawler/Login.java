package crawler;

import java.io.DataOutputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Login
{
    private List<String> cookies;
    private HttpsURLConnection conn;
    private String USER_AGENT;
    
    public Login(List<String> _cookies, HttpsURLConnection _conn, String _USER_AGENT)
    {
        cookies = _cookies;
        conn = _conn;
        USER_AGENT = _USER_AGENT;
    }
    
    // 提交POST表单完成登录
    public boolean SendPost(String url, String post_params) throws Exception
    {
        URL obj = new URL(url);
        conn = (HttpsURLConnection)obj.openConnection();
        
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "www.zhihu.com");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Referer", "https://www.zhihu.com/");
        conn.setRequestProperty("Origin", "http://www.zhihu.com");
        conn.setInstanceFollowRedirects(false);
        conn.setDoOutput(true);
        
        // Send post request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(post_params);wr.flush();wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        if (responseCode != 200)
            return false;
        
        // 获取cookies
        cookies = conn.getHeaderFields().get("Set-Cookie");
        
        return true;
    }
    
    public List<String> GetCookies()
    {
        return cookies;
    }

}
