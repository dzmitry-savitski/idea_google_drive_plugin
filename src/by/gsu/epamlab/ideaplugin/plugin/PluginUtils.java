package by.gsu.epamlab.ideaplugin.plugin;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.google.JarUtils;
import by.gsu.epamlab.ideaplugin.google.SolutionUtils;
import by.gsu.epamlab.ideaplugin.gui.GuiUtils;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectEx;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class PluginUtils {

    /**
     * Recursively deletes files & folders from given path.
     */
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

    /**
     * Deletes all files from current project's src folder.
     */
    public static void clearCurrentProject(Project project) {
        String path = getProjectPath(project);
        deleteRecursive(new File(path + "/src/"));
    }

    /**
     * Returns project path in filesystem.
     */
    public static String getProjectPath(Project project) {
        FileEditorManager editorManager = FileEditorManager.getInstance(project);
        String path = editorManager.getProject().getBasePath();
        return path;
    }

    /**
     * Checks if project name starts with special symbol from config.
     */
    public static boolean isProjectForPlugin(Project project) {
        String startSymbol = Config.getString("by.gsu.epamlab.ideaplugin.project.mark");
        if (!project.getName().startsWith(startSymbol)) {
            GuiUtils.showPopUpText("To work with plugin, project name should start with \"" + startSymbol + "\" symbol!", project);
            return false;
        }
        return true;
    }

    /**
     * Loads current solution to local file system (given project).
     */
    public static void loadSolution(Solution solution, String projectPath) {
        try {
            byte[] file = SolutionUtils.getFile(solution.getId());
            JarUtils.unpackJarTo(file, projectPath);
            LocalFileSystem.getInstance().refresh(false);
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

    /**
     * Opens file in current editor if found it.
     */
    public static void openFile(String filename, Project currentProject) {
        PsiFile file = projectFileSearch(filename, currentProject);
        if (file != null) {
            FileEditorManager.getInstance(currentProject).openFile(file.getVirtualFile(), true);
        }
    }

    /**
     * Searches for file in project and returns first found file or null.
     */
    private static PsiFile projectFileSearch(String filename, Project currentProject) {
        PsiFile[] foundFiles = FilenameIndex.getFilesByName(currentProject, filename, GlobalSearchScope.projectScope(currentProject));
        if (foundFiles.length >= 1) {
            return foundFiles[0];
        }
        return null;
    }
}
