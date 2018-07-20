package com.duyphung.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cactus extends Enemy {

    public static final int Y_LAND = 125;

    private int posX;
    private int width;
    private int height;

    private BufferedImage image;
    private Dino dino;

    private Rectangle rectBound;

    public Cactus(Dino dino, int posX, int width, int height, BufferedImage image) {
        this.posX = posX;
        this.width = width;
        this.height = height;
        this.image = image;
        this.dino = dino;
        rectBound = new Rectangle();
    }

    @Override
    public void update() {
        posX -= dino.getSpeedX();
    }

    @Override
    public void draw(Graphics2D g2D) {
        g2D.drawImage(image, posX, Y_LAND - image.getHeight(), null);
        g2D.setColor(Color.red);
        Rectangle bound = getBound();
        g2D.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Rectangle getBound() {
        rectBound = new Rectangle();
        rectBound.x = posX + (image.getWidth() - width) / 2;
        rectBound.y = Y_LAND - image.getHeight() + (image.getHeight() - height) / 2;
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;
    }

    @Override
    public boolean isOutOfScreen() {
        return posX < -image.getWidth();
    }
}
