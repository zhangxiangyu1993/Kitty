package crawler;

public class UserInfo
{
    // ���к���ע�������û��Ĵ���
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
    
    // ��ȡ�û�����ͬ�Ĵ���
    public int GetAgreeNum()
    {
        int agree_num = 0;
        
        return agree_num;
    }
    
    // ��ȡ�û�����л�Ĵ���
    public int GetThankNum()
    {
        int thank_num = 0;
        
        return thank_num;
    }
    
    // ��ȡ�Ա�
    public int GetGender()
    {
        int gender = 0;
        
        return gender;
    }
    
    // ��ȡ�ش���
    public int GetAnswerNum()
    {
        int ans_num = 0;
        
        return ans_num;
    }
    
    // ��ȡ���лش�����
    public String[] GetAllAnswerUrls()
    {
        String[] urls = null;
        
        return urls;
    }
    
    // ��ȡ������
    public int GetAskNum()
    {
        int ask_num = 0;
        
        return ask_num;
    }
    
    // ��ȡ������������
    public String[] GetAllAskUrls()
    {
        String[] urls = null;
        
        return urls;
    }
    
    // ��ȡ��˿��
    public int GetFollowerNum()
    {
        int follower_num = 0;
        
        return follower_num;
    }
    
    // ��ȡ��˿�û���Ϣ
    public UserInfo[] GetFollowersInfo()
    {
        UserInfo[] followers = null;
        
        return followers;
    }
    
    // ��ȡ��ע��
    public int GetFollowNum()
    {
        int follow_num = 0;
        
        return follow_num;
    }
    
    // ��ȡ��ע�û���Ϣ
    public UserInfo[] GetFollowsInfo()
    {
        UserInfo[] follows = null;
        
        return follows;
    }
}
