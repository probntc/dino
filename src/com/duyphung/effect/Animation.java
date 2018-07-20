package com.duyphung.effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    private List<BufferedImage> list;
    private long deltaTime;
    private int index = 0;
    private long previousTime;

    public Animation(int deltaTime) {
        this.deltaTime = deltaTime;
        list = new ArrayList<BufferedImage>();
        previousTime = 0;
    }

    public void updateFrame() {
        if (System.currentTimeMillis() - previousTime >= deltaTime) {
            index++;
            if (index >= list.size()) {
                index = 0;
            }
            previousTime = System.currentTimeMillis();
        }
    }

    public void addImage(BufferedImage image) {
        list.add(image);
    }

    public BufferedImage getImage() {
        return list.get(index);
    }
}
