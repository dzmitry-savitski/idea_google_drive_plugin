package by.gsu.epamlab.ideaplugin.gui;

import by.gsu.epamlab.ideaplugin.beans.Solution;
import by.gsu.epamlab.ideaplugin.google.SolutionUtils;
import by.gsu.epamlab.ideaplugin.plugin.Config;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.io.IOException;
import java.util.List;

class SolutionTree extends Tree {

    /**
     * Generates tree of solutions.
     */
    public static JTree createTree() {
        // top component
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode("Themes");

        // creating leaves
        createNodes(top);

        // assembling tree
        Tree tree = new Tree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        // setting renderer for viewed icons
        tree.setCellRenderer(new SolutionTreeCellRenderer());

        // adding listener
        tree.addTreeSelectionListener(new SolutionTreeListener());

        return tree;
    }

    /**
     * Creates leaves of solutions.
     */
    private static void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category;
        DefaultMutableTreeNode solutionNode;

        String[] scanDirs = Config.getStringArray();
        for (String dir : scanDirs) {

            // create category
            category = new DefaultMutableTreeNode(dir);
            top.add(category);

            // create leaves
            try {
                List<Solution> solutionList = SolutionUtils.getSolutionList(dir);
                for (Solution solution : solutionList) {
                    solutionNode = new DefaultMutableTreeNode(solution);
                    category.add(solutionNode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
