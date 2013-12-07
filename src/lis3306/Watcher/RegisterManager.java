/**
 * 
 */
package lis3306.Watcher;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

/**
 * 자녀와 부모를 포함한 회원의 가입을 관리하는 객체이다.
 */
public class RegisterManager {
	
	/**
	 * @title registerParent
	 * @param name			부모 이름
	 * @param userid		부모가 로그인에 사용할 id
	 * @param password		부모가 로그인에 사용할 password
	 * @param phonenumber	자녀의 전화번호
	 * @param TS			요청 시각 (sec)
	 * @return json message 
	 * 
	 * input : { action : "registerParent", name : "둘리엄마", userid : "dul2mother", password : "d2d2", phonenumber : "01022222222", TS : 1385734459 }
	 * 
	 * 등록 요청된 userid, password, name, phonenumber(로 부터 생성된 childrenIdx 값)에 대해서, 
	 * `parent` table 상에 동일한 userid가 존재한다면 예외를 발생시키고,
	 * 없다면 요청된 값을 DB상에 등록 후,
	 * 성공했다는 json을 return 한다.
	 */
	public String registerParent(String name, String userid, String password, String phonenumber, long TS) {
		String childrenIdx = EncryptManager.childrenIdx(phonenumber);
		String json = "";
		JSONObject obj=new JSONObject();
		obj.put("action","registerParent");
		
		ResultSet rs = DBManager.excuteQuery("SELECT * FROM parent WHERE userid != userid;");
	
		if (rs == null){
		  	try{	
				
		  		int nResult = DBManager.executeUpdate("INSERT INTO parent (name,userid,password,children_idx,ts) VALUES (name,userid,password,childrenIdx,TS)");	
				System.out.println("Registered Succesfully.");
			
				obj.put("success","1");
				obj.put("message","");
				
				
			}
			catch(Exception e){
				obj.put("success","0");
				obj.put("message","SQL error");	
			}
		}
		
		else
		{
			System.out.println("Record is Already existed");
			obj.put("success","0");
			obj.put("message","already existed error");	
	   	
		}
		
		json = obj.toString();
		return json;
		}




		
	/**
	 * @title registerChildren
	 * @param name			자녀의 이름
	 * @param phonenumber	자녀의 전화번호
	 * @param TS			요청 시각(sec)
	 * @return json message
	 * 
	 * 등록 요청된 name, phonenumber에 대하여,
	 * parent table 상의 children_idx 값들 중, phonenumber로부터 생성된 childrenIdx값과 일치되는 것이 있으면
	 * children table에 적절한 값을 씀으로써, 자녀를 회원으로 등록한다.
	 * 만약 존재하지 않는다면, 실패 시킨다. 
	 * 
	 * input : { action : "registerChildren", name : "둘리", phonenumber : "01022222222", TS : 1385734459 }
	 */
	public String registerChildren(String name, String phonenumber, long TS) {
		
		String json = "";
		JSONObject obj=new JSONObject();
		obj.put("action","registerChildren");
		String childrenIdx = EncryptManager.childrenIdx(phonenumber);
		
		ResultSet rs = DBManager.excuteQuery("SELECT * FROM children WHERE idx == childrenIdx;");
		
		if (rs != null){
			
		
		try{	
				
	    	int nResult = DBManager.executeUpdate("INSERT INTO children (name,phonenumbeer,ts) VALUES (name,phonenumber,TS)");	
			System.out.println("Registerd Successfully.");
			
			obj.put("success","1");
			obj.put("message","");
			
		}
		catch(Exception e){
			obj.put("success","0");
			obj.put("message","SQL error");	
		}
	}
	
	else
	{
		System.out.println("No Record");
		obj.put("success","0");
		obj.put("message","No Record error");	
   	
	}
	
	json = obj.toString();
	return json;
	}
}