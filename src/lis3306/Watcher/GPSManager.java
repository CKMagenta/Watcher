/**
 * 
 */
package lis3306.Watcher;

/**
 *	GPS 값을 DB에서 읽어오거나 DB에 입력하는 Model Class
 */
public class GPSManager {
	
	/**
	 * @title getGPS
	 * @param fromTS	읽어오기 시작할 ts
	 * @param toTS		읽어옴을 끝낼 ts
	 * @return json message { gps : [{lat : 37.566113, lon : 126.940148, TS : 1385734459}, {lat : 37.566113, lon : 126.940148, TS : 1385734459}, ... ] }
	 * ( gps라는 키는 배열을 가리키는데, 그 배열 안에는 lat/lon/TS로 이루어진 object 여러개가 들어있다 )
	 * 
	 * input : { action : "getGPS", fromTS : 1385714459, toTS : 1385724459, TS : 1385734459 }
	 * 
	 * LoginManger의 isLoggedIn() 함수를 통해
	 * (만약 로그인이 되어 있지 않다면, 그렇다는 내용의 에러를 발생시키고)
	 * 로그인이 되어 있다면, 주어진 fromTs부터 toTs까지의 GPS 값을 보두 읽어와서 JSON배열로 내보낸다. 
	 */
	public String getGPS(long fromTS, long toTS) {
		String json = "";
		
		//
		
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
		
		return json;
	}
}
