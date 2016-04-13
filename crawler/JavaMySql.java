package crawler;
import java.sql.*;
public class JavaMySql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  //驱动程序名//不固定，根据驱动
		  String driver = "com.mysql.jdbc.Driver";
		  // URL指向要访问的数据库名******
		  String url = "jdbc:mysql://localhost/******";
		  // MySQL配置时的用户名
		  String user = "root";
		  // Java连接MySQL配置时的密码******
		  String password = "******";
		  
		  try {
		  // 加载驱动程序
		  Class.forName(driver);
		  
		  // 连续数据库
		  Connection conn = DriverManager.getConnection(url, user, password);
		  if(!conn.isClosed())
		   System.out.println("Succeeded connecting to the Database!");
		  
		  // statement用来执行SQL语句
		  Statement statement = conn.createStatement();
		  // 要执行的SQL语句id和content是表review中的项。
		  String sql = "select DISTINCT id ,content from review ";
		  ResultSet rs = statement.executeQuery(sql);  
		  
		  //输出id值和content值
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
