package by.gsu.epamlab.ideaplugin.google;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SolutionUtils {

    /**
     * Returns list of solutions from given google drive folder name.
     */
    public static List<Solution> getSolutionList(String folderName) throws IOException {
        // empty list for output.
        List<Solution> solutions = new ArrayList<>();

        // getting folders with special name
        Drive service = DriveUtils.getDriveService();

        // getting list of google directory id's for given names
        List<File> dirs = getDirsId(folderName, service);

        if (dirs == null || dirs.size() == 0) {
            System.err.println("Dir not found: " + folderName);
        } else {

            // getting jars for each dir
            for (File dir : dirs) {

                // getting jars from dir with given id
                List<File> files = getJarsFromDir(dir, service);

                // creating solution from each jar file
                if (files != null && files.size() > 0) {
                    for (File file : files) {
                        solutions.add(new Solution(file.getName(), file.getId(), folderName));
                    }
                }
            }
        }
        return solutions;
    }

    /**
     * Gets jars from dir with given id.
     */
    private static List<File> getJarsFromDir(File dir, Drive service) throws IOException {
        FileList result = service.files().list()
                .setQ("name contains '.jar' and '" + dir.getId() + "' in parents")
                .setPageSize(100)
                .setFields("files(name,id)")
                .execute();
        return result.getFiles();
    }

    /**
     * Gets list of google directory id's for given names.
     */
    private static List<File> getDirsId(String folderName, Drive service) throws IOException {
        FileList result = service.files().list()
                .setQ("name = '" + folderName + "' and mimeType = 'application/vnd.google-apps.folder'")
                .setPageSize(100)
                .setFields("files(name,id)")
                .execute();
        return result.getFiles();
    }

    /**
     * Receives file as byte array from google disk by given id.
     */
    public static byte[] getFile(String fileId) throws IOException {
        Drive service = DriveUtils.getDriveService();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        outputStream.close();
        return outputStream.toByteArray();
    }
}
