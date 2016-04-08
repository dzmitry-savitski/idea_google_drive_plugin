package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.plugin.PluginStorage;
import by.gsu.epamlab.ideaplugin.plugin.PluginUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class Reader extends com.intellij.openapi.actionSystem.AnAction {

    /**
     * Action on clicking on plugin icon.
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        // creating new dialog 
        SolutionSelectDialog taskSelectDialog = new SolutionSelectDialog(anActionEvent.getProject());

        // choosing action by exit code
        Solution selectedSolution = SolutionTreeListener.getSelectedSolution();
        if ((taskSelectDialog.getExitCode() == 0) &&
                (selectedSolution != null)) {

            // clearing current project
            PluginUtils.clearCurrentProject(anActionEvent.getProject());
            String projectPath = PluginUtils.getProjectPath(anActionEvent.getProject());

            // loading new solution
            PluginUtils.loadSolution(selectedSolution, projectPath);

            // marking selected solution as viewed
            PluginStorage.setViewed(selectedSolution.getHash());
        }


    }


}
