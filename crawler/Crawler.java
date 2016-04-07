package crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class Crawler
{
    public static List<String> cookies;
    public static HttpsURLConnection conn;
    public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";

    public static void main(String[] args) throws Exception
    {
        Login login = new Login(cookies, conn, USER_AGENT);

        // ��cookies
        CookieHandler.setDefault(new CookieManager(null,
                CookiePolicy.ACCEPT_ALL));

        // ���õ�¼����
        String post_params = "remember_me=true&password=99912345yy&email=jmy5945hh@163.com";
        String login_url = "https://www.zhihu.com/login/email";
        // ��¼
        boolean is_login = login.SendPost(login_url, post_params);
        if (is_login)
            System.out.println("\nLogin Succeed!");
        else
        {
            System.out.println("\nLogin Failed!");
            return;
        }

        QuestionInfo q = new QuestionInfo(cookies, conn, USER_AGENT,
                "https://www.zhihu.com/question/39861369");
        q.ConnectUrl();
    }

    public static String GetPageContent(String url, HttpsURLConnection conn)
            throws Exception
    {
        URL obj = new URL(url);
        conn = (HttpsURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", USER_AGENT);

        int res_code = conn.getResponseCode();
        System.out.println("\nGET:" + url + ":" + res_code);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "utf-8"));
        String line;
        StringBuffer response = new StringBuffer();

        while ((line = in.readLine()) != null)
        {
            response.append(line);
        }

        in.close();
        return response.toString();
    }
}
