package com.bqh_2021.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    
    private Properties properties;

    private static FileInputStream file;

    private static final PropertiesReader SINGLE_INSTANCE = new PropertiesReader();

    public static PropertiesReader getInstance(){
        return SINGLE_INSTANCE;
    }

    public PropertiesReader(){
        try {
            file = new FileInputStream("Resources/config.properties");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        this.properties = new Properties();
        try {
            this.properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String propertyName) {
        return this.properties.getProperty(propertyName);
    }
}