package com.ict1009.wecansurvive.sprites.Player;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.sprites.Entity;

public abstract class Player extends Entity {
    protected int score;
    protected int lives;
    protected int kills;
    protected float stateTimer;
    protected State currentState;
    protected State previousState;
    // Sprites
    protected Animation<TextureRegion> Idle;
    protected Animation<TextureRegion> Run;
    protected Animation<TextureRegion> TakeHit;
    protected Animation<TextureRegion> Jump;
    protected Animation<TextureRegion> Fall;
    protected Animation<TextureRegion> Attack;
    protected Animation<TextureRegion> Dead;
    // Player Life cycle
    protected boolean isMovingLeft;
    protected boolean isMovingRight;
    protected boolean isAttacking;
    protected boolean runningRight;
    protected boolean isDead;
    protected boolean setToDie;
    private Music swing;
    public Player() {

        // state
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;
        isMovingLeft = false;
        isMovingRight = false;
        kills = 0;
        score = 0;
        lives = 3;
        setToDie = false;
        isDead = false;

        swing = WeCanSurvive.manager.get("audio/sound/swing.wav", Music.class);

    }

    public int getLives() {
        return lives;
    }

    public void reduceLives() {
        lives--;
        if (lives <= 0) {
            lives = 0;
            setToDie = true;
        }
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public boolean isRunningRight() {
        return runningRight;
    }

    public void setRunningRight(boolean runningRight) {
        runningRight = runningRight;
    }

    public int getScore() {
        return score;
    }

    public int addScore(int value) {
        kills++;
        return score += value;
    }

    public Body getB2body() {
        return b2body;
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        move();
        switch (currentState) {
            case JUMPING:
                region = Jump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = Run.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = Fall.getKeyFrame(stateTimer, true);
                break;
            case DYING:
                region = Dead.getKeyFrame(stateTimer);
                break;
            case ATTACKING:
                region = Attack.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = Idle.getKeyFrame(stateTimer, true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        if (isDead) {
            b2body.setActive(false);
            return State.DYING;
        } else if (b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else if (isAttacking) {
            if (!swing.isPlaying()) {
                swing.play();
            }
            return State.ATTACKING;
        } else if (setToDie) {
                if (Dead.isAnimationFinished(stateTimer)) {
                    isDead = true;
                }
                return State.DYING;
        } else {
            return State.STANDING;
        }
    }

    public void move() {
        if (isMovingRight && !setToDie) {
            b2body.applyLinearImpulse(new Vector2(0.1f, 0), b2body.getWorldCenter(), true);
        } else if (isMovingLeft && !setToDie) {
            b2body.applyLinearImpulse(new Vector2(-0.1f, 0), b2body.getWorldCenter(), true);
        }
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / WeCanSurvive.PPM, 64 / WeCanSurvive.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape playerShape = new PolygonShape();
        Vector2[] playerVertice = new Vector2[4];
        playerVertice[0] = new Vector2(12, 22).scl(1 / WeCanSurvive.PPM);
        playerVertice[1] = new Vector2(-10, 22).scl(1 / WeCanSurvive.PPM);
        playerVertice[2] = new Vector2(12, -20).scl(1 / WeCanSurvive.PPM);
        playerVertice[3] = new Vector2(-10, -20).scl(1 / WeCanSurvive.PPM);
        playerShape.set(playerVertice);
        fdef.filter.categoryBits = WeCanSurvive.PLAYER_BIT;
        fdef.filter.maskBits = WeCanSurvive.GROUND_BIT | WeCanSurvive.ENEMY_BIT | WeCanSurvive.ENEMY_SENSOR_BIT;
        fdef.shape = playerShape;
        b2body.createFixture(fdef).setUserData(this);


        PolygonShape weapon = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(30, 13).scl(1 / WeCanSurvive.PPM);
        vertice[1] = new Vector2(-30, 13).scl(1 / WeCanSurvive.PPM);
        vertice[2] = new Vector2(30, -20).scl(1 / WeCanSurvive.PPM);
        vertice[3] = new Vector2(-30, -20).scl(1 / WeCanSurvive.PPM);
        weapon.set(vertice);
        fdef.shape = weapon;
        fdef.isSensor = true;
        fdef.filter.categoryBits = WeCanSurvive.WEAPON_BIT;
        fdef.filter.maskBits = WeCanSurvive.ENEMY_BIT;
        System.out.println(this);
        b2body.createFixture(fdef).setUserData(this);
        weapon.dispose();

    }

    public boolean getIsJumping() {
        return currentState == State.FALLING || currentState == State.JUMPING;
    }

    public boolean isAttacking() {
        return currentState == State.ATTACKING;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean getIsDead() {
        return isDead;
    }
    public boolean getSetToDie() {
        return setToDie;
    }



    public int getkills(){
        return kills;
    }

    // States
    private enum State {STANDING, TAKINGHIT, RUNNING, JUMPING, FALLING, ATTACKING, DYING}


}
