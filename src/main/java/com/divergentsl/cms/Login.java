package com.divergentsl.cms;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.Setter;

/**
 * This Login class contains methods for login admin and doctor
 */
public class Login {

	
	private static final Logger logger;
	private static final ConsoleHandler handler;
	
	@Setter
	private static Admin a;
	@Setter
	private static Doctor d;
	
	
	static {
		logger = Logger.getAnonymousLogger();
		logger.setLevel(Level.FINE);
		handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		logger.addHandler(handler);

//		ApplicationContext context = new ClassPathXmlApplicationContext("com/divergentsl/cms/config/config.xml");
//		a = context.getBean("admin", Admin.class);
//		d = context.getBean("doctor", Doctor.class);
		
	}
	
	
	

    /**
     * This method is loginPanel which ask for that you want to login as admin or doctor
     * for admin or doctor to login, first admin have to login if username & password is correct then it will redirect admin to adminPanel if it login as admin or if it will login as doctor then it will redirect ot doctorPanel, other wise it will print message "You are not authorised"
     */
    public void loginPanel() {


        Main:
        while(true) {

            this.printLoginOption();

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            Login:
            switch (input) {

                case "1":
                    if (a.adminLogin()) {
                        a.adminPanel();
                    } else {
                    	logger.log(Level.FINE, "You are not authorised");
                    }
                    break;

                case "2":

                    String did = d.doctorLogin();

                    if (did != null) {
                        String s[] = did.split(" ");
                        d.doctorPanel(s[0], s[1]);
                    } else {
                    	logger.log(Level.FINE, "You are not Authorised");
                        break Login;
                    }
                    break;

                case "3":
                    break Main;

                default:
                	logger.log(Level.FINE, "Invalid Input");
                    break;
            }
        }
    }
    
    private void printLoginOption() {
    	System.out.println("\n----Login Panel----");
        System.out.println("1. Admin");
        System.out.println("2. Doctor");
        System.out.println("3. Exit");
    }


}
