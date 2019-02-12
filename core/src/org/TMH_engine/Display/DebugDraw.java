package org.TMH_engine.Display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.TMH_engine.Levels.Level;
import org.TMH_engine.Objects.Box2dOBJ;

import java.util.ArrayList;

/**
 * Created by Zacki on 27.08.2017.
 */

public class DebugDraw {
    ShapeRenderer shapeRenderer;
    World world;
    Box2dOBJ a=null;
    Batch batch;

    public  DebugDraw(ShapeRenderer render, World swiat,Batch batchx){
        shapeRenderer=render;
        world=swiat;
        batch=batchx;
    }

    public void draw() {
        Array<Body> balls = new Array<Body>(world.getBodyCount());
        world.getBodies(balls);

        for(int i =0; i<balls.size; i++){
            Body x = balls.get(i);

            if(Level.texture_or_polygon) {

                a = (Box2dOBJ) x.getUserData();
                a.draw_batch(batch);

            }else{
                a = (Box2dOBJ) x.getUserData();
                a.draw_geometry(shapeRenderer);
            }

            // }
        }

    }

}
