package com.divergentsl.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divergentsl.cms.dao.DoctorDao;
import com.divergentsl.cms.dao.LoginDao;


public class DoctorDaoTest {
	
	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists doctor");
		System.out.println("Table Deleted");
		
		st.executeUpdate("create table if not exists doctor (did int auto_increment, dname varchar(30), username varchar(15) unique, password varchar(20), speciality varchar(20), fee int)");
		System.out.println("Table created successfully...");
		
		st.execute("insert into doctor values(1001, 'jayant', 'jayant', 'jayant', 'dentist', 1000)");
		System.out.println("Data inserted successfully...");
		
	}

	
	@Test
	void doctorLoginWithCorrectUsernamePassword() throws SQLException {
		
		LoginDao loginDao = new LoginDao(driverManager);
		assertEquals("1001 jayant", loginDao.doctorLogin("jayant", "jayant"));
	}
	
	@Test
	void doctorLoginWithIncorrectUsernamePassword() throws SQLException {
		
		LoginDao loginDao = new LoginDao(driverManager);
		String result = loginDao.doctorLogin("jay", "jay");
		assertEquals(null, result);
	}
	
	@Test
	void searchDoctorFoundById() throws SQLException {
		
		DoctorDao doctorDao = new DoctorDao(driverManager);
		Map<String, String> result = doctorDao.searchById("1001");
		assertFalse(result.isEmpty());
	}
	
	@Test
	void searchDoctorNotFoundById() throws SQLException {
		
		DoctorDao doctorDao = new DoctorDao(driverManager);
		Map<String, String> result = doctorDao.searchById("102");
		assertTrue(result.isEmpty());
	}
	
	@Test
	void deleteDoctorSuccessful() throws SQLException {
		DoctorDao doctorDao = new DoctorDao(driverManager);
		assertEquals(1, doctorDao.delete("1001"));
	}
	
	@Test
	void deleteDoctorUnsuccessful() throws SQLException {
		DoctorDao doctorDao = new DoctorDao(driverManager);
		assertNotEquals(1, doctorDao.delete("101"));
	}
	
	
	@Test
	void insertDoctorSuccessful() throws SQLException {
		DoctorDao doctorDao = new DoctorDao(driverManager);
		int i = doctorDao.insert("abhishek", "abhishek", "abhishek", "surgeon");
		assertEquals(1, i);
	}
	
	@Test
	void insertDoctorUnsuccessful() throws SQLException {
		DoctorDao doctorDao = new DoctorDao(driverManager);
		assertThrows(SQLException.class, () -> doctorDao.insert("jayant", "jayant", "jayant", "surgeon"));
	}
	

	@Test
	void listDoctor() throws SQLException {
		DoctorDao doctorDao = new DoctorDao(driverManager);
		assertFalse(doctorDao.list().isEmpty());
	}
	
	@Test
	void updateDoctor() throws SQLException {
		DoctorDao doctorDao = new DoctorDao(driverManager);
		Map<String,String> map = new HashMap<>();
		map.put("dname", "abhishek");
		map.put("speciality", "surgeon");
		map.put("did", "1001");
		int i = doctorDao.update(map);
		assertEquals(1, i);
	}
	
	

}
