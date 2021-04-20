package com.divergentsl.cms;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.LabTestDao;


public class LabTest {
	
	private static final Logger logger;
	private static final ConsoleHandler handler;
	
	private static LabTestDao labTestDao;
	
	static {
		logger = Logger.getAnonymousLogger();
		logger.setLevel(Level.FINE);
		handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		logger.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/divergentsl/cms/config/ConfigDao.xml");
		
		labTestDao = context.getBean("labtestdao", LabTestDao.class); 
		
	}
	

	/**
	 * This method takes a input & redirect it to method that admin want to perform the operation.
	 */
	public static void labTestPanel() {
		Exit: while (true) {
			
			printTestOptions();

			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			switch (input) {

			case "1":
				addNewTest();
				break;

			case "2":
				readTest();
				break;

			case "3":
				listTest();
				break;

			case "4":
				deleteTest();
				break;

			case "5":
				updateTest();
				break;

			case "6":
				break Exit;
				
			default:
				logger.log(Level.FINE, "Invalid Input!");
				break;

			}
		}
	}

	
	/**
	 * This method print all the operation that admin can perform on lab
	 */
	private static void printTestOptions() {

		System.out.println("\n----Lab Test Panel----");
		System.out.println("1. Add New Test");
		System.out.println("2. Read Test");
		System.out.println("3. List All Tests");
		System.out.println("4. Delete Test");
		System.out.println("5. Update Test");
		System.out.println("6. Exit");
		System.out.print("Enter Your Choice: ");
	}

	/**
	 * This method is used for insert new test data of patient
	 */
	public static void addNewTest() {

		Scanner sc = new Scanner(System.in);

		System.out.println("\n----Adding Lab Test Data----");
		System.out.print("Enter Patient Id: ");
		String patient_id = sc.nextLine();

		System.out.print("\nEnter Test Name: ");
		String test_name = sc.nextLine();

		System.out.print("\nEnter Test Fee: ");
		String fee = sc.nextLine();

		try {

			int i = labTestDao.add(patient_id, test_name, fee);
			if(i > 0) {
				logger.log(Level.FINE, "New Test Added Successfully...");
			} else {
				logger.log(Level.FINE, "Test Add Unsuccessfull!");
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}

	/**
	 * This method print the test data of input patient id
	 */
	private static void readTest() {

		System.out.println("\n----Read Lab Test----");
		System.out.print("Enter Test Id: ");

		Scanner sc = new Scanner(System.in);
		String testId = sc.nextLine();

		try {

			Map<String, String> data = labTestDao.search(testId);

			if (data.size() != 0) {
				System.out.println("Test id: " + data.get("testId"));
				System.out.println("Test Name: " + data.get("testName"));
				System.out.println("Patient Id: " + data.get("patientId"));
				System.out.println("Test Fee: " + data.get("fee"));
			} else {
				logger.log(Level.FINE, "Test Not Found!");
			}

		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}
	
	
	/**
	 * This method print all the test records of patient
	 */
	public static void listTest() {

		System.out.println("\n----List Tests----");

		try {
			
			List<Map<String, String>> list = labTestDao.listTest();
			
			System.out.printf("%7s | %12s | %10s | %12s | %14s | %8s |", "Test Id", "Test Name", "Patient Id",
					"Patient Name", "Contact Number", "Test Fee");
			System.out.println(
					"\n--------------------------------------------------------------------------------------------");
			for(Map<String, String> data: list) {
				System.out.printf("%7s | %12s | %10s | %12s | %14s | %8s |\n", data.get("testId"), data.get("testName"),
						data.get("patientId"), data.get("patientName"), data.get("contactNumber"), data.get("fee"));
			}
			System.out.println(
					"--------------------------------------------------------------------------------------------");

		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}
	
	
	/**
	 * This method delete the test record of given input test id
	 */
	public static void deleteTest() {

		System.out.print("\nEnter Test Id: ");
		Scanner sc = new Scanner(System.in);

		String testId = sc.nextLine();
		
		try {
			
			if (labTestDao.search(testId).size() != 0) {
				int i = labTestDao.delete(testId);
				if (i > 0) {
					logger.log(Level.FINE, "Test Data Deleted Successfully...");
				} else {
					logger.log(Level.FINE, "Test Data Deletion Unsuccessfull!");
				}
			} else {
				logger.log(Level.FINE, "Test Data Not Found!");
			}
			
		} catch(SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}
	
	/**
	 * This method update the data of given input test id
	 */
	private static void updateTest() {

		System.out.println("\n----Update Test Data----");
		System.out.print("Enter Test Id; ");
		Scanner sc = new Scanner(System.in);

		String testId = sc.nextLine();

		try {
			
			Map<String, String> data = labTestDao.search(testId);
			if (data.size() != 0) {
				
				System.out.println("\n----Previous Data----");
				System.out.println("Previous Test Name: " + data.get("testName"));
				System.out.println("Previous Patient Id: " + data.get("patientId"));
				System.out.println("Previous Patient Name: " + data.get("patientName"));
				System.out.println("Previous Test Fee: " + data.get("fee"));
				
				System.out.print("\nEnter New Test Name: ");
				data.put("testName", sc.nextLine());

				System.out.print("\nEnter New Patient Id: ");
				data.put("patientId", sc.nextLine());
				
				
				System.out.print("\nEnter New Test Fee: ");
				data.put("fee", sc.nextLine());
				
				int i = labTestDao.update(data);
				
				if(i > 0) {
					logger.log(Level.FINE, "Test Data Update Successfully...");
				} else {
					logger.log(Level.FINE, "Test Data Update Unsuccessfull!");
				}
				
				
			} else {
				logger.log(Level.FINE, "Test Data Not Found!");
			}
		} catch(SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}
	
}
