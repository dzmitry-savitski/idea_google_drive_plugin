package by.gsu.epamlab.ideaplugin.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;

public class GuiUtils {
    /**
     * Shows popup with given text.
     */
    public static void showPopUpText(String message, Project currentProject) {
        JBPopupFactory.getInstance()
                .createMessage(message)
                .showCenteredInCurrentWindow(currentProject);
    }
}
