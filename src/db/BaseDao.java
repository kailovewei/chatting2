package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/GL_LibSystem";
	private static final String DBUSER = "root";
	private static final String DBPASS = "mysqladmin";
	private static Connection connection = null;
	
	private BaseDao(){
		try {
			Class.forName(DBDRIVER);
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static ResultSet executeQuery(String sql){
		if(connection == null){
			new BaseDao();
		}
		try {
			ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void close(){
		if(connection!=null){
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
