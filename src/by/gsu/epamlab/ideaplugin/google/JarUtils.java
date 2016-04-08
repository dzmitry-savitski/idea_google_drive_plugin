package by.gsu.epamlab.ideaplugin.google;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class JarUtils {

    /**
     * Unpacks jar file to given directory.
     */
    public static void unpackJarTo(byte[] jarBytes, String outputFolder) throws IOException {

        InputStream fis = new ByteArrayInputStream(jarBytes);
        ZipEntry entry;
        boolean first = true;
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis))) {
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();

                // checking file
                if (isProjectFile(entry, entryName)) {
                    continue;
                }

                // creating src if file is in wrong format
                if (first) {
                    if (!entry.getName().contains("src")) {
                        new File(outputFolder + "/src").mkdir();
                        outputFolder += "/src";
                    }
                    first = false;
                }

                String name = outputFolder + "/" + entry.getName();

                // creating parent dirs if absent
                createPath(name);

                // writing file to disk
                writeData(zis, name);
            }
        }
    }

    /**
     * Creates parent dirs for current file before extracting.
     */
    private static void createPath(String name) {
        File parent = new File(name).getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
    }

    /**
     * writes file to disk.
     */
    private static void writeData(ZipInputStream zis, String name) throws IOException {
        final int BUFFER = 512;

        try (BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(name), BUFFER)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            dest.flush();
        }
        zis.closeEntry();
    }

    /**
     * Checks if current file is a project file.
     */
    private static boolean isProjectFile(ZipEntry entry, String entryName) {
        return entry.isDirectory() || entryName.equals(".project") || entryName.equals(".classpath");
    }

}