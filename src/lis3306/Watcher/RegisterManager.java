/**
 * 
 */
package lis3306.Watcher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

/**
 * 자녀와 부모를 포함한 회원의 가입을 관리하는 객체이다.
 */
public class RegisterManager {
	
	/**
	 * @title registerParent
	 * @param parentName			부모 이름
	 * @param childrenName			자녀의 이름
	 * @param userid		부모가 로그인에 사용할 id
	 * @param password		부모가 로그인에 사용할 password
	 * @param phonenumber	자녀의 전화번호
	 * @param TS			요청 시각 (sec)
	 * @return json message 
	 * 
	 * input : { action : "registerParent", parentName : "둘리엄마", childrenName : "둘리", userid : "dul2mother", password : "d2d2", phonenumber : "01022222222", TS : 1385734459 }
	 * 
	 * 등록 요청된 userid, password, name, phonenumber(로 부터 생성된 childrenIdx 값)에 대해서, 
	 * `parent` table 상에 동일한 userid가 존재한다면 예외를 발생시키고,
	 * 없다면 요청된 값을 DB상에 등록 후,
	 * 성공했다는 json을 return 한다.
	 */
	public String registerParent(String parentName, String childrenName, String userid, String password, String phonenumber, long TS) {
		String childrenIdx = EncryptManager.childrenIdx(phonenumber);
		String json = "";
		JSONObject obj=new JSONObject();
		obj.put("action","registerParent");
		
		ArrayList<HashMap<String, Object>> rs = DBManager.excuteQuery("SELECT * FROM parent WHERE userid='"+userid+"';");
		int nRows = rs.size();
				
		if( nRows > 0 ) {
			obj.put("success","0");
			obj.put("message","Record already exists");
		} else {
		  	try{	
				DBManager.executeUpdate("INSERT INTO parent (name,userid,password,children_idx,ts) VALUES ('"+parentName+"','"+userid+"','"+password+"','"+childrenIdx+"',"+TS+")");
				DBManager.executeUpdate("INSERT INTO children (name, phonenumber, children_idx) VALUES ('"+childrenName+"','"+phonenumber+"','"+childrenIdx+"')");
				obj.put("success","1");
				obj.put("message","Registered Succesfully.");
			} catch(Exception e) {
				obj.put("success","0");
				obj.put("message","SQL error");	
			}
		  	
		}
		
		json = obj.toString();
		return json;
	}




		
	/**
	 * @title registerChildren
	 * @param phonenumber	자녀의 전화번호
	 * @param TS			요청 시각(sec)
	 * @return json message
	 * 
	 * 등록 요청된 name, phonenumber에 대하여,
	 * parent table 상의 children_idx 값들 중, phonenumber로부터 생성된 childrenIdx값과 일치되는 것이 있으면
	 * children table에 적절한 값을 씀으로써, 자녀를 회원으로 등록한다.
	 * 만약 존재하지 않는다면, 실패 시킨다. 
	 * 
	 * input : { action : "registerChildren", phonenumber : "01022222222", TS : 1385734459 }
	 */
	public String registerChildren(String phonenumber, long TS) {
		String childrenIdx = EncryptManager.childrenIdx(phonenumber);
		String json = "";
		JSONObject obj=new JSONObject();
		obj.put("action","registerChildren");
		
		ArrayList<HashMap<String, Object>> rs = DBManager.excuteQuery("SELECT * FROM parent WHERE children_idx='"+childrenIdx+"';");
		int nRows = rs.size();
		obj.put("log", "nRows : "+nRows);
		
		if (nRows > 0){
			try{	
		    	DBManager.executeUpdate("UPDATE children SET phonenumbeer='"+phonenumber+"', ts="+TS+" WHERE children_idx='"+childrenIdx+"'");	
				obj.put("success","1");
				obj.put("message","Registerd Successfully.");
			} catch(Exception e) {
				obj.put("success","0");
				obj.put("message","SQL error");	
			} catch( Error e ) {
				obj.put("error", e.getMessage());
			}
		} else {
			obj.put("success","0");
			obj.put("message","There is no matched rows");
		}
		
		rs = DBManager.excuteQuery("SELECT * FROM parent");
		nRows = rs.size();
		obj.put("log2", "nRows : "+nRows);
		
		json = obj.toString();
		return json;
	}
}