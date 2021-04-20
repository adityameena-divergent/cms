package com.divergentsl.cms;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.LoginDao;


public class Admin {

	
	private static final Logger logger;
	private static final ConsoleHandler handler;
	
	private static LoginDao loginDao;
	
	
	static {
		logger = Logger.getAnonymousLogger();
		logger.setLevel(Level.FINE);
		handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		logger.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/divergentsl/cms/config/ConfigDao.xml");
		loginDao = context.getBean("logindao", LoginDao.class);
	}

    /**
     * This method takes two input username and password from console.
     * @return It return true if username and password is correct otherwise return false.
     */
    public boolean adminLogin() {

        Scanner sc = new Scanner(System.in);

        Console cons = System.console();
        System.out.println("\n-----Admin Login------");
        System.out.print("\nEnter Username: ");
        String username = sc.nextLine();

        System.out.print("\nEnter Password: ");
        String password = sc.nextLine();

        try {

            if(loginDao.adminLogin(username, password)) {
            	logger.log(Level.FINE, "Login Successfull...");
                return true;
            } else {
            	logger.log(Level.FINE, "Incorrect Username & Password!");
                return false;
            }

        } catch(Exception e) {
        	logger.log(Level.FINE, e.getMessage());
        }
        return false;
    }


    /**
     * This method print all the operation that admin can perform, and ask for input for which operation admin want to perform, then after it redirect to that specific operation panel according to input.
     */
    public void adminPanel() {
        LOGOUT:
        while(true) {

            this.printAdminOptions();
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            switch (input) {

                case "1":
                    CRUDPatient.CRUDOperation();
                    break;

                case "2":
                    CRUDDoctor.CRUDOperation();
                    break;

                case "3":
                    CRUDDrugs.CRUDOperations();
                    break;

                case "4":
                    LabTest.labTestPanel();
                    break;

                case "5":
                    PatientAppointment.makeAppointment();
                    break;

                case "6":
                    break LOGOUT;

                default:
                	logger.log(Level.FINE, "Invalid Input!");
                    break;
            }
        }
    }


    /**
     * This method print all the operations that admin can perform
     */
    public void printAdminOptions() {

        System.out.println("\n----Admin Panel-----");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Drug");
        System.out.println("4. Lab Test");
        System.out.println("5. Make appointment");
        System.out.println("6. Logout");
        System.out.print("Enter Your Choice: ");

    }






}
