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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sun.org.apache.bcel.internal.classfile.Code;

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
    
    // 获取验证码图片，并且调用windows图片查看器显示，由用户识别输入
    public String GetCaptcha() throws Exception
    {
        String url = "https://www.zhihu.com/captcha.gif?r=1461832441369&type=login";
        
        // 由于存储图片需要二进制，因此此处单独实现一个连接方法，不调用Crawler.GetPageContent
        URL obj = new URL(url);
        conn = (HttpsURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", USER_AGENT);

        int code = conn.getResponseCode();
        System.out.println("\nGET:" + url + ":" + code);
        if (code != 200)
        {
            System.out.println("Get Captcha Failed!\n");
            return "";
        }
        
        // 按byte读取验证码图片信息
        byte[] bytes = new byte[1024];
        byte[] content = new byte[65536];
        DataInputStream in = new DataInputStream(conn.getInputStream());
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
        in.close();

        // 按byte写入图片信息到本地文件
        File file = new File("c:\\captcha.gif");
        if (file.exists())
            System.out.println(file.delete());
        file.createNewFile();
        
        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
        for (int j2 = 0; j2 < j; j2++)
        {
            out.writeByte(content[j2]);
            out.flush();
        }     
        out.close();

        // 调用windows图片查看器打开图片
        Runtime.getRuntime().exec("rundll32 c:\\Windows\\System32\\shimgvw.dll,ImageView_Fullscreen c:\\captcha.gif");
        
        // 手动输入验证码
        System.out.print("请输入验证码：");
        Scanner scan = new Scanner(System.in);
        String codes = scan.nextLine();
        scan.close();

        return codes;
    }
    
    // 获取与验证码对应的隐藏校验码xsrf
    public String GetXSRF() throws Exception
    {
        String url = "https://www.zhihu.com/";
        String content = Crawler.GetPageContent(url, conn);
        int code = conn.getResponseCode();
        if (code != 200)
        {
            System.out.println("Get XSRF Failed!\n");
            return "";
        }
            
        Document doc = Jsoup.parse(content);
        
        Elements eles = doc.getElementsByAttributeValue("name", "_xsrf");
        
        if (eles.size() > 0)
        {
            return eles.first().attr("value");
        }
        
        return "";
    }
    
    // POST登录信息
    public int SendPost(String url, String post_params) throws Exception
    {
        // 组装post参数
        post_params += ("&_xsrf=" + GetXSRF());
        post_params += ("&captcha=" + GetCaptcha());

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
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(post_params);wr.flush();wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nPOST:" + url + ":" + responseCode);
        
        // 验证网页，可以根据返回状态码确认是否登陆成功
        String check_url = "https://www.zhihu.com/settings/profile";
        Crawler.GetPageContent(check_url, conn);
        return conn.getResponseCode();
        
//      获取cookies
//        cookies = conn.getHeaderFields().get("Set-Cookie");
    }
    
    public List<String> GetCookies()
    {
        return cookies;
    }
}
