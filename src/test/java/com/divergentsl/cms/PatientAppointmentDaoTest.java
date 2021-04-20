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

import com.divergentsl.cms.dao.PatientAppointmentDao;


public class PatientAppointmentDaoTest {
	
	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists patient_appointment");
		System.out.println("Table Deleted");
		
		st.executeUpdate("create table if not exists patient_appointment (appointment_number int primary key auto_increment, patient_id int NOT NULL, doctor_id int NOT NULL, problem varchar(45), appointment_date Date, time varchar(20))");
		System.out.println("Table created successfully...");
		
		st.execute("insert into patient_appointment values(1, 101, 1001, 'Fever', '2020-08-18', '18:00:00')");
		System.out.println("Data inserted successfully...");	
	}
	
	@Test
	void makeAppointmentSuccessful() throws SQLException {
		
		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		Map<String, String> map = new HashMap<>();
		map.put("patientId", "101");
		map.put("doctorId", "1001");
		map.put("appointmentDate", "2021-03-11");
		map.put("problem", "Corona");
		map.put("time", "20:00:00");
		
		int i = patientAppointmentDao.makeAppointment(map);
		assertEquals(1, i);
	}
	
	@Test
	void patientAppointToYouSuccessful() throws SQLException {
		
		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		List<Map<String, String>> list = patientAppointmentDao.patientAppointToYou("1001"); 
		
		assertFalse(list.isEmpty());
	}
	
	@Test
	void patientAppointToYouUnsuccessful() throws SQLException {
		
		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		List<Map<String, String>> list = patientAppointmentDao.patientAppointToYou("100001"); 
		
		assertTrue(list.isEmpty());
	}
	
	@Test
	void allPatientList() throws SQLException {
		
		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		List<Map<String, String>> list = patientAppointmentDao.allPatientList();
		
		assertFalse(list.isEmpty());
	}
	
	
	@Test
	void checkPatientSuccessful() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		Map<String, String> map = patientAppointmentDao.checkPatient("1");
		
		assertFalse(map.isEmpty());
	}

	@Test
	void checkPatientUnsuccessful() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		Map<String, String> map = patientAppointmentDao.checkPatient("11213");
		
		assertTrue(map.isEmpty());
	}
	
	@Test
	void patientHistorySuccessful() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		List<Map<String,String>> history = patientAppointmentDao.patientHistory("101");
		
		assertFalse(history.isEmpty());
	}
	
	@Test
	void patientHistoryUnsuccessful() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		List<Map<String,String>> history = patientAppointmentDao.patientHistory("1023165");
		
		assertTrue(history.isEmpty());
	}
	
	@Test
	void patientInvoiceSuccessful() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		Map<String,String> map = patientAppointmentDao.generateInvoice("101");
		assertFalse(map.isEmpty());
	}
	
	@Test
	void patientInvoiceUnsuccessful() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		Map<String,String> map = patientAppointmentDao.generateInvoice("1046541");
		assertTrue(map.isEmpty());
	}
	
	
	
	
}