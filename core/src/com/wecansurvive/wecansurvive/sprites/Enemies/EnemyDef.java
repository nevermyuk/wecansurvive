package com.ict1009.wecansurvive.sprites.Enemies;

import com.badlogic.gdx.math.Vector2;

public class EnemyDef implements Comparable{
    public Vector2 position;
    public Class<?> type;

    public EnemyDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

