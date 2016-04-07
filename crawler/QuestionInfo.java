package crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuestionInfo
{
    private HttpsURLConnection conn;
    private String USER_AGENT;
    private String url;
    private String html;
    private File writename;
    private Document document;

    public QuestionInfo(List<String> _cookies, HttpsURLConnection _conn, String _USER_AGENT, String _url) throws IOException
    {
        conn = _conn;
        USER_AGENT = _USER_AGENT;
        url = _url;
    }
    
    // 访问问题网页，获取网页原始内容
    public boolean ConnectUrl() throws Exception
    {
        if (Pattern.matches("(http|https)://www.zhihu.com/question/\\d{8}", url))
        {
            html = Crawler.GetPageContent(url, conn);
            writename = new File(".\\123.txt");
            writename.createNewFile();
            document = Jsoup.parse(html);
            return true;
        }
        else
            return false;
    }
    
    // 获取问题标题
    public String GetQuestionTitle()
    {
        String title = "";
        
        return title;
    }
    
    // 获取问题具体内容
    public String GetQuestionDetail()
    {
        String detail = "";
        
        return detail;
    }
    
    // 获取问题关注人数
    public int GetFollowersNum()
    {
        int num = 0;
        
        return num;
    }
    
    // 获取问题所属topic
    public String[] GetTopics()
    {
        String[] topics = null;
        
        return topics;
    }
    
    // 获取问题下所有答案信息
    public void GetAllAnswers() throws Exception
    {
        int t_ans_num = GetAnswerNum();
        
        if (t_ans_num == 0)
        {
            System.out.println("No Answer!");
            return;
        }
        
        int t_page_num = (t_ans_num-1) / 20 + 1;
        String _xsrf = "";
        
        for (int i = 0; i < t_page_num; i++)
        {
            if (i == 0)
            {
                // 获取所有答案并遍历
                Elements elements = document.select("div.zm-item-answer.zm-item-expanded");
                for (Element element2 : elements)
                {
                    String str = GetAnswerByElement(element2, false);
                    WriteToFile(str);
                }
                
                Element ele = document.getElementsByTag("input").last();
                _xsrf = ele.attr("value");
            }
            else 
            {
                String post_url = "https://www.zhihu.com/node/QuestionAnswerListV2";
                
                
                int offset = i * 20;
                
                URL obj = new URL(post_url);
                conn = (HttpsURLConnection)obj.openConnection();
                
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Host", "www.zhihu.com");
                conn.setRequestProperty("User-Agent", USER_AGENT);
                conn.setRequestProperty("Referer", url);
                conn.setInstanceFollowRedirects(false);
                conn.setDoOutput(true);
                
                String[] url_split = url.split("/");
                String question_code = url_split[url_split.length-1];
                String params = "{\"url_token\":"+question_code+",\"pagesize\":20,\"offset\":"+offset+"}";
                String post_params = "_xsrf="+_xsrf+"&method=next&params="+params;
                
                // Send post request
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(post_params);wr.flush();wr.close();

                int responseCode = conn.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + post_url);
                System.out.println("Response Code : " + responseCode);
                
                if (responseCode != 200)
                {
                    System.out.println("翻页出错");
                    continue;
                }
                
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                StringBuffer response = new StringBuffer();
                
                while ((line = in.readLine()) != null)
                {
                    response.append(line);
                }
                
                in.close();
                
                String content = response.toString().substring(17, response.toString().length()-3);
                content = content.replace("\\\"", "\"");
                content = content.replace("\\n", " ");
                content = content.replace("\\/", "//");
                content = content.replace("//", "/");
                
                document = Jsoup.parse(content);
                Elements elements = document.select("div.zm-item-answer.zm-item-expanded");
                
                for (Element element2 : elements)
                {
                    String str = GetAnswerByElement(element2, true);
                    WriteToFile(str);
                }
            }
        }
    }
    
    // 根据答案的element进行模板解析
    private String GetAnswerByElement(Element element, boolean _unicode) throws Exception
    {
        // 获取回答者信息
        Element ele = element.getElementsByClass("author-link").first();
        String author_link = "";
        String author_name = "Anonymous";
        if (ele != null)
        {
            author_link = "http://www.zhihu.com" + ele.attr("href");
            author_name = ele.text();
            
            if (_unicode)
            {
                author_name = author_name.replace("\\", "\\\\");
                Pattern p = Pattern.compile("(?<=\\\\u)\\w\\w\\w\\w");
                Matcher m = p.matcher(author_name);
                
                while (m.find())
                {
//                    System.out.println(m.group());
                    int hex_val = Integer.parseInt(m.group(), 16);
                    String new_auth_name = "";
                    new_auth_name += (char)hex_val;
                    author_name = author_name.replaceFirst("\\\\u\\w\\w\\w\\w", new_auth_name);
                }
                
                author_name = author_name.replace("\\", "");
            }
        }
        
        // 获取点赞数
        ele = element.getElementsByClass("zm-item-vote-info").first();
        String vote_num = ele.attr("data-votecount");
        
        // 获取回答内容
        ele = element.getElementsByClass("zm-editable-content").first();
        String ans_content = ele.text();
        if (_unicode)
        {
            ans_content = ans_content.replace("\\", "\\\\");
            Pattern p = Pattern.compile("(?<=\\\\u)\\w\\w\\w\\w");
            Matcher m = p.matcher(ans_content);
            
            while (m.find())
            {
//                System.out.println(m.group());
                int hex_val = Integer.parseInt(m.group(), 16);
                String new_auth_name = "";
                new_auth_name += (char)hex_val;
                ans_content = ans_content.replaceFirst("\\\\u\\w\\w\\w\\w", new_auth_name);
            }
            
            ans_content = ans_content.replace("\\", "");
        }
        
        // 组合信息
        String info = author_name + "(" + author_link + ")" + " 赞同数=" + vote_num + "\r\n" + ans_content + "\r\n\r\n";
        
        return info;
    }
    
    // 写入文件
    private void WriteToFile(String content) throws IOException
    {
        BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));  
        out.write(content);
        out.flush();
        out.close();
    }
    
    // 获取问题的回答数量
    public int GetAnswerNum()
    {
        Element element = document.getElementById("zh-question-answer-num");
        return Integer.parseInt(element.text().split(" ")[0]);
    }
}
