package by.gsu.epamlab.ideaplugin;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.gui.SolutionSelectDialog;
import by.gsu.epamlab.ideaplugin.gui.SolutionSelectListener;
import by.gsu.epamlab.ideaplugin.plugin.Config;
import by.gsu.epamlab.ideaplugin.plugin.PluginStorage;
import by.gsu.epamlab.ideaplugin.plugin.PluginUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class Runner extends com.intellij.openapi.actionSystem.AnAction {

    /**
     * Action on clicking on plugin icon.
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project currentProject = anActionEvent.getProject();

        // creating new dialog 
        SolutionSelectDialog taskSelectDialog = new SolutionSelectDialog(currentProject);

        // choosing action by exit code
        Solution selectedSolution = SolutionSelectListener.getSelectedSolution();
        if ((taskSelectDialog.getExitCode() == 0) &&
                (selectedSolution != null)) {

            // checking if project name starts with special symbol to avoid code corruption
            if (!PluginUtils.isProjectForPlugin(currentProject)) {
                return;
            }

            // clearing current project
            PluginUtils.clearCurrentProject(currentProject);
            String projectPath = PluginUtils.getProjectPath(currentProject);

            // loading new solution
            PluginUtils.loadSolution(selectedSolution, projectPath);

            // marking selected solution as viewed
            PluginStorage.addValue(selectedSolution.getHash(), PluginStorage.VIEWED);

            // renaming project
            PluginUtils.renameProject(selectedSolution.getHash(), currentProject);
            
            // opening Runner.java
            final String startFileName = Config.getString("by.gsu.epamlab.ideaplugin.startfile");
            PluginUtils.openFile(startFileName, currentProject);
        }


    }


}
