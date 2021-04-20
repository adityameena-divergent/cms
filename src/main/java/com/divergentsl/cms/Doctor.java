package com.divergentsl.cms;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.LoginDao;


public class Doctor {
	
	private static final Logger logger;
	private static final ConsoleHandler handler;
	
	
	
	static {
		logger = Logger.getAnonymousLogger();
		logger.setLevel(Level.FINE);
		handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		logger.addHandler(handler);
		
	}


    public static void printDoctorOptions(String dname) {

        System.out.println("\n----Login as : " + dname + "----");
        System.out.println("----Doctor Panel----");
        System.out.println("1. List of patients");
        System.out.println("2. Add prescription and notes for a patient");
        System.out.println("3. See booked appointments for him");
        System.out.println("4. Check patient history and his prescription");
        System.out.println("5. Create Invoice of patient");
        System.out.println("6. Logout");
        System.out.print("Enter Your Choice: ");

    }



    public static void doctorPanel(String doctorId, String doctorName) {

        Logout:
        while(true) {

            printDoctorOptions(doctorName);

            Scanner sc = new Scanner(System.in);

            String input = sc.nextLine();

            switch (input) {

                case "1":
                	PatientAppointment.allPatientList(doctorName);
                    break;

                case "2":
                    PatientAppointment.addPrescription();
                    break;

                case "3":
                    PatientAppointment.patientAppointToYou(doctorId, doctorName);
                    break;

                case "4":
                    PatientAppointment.checkPatientHistory();
                    break;

                case "5":
                    PatientAppointment.generateInvoice();
                    break;

                case "6":
                    break Logout;
                
                default:
                	logger.log(Level.FINE, "Invalid Input");
                	break;
            }
        }
    }


    public static String doctorLogin() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n----Doctor Login----");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("\nEnter Password: ");
        String password = sc.nextLine();

        try {
            
        	LoginDao loginDao = new LoginDao(new DatabaseManager());
        	
        	String doctorName = loginDao.doctorLogin(username, password);
        	
            if(doctorName != null) {
            	logger.log(Level.FINE, "Doctor Login Successfull...");
                return doctorName;
            } else {
            	logger.log(Level.FINE, "Incorrect Username & Password");
                return null;
            }
        } catch (SQLException e) {
        	logger.log(Level.FINE, e.getMessage());
        }
        return null;
    }


}
