/**
 * 
 */
package lis3306.Watcher;

/**
 *	세션의 생성/폐기를 통한 로그인/로그아웃 관리 클래스 
 */
public class LoginManager {
	
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
		
	}
	
	/**
	 * @title isLoggedIn
	 * @return 세션 검사를 통해서 현재 로그인이 되어 있는지 아닌지 검사한다. 되어 있다면  true를, 아니라면 false를 리턴한다.
	 */
	protected boolean isLoggedIn() {
		
	}
}
