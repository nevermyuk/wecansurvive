package com.ict1009.wecansurvive.sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.scenes.Hud;
import com.ict1009.wecansurvive.screens.PlayScreen;

public class Goblin extends Enemy {
    public Goblin(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        // Current sprite dimensions
        this.spriteHeight = 150;
        this.spriteWidth = 150;
        // Enemy stats

        this.health = 200;
        // Run
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Goblin_Run"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Walk = new Animation<>(0.1f, frames);
        frames.clear();
        // Run
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Goblin_Take_Hit"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        TakeHit = new Animation<>(0.1f, frames);
        frames.clear();
        // Death
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Goblin_Death"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Death = new Animation<>(0.25f, frames);
        frames.clear();
        setBounds(getX(), getY(), spriteWidth / WeCanSurvive.PPM, spriteHeight/ WeCanSurvive.PPM);
    }
}
