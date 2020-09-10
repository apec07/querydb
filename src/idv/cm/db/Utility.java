// Utility for Account
package idv.cm.db;
import java.io.*;
import java.util.*;

import idv.cm.UserBean;

import java.sql.*;

public class Utility {

	public static boolean writeToFile (String pathName,Account account){
		try {
		FileOutputStream fos = new FileOutputStream (pathName);
		ObjectOutputStream oos = new ObjectOutputStream (fos);
		oos.writeObject (account);
		oos.flush();
		oos.close();
		fos.close();
		} catch (IOException ex) {
		System.err.println ("Trouble writing - "+ex);
		return false;
	  }
		
		return true;
	}
	
	public static Account readFromAccount(String pathName){
		Account account;
		try {
		FileInputStream fis = new FileInputStream (pathName);
		ObjectInputStream ois = new ObjectInputStream (fis);
		account = (Account)(ois.readObject());
		ois.close();
		fis.close();
		} catch (IOException ex) {
		System.err.println ("Trouble reading - "+ex);
		return null;
	  } catch (ClassNotFoundException ex){
	  System.err.println ("Trouble Casing - "+ex);
		return null;
		}
		return account;
	}

	public static boolean connectMySQL(){
		 try {
            Class.forName("com.mysql.jdbc.Driver"); 
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
	}
	// STUDY
	// Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop); 
	public static boolean loginDB(){
		/* 
		 use below SQL syntax :
		CREATE DATABASE IF NOT EXISTS db_morgan;
		USE db_morgan;
		*/
		
		return false;
	}
	
	public static boolean createAccount(){
		
		String sqlUrl="CREATE TABLE IF NOT EXISTS ACCOUNT ("+
		"ID INT NOT NULL AUTO_INCREMENT,"+
		"USER varchar(50) NOT NULL UNIQUE,"+
		"PASSWORD varchar(50) NOT NULL,"+
		"NOTE varchar(200),"+
		"PRIMARY KEY ( ID ));";
		boolean isTableCreated = false;
		
				try {
        		Properties prop = new Properties();
            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlUrl);
         
            stmt.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return false;
        }
		
		return true;
	}
	
	public static HashSet<UserBean> readFromMySQL(){
		
		HashSet<UserBean> set = new LinkedHashSet<>();
		
		 try {
//        		Properties prop = new Properties();
//            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", "root","1234");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from account");
   
            while (rs.next()) {
                UserBean account = new UserBean.Builder().build();
                int _id = rs.getInt(1);
                String user = rs.getString(2);
                String password = rs.getString(3);
                String note = rs.getString(4);

                System.out.print(" _ID= " + _id);
                System.out.print(" User= " + user);
                System.out.print(" Password= " + password);
                System.out.print(" Note= " + note);
                System.out.print("\n");
                account.set_id(_id);
                account.setUserName(user);
                account.setUserPass(password);
                account.setUserNote(note);
                set.add(account);
                
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return null;
        }
        
        return set;
	}
	
	public static int writeToMySQL(UserBean account,int type){
		int updateNum;
		System.out.println("WriteToMySQL account = "+account+"\n");
		int id=account.get_id();
		String user = account.getUserName();
		String password = account.getUserPass();
		String note = account.getUserNote();
		String sqlUrl="";
		System.out.println("WriteToMySQL account id= "+id);
		System.out.println("WriteToMySQL account user= "+user);
		System.out.println("WriteToMySQL account password= "+password);
		System.out.println("WriteToMySQL account note= "+note);
		if (user==null || user.trim().length()==0){
			System.out.println("WriteToMySQL return");
			return 0;
		}
		switch (type){
			case 0://type 0
				id = account.get_id();
				System.out.println("WriteToMySQL update id = "+id);
				sqlUrl = "UPDATE ACCOUNT SET USER=\""+user+"\", PASSWORD=\""+password+"\", NOTE=\""+note+"\" WHERE ID = "+id+";";
				break;
				
			case 1://type 1
				id = account.get_id();
				sqlUrl = "DELETE FROM ACCOUNT WHERE ID = "+id+" ;";
				break;
				
			case 2://type 2
				sqlUrl = "INSERT INTO ACCOUNT (USER,PASSWORD,NOTE) VALUES (\""+user+"\""+",\""+password+"\""+",\""+note+"\""+");";
																																	
				break;
			default:
				System.out.println("WriteToMySQL tpye =" +type);
		}
		
		if(sqlUrl.length()==0){
			return 0;
		}
		
		try {
        		Properties prop = new Properties();
            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop);
            Statement stmt = con.createStatement();
            updateNum = stmt.executeUpdate(sqlUrl);
         
            stmt.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return 0;
        }
		return updateNum;
	}
	
	public static int preparedWriteToMySQL(UserBean account,int type){
		int updateNum=0;
		int id=account.get_id();
		String user = account.getUserName();
		String password = account.getUserPass();
		String note = account.getUserNote();
		String sqlUrl="";
		
		if(type==0){
			sqlUrl = "UPDATE ACCOUNT SET USER= ?, PASSWORD= ?, NOTE= ? WHERE ID = ?";
		}else if(type==1){
			sqlUrl = "DELETE FROM ACCOUNT WHERE ID = ?";
		}else if(type==2){
			sqlUrl = "INSERT INTO ACCOUNT (USER,PASSWORD,NOTE) VALUES (? ,? ,? )";
		}else{
		  return 0;
		}
		
		
		try {
        		Properties prop = new Properties();
            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop);
      			PreparedStatement pstm = con.prepareStatement(sqlUrl); 
            switch (type){
								case 0://type 0
								System.out.println("type = "+type);
									pstm.setString(1,user);
									pstm.setString(2,password);
									pstm.setString(3,note);
									pstm.setInt(4,id);
									break;
				
								case 1://type 1
								System.out.println("type = "+type);
									pstm = con.prepareStatement(sqlUrl);      
									pstm.setInt(1,id);
									break;
				
								case 2://type 2
								System.out.println("type = "+type);
									pstm = con.prepareStatement(sqlUrl);      
									pstm.setString(1,user);
									pstm.setString(2,password);
									pstm.setString(3,note);																													
									break;
								default: 
									System.err.println("wrong type - "+type);
						}
            updateNum = pstm.executeUpdate();
            pstm.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return 0;
        }
					return updateNum;
		
	}

}