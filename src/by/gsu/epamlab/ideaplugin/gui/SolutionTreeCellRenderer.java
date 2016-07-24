package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.plugin.PluginStorage;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * Renders solutions with different icons.
 * Depends on their viewed status.
 */
class SolutionTreeCellRenderer implements TreeCellRenderer {
    private static final Icon ZIP_ICON = IconLoader.getIcon("/icons/zip.png");
    private static final Icon OK_ICON = IconLoader.getIcon("/icons/ok.png");
    private final JLabel label;  // creating new label for each solution

    SolutionTreeCellRenderer() {
        label = new JLabel();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        if (o instanceof Solution) {
            Solution solution = (Solution) o;
            label.setText(solution.getName());

            // changing icons
            if (PluginStorage.isValuePresent(solution.getHash(), PluginStorage.VIEWED)) {
                label.setIcon(OK_ICON);
            } else {
                label.setIcon(ZIP_ICON);
            }

        } else {
            label.setIcon(null);
            label.setText("" + value);
        }
        return label;
    }
}