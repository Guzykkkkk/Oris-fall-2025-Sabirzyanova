package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {

    JTextField win = new JTextField("0");
    private double digit = 0;
    String op = "";
    boolean flag = true;
    Main() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);

    win.setFont(new Font("Arial", Font.BOLD, 50));
    win.setPreferredSize(new Dimension(800, 60));
        JPanel winPanel = new JPanel();
        winPanel.add(win);

        frame.add(winPanel, BorderLayout.NORTH);
        JButton button = new JButton("0");
        button.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button1 = new JButton("1");
        button1.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button2 = new JButton("2");
        button2.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button3 = new JButton("3");
        button3.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button4 = new JButton("4");
        button4.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button5 = new JButton("5");
        button5.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button6 = new JButton("6");
        button6.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button7 = new JButton("7");
        button7.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button8 = new JButton("8");
        button8.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button9 = new JButton("9");
        button9.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button10 = new JButton("+");
        button10.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button11 = new JButton("-");
        button11.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button12 = new JButton("=");
        button12.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button13 = new JButton("*");
        button13.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button14 = new JButton("/");
        button14.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button15 = new JButton(".");
        button15.setFont(new Font("Arial", Font.BOLD, 50));
        JButton button16 = new JButton("C");
        button16.setFont(new Font("Arial", Font.BOLD, 50));

        button.addActionListener(e -> Result( "0"));
        button1.addActionListener(e -> Result( "1"));
        button2.addActionListener(e -> Result("2"));
        button3.addActionListener(e -> Result(  "3"));
        button4.addActionListener(e -> Result( "4"));
        button5.addActionListener(e -> Result( "5"));
        button6.addActionListener(e -> Result( "6"));
        button7.addActionListener(e -> Result( "7"));
        button8.addActionListener(e -> Result( "8"));
        button9.addActionListener(e -> Result( "9"));
        button10.addActionListener(e -> PlusSigns( "+"));
        button11.addActionListener(e -> PlusSigns( "-"));
        button12.addActionListener(e -> calculate());
        button13.addActionListener(e -> PlusSigns( "×"));
        button14.addActionListener(e -> PlusSigns( "÷"));
        button15.addActionListener(e -> Result( "."));
        button16.addActionListener(e -> clear());


        JPanel panel = new JPanel(new GridLayout(6, 4,5,5));
        panel.add(button);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
        panel.add(button7);
        panel.add(button8);
        panel.add(button9);
        panel.add(button10);
        panel.add(button11);
        panel.add(button12);
        panel.add(button13);
        panel.add(button14);
        panel.add(button15);
        panel.add(button16);

        frame.add(panel, BorderLayout.CENTER);


        frame.setVisible(true);

    }
    private void  Result(String result) {
        if (flag) {
            if (result.equals(".")) {
                win.setText("0.");
            } else if (result.equals("0")) {
                win.setText("0");
            } else {
                win.setText(result);
            }
            flag = false;
        } else {
            String winText = win.getText();
            if (result.equals(".") && winText.contains(".")) return;
            win.setText(winText + result);
        }
    }
    private void PlusSigns(String res) {
        if (!op.isEmpty()) {
            calculate();
        }
        digit = Double.parseDouble(win.getText());
        op = res;
        flag = true;
        win.setText(win.getText() + " " + op + " ");
    }
    public void clear() {
        win.setText("0");
        digit = 0;
        op = "";
        flag = true;
    }

    private void calculate() {
        if (op.isEmpty()) return;
        double num = Double.parseDouble(win.getText());
        double result = 0;
        switch (op) {
            case "+": result = digit + num; break;
            case "-": result = digit - num; break;
            case "×": result = digit * num; break;
            case "÷":
                if (num != 0) {
                    result = digit / num;
                } else {
                    win.setText("ERROR");
                    clear();
                    return;
                }
                break;

        }
        if (result == (long) result) {
            win.setText(String.valueOf((long) result));
        } else {
            win.setText(String.valueOf(result));
        }
        digit = result;
        op = "";
        flag = true;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }


}