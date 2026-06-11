package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            String configPath = "src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Error loading config.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default if not found
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get integer property
     */
    public static int getIntProperty(String key) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : 0;
    }

    /**
     * Get boolean property
     */
    public static boolean getBooleanProperty(String key) {
        String value = properties.getProperty(key);
        return value != null && Boolean.parseBoolean(value);
    }
}