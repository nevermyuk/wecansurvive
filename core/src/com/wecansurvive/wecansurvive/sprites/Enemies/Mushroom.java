package com.ict1009.wecansurvive.sprites.Enemies;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.Array;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.screens.PlayScreen;



public class Mushroom extends Enemy {
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        // Current sprite dimensions
        this.spriteHeight = 150;
        this.spriteWidth = 150;
        this.health = 250;
        // Run
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Mushroom_Run"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Walk = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        // Run
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Mushroom_Take_Hit"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        TakeHit = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();

        // Death
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Mushroom_Death"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Death = new Animation<TextureRegion>(0.25f, frames);
        frames.clear();
        setBounds(getX(), getY(), this.spriteWidth / WeCanSurvive.PPM, this.spriteHeight / WeCanSurvive.PPM);

    }

}