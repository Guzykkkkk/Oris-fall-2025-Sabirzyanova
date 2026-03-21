package org.Game.FoldersProvider.explorer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class NavigationPanel extends JPanel {
    private JButton backButton;
    private JButton forwardButton;
    private JButton upButton;
    private JButton homeButton;
    private JComboBox<String> pathComboBox;
    private JTextField pathField;

    public NavigationPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5, 5));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        backButton = new JButton("<-");
        backButton.setToolTipText("Назад");

        forwardButton = new JButton("->");
        forwardButton.setToolTipText("Вперед");

        upButton = new JButton("up");
        upButton.setToolTipText("На уровень выше");

        homeButton = new JButton("*");
        homeButton.setToolTipText("Домашняя директория");

        buttonPanel.add(backButton);
        buttonPanel.add(forwardButton);
        buttonPanel.add(upButton);
        buttonPanel.add(homeButton);

        add(buttonPanel, BorderLayout.WEST);

        pathField = new JTextField();
        pathField.setText(System.getProperty("user.home"));

        JButton goButton = new JButton("Перейти");
        JPanel pathPanel = new JPanel(new BorderLayout(5, 0));
        pathPanel.add(new JLabel(" Путь: "), BorderLayout.WEST);
        pathPanel.add(pathField, BorderLayout.CENTER);
        pathPanel.add(goButton, BorderLayout.EAST);

        add(pathPanel, BorderLayout.CENTER);
    }

    public String getCurrentPath() {
        return pathField.getText();
    }

    public void setCurrentPath(String path) {
        pathField.setText(path);
    }

    public JButton getBackButton() { return backButton; }
    public JButton getForwardButton() { return forwardButton; }
    public JButton getUpButton() { return upButton; }
    public JButton getHomeButton() { return homeButton; }
    public JButton getGoButton() {
        return (JButton) ((JPanel) getComponent(1)).getComponent(2);
    }
}