package org.Game.FoldersProvider.explorer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private NavigationPanel navigationPanel;
    private TreePanel treePanel;
    private TablePanel tablePanel;
    private FileController fileController;
    private FileModel fileSystemModel;

    public MainFrame() {
        initUI();
        setupControllers();
        setupEventHandlers();
    }

    private void initUI() {
        setTitle("File Explorer - собственный проводник");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        createMenuBar();

        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        navigationPanel = new NavigationPanel();
        mainPanel.add(navigationPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);

        treePanel = new TreePanel();
        splitPane.setLeftComponent(treePanel);

        tablePanel = new TablePanel();
        splitPane.setRightComponent(tablePanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel(" Готово");
        statusPanel.add(statusLabel, BorderLayout.WEST);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupControllers() {
        fileController = new FileController(this);
        fileSystemModel = new FileModel();
    }

    private void setupEventHandlers() {
        navigationPanel.getUpButton().addActionListener(e -> {
            File currentDir = tablePanel.getCurrentDirectory();
            if (currentDir != null) {
                File parent = currentDir.getParentFile();
                if (parent != null) {
                    loadDirectory(parent);
                }
            }
        });

        navigationPanel.getHomeButton().addActionListener(e -> {
            File home = new File(System.getProperty("user.home"));
            loadDirectory(home);
        });

        navigationPanel.getGoButton().addActionListener(e -> {
            String path = navigationPanel.getCurrentPath();
            File dir = new File(path);
            if (dir.isDirectory()) {
                loadDirectory(dir);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Указанный путь не является директорией",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        treePanel.getFileTree().addTreeSelectionListener(e -> {
            Object node = e.getPath().getLastPathComponent();
            if (node instanceof TreePanel.FileNode) {
                File file = ((TreePanel.FileNode) node).getFile();
                if (file.isDirectory()) {
                    loadDirectory(file);
                }
            }
        });

        tablePanel.getFileTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    File selectedFile = tablePanel.getSelectedFile();
                    if (selectedFile != null) {
                        if (selectedFile.isDirectory()) {
                            loadDirectory(selectedFile);
                        } else {
                            openFile(selectedFile);
                        }
                    }
                }
            }
        });
    }

    private void loadDirectory(File directory) {
        tablePanel.loadDirectory(directory);
        navigationPanel.setCurrentPath(directory.getAbsolutePath());
        fileSystemModel.setCurrentDirectory(directory);
    }

    private void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Не удалось открыть файл: " + e.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");

        JMenuItem newFileItem = new JMenuItem("Создать файл");
        newFileItem.addActionListener(e -> createNewFile());

        JMenuItem newFolderItem = new JMenuItem("Создать папку");
        newFolderItem.addActionListener(e -> createNewFolder());

        JMenuItem renameItem = new JMenuItem("Переименовать");
        renameItem.addActionListener(e -> renameFile());

        JMenuItem deleteItem = new JMenuItem("Удалить");
        deleteItem.addActionListener(e -> deleteFile());

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newFileItem);
        fileMenu.add(newFolderItem);
        fileMenu.addSeparator();
        fileMenu.add(renameItem);
        fileMenu.add(deleteItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        JMenu viewMenu = new JMenu("Вид");
        JMenuItem refreshItem = new JMenuItem("Обновить");
        refreshItem.addActionListener(e -> refreshView());
        viewMenu.add(refreshItem);
        JMenu helpMenu = new JMenu("Помощь");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }
    private void createNewFile() {
        File currentDir = tablePanel.getCurrentDirectory();
        if (currentDir == null) return;

        String fileName = JOptionPane.showInputDialog(
                this,
                "Введите имя файла:",
                "Создание файла",
                JOptionPane.PLAIN_MESSAGE
        );

        if (fileName != null && !fileName.trim().isEmpty()) {
            if (fileController.createNewFile(currentDir, fileName)) {
                refreshView();
            }
        }
    }

    private void createNewFolder() {
        File currentDir = tablePanel.getCurrentDirectory();
        if (currentDir == null) return;

        String folderName = JOptionPane.showInputDialog(
                this,
                "Введите имя папки:",
                "Создание папки",
                JOptionPane.PLAIN_MESSAGE
        );

        if (folderName != null && !folderName.trim().isEmpty()) {
            if (fileController.createNewFolder(currentDir, folderName)) {
                refreshView();
            }
        }
    }

    private void renameFile() {
        File selectedFile = tablePanel.getSelectedFile();
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this,
                    "Выберите файл для переименования",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newName = JOptionPane.showInputDialog(
                this,
                "Введите новое имя:",
                "Переименование",
                JOptionPane.PLAIN_MESSAGE
        );

        if (newName != null && !newName.trim().isEmpty()) {
            if (fileController.renameFile(selectedFile, newName)) {
                refreshView();
            }
        }
    }

    private void deleteFile() {
        File selectedFile = tablePanel.getSelectedFile();
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this,
                    "Выберите файл для удаления",
                    "Ошибка",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (fileController.deleteFile(selectedFile)) {
            refreshView();
        }
    }

    private void refreshView() {
        File currentDir = tablePanel.getCurrentDirectory();
        if (currentDir != null) {
            loadDirectory(currentDir);
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "File Explorer v1.0\nСобственный файловый менеджер\nРазработано для демонстрации работы с классом File",
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }
}