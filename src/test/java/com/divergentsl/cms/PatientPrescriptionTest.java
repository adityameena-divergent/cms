package com.divergentsl.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.divergentsl.cms.dao.PatientAppointmentDao;


public class PatientPrescriptionTest {

	Connection connection = null;
	Statement st = null;
	H2DatabaseManager driverManager = null;
	
	@BeforeEach
	void setUp() throws Exception {
		
		driverManager = new H2DatabaseManager();
		connection = driverManager.getConnection();	
		st = connection.createStatement();
		
		st.execute("drop table if exists prescription");
		st.executeUpdate("create table if not exists prescription (prescription_id int primary key auto_increment, patient_id int, prescription varchar(100), notes varchar(150), doctor_id int, appointment_number int)");
		
		st.execute("insert into prescription values(1, 101, 'PRESCRIPTION', 'NOTES', 1001, 1)");
		System.out.println("Data inserted successfully...");	
		
		st.execute("drop table if exists patient");
		st.executeUpdate("create table if not exists patient (patient_id int auto_increment primary key, patient_name varchar(20) NOT NULL, age int, weight int, gender varchar(8), contact_number varchar(15), address varchar(30))");
		System.out.println("Table created successfully...");
		
		st.execute("insert into patient values(101, 'john', 55, 55, 'male', '453648756', 'los')");
		
		st.execute("drop table if exists doctor");
		st.executeUpdate("create table if not exists doctor (did int auto_increment, dname varchar(30), username varchar(15) unique, password varchar(20), speciality varchar(20), fee int)");
		System.out.println("Table created successfully...");
		
		st.execute("insert into doctor values(1001, 'jayant', 'jayant', 'jayant', 'dentist', 1000)");
		
	}

	

	@Test
	void addPrescription() throws SQLException {

		PatientAppointmentDao patientAppointmentDao = new PatientAppointmentDao(driverManager);
		Map<String,String> map = new HashMap<>();
		map.put("patientId", "101");
		map.put("doctorId", "1001");
		map.put("prescription", "PRESCRIPTION");
		map.put("notes", "NOTES");
		
		int i = patientAppointmentDao.addPrescription(map);
		
		assertEquals(1, i);
	}

	
}
