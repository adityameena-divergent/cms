package com.divergentsl.cms;

import java.sql.*;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.divergentsl.cms.dao.DrugDao;


public class CRUDDrugs {

	
	private static final Logger logger;
	private static final ConsoleHandler handler;
	private static DrugDao drugDao;
	
	static {
		logger = Logger.getAnonymousLogger();
		logger.setLevel(Level.FINE);
		handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		logger.addHandler(handler);
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/divergentsl/cms/config/ConfigDao.xml");
		drugDao = context.getBean("drugdao", DrugDao.class);
		
	}
	
	
	/**
	 * This method takes input from user and redirect it to the operation that he
	 * wants to perform.
	 */
	public static void CRUDOperations() {
		CRUD: while (true) {

			printDrugOption();

			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();

			switch (input) {
			case "1":
				addDrug();
				break;

			case "2":
				searchDrug();
				break;

			case "3":
				deleteDrug();
				break;

			case "4":
				updateDrug();
				break;

			case "5":
				break CRUD;

			default:
				logger.log(Level.FINE, "Invalid Input");
				break;
			}
		}
	}
	
	
	/**
	 * This method print the operation name that admin can perform on drugs
	 */
	public static void printDrugOption() {
		System.out.println("\n\n----Drugs----");
		System.out.println("1. Add new drugs");
		System.out.println("2. Search drugs");
		System.out.println("3. Delete drugs");
		System.out.println("4. Update drugs");
		System.out.println("5. Back");
		System.out.print("Enter your choice: ");
	}
	

	/**
     * This method update the existing data of drugs
     */
    public static void updateDrug() {

        System.out.println("\n----Update Drug Data----");
        System.out.print("Enter Drug Id: ");
        Scanner sc = new Scanner(System.in);
        String id = sc.nextLine();
        
        try {
        	        
        	
        	Map<String, String> data = drugDao.search(id);

        	if (data.size() == 0) {
        		logger.log(Level.FINE, "Drug Not Found!");
        	} else {
	            System.out.println("\nDrug Id: " + id);
	            System.out.println("Previous Drug Name: " + data.get("name"));
	            System.out.println("Previous Drug Description: " + data.get("description"));
	
	            System.out.println("\nEnter New Drug Name: ");
	            String name = sc.nextLine();
	
	            System.out.print("\nEnter New Description: ");
	            String description = sc.nextLine();
	            
	            data.put("name", name);
	            data.put("description", description);
	            drugDao.update(data);
        	}
        } catch(SQLException e) {
        	logger.log(Level.FINE, e.getMessage());
        }
    }

    
	/**
	 * This method takes drug_id and remove it from database.
	 */
	public static void deleteDrug() {
		System.out.println("\n----Delete Drug----");
		System.out.print("Enter Drug Id: ");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();

		try {

			if (drugDao.search(id).size() != 0) {
				int i = drugDao.delete(id);
				if(i > 0) {
					logger.log(Level.FINE, "Drug Deleted Successfully...");
				} else {
					logger.log(Level.FINE, "Drug Deletion Unsuccessfull!");
				}
			} else {
				logger.log(Level.FINE, "Drug Not Found!");
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}

	}

	
	/**
	 * This method print the data of input drug_id
	 */
	public static void searchDrug() {
		System.out.println("\n----Search Drug----");
		System.out.println("Enter Drug Id: ");
		Scanner sc = new Scanner(System.in);

		String id = sc.nextLine();

		try {
			
			Map<String, String> data = drugDao.search(id);

			if (data.size() != 0) {
				System.out.println("\nDrug Id: " + data.get("id"));
				System.out.println("Drug Name: " + data.get("name"));
				System.out.println("Description: " + data.get("description"));
			} else {
				logger.log(Level.FINE, "Drug Not Found!");
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}

	/**
	 * This method takes input drug data from user and insert it into database.
	 */
	public static void addDrug() {

		System.out.println("\n----Add new drug----");
		System.out.println("Enter Drug Name: ");

		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();

		System.out.print("\nEnter Drug Description: ");
		String description = sc.nextLine();

		try {

			int i = drugDao.add(name, description);
			if (i > 0) {
				logger.log(Level.FINE, "Drug Added Successfully...");
			} else {
				logger.log(Level.FINE, "Drug Add Unsucessful!");
			}
		} catch (SQLException e) {
			logger.log(Level.FINE, e.getMessage());
		}
	}

}
