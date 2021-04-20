package com.divergentsl.cms.dao;

import com.divergentsl.cms.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * It is helper class for performing CRUD operation on doctor
 *
 */
public class DoctorDao {

	public static final String SPECIALITY = "speciality";
	public static final String NAME = "dname";
	public static final String ID = "did";
	
	IDatabaseManager databaseManager;

	public DoctorDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	
	/**
	 * It is a helper method for delete the doctor data
	 * @param doctorId
	 * @return 1 if successfully delete otherwise it return 0.
	 * @throws SQLException
	 */
	public int delete(String doctorId) throws SQLException {
		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		int i = st.executeUpdate("delete from doctor where " + ID + " = " + doctorId);

		st.close();
		con.close();
		return i;
	}

	/**
	 * It is a helper method for update the doctor
	 * @param map
	 * @return 1 if data successfully updated otherwise it return 0
	 * @throws SQLException
	 */
	public int update(Map<String, String> map) throws SQLException {
		Connection con;
		Statement st;
		con = databaseManager.getConnection();
		st = con.createStatement();

		int i = st.executeUpdate("update doctor set " + NAME + " = '" + map.get("dname") + "', " + SPECIALITY + " = '" + map.get(SPECIALITY)
				+ "' where " + ID + " = " + map.get("did"));

		st.close();
		con.close();
		return i;
	}

	/**
	 * It is a helper method for search doctor data.
	 * @param did
	 * @return Map of doctor data if doctor is found, otherwise it returns empty Map.
	 * @throws SQLException
	 */
	public Map searchById(String doctorId) throws SQLException {

		Connection con = null;
		Statement st = null;
		Map<String, String> map = new HashMap<>();

		con = databaseManager.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select " + ID + ", " + NAME + ", " + SPECIALITY + " from doctor where " + ID + " = '" + doctorId + "'");

		if (rs.next()) {
			map.put(ID, rs.getString(1));
			map.put(NAME, rs.getString(2));
			map.put(SPECIALITY, rs.getString(3));
			st.close();
			con.close();

			return map;
		}
		st.close();
		con.close();

		return map;

	}

	/**
	 * It is a helper method for inserting doctor data.
	 * @param dname
	 * @param username
	 * @param password
	 * @param speciality
	 * @return 1 if data inserted successfully, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int insert(String dname, String username, String password, String speciality) throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		int i = st.executeUpdate("insert into doctor (" + NAME + ", username, password, " + SPECIALITY + ") " + "values ('" + dname + "', '"
				+ username + "', '" + password + "', '" + speciality + "')");

		st.close();
		con.close();
		return i;
	}

	
	/**
	 * It is a helper method for retrieve all doctor data.
	 * @return List of Map of each doctor data.
	 * @throws SQLException
	 */
	public List<Map<String, String>> list() throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();

		ResultSet rs = st.executeQuery("select " + ID + ", " + NAME + ", " + SPECIALITY + " from doctor");

		List<Map<String, String>> doctorList = new ArrayList<>();

		while (rs.next()) {
			Map<String, String> aDoctorRecord = new HashMap<>();
			aDoctorRecord.put(ID, rs.getString(1));
			aDoctorRecord.put(NAME, rs.getString(2));
			aDoctorRecord.put(SPECIALITY, rs.getString(3));
			doctorList.add(aDoctorRecord);
		}
		st.close();
		con.close();
		
		return doctorList;

	}
	
	

}
