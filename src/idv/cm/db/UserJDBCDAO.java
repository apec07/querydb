package idv.cm.db;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class UserJDBCDAO implements UserVOImp {

	private PreparedStatement ptmt = null;
	private ResultSet rs = null;

	private static final String INSERT_STMT="INSERT INTO ACCOUNT (USER,PASSWORD,NOTE) VALUES(?,?,?)";
	private static final String GET_ALL_STMT="SELECT * FROM ACCOUNT ORDER BY -ID";
	private static final String GET_ONE_STMT="SELECT * FROM ACCOUNT WHERE ID = ?";
	private static final String DELETE_STMT="DELETE FROM ACCOUNT WHERE ID =?";
	private static final String UPDATE_STMT="UPDATE ACCOUNT SET USER=?, PASSWORD=?,NOTE=? WHERE ID=?";

	@Override
	public int insert(Connection con, UserVO user) {
		int updateCount=0;
		String name = user.getUserName();
		String pass = user.getUserPass();
		String note = user.getUserNote();
		try(PreparedStatement ptmt = con.prepareStatement(INSERT_STMT)){
			ptmt.setString(1, name);
			ptmt.setString(2, pass);
			ptmt.setString(3, note);
			updateCount = ptmt.executeUpdate();
		}catch(SQLException ex) {
			Utility.getLogger(this.getClass().getSimpleName().toString(), ex.getMessage());
		}
		return updateCount;
	}

	@Override
	public int update(Connection con, int id, UserVO user) {
		int updateCount=0;
		String name = user.getUserName();
		String pass = user.getUserPass();
		String note = user.getUserNote();
		try(PreparedStatement ptmt = con.prepareStatement(UPDATE_STMT)){
			ptmt.setString(1, name);
			ptmt.setString(2, pass);
			ptmt.setString(3, note);
			ptmt.setInt(4, id);
			updateCount = ptmt.executeUpdate();
		}catch(SQLException ex) {
			Utility.getLogger(this.getClass().getSimpleName().toString(), ex.getMessage());
		}
		return updateCount;
	}

	@Override
	public int delete(Connection con, int id) {
		int updateCount=0;
		try(PreparedStatement ptmt = con.prepareStatement(DELETE_STMT)){
			ptmt.setInt(1, id);
			updateCount = ptmt.executeUpdate();
		}catch(SQLException ex) {
			Utility.getLogger(this.getClass().getSimpleName().toString(), ex.getMessage());
		}
		return updateCount;
	}

	@Override
	public UserVO findByIndex(Connection con, int id) {
		UserVO mUser= new UserVO.Builder().build();
		try(PreparedStatement ptmt = con.prepareStatement(GET_ONE_STMT)){
			ptmt.setInt(1, id); // ID in DB is 1,value is index
			rs = ptmt.executeQuery();
			while(rs.next()) {
				int _id = rs.getInt(1);
				String name = rs.getString(2);
				String pass = rs.getString(3);
				String note = rs.getString(4);
				mUser.set_id(_id);
				mUser.setUserName(name);
				mUser.setUserPass(pass);
				mUser.setUserNote(note);
			}
		}catch(SQLException ex) {
			Utility.getLogger(this.getClass().getSimpleName().toString(), ex.getMessage());
		}
		
		return mUser;
	}

	@Override
	public HashSet<UserVO> findAll(Connection con) {
		HashSet<UserVO> set = new LinkedHashSet<>();
		try(PreparedStatement ptmt = con.prepareStatement(GET_ALL_STMT)){
			rs = ptmt.executeQuery();
			while(rs.next()) {
				UserVO user = new UserVO.Builder().build();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String pass = rs.getString(3);
				String note = rs.getString(4);
				
				user.set_id(id);
				user.setUserName(name);
				user.setUserPass(pass);
				user.setUserNote(note);
				set.add(user);
			}
		}catch(SQLException ex) {
			Utility.getLogger(this.getClass().getSimpleName().toString(), ex.getMessage());
		}
		return set;
	}


}
