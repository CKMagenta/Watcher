/**
 * 
 */
package lis3306.Watcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @summary 외부로부터 모든 접근은 이 HandlerServlet을 통해 이루어 진다.
 */
public class HandlerServlet extends HttpServlet {
	/**
	 * @title doGet
	 * @param request
	 * @param response
	 * @summary  
	 * 모든 request는 공통적으로 action 이라는 값을 가지고 있으며, 다음과 같은 6가지가 존재한다.
	 * 1) registerParent, registerParent
	 * 2) putGPS, getGPS,
	 * 3) login, logout
	 * 그리고 1), 2), 3) 각각 RegisterManager, GPSManager, LoginManager과 관련이 있으므로 
	 * action값을 통해서 각각의 Manager Class 내의method들로 이어 줄 수 있다.
	 * 
	 * 각각의 action 값에 따라 추가적으로 따라오는 값들이 다른데, 이는 각각의 Manager Class 내의 주석을 확인하도록 하자.
	 * 각각의 method와 대응되는 form data의 시를 json 형식으로 적어두었다. 
	 * 해당 주석을 보고 인자 값을 전달받은 request의 formdata로부터 뽑아내어 method를 적절히 호출할 수 있도록 하자.
	 * input은 request 내부에 key-value로 들어있는 formdata이다.
	 * 
	 * Login을 제외한 응답은 모두 json type으로 하도록 한다.
	 * 즉, 각 Manager Class에서 json String을 return 해 주면,
	 * doGet Method에서 PrintWriter를 통해 json string을 화면에 써주기만 하면 된다.
	 * 모든 output json string은 기본적으로 다음의 key-value를 포함하고 있어야 한다.
	 * { action : "(input으로 받은 action)", success : 0 or 1, message : "~~" }
	 * - action의 경우, input으로 들어와 현재 실행중인 method를 호출하게 만든 그 action값을 그대로 넣어준다.
	 * - success의 값은 최종적으로 오류 없이 실행 되어서 올바른 결과를 내보내고 있다면 1, 그렇지 않다면 0을 내보낸다.
	 * 특히, 여러 if try-catch 구문을 사용하여 error handling을 적절히 하도록 한다.
	 * - message의 경우, 오류나 예외가 발생하였을 때 이유를 적어준다.
	 * 만약 성공적으로 실행되었다면(success == 1) 특별히 쓸 말이 없을 수 있다. 있다면 쓰고, 없다면 "" (빈 문자열, null과는 다르다)을 넣어주도록 하자.
	 * 
	 * 각 Manager Class에는 이 3가지 key-value pair를 제외한 추가적인 데이터에 대해서만 언급하도록 하겠다.
	 * 
	 * JSON 어레이 추가
	 * Login/Logout은 json return 없이 세션에 로그인 정보를 쓰고, page를 redirection 해줄 수 있도록 한다.
	 */
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		String json = null;
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		
		if(action == null) {
			writer.print("ACCESS DENIED");
			return;
		} else if(action.equalsIgnoreCase("login")) {
			LoginManager manager = new LoginManager(request, response);
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			
			manager.login( userid, password);
			
		} else if (action.equalsIgnoreCase("logout")) {
			LoginManager manager = new LoginManager(request, response);
			long TS = Long.parseLong(request.getParameter("TS"));
			
			json = manager.logout();
			
		} else if (action.equalsIgnoreCase("registerParent")) {
			RegisterManager manager = new RegisterManager();
			String parentName = request.getParameter("parentName");
			String childrenName = request.getParameter("childrenName");
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			String phonenumber = request.getParameter("phonenumber");
			
			long TS = Long.parseLong( request.getParameter("TS") );
			
			json = manager.registerParent(parentName, childrenName, userid, password, phonenumber, TS);

		} else if (action.equalsIgnoreCase("registerChildren")) {
			RegisterManager manager = new RegisterManager();
			String phonenumber = request.getParameter("phonenumber");
			long TS = Long.parseLong(request.getParameter("TS"));
			
			json = manager.registerChildren(phonenumber, TS);

		} else if (action.equalsIgnoreCase("getGPS")) {
			GPSManager manager = new GPSManager(request, response);
			long fromTS = 0;
			try {
				fromTS = Long.parseLong(request.getParameter("fromTS"));
			} catch (Exception e) {
				Calendar date = new GregorianCalendar();
				date.set(Calendar.HOUR_OF_DAY, 0);
				date.set(Calendar.MINUTE, 0);
				date.set(Calendar.SECOND, 0);
				date.set(Calendar.MILLISECOND, 0);
				fromTS = date.getTimeInMillis()/1000L;
			}
			
			long toTS = 0;
			try {
				toTS = Long.parseLong(request.getParameter("toTS"));
			} catch (Exception e) {
				Calendar date = new GregorianCalendar();
				date.set(Calendar.HOUR_OF_DAY, 0);
				date.set(Calendar.MINUTE, 0);
				date.set(Calendar.SECOND, 0);
				date.set(Calendar.MILLISECOND, 0);
				date.add(Calendar.DAY_OF_MONTH, 1);
				toTS = date.getTimeInMillis()/1000L;
			}
			
			LoginManager loginManager = new LoginManager(request, response);
			String phonenumber = loginManager.getChildrenIdx();
			
			json = manager.getGPS(fromTS, toTS, phonenumber);

		} else if (action.equalsIgnoreCase("putGPS")) {
			GPSManager manager = new GPSManager(request, response);
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			String phonenumber = request.getParameter("phonenumber");
			long TS = Long.parseLong(request.getParameter("TS"));
			
			json = manager.putGPS(lat, lon, phonenumber, TS);
		} 
		
		if ( json != null ) {
			writer.print(json);
		}
		
	}
	
	/**
	 * @title doPost
	 * @param request
	 * @param response
	 * @summary HTTP POST 요청을 받는 method이다. 이번 프로젝트에서는 단순히 doGet으로 redirection만 해 주도록 한다.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{	
		doGet(request, response);		
		
	}
}
