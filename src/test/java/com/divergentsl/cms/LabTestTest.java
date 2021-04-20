package com.divergentsl.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divergentsl.cms.dao.LabTestDao;



public class LabTestTest {
	
	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists lab_test");
		System.out.println("Table Deleted");
		
		st.executeUpdate("create table if not exists lab_test (test_id int primary key auto_increment, test_name varchar(30), patient_id int, test_fee int)");
		System.out.println("Table created successfully...");
		
		st.execute("insert into lab_test values(1, 'Covid Test', 101, 500)");
		System.out.println("Data inserted successfully...");
		
		st.execute("drop table if exists patient");
		
		st.executeUpdate("create table if not exists patient (patient_id int auto_increment primary key, patient_name varchar(20) NOT NULL, age int, weight int, gender varchar(8), contact_number varchar(15), address varchar(30))");
		System.out.println("Table created successfully...");
		
		st.execute("insert into patient values(101, 'john', 55, 55, 'male', '453648756', 'los')");
		System.out.println("Data inserted successfully...");
		
		
	}

	
	@Test
	void insertTestSuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		
		int i = labTestDao.add("101", "Blood Test", "700");
		assertEquals(1, i);
	}
	
	@Test
	void searchTestSuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		Map<String,String> map = labTestDao.search("1");
		
		assertFalse(map.isEmpty());
	}
	
	@Test
	void searchTestUnsuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		Map<String,String> map = labTestDao.search("14864");
		
		assertTrue(map.isEmpty());
	}
	
	@Test
	void listTestSuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		List<Map<String,String>> map = labTestDao.listTest();
		
		assertFalse(map.isEmpty());
	}
	

	@Test
	void deleteTestSuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		
		int i = labTestDao.delete("1");
		
		assertEquals(1, i);
	}
	
	@Test
	void deleteTestUnsuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		
		int i = labTestDao.delete("1123");
		
		assertEquals(0, i);
	}
	
	@Test
	void updateTestSuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		
		Map<String,String> map = new HashMap<>();
		map.put("patientId", "105");
		map.put("testId", "1");
		map.put("testName", "Suger Test");
		map.put("fee", "800");
		
		int i = labTestDao.update(map);
		
		assertEquals(1, i);
	}

	@Test
	void updateTestUnsuccessful() throws SQLException {
		LabTestDao labTestDao = new LabTestDao(driverManager);
		
		Map<String,String> map = new HashMap<>();
		map.put("patientId", "105");
		map.put("testId", "14646");
		map.put("testName", "Suger Test");
		map.put("fee", "800");
		
		int i = labTestDao.update(map);
		
		assertEquals(0, i);
	}
}