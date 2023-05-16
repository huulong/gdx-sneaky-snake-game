package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class CustomSprite {
    private int x, y;
    private Texture texture;

    public CustomSprite(Texture stexture) {
        this.texture = stexture;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawOnScreen(Batch batch) {
        batch.draw(texture, x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
