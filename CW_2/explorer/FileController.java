package org.Game.FoldersProvider.explorer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileController {
    private JFrame parentFrame;

    public FileController(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    // Создать новый файл
    public boolean createNewFile(File directory, String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        File newFile = new File(directory, fileName.trim());

        try {
            if (newFile.createNewFile()) {
                JOptionPane.showMessageDialog(
                        parentFrame,
                        "Файл создан: " + newFile.getName(),
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return true;
            } else {
                JOptionPane.showMessageDialog(
                        parentFrame,
                        "Не удалось создать файл. Возможно, файл уже существует.",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Ошибка при создании файла: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean createNewFolder(File directory, String folderName) {
        if (folderName == null || folderName.trim().isEmpty()) {
            return false;
        }

        File newFolder = new File(directory, folderName.trim());

        if (newFolder.mkdir()) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Папка создана: " + newFolder.getName(),
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } else {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Не удалось создать папку. Возможно, папка уже существует.",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean renameFile(File file, String newName) {
        if (file == null || !file.exists()) {
            return false;
        }

        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }

        File newFile = new File(file.getParent(), newName.trim());

        if (file.renameTo(newFile)) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Файл переименован: " + newFile.getName(),
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } else {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Не удалось переименовать файл.",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(
                parentFrame,
                "Вы уверены, что хотите удалить '" + file.getName() + "'?",
                "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }

        boolean success = false;
        if (file.isDirectory()) {
            success = deleteDirectory(file);
        } else {
            success = file.delete();
        }

        if (success) {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Удалено: " + file.getName(),
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } else {
            JOptionPane.showMessageDialog(
                    parentFrame,
                    "Не удалось удалить. Возможно, файл используется.",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private boolean deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    public void showFileProperties(File file) {
        if (file == null || !file.exists()) {
            return;
        }

        StringBuilder properties = new StringBuilder();
        properties.append("Имя: ").append(file.getName()).append("\n");
        properties.append("Путь: ").append(file.getAbsolutePath()).append("\n");

        if (file.isDirectory()) {
            properties.append("Тип: Папка\n");
            File[] files = file.listFiles();
            int fileCount = files != null ? files.length : 0;
            properties.append("Содержит файлов: ").append(fileCount).append("\n");
        } else {
            properties.append("Тип: Файл\n");
            properties.append("Размер: ").append(formatFileSize(file.length())).append("\n");
        }

        properties.append("Последнее изменение: ")
                .append(new java.util.Date(file.lastModified())).append("\n");
        properties.append("Можно читать: ").append(file.canRead() ? "Да" : "Нет").append("\n");
        properties.append("Можно писать: ").append(file.canWrite() ? "Да" : "Нет").append("\n");
        properties.append("Скрытый: ").append(file.isHidden() ? "Да" : "Нет").append("\n");

        JOptionPane.showMessageDialog(
                parentFrame,
                properties.toString(),
                "Свойства: " + file.getName(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String formatFileSize(long size) {
        if (size < 1024) return size + " Б";
        if (size < 1024 * 1024) return String.format("%.1f КБ", size / 1024.0);
        if (size < 1024 * 1024 * 1024) return String.format("%.1f МБ", size / (1024.0 * 1024.0));
        return String.format("%.1f ГБ", size / (1024.0 * 1024.0 * 1024.0));
    }
}