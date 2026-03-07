package org.Game.FoldersProvider.explorer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class TablePanel extends JPanel {
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private File currentDirectory;

    public TablePanel() {
        initUI();
        loadDirectory(new File(System.getProperty("user.home")));
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Содержимое папки"));
        String[] columns = {"Имя", "Размер", "Тип", "Изменен"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        fileTable = new JTable(tableModel);
        fileTable.setAutoCreateRowSorter(true);
        fileTable.setFillsViewportHeight(true);
        fileTable.setRowHeight(25);
        JTableHeader header = fileTable.getTableHeader();
        header.setReorderingAllowed(false);

        fileTable.getColumnModel().getColumn(0).setPreferredWidth(300);
        fileTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        fileTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        fileTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        createContextMenu();

        scrollPane = new JScrollPane(fileTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createContextMenu() {
        JPopupMenu contextMenu = new JPopupMenu();

        JMenuItem openItem = new JMenuItem("Открыть");
        JMenuItem renameItem = new JMenuItem("Переименовать");
        JMenuItem deleteItem = new JMenuItem("Удалить");
        JMenuItem propertiesItem = new JMenuItem("Свойства");

        contextMenu.add(openItem);
        contextMenu.addSeparator();
        contextMenu.add(renameItem);
        contextMenu.add(deleteItem);
        contextMenu.addSeparator();
        contextMenu.add(propertiesItem);

        fileTable.setComponentPopupMenu(contextMenu);
    }

    public void loadDirectory(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return;
        }

        currentDirectory = directory;

        tableModel.setRowCount(0);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        Arrays.sort(files, Comparator.comparing(File::isDirectory)
                .reversed()
                .thenComparing(File::getName));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        for (File file : files) {
            Object[] rowData = new Object[4];
            rowData[0] = file.getName();

            if (file.isDirectory()) {
                rowData[1] = "<Папка>";
                rowData[2] = "Папка";
            } else {
                rowData[1] = formatFileSize(file.length());
                rowData[2] = getFileExtension(file);
            }

            rowData[3] = dateFormat.format(new Date(file.lastModified()));

            tableModel.addRow(rowData);
        }
        ((javax.swing.border.TitledBorder) getBorder()).setTitle(
                "Содержимое папки: " + directory.getAbsolutePath());
        repaint();
    }

    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " Б";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f КБ", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.1f МБ", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.1f ГБ", size / (1024.0 * 1024.0 * 1024.0));
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot > 0 && lastDot < name.length() - 1) {
            return name.substring(lastDot + 1).toUpperCase() + " файл";
        }
        return "Файл";
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public JTable getFileTable() {
        return fileTable;
    }

    public File getSelectedFile() {
        int selectedRow = fileTable.getSelectedRow();
        if (selectedRow >= 0) {
            String fileName = (String) tableModel.getValueAt(selectedRow, 0);
            return new File(currentDirectory, fileName);
        }
        return null;
    }
}