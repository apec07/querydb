package idv.cm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private String diriverClassName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false"; 
	private String userName = "root";
	private String password = "1234";
	
	private static ConnectionFactory connectionFactory=null;
	
	private ConnectionFactory() {
		 try {
	           Class.forName(diriverClassName); 
	       } catch (java.lang.ClassNotFoundException e) {
	           Utility.getLogger(this.getClass().getSimpleName().toString(),e.getMessage());
	           System.err.println(e.getMessage());
	       }
	}
	public static ConnectionFactory getInstance() {
		if(connectionFactory==null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, userName, password);
	}
	

       
}
