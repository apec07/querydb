package idv.cm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import idv.cm.db.ConnectionFactory;
import idv.cm.db.UserJDBCDAO;
import idv.cm.db.UserVO;
import idv.cm.db.UserVOImp;
import idv.cm.db.Utility;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class ServletLogin
 */

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//fake data
	private List<UserVO> list = UserVO.createUserList();
	
	private RequestDispatcher rd;
	public Logger log1,log2,log3;
	
	//retrieve from db
	private HashSet<UserVO> valueSet;
	HashSet<UserVO> set = null;
	private UserJDBCDAO userDao = null;
//	private Hashtable<Integer,UserBean> table = new Hashtable<>();
	
	public HashSet<UserVO> getMyDb() throws IOException{
		
		ConnectionFactory factory = ConnectionFactory.getInstance();
		try(Connection con = factory.getConnection()){
			userDao = new UserJDBCDAO();
			set = userDao.findAll(con);
		}catch(SQLException ex) {
			Utility.writeLogger("getMethod", ex.toString());
			return null;
		}
		return set;
	}
	
	public boolean saveMyDb(UserVO user) throws IOException {
		ConnectionFactory factory = ConnectionFactory.getInstance();
		try(Connection con = factory.getConnection()){
			UserJDBCDAO userDao = new UserJDBCDAO();
			userDao.insert(con, user);
		}catch(SQLException ex) {
			Utility.writeLogger("saveMethod", ex.toString());
			return false;
		}
		getMyDb();
		return true;
	}
	
	public boolean updateMyDb(UserVO user,String idStr) throws IOException {
		int id = Integer.parseInt(idStr);
		ConnectionFactory factory = ConnectionFactory.getInstance();
		try(Connection con = factory.getConnection()){
			UserJDBCDAO userDao = new UserJDBCDAO();
			userDao.update(con, id, user);
		}catch(SQLException ex) {
			Utility.writeLogger("updateMethod", ex.toString());
			return false;
		}
		getMyDb();
		return true;
	}
	public boolean deleteMyDb(String idStr) throws IOException{
		int id = Integer.parseInt(idStr);
		ConnectionFactory factory = ConnectionFactory.getInstance();
		try(Connection con = factory.getConnection()){
			userDao.delete(con, id);
		}catch(SQLException ex) {
			Utility.writeLogger("updateMethod", ex.toString());
			return false;
		}
		getMyDb();
		return true;
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
//		super.service(request, response);
		
		valueSet=getMyDb();
		request.setAttribute("listA", valueSet);
		rd = getServletContext().getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		HttpSession session = request.getSession();
//		session.setAttribute(name, value);
//		valueSet = getMyDb();		
//		request.setAttribute("listA", list);
//	    request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String note = request.getParameter("note");
		if(name==null||pass==null||note==null) {
			Utility.getLogger("doPost", "not input anything");
			return;
		}
		String type = request.getParameter("submitAction");
		if(type==null) {
			Utility.getLogger("doPost", "Type ==null");
			return;
		}
		Utility.getLogger("doPost", "type = "+ type);
		UserVO user = new UserVO.Builder()
				.userName(name)
				.userPass(pass)
				.userNote(note)
				.build();
		switch(type) {
		case "New":
			saveMyDb(user);
			break;
			
		case "Update":
			String idStr1 = request.getParameter("id");
			updateMyDb(user,idStr1);
			break;
			
		case "Delete":
			String idStr2 = request.getParameter("id");
			deleteMyDb(idStr2);
			break;
			default:
				Utility.getLogger("doPost", "default");
				return;
		}
		
//		doGet(request, response);
	}

}
