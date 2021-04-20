package com.divergentsl.cms;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divergentsl.cms.dao.LoginDao;


public class AdminLoginTest {
	
	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists admin");
		System.out.println("Table Deleted");
		
		st.executeUpdate("create table if not exists admin (username varchar(15), password varchar(15))");
		System.out.println("Table created successfully...");
		
		st.execute("insert into admin values('admin', 'root')");
		System.out.println("Data inserted successfully...");
		
	}

	@Test
	void testAdminLoginSuccessful() throws SQLException {
		
		LoginDao loginDao = new LoginDao(driverManager);
		assertTrue(loginDao.adminLogin("admin", "root"));
	}
	
	@Test
	void testAdminLoginUnsuccessful() throws SQLException {
		
		LoginDao loginDao = new LoginDao(driverManager);
		assertFalse(loginDao.adminLogin("adminasdas", "ramsdbhoot"));
	}




}
