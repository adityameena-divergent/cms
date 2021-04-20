package com.divergentsl.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divergentsl.cms.dao.PatientDao;


public class PatientDaoTest {
	
	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists patient");
		System.out.println("Table Deleted");
		
		st.executeUpdate("create table if not exists patient (patient_id int auto_increment primary key, patient_name varchar(20) NOT NULL, age int, weight int, gender varchar(8), contact_number varchar(15), address varchar(30))");
		System.out.println("Table created successfully...");
		
		st.execute("insert into patient values(101, 'john', 55, 55, 'male', '453648756', 'los')");
		System.out.println("Data inserted successfully...");
		
	}

	
	@Test
	void insertPatientSuccessful() throws SQLException {
		PatientDao patientDao = new PatientDao(driverManager);
		Map<String, String> map = new HashMap<>();
		map.put("patient_name", "deepak");
		map.put("age", "30");
		map.put("weight", "75");
		map.put("gender", "Male");
		map.put("contact_number", "9876543210");
		map.put("address", "Mumbai");
		
		assertEquals(1, patientDao.insert(map));
	}
	
	
	@Test
	void searchPatientSuccessful() throws SQLException {
		
		PatientDao patientDao = new PatientDao(driverManager);
		Map<String, String> map = patientDao.search("101");
		assertFalse(map.isEmpty());
		
	}
	
	
	@Test
	void searchPatientUnsuccessful() throws SQLException {
		
		PatientDao patientDao = new PatientDao(driverManager);
		Map<String, String> map = patientDao.search("10101");
		assertTrue(map.isEmpty());
	}
	
	@Test
	void listAllPatient() throws SQLException {
		PatientDao patientDao = new PatientDao(driverManager);
		List<Map<String,String>> list = patientDao.listAll();
		assertFalse(list.isEmpty());
	}
	
	@Test
	void deletePatientSuccessful() throws SQLException {
		PatientDao patientDao = new PatientDao(driverManager);
		int i = patientDao.delete("101");
		assertEquals(1, i);
	}
	
	@Test
	void deletePatientUnsuccessful() throws SQLException {
		PatientDao patientDao = new PatientDao(driverManager);
		int i = patientDao.delete("10101");
		assertNotEquals(1, i);
	}
	
	@Test
	void updatePatientSuccessful() throws SQLException {
		PatientDao patientDao = new PatientDao(driverManager);
		Map<String,String> map = new HashMap<>();
		
		map.put("id", "101");
		map.put("age", "52");
		map.put("weight", "33");
		map.put("name", "Paul");
		map.put("gender", "Male");
		map.put("contactNumber", "123456789");
		map.put("address", "Bhopal");
		
		int i = patientDao.update(map);
		assertEquals(1, i);
	}
	
	@Test
	void updatePatientUnsuccessful() throws SQLException {
		PatientDao patientDao = new PatientDao(driverManager);
		Map<String,String> map = new HashMap<>();
		
		map.put("id", "10101");
		map.put("age", "52");
		map.put("weight", "33");
		map.put("name", "Paul");
		map.put("gender", "Male");
		map.put("contactNumber", "123456789");
		map.put("address", "Bhopal");
		
		int i = patientDao.update(map);
		assertNotEquals(1, i);
	}
	
	
}