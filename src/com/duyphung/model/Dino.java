package com.duyphung.model;

import com.duyphung.audio.SoundManager;
import com.duyphung.effect.Animation;
import com.duyphung.resource.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Dino {

    public static final int LAND_POSY = 80;
    public static final float GRAVITY = 0.4f;

    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DOWN_RUN = 2;
    private static final int DEATH = 3;

    private float posY;
    private float posX;
    private float speedX;
    private float speedY;
    private Rectangle rectBound;

    private long previousTime;
    public int score = 0;
    public int highScore = 0;
    private int state = NORMAL_RUN;

    private Animation normalRunAnim;
    private BufferedImage jumping;
    private Animation downRunAnim;
    private BufferedImage deathImage;

    public Dino() {
        posX = 50;
        posY = LAND_POSY;
        rectBound = new Rectangle();
        normalRunAnim = new Animation(90);
        normalRunAnim.addImage(Resource.getResouceImage("data/main-character1.png"));
        normalRunAnim.addImage(Resource.getResouceImage("data/main-character2.png"));
        jumping = Resource.getResouceImage("data/main-character3.png");
        downRunAnim = new Animation(90);
        downRunAnim.addImage(Resource.getResouceImage("data/main-character5.png"));
        downRunAnim.addImage(Resource.getResouceImage("data/main-character6.png"));
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void draw(Graphics2D g2D) {
        switch (state) {
            case NORMAL_RUN:
                g2D.drawImage(normalRunAnim.getImage(), (int) posX, (int) posY, null);
                break;
            case JUMPING:
                g2D.drawImage(jumping, (int) posX, (int) posY, null);
                break;
            case DOWN_RUN:
                g2D.drawImage(downRunAnim.getImage(), (int) posX, (int) (posY + 20), null);
                break;
            case DEATH:
                g2D.drawImage(deathImage, (int) posX, (int) posY, null);
                break;
        }
        Rectangle bound = getBound();
		g2D.setColor(Color.RED);
		g2D.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    public void update() {
        normalRunAnim.updateFrame();
        downRunAnim.updateFrame();
        if (posY >= LAND_POSY) {
            posY = LAND_POSY;
            if (state != DOWN_RUN) {
                state = NORMAL_RUN;
            }
        } else {
            speedY += GRAVITY;
            posY += speedY;
        }
        upSpeed();
        upScore();
    }

    public void jump() {
        if (posY >= LAND_POSY) {
            speedY = -7.5f;
            posY += speedY;
            state = JUMPING;
        }
        SoundManager.play("jump.wav");
    }

    public void down(boolean isDown) {
        if (state == JUMPING) {
            return;
        }
        if (isDown) {
            state = DOWN_RUN;
        } else {
            state = NORMAL_RUN;
        }
    }

    public Rectangle getBound() {
        rectBound = new Rectangle();
        if (state == DOWN_RUN) {
            rectBound.x = (int) posX;
            rectBound.y = (int) posY + 20;
            rectBound.width = downRunAnim.getImage().getWidth();
            rectBound.height = downRunAnim.getImage().getHeight();
        } else {
            rectBound.x = (int) posX;
            rectBound.y = (int) posY;
            rectBound.width = normalRunAnim.getImage().getWidth();
            rectBound.height = normalRunAnim.getImage().getHeight();
        }
        return rectBound;
    }

    public void dead(boolean isDeath) {
        if (isDeath) {
            state = DEATH;
        } else {
            state = NORMAL_RUN;
        }
    }

    public void reset() {
        posY = LAND_POSY;
        score = 0;
    }

    public void upScore() {
        long T = System.currentTimeMillis();
        if (T - previousTime < 50) {
            return;
        }
        previousTime = T;
        score ++;
        if (score >= highScore) {
            highScore = score;
        }
        if (score % 100 == 0) {
            SoundManager.play("scoreup.wav");
        }
    }

    public void upSpeed() {
        speedX = score/100/2 + 4;
    }
}
