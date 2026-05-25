package testmu.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream(
                "src/test/resources/config/config.properties"
            );
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException("config.properties file not found", e);
        }
    }

    public static String get(String key) {
        // For api.key, check environment variable first
        if (key.equals("api.key")) {
            String envKey = System.getenv("API_KEY");
            if (envKey != null && !envKey.isEmpty()) {
                return envKey;
            }
        }
        return properties.getProperty(key);
    }
}