package org.Game.FoldersProvider.explorer;

import javax.swing.*;

import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class TreePanel extends JPanel {
    private JTree fileTree;
    private DefaultTreeModel treeModel;
    private JScrollPane scrollPane;

    public TreePanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Дерево файловой системы"));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Мой компьютер");

        File[] roots = File.listRoots();
        if (roots != null) {
            Arrays.sort(roots, Comparator.comparing(File::getAbsolutePath));

            for (File rootFile : roots) {
                FileNode rootNode = new FileNode(rootFile);
                rootNode.setAllowsChildren(true);
                root.add(rootNode);
                addChildren(rootNode);
            }
        }

        treeModel = new DefaultTreeModel(root);
        fileTree = new JTree(treeModel);

        fileTree.setRootVisible(true);
        fileTree.setShowsRootHandles(true);
        fileTree.setCellRenderer(new FileTreeCellRenderer());
        fileTree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            @Override
            public void treeExpanded(javax.swing.event.TreeExpansionEvent event) {
                TreePath path = event.getPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (node instanceof FileNode) {
                    loadChildren((FileNode) node);
                }
            }

            @Override
            public void treeCollapsed(javax.swing.event.TreeExpansionEvent event) {
            }
        });

        scrollPane = new JScrollPane(fileTree);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addChildren(DefaultMutableTreeNode parent) {
        if (parent instanceof FileNode) {
            FileNode fileNode = (FileNode) parent;
            File file = fileNode.getFile();

            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children != null) {
                    Arrays.sort(children, Comparator.comparing(File::isDirectory)
                            .reversed()
                            .thenComparing(File::getName));

                    for (File child : children) {
                        if (child.isDirectory()) {
                            FileNode childNode = new FileNode(child);
                            childNode.setAllowsChildren(true);
                            parent.add(childNode);
                            if (child.listFiles() != null && child.listFiles().length > 0) {
                                childNode.add(new DefaultMutableTreeNode("..."));
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadChildren(FileNode parent) {
        if (parent.getChildCount() == 1) {
            TreeNode child = parent.getChildAt(0);
            if (child instanceof DefaultMutableTreeNode) {
                String userObject = ((DefaultMutableTreeNode) child).getUserObject().toString();
                if (userObject.equals("Загрузка...")) {
                    parent.remove(0);
                }
            }
        }
        addChildren(parent);
        treeModel.reload(parent);
    }

    public JTree getFileTree() {
        return fileTree;
    }

    public static class FileNode extends DefaultMutableTreeNode {
        private File file;

        public FileNode(File file) {
            super(file.getName());
            this.file = file;
        }

        public File getFile() {
            return file;
        }

        @Override
        public String toString() {
            String name = file.getName();
            return name.isEmpty() ? file.getAbsolutePath() : name;
        }
    }

    private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {
        private Icon folderIcon;
        private Icon fileIcon;
        private Icon driveIcon;

        public FileTreeCellRenderer() {
            folderIcon = UIManager.getIcon("FileView.directoryIcon");
            fileIcon = UIManager.getIcon("FileView.fileIcon");
            driveIcon = UIManager.getIcon("FileView.hardDriveIcon");
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            if (value instanceof FileNode) {
                FileNode node = (FileNode) value;
                File file = node.getFile();

                if (file.isDirectory()) {
                    if (file.getParent() == null) {
                        setIcon(driveIcon);
                    } else {
                        setIcon(folderIcon);
                    }
                } else {
                    setIcon(fileIcon);
                }

                setToolTipText(file.getAbsolutePath());
            }

            return this;
        }
    }
}