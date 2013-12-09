/**
 * 
 */
package lis3306.Watcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * @author pong0923
 */
public class DBManager {
	static final private String environment = "deploy";
	
	static final private String connectorName	= "jdbc";			//> db connector type. (DON'T TOUCH)
	static final private String dbType 			= "mysql";			//> db type
	
	static final private String hostURL 		= "localhost";		//> DB가 존재하는 ip 혹은 url ex) localhost
	static final private String dbName 			= "watcher";		//> Database name ex) web
	
    static final private String userid 		= "root";
    static final private String password 	= "";
    static final private String server 		= environment.equalsIgnoreCase("development") 
    							? "jdbc:mysql//localhost:3306/watcher" 
    							: connectorName + ":"+ dbType +"://" + DBManager.hostURL + ":3306/"+dbName;
    
    // http://stackoverflow.com/questions/16425224/java-servlet-insert-mysql
    
    static private Connection connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	return DriverManager.getConnection(server, userid, password);
    }
    
    static private void close(Connection conn) throws SQLException {
    	conn.close();
    }
    
    /**
     * excuteQuery는 SELECT와 같이 실행결과가 DB, table을 변화시키지 않는 query를 수행한다.
     * @return SELECT한 결과물의 집합을 반환한다.
     * 사용법 : DBManager.excuteQuery("SELECT...");
     */
    static public ArrayList<HashMap<String, Object>> excuteQuery(String query) throws Error {
    	Connection conn = null;
    	Statement st = null;
    	ResultSet rs = null;
    	
    	ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
    	ResultSetMetaData rsmd;
    	int nCol = 0;
    	
    	try {
    		conn = DBManager.connect();
    		st = conn.createStatement();
    		PreparedStatement ps = conn.prepareStatement(query);
    		rs = ps.executeQuery();
    		
    		rsmd = (ResultSetMetaData)rs.getMetaData();
			nCol = rsmd.getColumnCount();
    		
			while(rs.next()) {
    			HashMap<String, Object> row = new HashMap<String, Object>();
    			for(int i=0; i< nCol; i++) {
    				String key = rsmd.getColumnLabel(i+1);
    				if(key.equalsIgnoreCase("lat") || key.equalsIgnoreCase("lon")) {
    					Double val = rs.getDouble(key);
    					row.put(key, val);
    				} else if(key.equalsIgnoreCase("ts")) {
    					Long val = rs.getLong(key);
    					row.put(key, val);
    				} else {
    					String val = rs.getString(key);
    					row.put(key, val);
    				}
    				
    			}
    			result.add(row);
    		}
    	} catch (Exception e) {
    		Logger lgr = Logger.getLogger(DBManager.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
            Error err = null;
            String errMsg = e.getMessage();
            if(errMsg == null || errMsg.length() < 1 ) {
            	err = new Error();
            } else {
            	err = new Error(e.getMessage());
            }
            
            throw err;
    	} finally {
    		if(st != null) {
    			try {
    				st.close();
    			} catch (SQLException e) {
    				Error err = new Error(e.getMessage() + "");
    	            throw err;
    			}
    		}
    		if(conn != null) {
    			try {
    				DBManager.close(conn);
    			} catch (SQLException e) {
    				Error err = new Error(e.getMessage() + "");
    	            throw err;
    			}
    		}
    	}
    	
    	
    	
    	return result;
    }
    
    /**
     * executeUpdate는 query의 실행결과가 DB, table 변화시킬 때 사용한다.
     * INSERT, DELETE, UPDATE등이 이헤 해당한다.
     * @return ResultSet 얼마나 많은 row 들에 변화(삽입/수정)가 있었는지 반환된다.
     * 사용법 : DBManager.executeUpdate("INSERT...");
     */
    static public int executeUpdate(String query) {
    	Connection conn = null;
    	Statement st = null;
    	int nResult = 0;
    	try {
    		conn = DBManager.connect();
    		st = conn.createStatement();
    		PreparedStatement ps = conn.prepareStatement(query);
    		nResult = ps.executeUpdate();
    	} catch (Exception e) {
    		Logger lgr = Logger.getLogger(DBManager.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
    	} finally {
    		if(st != null) {
    			try {
    				st.close();
    			} catch (SQLException e) {}
    		}
    		if(conn != null) {
    			try {
    				DBManager.close(conn);
    			} catch (SQLException e) {}
    		}
    	}
    	
    	return nResult;
    }
    
    
    
}
