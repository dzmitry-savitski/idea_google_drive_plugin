package by.gsu.epamlab.ideaplugin.plugin;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.ArrayUtil;

/**
 * Represents IDEA plugin storage.
 */
public final class PluginStorage {
    // getting IDEA properties object
    private static final PropertiesComponent properties = PropertiesComponent.getInstance();

    // initializing viewed property first time
    static {
        if (!properties.isValueSet("viewed")) {
            properties.setValues("viewed", new String[0]);
        }
    }
    
    /**
     * Adds new solution to viewed array.
     */
    public static void setViewed(String solutionHash) {
        String[] storageArray = properties.getValues("viewed");
        
        // adding hash to storage if not present
        if (!isViewed(solutionHash)) {
            // creating new storage array
            String[] newArray = new String[storageArray.length + 1];
            System.arraycopy(storageArray, 0, newArray, 0, storageArray.length);
            newArray[storageArray.length] = solutionHash;
            properties.setValues("viewed", newArray);
        }

    }

    /**
     * Checks if current solution has been already viewed.
     */
    public static boolean isViewed(String value) {
        String[] viewed = properties.getValues("viewed");
        return ArrayUtil.contains(value, viewed);
    }

    /**
     * Clears all viewed marks.
     */
    public static void clearViewed() {
        properties.setValues("viewed", new String[0]);
    }
}
