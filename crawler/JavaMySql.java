package crawler;
import java.sql.*;
public class JavaMySql {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/";
	private String user = "root";
	private String password = null;
	private static Connection conn;
	
	public JavaMySql(String database, String username, String password) throws SQLException {
		// TODO Auto-generated constructor stub
		url += database;
		user = username;
		this.password = password;

		  try {
		  // 加载驱动程序
		  Class.forName(driver);
		  
		  // 连接数据库
		  conn = DriverManager.getConnection(url, user, password);
		  if(!conn.isClosed())
		   System.out.println("Succeeded connecting to the Database!");
		  }catch(ClassNotFoundException e) {  
			   System.out.println("Sorry,can`t find the Driver!");  
			   e.printStackTrace();  
		  }
	}
	
	
	
	

	public void AddElementToTable (String tablename, String value) {	  
		  try {
		  
		  // statement用来执行SQL语句
		  Statement statement = conn.createStatement();

		  String sql = "INSERT INTO " + tablename  + " values " +
				  "(" + value + ")";
		  statement.executeUpdate(sql);
		  System.out.println("add data successfully!");
		  
		  //输出id值和content值

		  } catch(Exception e){
		   e.printStackTrace();
		  }
	}
	
	public void ShowTable(String tablename) throws SQLException {
		Statement statement = conn.createStatement();
		String sql = "select * from " + tablename;
		ResultSet rs = statement.executeQuery(sql);
	
		System.out.println("TABLE:" + tablename);
		System.out.println("user_Id" + "\t" + " user_name"+ "\t" + "user_link" + "\t" + "followee_num" + "\t" + "follower_num");
		while(rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" +
		rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5)); 
		} 
		rs.close(); 
	
	}

}
