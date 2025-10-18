package org.example.Connections;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class UtilProperties {
    private static final Properties PROPERTIES = new Properties();
    private UtilProperties() {}
    static {
        try  (InputStream is = UtilProperties.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get2(String key){
        return PROPERTIES.getProperty(key);
    }

}

