package crawler;
import java.sql.*;
public class JavaMySql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  //����������//���̶�����������
		  String driver = "com.mysql.jdbc.Driver";
		  // URLָ��Ҫ���ʵ����ݿ���******
		  String url = "jdbc:mysql://localhost/******";
		  // MySQL����ʱ���û���
		  String user = "root";
		  // Java����MySQL����ʱ������******
		  String password = "******";
		  
		  try {
		  // ������������
		  Class.forName(driver);
		  
		  // �������ݿ�
		  Connection conn = DriverManager.getConnection(url, user, password);
		  if(!conn.isClosed())
		   System.out.println("Succeeded connecting to the Database!");
		  
		  // statement����ִ��SQL���
		  Statement statement = conn.createStatement();
		  // Ҫִ�е�SQL���id��content�Ǳ�review�е��
		  String sql = "select DISTINCT id ,content from review ";
		  ResultSet rs = statement.executeQuery(sql);  
		  
		  //���idֵ��contentֵ
		  while(rs.next()) {
		   System.out.println(rs.getString("id") + "\t" + rs.getString("content")); 
		   } 
		  
		  rs.close(); 
		  conn.close();  
		  } catch(ClassNotFoundException e) {  
		   System.out.println("Sorry,can`t find the Driver!");  
		   e.printStackTrace();  
		  } catch(SQLException e) {
		   e.printStackTrace();
		  } catch(Exception e){
		   e.printStackTrace();
		  }
	}

}
