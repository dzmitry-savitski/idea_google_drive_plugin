package by.gsu.epamlab.ideaplugin.plugin;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.google.JarUtils;
import by.gsu.epamlab.ideaplugin.google.SolutionUtils;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectEx;
import com.intellij.openapi.vfs.LocalFileSystem;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class PluginUtils {

    private static void deleteRecursive(java.io.File path) {
        path.listFiles(new FileFilter() {
            @Override
            public boolean accept(java.io.File pathname) {
                if (pathname.isDirectory()) {
                    pathname.listFiles(this);
                    pathname.delete();
                } else {
                    pathname.delete();
                }
                return false;
            }
        });
        path.delete();
    }

    public static void clearCurrentProject(Project project) {
        String path = getProjectPath(project);
        deleteRecursive(new File(path + "/src/"));
    }

    public static String getProjectPath(Project project) {
        FileEditorManager editorManager = FileEditorManager.getInstance(project);
        String path = editorManager.getProject().getBasePath();
        return path;
    }


    /**
     * Loads current solution to local file system (given project).
     */
    public static void loadSolution(Solution solution, String projectPath) {
        try {
            byte[] file = SolutionUtils.getFile(solution.getId());
            JarUtils.unpackJarTo(file, projectPath);
            LocalFileSystem.getInstance().refresh(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renames current project.
     */
    public static void renameProject(String projectName, Project currentProject) {
        ProjectEx projectEx = (ProjectEx) currentProject;
        projectEx.setProjectName(projectName);
    }
}
