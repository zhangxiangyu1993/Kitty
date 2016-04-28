package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

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
    
    public String GetCaptcha() throws Exception
    {
        String url = "https://www.zhihu.com/captcha.gif";
        
        URL obj = new URL(url);
        conn = (HttpsURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", USER_AGENT);

        int res_code = conn.getResponseCode();
        System.out.println("\nGET:" + url + ":" + res_code);
        
        DataInputStream in = new DataInputStream(conn.getInputStream());

        byte[] bytes = new byte[1024];
        byte[] content = new byte[65536];
        int i = in.read(bytes);
        int j = 0;
        while (i > 0)
        {
            for (int j2 = j, i2 = 0; j2 < j+i && i2 < i; j2++, i2++)
            {
                content[j2] = bytes[i2];
            }
            
            j += i;
            i = in.read(bytes);
        }
        

        File file = new File("c:\\captcha.gif");
        if (file.exists())
        {
            System.out.println(file.delete());
        }
        file.createNewFile();
        
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        
        for (int j2 = 0; j2 < j; j2++)
        {
            out.writeByte(content[j2]);
            out.flush();
        }     
        out.close();
        
        Runtime.getRuntime().exec("rundll32 c:\\Windows\\System32\\shimgvw.dll,ImageView_Fullscreen c:\\captcha.gif");
        
        System.out.print("请输入验证码：");
        Scanner scan = new Scanner(System.in);
        String code = scan.nextLine();

        return code;
    }
    
    public String GetXSRF() throws Exception
    {
        String url = "https://www.zhihu.com/";
        
        URL obj = new URL(url);
        conn = (HttpsURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        
        return url;
    }
    
    // POST登录信息
    public boolean SendPost(String url, String post_params) throws Exception
    {
        String code = GetCaptcha();
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
        
        String check_url = "https://www.zhihu.com/settings/profile";
        
        Crawler.GetPageContent(check_url, conn);
        
        // 获取cookies
        cookies = conn.getHeaderFields().get("Set-Cookie");
        
        return true;
    }
    
    public List<String> GetCookies()
    {
        return cookies;
    }

}
