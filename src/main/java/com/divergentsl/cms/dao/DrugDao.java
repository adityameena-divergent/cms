package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.divergentsl.cms.IDatabaseManager;



/**
 * This class is a helper class for performing CRUD operation on drug
 */
public class DrugDao {
	IDatabaseManager databaseManager;
	
	public final String ID = "drug_id";
	public final String DESCRIPTION = "description";
	public final String NAME = "drug_name";

	public DrugDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	/**
	 * It is a helper method for adding a new drug
	 * @param name
	 * @param description
	 * @return 1 if drug added successfully, otherwise it return 0.
	 * @throws SQLException
	 */
	public int add(String name, String description) throws SQLException {

		Connection con = null;
		Statement st = null;

		con = databaseManager.getConnection();
		st = con.createStatement();
		int i = st.executeUpdate("insert into drug (" + NAME + ", " + DESCRIPTION + ") values ('" + name + "','" + description + "')");
		st.close();
		con.close();
		return i;
	}

	/**
	 * It is a helper method for search the drug by drug id
	 * @param id
	 * @return Map of drug data if drug found, otherwise returns empty Map.
	 * @throws SQLException
	 */
	public Map<String, String> search(String id) throws SQLException {
    	
    	Connection con = null;
    	Statement st = null;
    	
    	con = databaseManager.getConnection();
    	st = con.createStatement();
    	
    	ResultSet rs = st.executeQuery("select * from drug where " + ID + " = " + id);
    	Map<String, String> data = new HashMap<>();
    	if (rs.next()) {
    		data.put("id", rs.getString(1));
    		data.put("name", rs.getString(2));
    		data.put("description", rs.getString(3));
    	}
		st.close();
		con.close();
    	return data;
    }
	

	/**
	 * It is helper method for deleting the drug data by  drug id.
	 * @param id
	 * @return 1 if drug data successfully deleted, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int delete(String id) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		int i = st.executeUpdate("delete from drug where " + ID + " = " + id);

		st.close();
		con.close();
		return i;
	}
	
	
	/**
	 * It is helper method for update the specific drug data
	 * @param data
	 * @return 1 if drug data successfully updated, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int update(Map<String, String> data) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		int i = st.executeUpdate("update drug set " + NAME + " = '" + data.get("name") + "', " + DESCRIPTION + " = '" + data.get("description") + "' where " + ID + " = " + data.get("id"));
		
		st.close();
		con.close();
		return i;
	}

}
