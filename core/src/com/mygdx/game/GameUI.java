package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameUI {
    private Stage stage;
    private Table table;

    public GameUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Cho phép Stage xử lý InputEvent

        // Tạo bảng chứa các phần tử trong hộp thoại game over
        table = new Table();
        table.setFillParent(true);

        // Thêm lời nhắn game over vào bảng
        Label gameOverLabel = new Label("GAME OVER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(gameOverLabel).expandX().padTop(50f);
        table.row();
        // Thêm nút Restart vào bảng
//        TextButton restartButton = new TextButton("Restart", new Skin());
//        table.add(restartButton).expandX().padTop(50f);
//        table.row();
//
//        // Thêm nút Exit vào bảng
//        TextButton exitButton = new TextButton("Exit", new Skin());
//        table.add(exitButton).expandX().padTop(10f);
//
//        // Thêm sự kiện click cho các nút Restart và Exit
//        restartButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Khởi động lại trò chơi
//                resetGame();
//            }
//        });
//
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Thoát khỏi trò chơi
//                Gdx.app.exit();
//            }
//        });
//
//        // Thêm bảng vào Stage để hiển thị
        stage.addActor(table);
    }

    public void drawGameOver() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    private void resetGame() {
        table.setVisible(false); // Ẩn bảng game over
        // Thực hiện các tác vụ reset game
    }
}