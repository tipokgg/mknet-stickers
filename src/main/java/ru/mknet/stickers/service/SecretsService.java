package ru.mknet.stickers.service;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class SecretsService {

    private static final Properties PROPERTIES;
    private static final String PROP_FILE = "secrets.properties";

    private SecretsService() {
    }

    static {
        PROPERTIES = new Properties();
        final URL props = ClassLoader.getSystemResource(PROP_FILE);
        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return PROPERTIES.getProperty(name);
    }

}