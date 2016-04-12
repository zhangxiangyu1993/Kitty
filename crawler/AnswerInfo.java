package crawler;

public class AnswerInfo
{
    private String answer_url;
    
    public AnswerInfo(String _url)
    {
        answer_url = _url;
    }
    
    // 获取用户所有信息，组合为一个string
    public String GetAllInfo()
    {
        UserInfo user = GetAuthor();
        String content = GetConent();
        int upvote_num = GetUpvoteNum();
        UserInfo[] users = GetUpvoters();
        
        
        String all_info = "";
        return all_info;
    }
    
    // 获取用户信息
    public UserInfo GetAuthor()
    {
        UserInfo user = null;
        
        return user;
    }
    
    // 获取答案内容
    public String GetConent()
    {
        String content = "";
        
        return content;
    }
    
    // 获取被赞同数量
    public int GetUpvoteNum()
    {
        int upvote = 0;
        
        return upvote;
    }
    
    // 获取赞同者信息
    public UserInfo[] GetUpvoters()
    {
        UserInfo[] users = null;
        
        return users;
    }
}
