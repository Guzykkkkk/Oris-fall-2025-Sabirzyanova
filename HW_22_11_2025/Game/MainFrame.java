package org.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainFrame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_SPEED = 10;

    private Timer timer;
    private int ballX, ballY;
    private int ballSpeedX = 5, ballSpeedY = 5;
    private int player1Y, player2Y;
    private int score1 = 0, score2 = 0;
    private boolean upPressed, downPressed, wPressed, sPressed;

    public MainFrame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        resetGame();

        timer = new Timer(16, this);
        timer.start();
    }

    private void resetGame() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        player1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        player2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;

        Random rand = new Random();
        ballSpeedX = rand.nextBoolean() ? 5 : -5;
        ballSpeedY = rand.nextBoolean() ? 5 : -5;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        for (int i = 0; i < HEIGHT; i += 20) {
            g.fillRect(WIDTH / 2 - 1, i, 2, 10);
        }

        g.fillRect(20, player1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - 20 - PADDLE_WIDTH, player2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.setColor(Color.RED);

        g.fillRect(ballX, ballY, BALL_SIZE, BALL_SIZE);
        g.setColor(Color.GREEN);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(String.valueOf(score1), WIDTH / 4, 50);
        g.drawString(String.valueOf(score2), 3 * WIDTH / 4, 50);
    }

    private void update() {
        if (wPressed && player1Y > 0) player1Y -= PADDLE_SPEED;
        if (sPressed && player1Y < HEIGHT - PADDLE_HEIGHT) player1Y += PADDLE_SPEED;
        if (upPressed && player2Y > 0) player2Y -= PADDLE_SPEED;
        if (downPressed && player2Y < HEIGHT - PADDLE_HEIGHT) player2Y += PADDLE_SPEED;

        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballSpeedY = -ballSpeedY;
        }


        if (ballX <= 20 + PADDLE_WIDTH &&
                ballY + BALL_SIZE >= player1Y &&
                ballY <= player1Y + PADDLE_HEIGHT) {
            ballSpeedX = Math.abs(ballSpeedX);
        }

        if (ballX >= WIDTH - 20 - PADDLE_WIDTH - BALL_SIZE &&
                ballY + BALL_SIZE >= player2Y &&
                ballY <= player2Y + PADDLE_HEIGHT) {
            ballSpeedX = -Math.abs(ballSpeedX);
        }

        if (ballX < 0) {
            score2++;
            resetGame();
        }
        if (ballX > WIDTH) {
            score1++;
            resetGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

}