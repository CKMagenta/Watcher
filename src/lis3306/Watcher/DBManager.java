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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pong0923
 */
public class DBManager {
	static final private String environment = "development";
	
	static final private String connectorName	= "jdbc";			//> db connector type. (DON'T TOUCH)
	static final private String dbType 			= "mysql";			//> db type
	
	static final private String hostURL 		= "HOST_URL";		//> DB가 존재하는 ip 혹은 url ex) localhost
	static final private String dbName 			= "DB_NAME";		//> Database name ex) web
	
    static final private String userid 		= "DB_USER_ID";
    static final private String password 	= "DB_PASSWORD";
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
    static public ResultSet excuteQuery(String query) {
    	Connection conn = null;
    	Statement st = null;
    	ResultSet rs = null;
    	try {
    		conn = DBManager.connect();
    		st = conn.createStatement();
    		PreparedStatement ps = conn.prepareStatement(query);
    		rs = ps.executeQuery();
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
    	
    	return rs;
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
