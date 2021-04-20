package com.divergentsl.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divergentsl.cms.dao.DrugDao;


public class DrugDaoTest {
	
	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists drug");
		System.out.println("Table Deleted");
		
		st.executeUpdate("create table if not exists drug (drug_id int auto_increment primary key, drug_name varchar(20) NOT NULL, description varchar(250))");
		System.out.println("Table created successfully...");
		
		st.execute("insert into drug values(1, 'Amoxicillin', 'DESCRIPTION')");
		System.out.println("Data inserted successfully...");	
	}
	
	@Test
	void addDrugSuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		int i = drugDao.add("Aspirin", "DESCRIPTION");
		assertEquals(1, i);
	}
	
	@Test
	void searchDrugSuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		Map<String,String> map = drugDao.search("1");
		assertFalse(map.isEmpty());
	}
	
	@Test
	void searchDrugUnsuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		Map<String,String> map = drugDao.search("5");
		assertTrue(map.isEmpty());
	}
	
	@Test
	void deleteDrugSuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		int i = drugDao.delete("1");
		assertEquals(1, i);
	}
	
	
	@Test
	void deleteDrugUnsuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		int i = drugDao.delete("1011001");
		assertEquals(0, i);
	}
	

	@Test
	void updateDrugSuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		Map<String,String> map = new HashMap<>();
		
		map.put("id", "1");
		map.put("name", "Larigo");
		map.put("description", "DESCRIPTION");
		
		int i = drugDao.update(map);
		assertEquals(1, i);
	}
	
	@Test
	void updateDrugUnsuccessful() throws SQLException {
		
		DrugDao drugDao = new DrugDao(driverManager);
		Map<String,String> map = new HashMap<>();
		
		map.put("id", "144456");
		map.put("name", "Larigo");
		map.put("description", "DESCRIPTION");
		
		int i = drugDao.update(map);
		assertEquals(0, i);
	}
	
	
}