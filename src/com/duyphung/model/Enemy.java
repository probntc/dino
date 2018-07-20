package com.duyphung.model;

import java.awt.*;

public abstract class Enemy {
    public abstract void update();
    public abstract void draw(Graphics2D g2D);
    public abstract Rectangle getBound();
    public abstract boolean isOutOfScreen();
}
