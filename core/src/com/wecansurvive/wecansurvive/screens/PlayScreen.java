package com.ict1009.wecansurvive.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.scenes.Hud;
import com.ict1009.wecansurvive.sprites.Enemies.Enemy;
import com.ict1009.wecansurvive.sprites.Enemies.EnemyDef;
import com.ict1009.wecansurvive.sprites.Enemies.FlyEye;
import com.ict1009.wecansurvive.sprites.Enemies.Goblin;
import com.ict1009.wecansurvive.sprites.Enemies.Mushroom;
import com.ict1009.wecansurvive.sprites.Enemies.Skeleton;
import com.ict1009.wecansurvive.sprites.Player.Player1;
import com.ict1009.wecansurvive.sprites.Player.Player2;
import com.ict1009.wecansurvive.tools.B2WorldCreator;
import com.ict1009.wecansurvive.tools.EnemyFactory;
import com.ict1009.wecansurvive.tools.WorldContactListener;

import java.util.PriorityQueue;

import javax.xml.soap.Text;


public class PlayScreen implements Screen, InputProcessor {
    // Reference to game, to set screen.
    private WeCanSurvive game;


    // default playscreen variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    // Map variable
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // create world
    private World world;
    //  debug line
    private Box2DDebugRenderer b2dr;

    // sprites
    private Player1 player1;
    private Player2 player2;
    private TextureAtlas atlas;


    // private Array<Enemy> enemies;
    private PriorityQueue<EnemyDef> enemiesToSpawn;

    private B2WorldCreator creator;
    private EnemyFactory enemyFactory;

    // music

    private Music music;

    public PlayScreen(WeCanSurvive game) {
        atlas = new TextureAtlas("assets.atlas");
        this.game = game;

        // Camera
        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(WeCanSurvive.V_WIDTH / WeCanSurvive.PPM, WeCanSurvive.V_HEIGHT / WeCanSurvive.PPM, gameCam);



        // load map and setup map render
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapassets/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / WeCanSurvive.PPM);

        // setup game camera
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // create box 2d world, setting no gravity in X and -10 gravity in Y, allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        // show debug lines
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);
        // create player in world
        player1 = new Player1(this);
        player2 = new Player2(this);

        // create HUD
        hud = new Hud(game.batch);
        world.setContactListener(new WorldContactListener());

        // Add music

        music = WeCanSurvive.manager.get("audio/bgm/adventure.ogg", Music.class);
        music.setLooping(true);
        music.play();


        //

        // Spawn enemy
        enemyFactory = new EnemyFactory();
        enemiesToSpawn = new PriorityQueue<EnemyDef>();



    }

    public void handleSpawnEnemies() {
        if (!enemiesToSpawn.isEmpty()) {
            EnemyDef edef = enemiesToSpawn.poll();
            enemyFactory.addEnemy(this, edef);
        }
    }

    public void spawnEnemy(EnemyDef edef) {
        enemiesToSpawn.add(edef);
    }

    public void handleRemoveEnemies() {
        enemyFactory.removeEnemies();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }


    public void update(float dt) {
        handleSpawnEnemies();
        handleRemoveEnemies();
        world.step(1 / 60f, 6, 2);
        // update player
        player1.update(dt);
        player2.update(dt);
        enemyFactory.update(dt);

        hud.update(dt,player1,player2);
        // update gamecam with coordinate
        gameCam.update();
        // tell renderer to draw only what camera sees
        renderer.setView(gameCam);
        if (player1.getIsDead() && player2.getIsDead()){
            System.out.println("CHANGE CSCREEN");
            game.setScreen(new ScoreScreen(game));
        }
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        update(delta);
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // render game map
        renderer.render();
        // render debug lines
        b2dr.render(world, gameCam.combined);

        // render player
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player1.draw(game.batch);
        player2.draw(game.batch);
        enemyFactory.draw(game);


        // render mob
        game.batch.end();
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
        hud.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {


        if (keycode == Keys.T) {
           spawnEnemy(new EnemyDef(new Vector2(player1.getB2body().getPosition().x , player1.getB2body().getPosition().y), FlyEye.class));
        }
        if (keycode == Keys.Y) { spawnEnemy(new EnemyDef(new Vector2(player1.getB2body().getPosition().x, player1.getB2body().getPosition().y), Mushroom.class));
        }
        if (keycode == Keys.U) {
          spawnEnemy(new EnemyDef(new Vector2(player1.getB2body().getPosition().x, player1.getB2body().getPosition().y), Goblin.class));
        }
        if (keycode == Keys.I) {
            spawnEnemy(new EnemyDef(new Vector2(player1.getB2body().getPosition().x, player1.getB2body().getPosition().y), Skeleton.class));
        }

        // Player 1
        if (keycode == Keys.W) {
            if (!player1.getIsJumping() && !player1.getSetToDie()) {
                player1.getB2body().applyLinearImpulse(new Vector2(0, 5f), player1.getB2body().getWorldCenter(), true);
            }
        }
        if (keycode == Keys.A) {
            player1.setMovingLeft(true);
        }
        if (keycode == Keys.D) {
            player1.setMovingRight(true);
        }
        if (keycode == Keys.G) {
            player1.setAttacking(true);
        }

        // Player 2
        if (keycode == Keys.UP) {
            if (!player2.getIsJumping() && !player2.getSetToDie()) {
                player2.getB2body().applyLinearImpulse(new Vector2(0, 5f), player1.getB2body().getWorldCenter(), true);
            }
        }
        if (keycode == Keys.LEFT) {
            player2.setMovingLeft(true);
        }
        if (keycode == Keys.RIGHT) {
            player2.setMovingRight(true);
        }
        if (keycode == Keys.L) {
            player2.setAttacking(true);
        }


        return true;

    }

    @Override
    public boolean keyUp(int keycode) {
        // Player 1
        if (keycode == Keys.G) {
            player1.setAttacking(false);
        }
        if (keycode == Keys.A) {
            player1.setMovingLeft(false);
        }
        if (keycode == Keys.D) {
            player1.setMovingRight(false);
        }
        // Player 2
        if (keycode == Keys.L) {
            player2.setAttacking(false);
        }
        if (keycode == Keys.LEFT) {
            player2.setMovingLeft(false);
        }
        if (keycode == Keys.RIGHT) {
            player2.setMovingRight(false);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}

