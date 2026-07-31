package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Simple config reader for test resources/config.properties
 */
public class ConfigReader {

    private static Properties properties = new Properties();

    public static void init() {
        if (!properties.isEmpty()) return;
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

