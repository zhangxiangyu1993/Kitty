package crawler;


import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.DocAttribute;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class UserInfo
{
    // 用户基本信息
    private String user_name;
    private String user_url;
    private String user_Id;
    private String followee_num;
    private String follower_num;
    public static HttpsURLConnection conn;
    
 
    
    public UserInfo(String _user_name, String _user_url, HttpsURLConnection conn)
    {
        user_name = _user_name;
        user_url = _user_url;
        this.conn = conn;
    }
    
    public void SerAllInfo() throws Exception {
 		String html = Crawler.GetPageContent(user_url, conn);
 		Document document = Jsoup.parse(html);
 		Element element = document.select("div.title-section.ellipsis").first().getElementsByClass("name").first();
 		user_name = element.text();
 		System.out.println(user_name);
 		
 		element = document.select("button.zg-btn.zg-btn-follow.zm-rich-follow-btn").first();
 		user_Id = element.attr("data-id");
 		System.out.println(user_Id);
 		
 		element = document.getElementsByAttributeValue("href", "/people/rosecrimson/followees").first().getElementsByTag("strong").first();
 		followee_num = element.text();
 		System.out.println(followee_num);
 		
 		element = document.getElementsByAttributeValue("href", "/people/rosecrimson/followers").first().getElementsByTag("strong").first();
 		follower_num = element.text();
 		System.out.println(follower_num);
		
 	}
    
    public void AddInfoToDB() throws SQLException{
    	JavaMySql jMySql = new JavaMySql("mybase", "root", "930727");
    	
//    	String columnname = "user_Id" + ","  + "user_name"+ ","  + "user_link" + "," + "followee_num"
//    			+ "follower_num";
    	String value = "'" + user_Id + "'" + ","  + "'" + user_name + "'" + "," + "'" + user_url + "'" + "," + "'" + followee_num + "'" + "," + "'" + follower_num + "'";
    	System.out.println(value);
    	
    	jMySql.AddElementToTable("usersinfo", value);
    	jMySql.ShowTable("usersinfo");
    }
    
  
    public String GetUserName()
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
        int agree_num = 0;
        
        return agree_num;
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
    
    // 获取关注者信息
    public UserInfo[] GetFollowersInfo()
    {
        UserInfo[] followers = null;
        
        return followers;
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

