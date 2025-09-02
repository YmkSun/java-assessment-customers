package com.question6.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/customer_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "P@ssw0rd";

	public static Connection getConnection() throws Exception {
		
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

}
