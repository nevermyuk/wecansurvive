package com.ict1009.wecansurvive.sprites.Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.screens.PlayScreen;


public class Player2 extends Player {

    public Player2(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();

        // Current sprite dimensions
        this.spriteHeight = 200;
        this.spriteWidth = 200;

        // stats
        this.health = 300;

        definePlayer();

        // Idle
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("martial_hero_Idle"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Idle = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        // Run
        for (int i = 1; i < 8; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("martial_hero_Run"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Run = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        // Jump
        for (int i = 1; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("martial_hero_Jump"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }

        Jump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        // Fall
        for (int i = 1; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("martial_hero_Fall"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Fall = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        // Fall
        for (int i = 1; i < 6; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("martial_hero_Death"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Dead = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        // Attack
        for (int i = 1; i < 6; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("martial_hero_Attack1"), i * spriteWidth, 0, spriteWidth, spriteHeight));
        }
        Attack = new Animation<TextureRegion>(0.075f, frames);
        frames.clear();



        // initial value for location
        setBounds(0, 0, spriteWidth / WeCanSurvive.PPM, spriteHeight / WeCanSurvive.PPM);

    }

}
