package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture appleTexture;
    private Apple apple;
    private Snake snake;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;
    private int score = 0;
    float xPosition = 0;
    float yPosition = 0;
    private GameUI gameUI;
    private boolean gameOver = true;
    private Music music;
    @Override
    public void show() {
        batch = new SpriteBatch();
        appleTexture = new Texture(Gdx.files.internal("apple.png"));

        apple = new Apple(appleTexture);
        snake = new Snake();
        gameUI = new GameUI();
        music = Gdx.audio.newMusic(Gdx.files.internal("f88.wav"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        queryKeyboardInput();
        timer -= delta*4;
        if (timer <= 0) {
            timer = MOVE_TIME;
            moveSnake();
            checkIfOutOfBounds();
            updateBodyPartsPosition();
        }
        checkAppleCollision();
        checkAndPlaceApple();

        // set screen color
        clearScreen();
//
//        // display sprites on screen
        drawOnScreen();
//        gameUI.drawGameOver();


        if (snake.isGameOver()) {
            gameOver = true; // set game over flag to true
        }

        if (gameOver) {
            // Load the image and create a texture from it
            Texture gameOverTexture = new Texture(Gdx.files.internal("assets/gameover.png"));
            float xPosition = Gdx.graphics.getWidth()/2 - gameOverTexture.getWidth()/2;
            float yPosition = Gdx.graphics.getHeight()/2 - gameOverTexture.getHeight()/2;
            snake.drawGameOver(batch, gameOverTexture, xPosition, yPosition);
        }
    }

    private void clearScreen() {
        // Set screen color to black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawOnScreen() {
        batch.begin();

        snake.drawFullSnake(batch);

        if (apple.isAvailable()) {
            apple.drawOnScreen(batch);
        }
        drawHUD(batch);
        batch.end();
    }




    private void checkIfOutOfBounds() {
         if (snake.getHead().getX() >= Gdx.graphics.getWidth()) {
            snake.getHead().setX(0);
        }
        if (snake.getHead().getX() < 0) {
            snake.getHead().setX(Gdx.graphics.getWidth() - snake.getMOVEMENT());
        }
        if (snake.getHead().getY() >= Gdx.graphics.getHeight()) {
            snake.getHead().setY(0);
        }
        if (snake.getHead().getY() < 0) {
            snake.getHead().setY(Gdx.graphics.getHeight() - snake.getMOVEMENT());
        }
    }

    private void moveSnake() {
        snake.move();
    }
//Kiểm tra input từ bàn phím của người chơi để thay đổi hướng di chuyển của rắn.
    private void queryKeyboardInput(){
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            snake.setDirection(Direction.LEFT);
        }
        if (rightPressed) {
            snake.setDirection(Direction.RIGHT);
        }
        if (upPressed) {
            snake.setDirection(Direction.UP);
        }
        if (downPressed) {
            snake.setDirection(Direction.DOWN);
        }
    }
//Kiểm tra nếu không có quả táo trên màn hình thì đặt một quả táo mới ở vị trí ngẫu nhiên trên màn hình.
    private void checkAndPlaceApple() {
        if (!apple.isAvailable()) {
            do {
                int SNAKE_MOVEMENT = snake.getMOVEMENT();
                int appleX = MathUtils.random(0, (Gdx.graphics.getWidth() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                int appleY = MathUtils.random(0, (Gdx.graphics.getHeight() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                apple.updatePosition(appleX, appleY);
            } while (apple.getX() == snake.getHead().getX() && apple.getY() == snake.getHead().getY());
            apple.setAvailable(true);
        }
    }
//Kiểm tra xem rắn đã va chạm với quả táo hay chưa.
// Nếu có, tăng chiều dài của rắn và loại bỏ quả táo khỏi màn hình.
    private void checkAppleCollision() {
        boolean condition1 = apple.isAvailable();
        boolean condition2 = snake.getHead().getX() == apple.getX();
        boolean condition3 = snake.getHead().getY() == apple.getY();
        boolean isCollision = condition1 && condition2 && condition3;
        if (isCollision) {
            snake.addBodyPart();
            apple.setAvailable(false);
            score+=10; // tăng điểm số lên 1
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("assets/Eat.wav"));
            sound.play();
        }
    }


    private void drawHUD(Batch batch) {
        BitmapFont font = new BitmapFont();
        font.draw(batch, "Score: " + score, 0, Gdx.graphics.getHeight() - 10);
    }

    //Cập nhật vị trí của các phần thân của rắn sau khi chúng di chuyển theo đầu của rắn.
    private void updateBodyPartsPosition() {
        snake.updateBodyPartsPosition();
    }

}
