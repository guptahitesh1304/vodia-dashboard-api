package com.vodia.api.dashboard.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.vodia.api.dashboard.DashboardApplication;

public class URLConfig {
	
	
	
	
	public static String getURL(String key) {
		
		Properties prop = new Properties();

        try (InputStream input = DashboardApplication.class.getClassLoader().getResourceAsStream("config.properties")) {

            

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return "";
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            
            //return prop.getProperty(key);

            //get the property value and print it out
//            System.out.println(prop.getProperty("db.url"));
//            System.out.println(prop.getProperty("db.user"));
//            System.out.println(prop.getProperty("db.password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return prop.getProperty(key);

    }

}
