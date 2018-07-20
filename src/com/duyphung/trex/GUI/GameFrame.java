package com.duyphung.trex.GUI;

import javax.swing.*;

public class GameFrame extends JFrame {
    public static final int W_FRAME = 600;
    public static final int H_FRAME = 175;
    private GamePanel gamePanel;

    public GameFrame() {
        setTitle("T-Rex offline game");
        setSize(W_FRAME, H_FRAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        gamePanel = new GamePanel();
        add(gamePanel);
        setVisible(true);
    }
}
