package com.ict1009.wecansurvive.sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.scenes.Hud;
import com.ict1009.wecansurvive.screens.PlayScreen;
import com.ict1009.wecansurvive.sprites.Entity;
import com.ict1009.wecansurvive.sprites.Player.Player;

public abstract class Enemy extends Entity {

    // Speed
    protected Vector2 velocity;


    // Enemy Sprites
    protected Animation<TextureRegion> Walk;
    protected Animation<TextureRegion> TakeHit;
    protected Animation<TextureRegion> Death;


    // Enemy states
    protected enum State {RUNNING,TAKE_HIT,DYING}
    protected float stateTimer;
    protected State currentState;
    protected State previousState;

    //  Enemy life cycle
    protected boolean toDestroy;
    protected boolean destroyed;
    protected boolean runningRight;
    protected boolean takingHit;

    // Score
    protected Integer score;
    private Sound sound;


    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.score = 10;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(3, 0);
        toDestroy = false;
        destroyed = false;
        currentState = State.RUNNING;
        previousState = State.RUNNING;
        sound = WeCanSurvive.manager.get("audio/sound/hit.wav", Sound.class);

    }


    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-10, 13).scl(1 / WeCanSurvive.PPM);
        vertice[1] = new Vector2(10, 13).scl(1 / WeCanSurvive.PPM);
        vertice[2] = new Vector2(-10, -25).scl(1 / WeCanSurvive.PPM);
        vertice[3] = new Vector2(10, -25).scl(1 / WeCanSurvive.PPM);
        shape.set(vertice);
        fdef.filter.categoryBits = WeCanSurvive.ENEMY_BIT;
        fdef.filter.maskBits = WeCanSurvive.GROUND_BIT | WeCanSurvive.WEAPON_BIT;
        fdef.restitution = 0.5f;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        // Sensor

        fdef.filter.categoryBits = WeCanSurvive.ENEMY_SENSOR_BIT;
        fdef.filter.maskBits = WeCanSurvive.PLAYER_BIT;
        shape.set(vertice);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }




    public void draw(Batch batch) {
        if (!destroyed)
            super.draw(batch);
    }

    public void update(float dt){

        if(Death.isAnimationFinished(stateTimer)) {
            if(toDestroy && !destroyed){
                System.out.println("Destroying body");
                world.destroyBody(b2body);
                destroyed = true;
            }
        } else if (TakeHit.isAnimationFinished(stateTimer)) {
            takingHit = false;
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case DYING:
                region = Death.getKeyFrame(stateTimer);

                break;
            case TAKE_HIT:
                region = TakeHit.getKeyFrame(stateTimer);
                break;
            default:
                region = Walk.getKeyFrame(stateTimer,true);
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight ) && region.isFlipX()) {
            region.flip(true,false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        if (toDestroy) {
            b2body.setActive(false);
            return State.DYING;
        }  else if (takingHit){
            b2body.setActive(false);
            return State.TAKE_HIT;
        }   else {
            b2body.setActive(true);

            b2body.setLinearVelocity(velocity);
            return State.RUNNING;
        }
    }

    public void hitEnemy(Player player) {
        if (health <= 0) {
            player.addScore(score);
            this.toDestroy = true;
        }
        else {
            sound.play();
            health -= 20;
            takingHit = true;
        }

    }


    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
