package com.duyphung.model;

import com.duyphung.resource.Resource;
import com.duyphung.trex.GUI.GameFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import com.duyphung.audio.SoundManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameManager {
    public static final int START_GAME_STATE = 0;
    public static final int GAME_PLAYING_STATE = 1;
    public static final int GAME_OVER_STATE = 2;
    private int gameState = START_GAME_STATE;

    private List<Enemy> enemies;

    private Dino dino;
    private Clouds clouds;
    private Land land;

    private BufferedImage replayButtonImage;
    private BufferedImage gameOverButtonImage;

    public void initGame() {
        dino = new Dino();
        land = new Land(GameFrame.W_FRAME, dino);
        clouds = new Clouds(GameFrame.W_FRAME, dino);
        dino.setSpeedX(4);
        replayButtonImage = Resource.getResouceImage("data/replay_button.png");
        gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
        enemies = new LinkedList<Enemy>();
        enemies.add(createCactus());
    }

    private Enemy createCactus() {
        BufferedImage cactus1 = Resource.getResouceImage("data/cactus1.png");
        BufferedImage cactus2 = Resource.getResouceImage("data/cactus2.png");
        Random rand = new Random();
        int type = rand.nextInt(2);
        if (type == 0) {
            return new Cactus(dino, 800, cactus1.getWidth() , cactus1.getHeight() , cactus1);
        } else {
            return new Cactus(dino, 800, cactus2.getWidth() , cactus2.getHeight() , cactus2);
        }
    }

    public void draw(Graphics2D graphics2D) {
        switch (gameState) {
            case START_GAME_STATE:
                dino.draw(graphics2D);
                break;
            case GAME_PLAYING_STATE:
            case GAME_OVER_STATE:
                clouds.draw(graphics2D);
                land.draw(graphics2D);
                for (Enemy e : enemies) {
                    e.draw(graphics2D);
                }
                dino.draw(graphics2D);
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString("HI " + dino.highScore, 500, 20);
                graphics2D.drawString("Score " + dino.score, 400, 20);
                if (gameState == GAME_OVER_STATE) {
                    replayButtonImage = Resource.getResouceImage("data/replay_button.png");
                    gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
                    graphics2D.drawImage(gameOverButtonImage, 200, 30, null);
                    graphics2D.drawImage(replayButtonImage, 283, 50, null);

                }
                break;
        }
    }

    public boolean isCollision() {
        for (Enemy e : enemies){
            if (dino.getBound().intersects(e.getBound())) {
                return true;
            }
        }
        return false;
    }

    public void gameUpdate() {
        if (gameState == GAME_PLAYING_STATE) {
            clouds.update();
            land.update();
            dino.update();

            for (Enemy e : enemies) {
                e.update();
            }
            if (enemies.isEmpty() == false) {
                Enemy enemy = enemies.get(0);
                if (enemy.isOutOfScreen()) {
                    enemies.clear();
                    enemies.add(createCactus());
                }
            }

            if (this.isCollision()) {
                SoundManager.play("dead.wav");
                gameState = GAME_OVER_STATE;
                dino.dead(true);
            }
        }
    }

    public void reset() {
        enemies.clear();
        enemies.add(createCactus());
        dino.dead(false);
        dino.reset();
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getGameState() {
        return gameState;
    }

    public Dino getDino() {
        return dino;
    }
}
