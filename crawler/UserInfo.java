package crawler;

public class UserInfo
{
    // 用户基本信息
    private String user_name;
    private String user_url;
    
    public UserInfo(String _user_name, String _user_url)
    {
        user_name = _user_name;
        user_url = _user_url;
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
