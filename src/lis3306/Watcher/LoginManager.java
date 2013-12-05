package lis3306.Watcher;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//
/**
 *	세션의 생성/폐기를 통한 로그인/로그아웃 관리 클래스 
 */
public class LoginManager {

	/**
	* @title Constructor
	* @param HttpServletRequest request
	* @param HttpServletResponse response
	* 
	* HttpServlet으로 부터 주어진  request와 response로부터 HttpSession을 생성한다.
	*/
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private HttpSession session = null;
	public LoginManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	
	/**
	* @title login
	* @param userid	부모의 사용자 id
	* @param password	부모의 password
	* 
	* input : { action : "login", userid : "dul2mother", password : "d2d2", TS : 1385734459 }
	* 
	* parent table을 뒤져 userid/password가 일치하는지 확인한다.
	* 그렇다면, 세션 정보를 기록하고, 사용자를 controlCenter.jsp page로 forward하도록 한다.
	* 만약 존재하지 않는다면, error.jsp 페이지로 redirection한다.
	*/

	public void login(String userid, String password) {
		// System.out.println("userid : "+userid+" / password : "+password);	//param값이 정상적으로 들어왔는지 확인용
		
		ResultSet rs = DBManager.excuteQuery("select * from parent where userid = "+userid);
		String rspassword = "";
		int nRow = 0;
		try {
	        while(rs.next()){
				rspassword = rs.getString(rs.findColumn("password"));
	        }   
			
			if(rs.last()) {
				nRow = rs.getRow();
			}
		} catch (SQLException e) {
			// e.printStackTrace();
		}
		
		String nextPage = "";
        if(nRow > 0){			
			if(rspassword.equals(password)){		//DB 조회 결과 비밀번호가 가져온 비밀번호와 일치하는 지 확인합니다. equals가 맞는지는 사실 잘 기억이 안나네요; 보통 직접 안써서;)			
				// 로그인 성공
				session.setAttribute("userid",userid); 
				nextPage = "controlCenter.jsp";
			} else{
				// 비밀번호가 일치하지 않습니다
				nextPage = "error.jsp";
			}
		} else{
			// 없는 사용자입니다
			nextPage = "error.jsp";
		}
        
        try {
			request.getRequestDispatcher("/"+nextPage).forward(request, response);
		} catch (ServletException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
       
	}
	
	/**
	* @title logout
	* 
	* input : { action : "logout", TS : 1385734459 }
	* 
	* 세션 내의 정보를 지우고, 세션을 invalidate하고
	* index.html로 redirection해 준다.
	*/
	public void logout() {
		boolean isLogin = isLoggedIn();
		session.removeAttribute("userid");
		if(isLogin){
			session.invalidate();
		}
	}
	
	/**
	* @title isLoggedIn
	* @return 세션 검사를 통해서 현재 로그인이 되어 있는지 아닌지 검사한다. 되어 있다면  true를, 아니라면 false를 리턴한다.
	*/
	protected boolean isLoggedIn() {
		boolean isLoggedIn = false;
		String log_check = (String)session.getAttribute("userid"); 
		isLoggedIn = (log_check != null && !log_check.equals("") ) ? true : false; 
		return isLoggedIn;
	}
}




