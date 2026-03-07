package org.Game.FoldersProvider.explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileModel {
    private File currentDirectory;
    private List<FileSystemListener> listeners;

    public FileModel() {
        this.currentDirectory = new File(System.getProperty("user.home"));
        this.listeners = new ArrayList<>();
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File directory) {
        if (directory != null && directory.isDirectory()) {
            this.currentDirectory = directory;
            notifyDirectoryChanged();
        }
    }

    public void navigateUp() {
        File parent = currentDirectory.getParentFile();
        if (parent != null) {
            setCurrentDirectory(parent);
        }
    }

    public void navigateToHome() {
        setCurrentDirectory(new File(System.getProperty("user.home")));
    }

    public List<File> getFilesInCurrentDirectory() {
        List<File> files = new ArrayList<>();
        File[] fileArray = currentDirectory.listFiles();

        if (fileArray != null) {
            for (File file : fileArray) {
                files.add(file);
            }
        }

        return files;
    }

    public void addListener(FileSystemListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FileSystemListener listener) {
        listeners.remove(listener);
    }

    private void notifyDirectoryChanged() {
        for (FileSystemListener listener : listeners) {
            listener.directoryChanged(currentDirectory);
        }
    }

    public interface FileSystemListener {
        void directoryChanged(File newDirectory);
    }
}