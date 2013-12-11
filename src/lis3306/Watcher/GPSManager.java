/**
 * 
 */
package lis3306.Watcher;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *	GPS 값을 DB에서 읽어오거나 DB에 입력하는 Model Class
 */
public class GPSManager {
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	public GPSManager(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/**
	 * @title getGPS
	 * @param fromTS	읽어오기 시작할 ts
	 * @param toTS		읽어옴을 끝낼 ts
	 * @param phonenumber		읽어올 아이의 전화번호
	 * @return json message { gps : [{lat : 37.566113, lon : 126.940148, TS : 1385734459}, {lat : 37.566113, lon : 126.940148, TS : 1385734459}, ... ] }
	 * ( gps라는 키는 배열을 가리키는데, 그 배열 안에는 lat/lon/TS로 이루어진 object 여러개가 들어있다 )
	 * 
	 * input : { action : "getGPS", fromTS : 1385714459, toTS : 1385724459, phonenumber : "01012345678", TS : 1385734459 }
	 * 
	 * 1. LoginManger의 isLoggedIn() 함수를 통해
	 * (만약 로그인이 되어 있지 않다면, 그렇다는 내용의 에러를 발생시키고)
	 * 
	 * 2. 로그인이 되어 있다면, 주어진 fromTs부터 toTs까지의 GPS 값을 보두 DB로부터 읽어온다.
	 * 
	 * 3.읽어온 값들을 JSON배열로 내보낸다. 
	 */		
	public String getGPS(long fromTS, long toTS, String phonenumber) {
		String json = "";
		JSONObject obj = new JSONObject();
    	obj.put("action","getGPS");
    	if(phonenumber == null) {
    		obj.put("success", "0");
    		obj.put("message", "not Logged in");
    		return obj.toJSONString();
    	}
    	
    	String childrenIdx = EncryptManager.childrenIdx(phonenumber);

        LoginManager lm = new LoginManager(this.request, this.response);
       	boolean log = lm.isLoggedIn();
	    if (log == false){
	    	//로그인이 안되어있으면 에러메세지를 출력한다.
    	  	obj.put("success",0);
	    	obj.put("message","Not logined");
	    	obj.put("gps",null);
	    	
	    } else {
	    	//로그인이 되었으면 DB에서 가져온다.
	    	JSONArray coords = new JSONArray();
	    	try {
	    		ArrayList<HashMap<String, Object>> rs = DBManager.excuteQuery("SELECT * FROM gps WHERE ( ts >= "+fromTS+" ) AND ( ts <="+toTS+" ) AND (children_idx='"+childrenIdx+"') ORDER BY ts ASC, id ASC;");
	    		Iterator<HashMap<String, Object>> it = rs.iterator();
		    	while( it.hasNext() ) {
		    		HashMap<String, Object> map = it.next();
		    		JSONObject row = new JSONObject();	    		
		    		row.put("lat", (Double)map.get("lat"));
		    		row.put("lon", (Double)map.get("lon"));
		    		row.put("ts", (Long)map.get("ts"));
		    		coords.add(row);
		    	}
		    	
		    	obj.put("success",1);
		    	obj.put("message","");
		    	
	    	}  finally {
	    		obj.put("gps",coords);
	    	}
	    	
	    }
    	json = obj.toString();
		return json;
	}
	
	/**
	 * @title putGPS
	 * @param lat			latitude, 위도 
	 * @param lon			longitude, 경도 
	 * @param phonenumber	자녀의 phone number
	 * @param TS			요청 시각(sec)
	 * @return json message { nResult : executeUpdate 결과 return된 int값 }
	 * 
	 * input : { action : "putGPS", lat : 37.566113, lon : 126.940148, phonenumber : "01022222222", TS : 1385734459 }
	 * 
	 * 우선, phonenumber로부터 생성된 childrenIdx 값이 children table 상에 존재하는지 확인한다.
	 * 만약 존재한다면, 해당 childrenIdx값과 주어진 위도, 경도, 시간 값을 gps table에 insert한다.
	 * 만약 존재하지 않는다면, 오류를 내보낸다.
	 */
	public String putGPS(double lat, double lon, String phonenumber, long TS) {
		String childrenIdx = EncryptManager.childrenIdx(phonenumber);
		String json = "";
		JSONObject obj=new JSONObject();
		obj.put("action","putGPS");
		
		ArrayList<HashMap<String, Object>> rs = DBManager.excuteQuery("SELECT * FROM children WHERE idx='"+childrenIdx+"';");
		int nRows = rs.size();
		
		if (nRows > 0 ){
		   	try{	
				int nResult = DBManager.executeUpdate("INSERT INTO gps (lat,lon,ts,children_idx) VALUES("+lat+","+lon+","+TS+",'"+childrenIdx+"');");	
					
				obj.put("success","1");
				obj.put("message","Record inserted successfully!");
				obj.put("nResult",nResult);
				
				
					
			} catch (Exception e){
				obj.put("success","0");
				obj.put("message","SQL error");	
				obj.put("nResult",null);	
			}
		} else {
			obj.put("success","0");
			obj.put("message","No Record error");	
			obj.put("nResult",null);	
		}
	
		json = obj.toString();
		return json;
	}
}