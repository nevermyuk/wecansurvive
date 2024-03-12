package com.ict1009.wecansurvive.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ict1009.wecansurvive.sprites.Player.Player;
import com.ict1009.wecansurvive.sprites.Player.Player1;
import com.ict1009.wecansurvive.sprites.Player.Player2;


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private float timeCount;
    private Integer levelTimer;
    private Integer level;
    private  Integer player1Score;
    private  Integer player2Score;
    private  Integer player1Lives;
    private  Integer player2Lives;


    // Font
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;

    private Label timerLabel;
    private Label timeLabel;
    private Label currentLevelLabel;
    private Label player1LivesLabel;
    private Label player1LivesNumberLabel;
    private Label player2LivesLabel;
    private Label player2LivesNumberLabel;

    private Label levelLabel;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private Label  player1ScoreNumberLabel;
    private Label  player2ScoreNumberLabel;

    public Hud(SpriteBatch sb) {



        // Font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("monogram.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 64;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        // HUD
        levelTimer = 0 ;
        level = 1;
        viewport = new ScreenViewport();
        stage = new Stage(viewport,sb);
        player1Lives = 3;
        player2Lives = 3;
        player1Score = 0;
        player2Score = 0;

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        timerLabel = new Label(String.format("%03d",levelTimer), new Label.LabelStyle(font, Color.WHITE));
        currentLevelLabel = new Label(String.format("%1d",level), new Label.LabelStyle(font, Color.WHITE));

        player1LivesLabel = new Label("P1 LIVES", new Label.LabelStyle(font, Color.WHITE));
        player2LivesLabel = new Label("P2 LIVES", new Label.LabelStyle(font, Color.WHITE));
        player1LivesNumberLabel= new Label(String.format("%1d",player1Lives), new Label.LabelStyle(font, Color.WHITE));
        player2LivesNumberLabel = new Label(String.format("%1d",player2Lives), new Label.LabelStyle(font, Color.WHITE));
        player1ScoreLabel = new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
        player2ScoreLabel = new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
        player1ScoreNumberLabel= new Label(String.format("%06d",player1Score), new Label.LabelStyle(font, Color.WHITE));
        player2ScoreNumberLabel= new Label(String.format("%06d",player2Score), new Label.LabelStyle(font, Color.WHITE));


        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("LEVEL", new Label.LabelStyle(font, Color.WHITE));


        table.add(player1LivesLabel).expandX().padTop(25);
        table.add(levelLabel).expandX().padTop(25);
        table.add(player2LivesLabel).expandX().padTop(25);
        table.row();
        table.add(player1LivesNumberLabel).expandX();
        table.add(currentLevelLabel).expandX();
        table.add(player2LivesNumberLabel).expandX();
        table.row();
        table.add(player1ScoreLabel).expandX();
        table.add(timeLabel).expandX();
        table.add(player2ScoreLabel).expandX();
        table.row();
        table.add(player1ScoreNumberLabel).expandX();
        table.add(timerLabel).expandX();
        table.add(player2ScoreNumberLabel).expandX();

        stage.addActor(table);


    }

    public void update(float dt,Player player1,Player player2){
        player1LivesNumberLabel.setText(String.format("%1d",player1.getLives()));
        player2LivesNumberLabel.setText(String.format("%1d", player2.getLives()));
        player1ScoreNumberLabel.setText(String.format("%06d",player1.getScore()));
        player2ScoreNumberLabel.setText(String.format("%06d", player2.getScore()));

        timeCount += dt;
        if (timeCount>= 1){
            levelTimer++;
            timerLabel.setText(String.format("%03d",levelTimer));
            timeCount -= 1;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
