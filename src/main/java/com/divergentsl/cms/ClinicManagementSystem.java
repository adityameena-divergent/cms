package com.divergentsl.cms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * It is a main class where application execution start
 * @author Aditya Meena
 *
 */
public class ClinicManagementSystem {

    public static void main(String args[]) {

        
    	ApplicationContext context = new ClassPathXmlApplicationContext("com/divergentsl/cms/config/config.xml");
    	
    	Login login = context.getBean("login", Login.class);
    	login.loginPanel();
    	
    }

}
