package com.divergentsl.cms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.divergentsl.cms.IDatabaseManager;


/**
 * It is a helper class that contains methods releted to patient appointment
 * 
 */
public class PatientAppointmentDao {
	
	IDatabaseManager databaseManager;

	public PatientAppointmentDao(IDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	
	/**
	 * It is helper method for making new appointment for patient
	 * @param data
	 * @return 1 if appointment successfully created, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int makeAppointment(Map<String, String> data) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		int i = st.executeUpdate("insert into patient_appointment (patient_id, doctor_id, problem, appointment_date, time) values(" + data.get("patientId") + ", " + data.get("doctorId") + ", '" + data.get("problem") + "', '" + data.get("appointmentDate") + "', '" + data.get("time") + "')");
        System.out.println("\nAppointment created successfully...");
		
        st.close();
		con.close();
		return i;
	}
	
	
	/**
	 * It is a helper method for retreiving all patient that has been appointed to doctor, whose currently logged in.
	 * @param id
	 * @return List of Map of patient data.
	 * @throws SQLException
	 */
	public List<Map<String, String>> patientAppointToYou(String id) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select pa.appointment_number, pa.patient_id, p.patient_name, pa.appointment_date, pa.time, pa.problem from doctor d join patient_appointment pa on pa.doctor_id = d.did join patient p on pa.patient_id = p.patient_id where d.did = " + id);
		
		List<Map<String, String>> listOfPatients = new ArrayList<>();
		
		while(rs.next()) {
			
			Map<String, String> record = new HashMap<>();
			record.put("appointmentNumber", rs.getString(1));
			record.put("id", rs.getString(2));
			record.put("name", rs.getString(3));
			record.put("appointmentDate", rs.getString(4));
			record.put("time", rs.getString(5));
			record.put("problem", rs.getString(6));
			
			listOfPatients.add(record);
		}
		st.close();
		con.close();
		return listOfPatients;
	}
	
	/**
	 * It is a helper method for retreiving all patient appointed data.
	 * @return List of Map of patient data.
	 * @throws SQLException
	 */
	public List<Map<String, String>> allPatientList() throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select pa.appointment_number, pa.appointment_date, pa.time, p.patient_id, p.patient_name, p.gender, pa.problem, d.did, d.dname from patient p join patient_appointment pa on p.patient_id = pa.patient_id join doctor d on d.did = pa.doctor_id");
		List<Map<String, String>> listOfPatient = new ArrayList<>();
		
		while(rs.next()) {
			Map<String, String> record = new HashMap<>();
			record.put("appointmentNumber", rs.getString(1));
			record.put("appointmentDate", rs.getString(2));
			record.put("time", rs.getString(3));
			record.put("patientId", rs.getString(4));
			record.put("patientName", rs.getString(5));
			record.put("gender", rs.getString(6));
			record.put("problem", rs.getString(7));
			record.put("doctorId", rs.getString(8));
			record.put("doctorName", rs.getString(9));
			
			listOfPatient.add(record);
		}
		st.close();
		con.close();
		return listOfPatient;
	}
	
	/**
	 * It is a helper method for retreiving patient data by appointment number
	 * @param appointmentNumber
	 * @return Map of patient data if found, otherwise it return empty Map.
	 * @throws SQLException
	 */
	public Map<String, String> checkPatient(String appointmentNumber) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select patient_id, doctor_id from patient_appointment where appointment_number = " + appointmentNumber);		
		Map<String, String> record = new HashMap<>();
		
		if(rs.next()) {
			record.put("patientId", rs.getString(1));
			record.put("doctorId", rs.getString(2));
		}

		st.close();
		con.close();
		return record;
	}
	
	
	/**
	 * It is a helper method for adding a prescription
	 * @param data
	 * @return 1 if prescription added successfully, otherwise it returns 0.
	 * @throws SQLException
	 */
	public int addPrescription(Map<String, String> data) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		int i = st.executeUpdate("insert into prescription (patient_id, prescription, notes, doctor_id) values (" + data.get("patientId") + ", '" + data.get("prescription") + "', '" + data.get("notes") + "', " + data.get("doctorId") + ")");
		
		st.close();
		con.close();
		return i;
	}
	
	/**
	 * It is a helper method retreiving all appointment history of patient.
	 * @param patientId
	 * @return List of Map of patient historical data.
	 * @throws SQLException
	 */
	public List<Map<String, String>> patientHistory(String patientId) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select p.patient_id, p.patient_name, pa.problem, pa.appointment_number, pa.appointment_date, pa.doctor_id, pp.prescription, pp.notes from patient_appointment pa join patient p on p.patient_id = pa.patient_id left join prescription pp on pp.appointment_number = pa.appointment_number where pa.patient_id = " + patientId + " order by appointment_date desc");
		List<Map<String, String>> list = new ArrayList<>();
		while(rs.next()) {
			Map<String, String> record = new HashMap<>();
			record.put("patientId", rs.getString(1));
			record.put("patientName", rs.getString(2));
			record.put("problem", rs.getString(3));
			record.put("appointmentNumber", rs.getString(4));
			record.put("appointmentDate", rs.getString(5));
			record.put("doctorId", rs.getString(6));
			record.put("prescription", rs.getString(7));
			record.put("notes", rs.getString(8));
			
			list.add(record);
		}

		st.close();
		con.close();
		return list;
	}
	
	
	/**
	 * It is a helper method for generating invoice for patient
	 * @param patientId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> generateInvoice(String patientId) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		
		con = databaseManager.getConnection();
		st = con.createStatement();
		
		ResultSet rs = st.executeQuery("select pa.patient_id, p.patient_name, d.dname, d.fee,pa.problem, pa.appointment_date from prescription pp join patient_appointment pa on pa.appointment_number = pp.appointment_number join patient p on p.patient_id = pa.patient_id join doctor d on d.did = pa.doctor_id where pa.patient_id = " + patientId + " order by pa.appointment_date desc");
		
		Map<String, String> invoice = new HashMap<>();
		if(rs.next()) {
			invoice.put("patientId", rs.getString(1));
			invoice.put("patientName", rs.getString(2));
			invoice.put("doctorName", rs.getString(3));
			invoice.put("fee", rs.getString(4));
			invoice.put("problem", rs.getString(5));
			invoice.put("appointmentDate", rs.getString(6));
		}

		st.close();
		con.close();
		
		return invoice;
		
	}
	

}
