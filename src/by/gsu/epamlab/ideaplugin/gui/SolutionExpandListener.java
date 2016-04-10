package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.plugin.PluginStorage;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;

/**
 * Listens all tre expansions / collapsions.
 */
class SolutionExpandListener implements TreeExpansionListener {
    private final JTree tree;

    public SolutionExpandListener(JTree tree) {
        this.tree = tree;
    }

    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        saveExpandedNodes();
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        saveExpandedNodes();
    }

    /**
     * Saves state for expanded branches.
     */
    private void saveExpandedNodes() {
        PluginStorage.clearProperty(PluginStorage.EXPANDED_NODES);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration e = root.preorderEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            TreePath path = new TreePath(node.getPath());
            if (tree.isExpanded(path)) {
                PluginStorage.addValue(path.toString(), PluginStorage.EXPANDED_NODES);
            }
        }
    }
}
