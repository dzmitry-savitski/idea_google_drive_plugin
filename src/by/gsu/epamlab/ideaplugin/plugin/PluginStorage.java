package by.gsu.epamlab.ideaplugin.plugin;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.ArrayUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents IDEA plugin storage.
 */
public final class PluginStorage {
    // saved values
    public static final String VIEWED = "viewed";
    public static final String EXPANDED_NODES = "nodes";

    // getting IDEA properties object
    private static final PropertiesComponent properties = PropertiesComponent.getInstance();

    // initializing properties first time
    static {
        if (!properties.isValueSet(VIEWED)) {
            properties.setValues(VIEWED, new String[0]);
        }
        if (!properties.isValueSet(EXPANDED_NODES)) {
            properties.setValues(EXPANDED_NODES, new String[0]);
        }
    }

    /**
     * Adds new value to storage array.
     */
    public static void addValue(String value, String propertyName) {
        String[] storageArray = properties.getValues(propertyName);
        Set<String> setOut = new HashSet<>();

        setOut.add(value);
        for (String aStorageArray : storageArray) {
            if (aStorageArray != null && aStorageArray.length() > 0) {
                setOut.add(aStorageArray);
            }
        }
        String[] newArray = setOut.toArray(new String[setOut.size()]);
        properties.setValues(propertyName, newArray);
    }


    /**
     * Checks if current value present in storage array.
     */
    public static boolean isValuePresent(String value, String propertyName) {
        String[] propArray = properties.getValues(propertyName);
        return ArrayUtil.contains(value, propArray);
    }

    /**
     * Clears property.
     */
    public static void clearProperty(String propertyName) {
        properties.setValues(propertyName, new String[0]);
    }

    /**
     * Returns array from storage.
     */
    public static String[] getValuesArray(String propertyName) {
        return properties.getValues(propertyName);
    }
}
