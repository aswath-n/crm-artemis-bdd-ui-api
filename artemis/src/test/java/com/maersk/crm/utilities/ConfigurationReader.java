package com.maersk.crm.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties configFile;

    static {
        try {
            String path = "configuration.properties";

            try {
                if (!System.getProperty("env").equalsIgnoreCase("qa")) {
                    path = path.replace("configuration", "configuration" + System.getProperty("env").toLowerCase());
                }
            } catch (NullPointerException E) {
            }
            FileInputStream input = new FileInputStream(path);
            configFile = new Properties();
            configFile.load(input);

            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String keyName) {
        return configFile.getProperty(keyName);
    }
}
