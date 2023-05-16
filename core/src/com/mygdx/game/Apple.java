package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Apple extends CustomSprite {

    private boolean isAvailable = false;

    public Apple(Texture texture) {
        super(texture);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
