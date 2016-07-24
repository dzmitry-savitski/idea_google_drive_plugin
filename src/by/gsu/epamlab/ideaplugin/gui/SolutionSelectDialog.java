package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.plugin.Config;
import by.gsu.epamlab.ideaplugin.plugin.PluginStorage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SolutionSelectDialog extends DialogWrapper {

    public SolutionSelectDialog(@Nullable Project project) {
        super(project, true);
        this.init();
        this.show();
    }

    /**
     * Creates main dialog window.
     */
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(900, 500));

        // getting welcome phrase from config
        String welcomePhrase = Config.getString("by.gsu.epamlab.ideaplugin.welcome");
        JLabel pane = new JLabel(welcomePhrase);
        pane.setPreferredSize(new Dimension(500, 20));
        panel.add(pane, "North");

        // creating tree of solutions
        JTree tree = SolutionTree.createTree();
        JBScrollPane pane1 = new JBScrollPane(tree);
        panel.add(pane1, "Center");
        return panel;
    }

    /**
     * Creates actions on main dialog window.
     */
    @NotNull
    @Override
    protected Action[] createActions() {
        return new Action[]{this.getOKAction(), this.getCancelAction(), clearAction()};
    }

    private Action clearAction() {
        return new AbstractAction("Clear Marks") {
            /**
             * Clears all user marks of viewed files & closes window.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                PluginStorage.clearProperty(PluginStorage.VIEWED);
                SolutionSelectDialog.this.close(SolutionSelectDialog.CANCEL_EXIT_CODE);
            }
        };
    }
}
