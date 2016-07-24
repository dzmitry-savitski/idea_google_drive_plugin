package by.gsu.epamlab.ideaplugin.plugin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Creates config instance to return config values.
 */
public class Config {
    private static final String PROPERTIES_FILENAME = "plugin.properties";
    private static final Properties props = new Properties();

    // initializing
    static {
        InputStream input = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
        try {
            props.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        } catch (Exception e) {
            System.err.println("Configuration fail to load properties file" + PROPERTIES_FILENAME);
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * Gets string from config.
     */
    public static String getString(String key) {
        if (props.containsKey(key)) {
            return String.valueOf(props.get(key));
        } else {
            System.err.println("Properties not found" + key);
            return null;
        }
    }


    /**
     * Gets string array from config.
     * Separator is ";" symbol.
     */
    public static String[] getStringArray() {
        String property = String.valueOf(props.get("by.gsu.epamlab.ideaplugin.solution.dirs"));
        return property.split(";");
    }
}
