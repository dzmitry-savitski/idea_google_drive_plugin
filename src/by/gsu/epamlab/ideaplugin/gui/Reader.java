package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.plugin.PluginStorage;
import by.gsu.epamlab.ideaplugin.plugin.PluginUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class Reader extends com.intellij.openapi.actionSystem.AnAction {

    /**
     * Action on clicking on plugin icon.
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project currentProject = anActionEvent.getProject();

        // creating new dialog 
        SolutionSelectDialog taskSelectDialog = new SolutionSelectDialog(currentProject);

        // choosing action by exit code
        Solution selectedSolution = SolutionTreeListener.getSelectedSolution();
        if ((taskSelectDialog.getExitCode() == 0) &&
                (selectedSolution != null)) {

            // clearing current project
            PluginUtils.clearCurrentProject(currentProject);
            String projectPath = PluginUtils.getProjectPath(currentProject);

            // loading new solution
            PluginUtils.loadSolution(selectedSolution, projectPath);

            // marking selected solution as viewed
            PluginStorage.setViewed(selectedSolution.getHash());

            // renaming project
            PluginUtils.renameProject(selectedSolution.getHash(), currentProject);
        }


    }


}
