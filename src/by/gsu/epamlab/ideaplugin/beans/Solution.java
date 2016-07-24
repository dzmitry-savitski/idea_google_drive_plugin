package by.gsu.epamlab.ideaplugin.beans;

/**
 * Represents jar file with Idea project.
 */
public class Solution {
    private final String name;
    private final String id; // goggle drive id, may vary from session
    private final String folder;

    public Solution(String name, String id, String folder) {
        this.name = name;
        this.id = id;
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getHash() {
        return '(' + folder + ')' + " - " + name;
    }
}
