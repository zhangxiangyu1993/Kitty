package crawler;

public class AnswerInfo
{
    private String answer_url;
    
    public AnswerInfo(String _url)
    {
        answer_url = _url;
    }
    
    // ���ش��ߡ��𰸡���ͬ������ͬ����װ��������������string
    public String GetAllInfo()
    {
        UserInfo user = GetAuthor();
        String content = GetConent();
        int upvote_num = GetUpvoteNum();
        UserInfo[] users = GetUpvoters();
        
        
        String all_info = "";
        return all_info;
    }
    
    // ��ȡ�ش�����Ϣ������һ��UserInfoʵ��
    public UserInfo GetAuthor()
    {
        UserInfo user = null;
        
        return user;
    }
    
    // ��ȡ�ش�����
    public String GetConent()
    {
        String content = "";
        
        return content;
    }
    
    // ��ȡ��ͬ��
    public int GetUpvoteNum()
    {
        int upvote = 0;
        
        return upvote;
    }
    
    // ��ȡ�����û���Ϣ
    public UserInfo[] GetUpvoters()
    {
        UserInfo[] users = null;
        
        return users;
    }
}
