package com.mvcapp.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.mvcapp.db.JDBCUtils;

public class JDBCUtilsTest {

	@Test
	public void testGetConnetion() throws SQLException {
		Connection connection = JDBCUtils.getConnetion();
		System.out.println(connection);
	}

}
