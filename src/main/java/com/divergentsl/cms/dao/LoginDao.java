package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.divergentsl.cms.IDatabaseManager;


/**
 * It is a helper class for admin and doctor login.
 * 
 */
public class LoginDao {
	
	IDatabaseManager databaseManager;
	public final String USERNAME = "username";
	public final String PASSWORD = "password";
	
	public LoginDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	/**
	 * It is a helper for doctor login
	 * @param username
	 * @param password
	 * @return It return doctorId & doctorName if username & password is correct, otherwise it returns null.
	 * @throws SQLException
	 */
	public String doctorLogin(String username, String password) throws SQLException {
	
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select * from doctor where " + USERNAME + " = '" + username + "' AND " + PASSWORD + " = '" + password + "'");
		
		String s = null;
		if(rs.next())
			s = rs.getString(1) + " " + rs.getString(2); 
		
		else
			s = null;
		
		st.close();
		con.close();
		
		return s;
	}
	
	
	/**
	 * It is a helper method for admin login
	 * @param username
	 * @param password
	 * @return It return true if username & password is correct, otherwise it returns false.
	 * @throws SQLException
	 */
	public boolean adminLogin(String username, String password) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select * from admin where " + USERNAME + " = '" + username + "' AND " + PASSWORD + " = '" + password + "'");

//		st.close();
//		con.close();
		if (rs.next())
			return true;
		else
			return false;
	}
	
	
	

}
