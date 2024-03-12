package com.ict1009.wecansurvive.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.sprites.Enemies.Enemy;
import com.ict1009.wecansurvive.sprites.Player.Player;
import com.ict1009.wecansurvive.sprites.Player.Player1;


public class WorldContactListener implements ContactListener
    {
        @Override
        public void beginContact(Contact contact) {
            Fixture fixA = contact.getFixtureA();
            Fixture fixB = contact.getFixtureB();
            int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef){
                case WeCanSurvive.WEAPON_BIT | WeCanSurvive.ENEMY_BIT:
                    if(fixA.getFilterData().categoryBits == WeCanSurvive.ENEMY_BIT) {
                            if(((Player) fixB.getUserData()).isAttacking())
                            ((Enemy)fixA.getUserData()).hitEnemy((Player)fixB.getUserData());
                    }
                    else {
                        if(((Player) fixA.getUserData()).isAttacking())
                            ((Enemy)fixB.getUserData()).hitEnemy((Player)fixA.getUserData());
                    }
                    break;
                case WeCanSurvive.ENEMY_BIT | WeCanSurvive.GROUND_BIT:
                    if(fixA.getFilterData().categoryBits == WeCanSurvive.ENEMY_BIT) {
                        ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                    }
                    else {
                        ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                    }
                    break;

                case WeCanSurvive.PLAYER_BIT | WeCanSurvive.ENEMY_SENSOR_BIT:
                    if(fixA.getFilterData().categoryBits == WeCanSurvive.ENEMY_SENSOR_BIT) {
                        ((Player)fixB.getUserData()).reduceLives();
                    }
                    else {
                        ((Player)fixA.getUserData()).reduceLives();
                    }
                    break;
            }

        }


        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }
