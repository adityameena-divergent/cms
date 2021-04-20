package com.divergentsl.cms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class H2DatabaseManager implements IDatabaseManager {

//	public static String DB_URL = "jdbc:h2:mem:";
	public static String DB_URL = "jdbc:h2:~/test";

	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, "sa", "");
	}

}
