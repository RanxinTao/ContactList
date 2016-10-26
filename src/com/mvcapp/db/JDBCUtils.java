package com.mvcapp.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	
	private static DataSource dataSource = null;
	
	static {
		dataSource = new ComboPooledDataSource("mvcapp");
	}
	
	public static Connection getConnetion() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static void closeConnection(Connection conn) {
		try {
			if(conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
