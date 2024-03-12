package com.ict1009.wecansurvive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ict1009.wecansurvive.screens.PlayScreen;
import com.ict1009.wecansurvive.screens.TitleScreen;

public class WeCanSurvive extends Game {


	private TitleScreen TitleScreen;
	private PlayScreen playScreen;
	//private GameOver gameOverScreen;
	// Virtual screen size and Box2D Scale (PPM)

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 240;
	public static final float PPM = 100;


	// Box2D Collision bits;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short WEAPON_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short ENEMY_SENSOR_BIT = 16;



	public SpriteBatch batch;

	public static AssetManager manager;


	@Override
	public void create() {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/bgm/title.ogg", Music.class);
		manager.load("audio/bgm/adventure.ogg", Music.class);
		manager.load("audio/bgm/title.ogg", Music.class);
		manager.load("audio/sound/hit.wav", Sound.class);
		manager.load("audio/sound/jump.wav", Sound.class);
		manager.load("audio/sound/running.wav", Music.class);
		manager.load("audio/sound/swing.wav", Music.class);

		manager.finishLoading();

		setScreen(new TitleScreen(this));

	}



	@Override
	public void render() {
		super.render();
	}
}