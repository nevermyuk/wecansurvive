package com.ict1009.wecansurvive.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ict1009.wecansurvive.screens.PlayScreen;

public abstract class Entity extends Sprite {

    // Game logic
    protected World world;
    protected PlayScreen screen;
    protected Body b2body;


    // Sprite Dimensions
    protected int spriteHeight;
    protected int spriteWidth;

    // Stats
    protected int health;

}
