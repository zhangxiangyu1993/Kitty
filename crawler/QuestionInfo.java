package crawler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class UserInfo
{
	private List<String> cookies;
	private String USER_AGENT;
	private HttpsURLConnection conn;
    // 用户基本信息
    private String user_name;
    private String user_url;
    private String user_ID;
	private String followerListUrl;
	private String followeeListUrl;
    private int followerNum;
    private int followeeNum;
    
    public UserInfo(HttpsURLConnection conn2, List<String> cookies2, String userAgent, String _userUrl) throws Exception
    {
        user_url = _userUrl;
        conn = conn2;
		cookies = cookies2;
		USER_AGENT = userAgent;
		
		GetFollowInfo();
    }
    
    //获取关注和被关注的列表url和数量
    void GetFollowInfo() throws Exception{
		
		Document document = Jsoup.parse(Crawler.GetPageContent(user_url, conn));
		Element element = document.select("div.zm-profile-side-following.zg-clear").last();
		String string = element.getElementsByTag("a").first().attr("href");
		followeeListUrl = "https://www.zhihu.com" + string;
		string = element.getElementsByTag("a").last().attr("href");
		followerListUrl = "https://www.zhihu.com" + string;
		
		string = element.getElementsByTag("a").first().getElementsByTag("strong").first().text();
  	    followeeNum = Integer.parseInt(string);
  	    string = element.getElementsByTag("a").last().getElementsByTag("strong").first().text();
  	    followerNum = Integer.parseInt(string);
	}
    
  //获取关注者信息  
    ArrayList<String > GetALLFollowersInfo() throws Exception{
		ArrayList<String> followerList = new ArrayList<>();
		if (followerNum == 0)
        {
            System.out.println("No Follower!");
            return followerList;
        }
		String _xsrf = "";
		Document document = Jsoup.parse(Crawler.GetPageContent(followerListUrl, conn));
		int pageNum = (followerNum - 1) / 20 + 1;
		for (int i = 0; i < pageNum; i++) {
			if (i == 0) {
		           Elements elements = document.select("div.zm-profile-card.zm-profile-section-item.zg-clear.no-hovercard");
	                for (Element element2 : elements)
	                {
	                    String url = element2.getElementsByTag("a").first().attr("href");
	                    System.out.println(url);
	                    followerList.add(url);
	                }
	                
	                Element ele = document.getElementsByTag("input").last();
	                _xsrf = ele.attr("value");				
			}else{
				
				 String post_url = "https://www.zhihu.com/node/ProfileFollowersListV2";
	                
	                int offset = i * 20;
	                
	                URL obj = new URL(post_url);
	                conn = (HttpsURLConnection)obj.openConnection();
	                
	                conn.setUseCaches(false);
	                conn.setRequestMethod("POST");
	                conn.setRequestProperty("Host", "www.zhihu.com");
	                conn.setRequestProperty("User-Agent", USER_AGENT);
	                conn.setRequestProperty("Referer", followerListUrl);
	                conn.setInstanceFollowRedirects(false);
	                conn.setDoOutput(true);
	                
	                String params = "{\"offset\":" + offset +",\"order_by\":\"created\",\"hash_id\":\"64281025b4d733997d155676b9270d5a\"}";
	                String post_params = "method=next&params="+params + "&_xsrf="+_xsrf;
	                
	                // Send post request
	                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	                wr.writeBytes(post_params);wr.flush();wr.close();

	                int responseCode = conn.getResponseCode();
	                System.out.println("\nSending 'POST' request to URL : " + post_url);
	                System.out.println("Response Code : " + responseCode);
	                
	                if (responseCode != 200)
	                {
	                    System.out.println("获取更多信息失败！");
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
	             
	                Elements elements = document.select("div.zm-profile-card.zm-profile-section-item.zg-clear.no-hovercard");
	                for (Element element2 : elements)
	                {
	                	String url = element2.getElementsByTag("a").first().attr("href");
	                	System.out.println(url);
	                    followerList.add(url);
	                }
			}
		}
		
		return followerList;		
	}
	
    //获取被关注者信息
    ArrayList<String> GetALLFolloweesInfo() throws Exception{
		ArrayList<String> followeeList = new ArrayList<>();
		if (followeeNum == 0)
        {
            System.out.println("No Followee!");
            return followeeList;
        }
		String _xsrf = "";
		Document document = Jsoup.parse(Crawler.GetPageContent(followeeListUrl, conn));
		int pageNum = (followeeNum - 1) / 20 + 1;
		for (int i = 0; i < pageNum; i++) {
			if (i == 0) {
		           Elements elements = document.select("div.zm-profile-card.zm-profile-section-item.zg-clear.no-hovercard");
	                for (Element element2 : elements)
	                {
	                    String url = element2.getElementsByAttributeValue("class", "zm-list-content-title").first().getElementsByTag("a").first().attr("href"); 
	                    followeeList.add(url);
	                }
	                
	                Element ele = document.getElementsByTag("input").last();
	                _xsrf = ele.attr("value");				
			}else{
				
				 String post_url = "https://www.zhihu.com/node/ProfileFolloweesListV2";
	                
	                int offset = i * 20;
	                
	                URL obj = new URL(post_url);
	                conn = (HttpsURLConnection)obj.openConnection();
	                
	                conn.setUseCaches(false);
	                conn.setRequestMethod("POST");
	                conn.setRequestProperty("Host", "www.zhihu.com");
	                conn.setRequestProperty("User-Agent", USER_AGENT);
	                conn.setRequestProperty("Referer", followeeListUrl);
	                conn.setInstanceFollowRedirects(false);
	                conn.setDoOutput(true);
	                
	                String params = "{\"offset\":" + offset +",\"order_by\":\"created\",\"hash_id\":\"9716adc74ed906724dbc8ffa9510cf68\"}";
	                String post_params = "method=next&params="+params + "&_xsrf="+_xsrf;
	                
	                // Send post request
	                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	                wr.writeBytes(post_params);wr.flush();wr.close();

	                int responseCode = conn.getResponseCode();
	                System.out.println("\nSending 'POST' request to URL : " + post_url);
	                System.out.println("Response Code : " + responseCode);
	                
	                if (responseCode != 200)
	                {
	                    System.out.println("获取更多信息失败！");
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
	             
	                Elements elements = document.select("div.zm-profile-card.zm-profile-section-item.zg-clear.no-hovercard");
	                for (Element element2 : elements)
	                {
	                	String url = element2.getElementsByAttributeValue("class", "zm-list-content-title").first().getElementsByTag("a").first().attr("href");
	                    followeeList.add(url);               
	                }
			}
		}
		
		return followeeList;
	}
    
    public void SetAllInfo() throws Exception{
      	String html = Crawler.GetPageContent(user_url, conn);
      	Document doucDocument= Jsoup.parse(html);
      	
      	Element element=doucDocument.select("div.title-section.ellipsis").first().getElementsByClass("name").first();
      	user_name = element.text();
      	System.out.println(user_name);
      	     	
      	element = doucDocument.select("button.zg-btn.zg-btn-follow.zm-rich-follow-btn").first();
      	user_ID = element.attr("data-id");
      	System.out.println(user_ID);
      	
      	
     	
    }
    public String GetUserName()throws Exception
    {
  
        return user_name;
        
        
    }
    
    public String GetUserUrl()
    {
        return user_url;
    }
    
    // 获取被赞同次数
    public int GetAgreeNum()
    {
    	int num = 0;
    	return num;
       
    }
    
    // 获取被感谢次数
    public int GetThankNum()
    {
        int thank_num = 0;
        
        return thank_num;
    }
    
    // 获取性别
    public int GetGender()
    {
        int gender = 0;
        
        return gender;
    }
    
    // 获取回答数量
    public int GetAnswerNum()
    {
        int ans_num = 0;
        
        return ans_num;
    }
    
    // 获取回答的url
    public String[] GetAllAnswerUrls()
    {
        String[] urls = null;
        
        return urls;
    }
    
    // 获取提问数量
    public int GetAskNum()
    {
        int ask_num = 0;
        
        return ask_num;
    }
    
    // 获取提问url
    public String[] GetAllAskUrls()
    {
        String[] urls = null;
        
        return urls;
    }
    
    // 获取关注者数量
    public int GetFollowerNum()
    {
        int follower_num = 0;
        
        return follower_num;
    }
    
    

    
    // 获取关注数量
    public int GetFollowNum()
    {
        int follow_num = 0;
        
        return follow_num;
    }
    
    // 获取被关注者信息
    public UserInfo[] GetFollowsInfo()
    {
        UserInfo[] follows = null;
        
        return follows;
    }
}
