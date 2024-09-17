package com.weatherMap.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
    public static final String URL;
    public static final String BASE_URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;
    public static final String DB_URL;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String YOUR_API_KEY;

    static {
        Properties properties = null;
        String environment = System.getProperty("environment") != null ? environment = System.getProperty("environment") : ConfigurationReader.getProperty("environment");
        //this field will get its value from configuration.properties file environment

        try {

            String path = System.getProperty("user.dir") + "/src/test/resources/Environments/" + environment + ".properties";

            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = properties.getProperty("url");
        BASE_URL = properties.getProperty("base_url");
        DB_USERNAME = properties.getProperty("dbUsername");
        DB_PASSWORD = properties.getProperty("dbPassword");
        DB_URL = properties.getProperty("dbUrl");
        USERNAME = properties.getProperty("username");
        PASSWORD = properties.getProperty("password");
        YOUR_API_KEY = properties.getProperty("apiKey");
    }
}
