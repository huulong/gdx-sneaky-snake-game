package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class Snake {
    private SnakeHead head;
    private Array<SnakeBodyPart> bodyParts = new Array<>();
    private int MOVEMENT = 32;
    private int direction;
    private int snakeXBeforeUpdate = 0, snakeYBeforeUpdate = 0;
    private Snake endGame;
    private Stage stage = new Stage();
    private boolean isGameOver = false;
    private Table gameOverTable;


    public Snake() {
        head = new SnakeHead(new Texture(Gdx.files.internal("snakehead.png")));
        head.updatePosition(0, 0);
    }

    public void drawFullSnake(Batch batch) {
        head.drawOnScreen(batch);

        for (SnakeBodyPart bodyPart : bodyParts) {
            if (!(bodyPart.getX() == head.getX() && bodyPart.getY() == head.getY())) {
                bodyPart.drawOnScreen(batch);
            }
        }

    }

    public void move() {
        snakeXBeforeUpdate = head.getX();
        snakeYBeforeUpdate = head.getY();
        switch (direction) {
            case Direction.RIGHT:
                head.setX(head.getX() + MOVEMENT);
                break;
            case Direction.LEFT:
                head.setX(head.getX() - MOVEMENT);
                break;
            case Direction.UP:
                head.setY(head.getY() + MOVEMENT);
                break;
            case Direction.DOWN:
                head.setY(head.getY() - MOVEMENT);
                break;
        }

        // kiểm tra va chạm với tường hoặc phần thân của rắn
        if (head.getX() < 0 || head.getX() >= Gdx.graphics.getWidth() ||
                head.getY() < 0 || head.getY() >= Gdx.graphics.getHeight() ||
                isCollidingWithBody()) {
            // rắn đã va chạm, xử lý khi kết thúc trò chơi
            gameOver();
        }
    }

    private boolean isCollidingWithBody() {
        for (SnakeBodyPart bodyPart : bodyParts) {
            if (bodyPart.getX() == head.getX() && bodyPart.getY() == head.getY()) {
                return true;
            }
        }
        return false;
    }

    public void addBodyPart() {
        SnakeBodyPart bodyPart = new SnakeBodyPart(new Texture(Gdx.files.internal("snakebody.png")));
        bodyPart.updatePosition(head.getX(), head.getY());
        bodyParts.insert(0, bodyPart);
    }

    public void updateBodyPartsPosition() {
        if (bodyParts.size > 0) {
            SnakeBodyPart bodyPart = bodyParts.removeIndex(0);
            bodyPart.updatePosition(snakeXBeforeUpdate, snakeYBeforeUpdate);
            bodyParts.add(bodyPart);
        }
    }

    public SnakeHead getHead() {
        return head;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getMOVEMENT() {
        return MOVEMENT;
    }

    public void gameOver() {
        isGameOver = true; // Đặt biến isGameOver thành true
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void drawGameOver(Batch batch, Texture gameOverTexture, float xPosition, float yPosition) {
        if (isGameOver()) {
            // Draw the texture to the screen using the SpriteBatch
            batch.begin();
            batch.draw(gameOverTexture, xPosition, yPosition);
            batch.end();
        }
    }

}