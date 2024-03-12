package com.ict1009.wecansurvive.tools;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.ict1009.wecansurvive.WeCanSurvive;
import com.ict1009.wecansurvive.screens.PlayScreen;
import com.ict1009.wecansurvive.sprites.Enemies.Enemy;
import com.ict1009.wecansurvive.sprites.Enemies.EnemyDef;
import com.ict1009.wecansurvive.sprites.Enemies.FlyEye;
import com.ict1009.wecansurvive.sprites.Enemies.Goblin;
import com.ict1009.wecansurvive.sprites.Enemies.Mushroom;
import com.ict1009.wecansurvive.sprites.Enemies.Skeleton;



public class EnemyFactory {
    private Array<Enemy> enemies_list;
    ArrayIterator<Enemy> iterator;

    public EnemyFactory() {
        enemies_list = new Array<>();
    }

    public void addEnemy(PlayScreen screen, EnemyDef enemyDef){
        Enemy enemy = null;
        if(enemyDef.type == Mushroom.class){
            enemy = new Mushroom(screen,enemyDef.position.x,enemyDef.position.y);
        }
        if(enemyDef.type == FlyEye.class){
            enemy = new FlyEye(screen,enemyDef.position.x,enemyDef.position.y);
        }
        if(enemyDef.type == Goblin.class){
            enemy = new Goblin(screen,enemyDef.position.x,enemyDef.position.y);
        }
        if(enemyDef.type == Skeleton.class){
            enemy = new Skeleton(screen,enemyDef.position.x,enemyDef.position.y);
        }
        enemies_list.add(enemy);
    }

    public void removeEnemies(){
        iterator = enemies_list.iterator();
        while(iterator.hasNext()) {
            Enemy current = iterator.next();
            if(current.isDestroyed()){
                iterator.remove();
            }
        }
    }
    public void update(float dt){
        for (Enemy enemy : getEnemies()) {
            enemy.update(dt);
        }
    }
    public void draw(WeCanSurvive game){
        for(Enemy enemy:getEnemies())
            enemy.draw(game.batch);

    }
    public Array<Enemy> getEnemies() {
        return enemies_list;
    }
}
