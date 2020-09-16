package idv.cm.db;

import java.sql.Connection;
import java.util.HashSet;

public interface UserVOImp {
	
	int insert(Connection con, UserVO user);
	int update(Connection con,int id, UserVO user);
	int delete(Connection con,int id);
	UserVO findByIndex(Connection con,int id);
	HashSet<UserVO> findAll(Connection con);

}
