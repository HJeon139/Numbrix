package Numbrix;
import java.sql.*;
public class test {
	
		public static void main(String[] args) {
	        String url = "jdbc:mysql://70.171.40.125:3306/";
	        String dbName = "demo";
	        String driver = "java.sql.Driver";
	        String userName = "Admin"; 
	        String password = "AdminPassword1";
	        try {
	        Class.forName(driver).newInstance();
	        Connection conn = DriverManager.getConnection(url+dbName,userName,password);
	        Statement st = conn.createStatement();
	        ResultSet res = st.executeQuery("SELECT * FROM  account WHERE ACCOUNTID <10");
	        while (res.next()) {
	        int id = res.getInt("id");
	        String msg = res.getString("msg");
	        System.out.println(id + "\t" + msg);
	        }
	        /*int val = st.executeUpdate("INSERT into event VALUES("+1+","+"'Easy'"+")");
	        if(val==1)
	            System.out.print("Successfully inserted value");*/
	        conn.close();
	        } catch (Exception e) {
	        e.printStackTrace();
	        }
	   }


}
