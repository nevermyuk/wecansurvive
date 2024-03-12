package com.ict1009.wecansurvive.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.screens.PlayScreen;

public class B2WorldCreator {
    // Box2d variable

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();


        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;

        for(MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / WeCanSurvive.PPM , (rect.getY() + rect.getHeight() / 2) / WeCanSurvive.PPM) ;
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / WeCanSurvive.PPM,rect.getHeight() / 2 / WeCanSurvive.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = WeCanSurvive.GROUND_BIT;
            fdef.filter.maskBits = WeCanSurvive.ENEMY_BIT | WeCanSurvive.PLAYER_BIT;


            body.createFixture(fdef);
        }
    }
}
