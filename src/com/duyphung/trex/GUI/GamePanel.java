package com.duyphung.trex.GUI;

import com.duyphung.model.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.duyphung.model.GameManager.GAME_OVER_STATE;
import static com.duyphung.model.GameManager.GAME_PLAYING_STATE;
import static com.duyphung.model.GameManager.START_GAME_STATE;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private GameManager gameManager;

    public GamePanel() {
        setBackground(Color.decode("#f7f7f7"));
        gameManager = new GameManager();
        gameManager.initGame();
        setFocusable(true);
        addKeyListener(this);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(graphics2D);
        gameManager.draw(graphics2D);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameManager.getGameState()) {
            case START_GAME_STATE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameManager.setGameState(GAME_PLAYING_STATE);
                }
                break;
            case GAME_PLAYING_STATE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameManager.getDino().jump();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    gameManager.getDino().down(true);
                }
                break;
            case GAME_OVER_STATE:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameManager.setGameState(GAME_PLAYING_STATE);
                    gameManager.reset();
                }
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameManager.getGameState() == GAME_PLAYING_STATE) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                gameManager.getDino().down(false);
            }
        }
    }

    @Override
    public void run() {

        while (true) {
            gameManager.gameUpdate();
            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
