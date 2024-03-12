package com.ict1009.wecansurvive.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ict1009.wecansurvive.WeCanSurvive;

public class ScoreScreen extends ScreenAdapter {
    WeCanSurvive game;
    private Music music;
    private Texture texture;
    private TextureRegion mainBackground;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    public ScoreScreen(WeCanSurvive game) {
        this.game = game;
        // Set background
        texture = new Texture(Gdx.files.internal("mapassets/score.png"));
        mainBackground = new TextureRegion(texture, 0, 0, 480, 240);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(WeCanSurvive.V_WIDTH / WeCanSurvive.PPM, WeCanSurvive.V_HEIGHT / WeCanSurvive.PPM, gameCam);
        gamePort.apply();

        // Music
        music = WeCanSurvive.manager.get("audio/bgm/title.ogg", Music.class);
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    music.stop();
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameCam.update();
        game.batch.begin();
        game.batch.draw(mainBackground, 0, 0, WeCanSurvive.V_WIDTH * 3, WeCanSurvive.V_HEIGHT * 3);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}