package idv.cm;

import java.io.IOException;

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

import idv.cm.db.Utility;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class ServletLogin
 */

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//fake data
	private List<UserBean> list = UserBean.createUserList();
	
	private RequestDispatcher rd;
	public Logger log1,log2,log3;
	
	//retrieve from db
	private HashSet<UserBean> valueSet;
//	private Hashtable<Integer,UserBean> table = new Hashtable<>();
	
	public HashSet<UserBean> getMyDb() throws SecurityException, IOException{
		//check connection
		boolean isConnect = Utility.connectMySQL();
		if(isConnect) {
			Utility.writeLogger(this.getServletInfo(),"isConnect = "+isConnect);
            //make sure table is created!
			Utility.writeLogger(this.getServletInfo(),"isCreateTable = "+Utility.createTable());
			valueSet = Utility.readFromMySQL();
			Iterator<UserBean> it = valueSet.iterator();
			while(it.hasNext()) {
				UserBean user = it.next();
				Utility.writeLogger(this.getServletInfo(),"user = "+user);
				Integer key = user.get_id();
//				table.put(Integer.valueOf(key), user);
			}
			
//			valueSet.forEach(s->System.out.println(s));
			return valueSet;
		}else {
			return null;
		}
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
		
//		  request.setAttribute("listA", list);
//	      request.getRequestDispatcher("/index.jsp").forward(request, response);
		valueSet = getMyDb();
		request.setAttribute("listA", valueSet);
		rd = getServletContext().getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
	}

}
