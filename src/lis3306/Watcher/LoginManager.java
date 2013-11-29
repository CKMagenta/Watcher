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
	 * @return	json message
	 * 
	 * input : { action : "login", userid : "dul2mother", password : "d2d2", TS : 1385734459 }
	 * 
	 * 
	 */
	public String login(String userid, String password) {
		
	}
	
	/**
	 * @title logout
	 * @return json message
	 * 
	 * input : { action : "logout", TS : 1385734459 }
	 */
	public String logout() {
		
	}
	
	/**
	 * @title isLoggedIn
	 * @return 세션 검사를 통해서 현재 로그인이 되어 있는지 아닌지 검사한다. 되어 있다면  true를, 아니라면 false를 리턴한다.
	 */
	protected boolean isLoggedIn() {
		
	}
}
