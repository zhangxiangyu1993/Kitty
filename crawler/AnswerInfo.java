package crawler;

public class AnswerInfo
{
    private String answer_url;
    
    public AnswerInfo(String _url)
    {
        answer_url = _url;
    }
    
    // 将回答者、答案、赞同数、赞同者组装起来，返回整体string
    public String GetAllInfo()
    {
        UserInfo user = GetAuthor();
        String content = GetConent();
        int upvote_num = GetUpvoteNum();
        UserInfo[] users = GetUpvoters();
        
        
        String all_info = "";
        return all_info;
    }
    
    // 获取回答者信息，返回一个UserInfo实例
    public UserInfo GetAuthor()
    {
        UserInfo user = null;
        
        return user;
    }
    
    // 获取回答内容
    public String GetConent()
    {
        String content = "";
        
        return content;
    }
    
    // 获取赞同数
    public int GetUpvoteNum()
    {
        int upvote = 0;
        
        return upvote;
    }
    
    // 获取点赞用户信息
    public UserInfo[] GetUpvoters()
    {
        UserInfo[] users = null;
        
        return users;
    }
}
