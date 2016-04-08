package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.beans.Solution;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

class SolutionTreeListener implements TreeSelectionListener {
    private static Solution selectedSolution;

    static Solution getSelectedSolution() {
        return selectedSolution;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
        if (node == null) {
            selectedSolution = null;
            return;
        }
        Object nodeObject = node.getUserObject();
        if (nodeObject instanceof Solution) {
            selectedSolution = (Solution) nodeObject;
        } else {
            selectedSolution = null;
        }
    }

}
