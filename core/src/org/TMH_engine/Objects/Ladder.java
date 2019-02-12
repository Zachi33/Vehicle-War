package org.TMH_engine.Objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Zacki on 27.08.2017.
 */

public class Ladder {
    public Body ladder;
    public boolean touching;
    public Ladder(String jsonFILEpath, String textureFILEpath, World world, float x, float y ){

        JSONreader smth = new JSONreader(jsonFILEpath,world);
        ladder=smth.getBody(x,y,textureFILEpath);
        Box2dOBJ w=(Box2dOBJ) smth.body.getUserData();
        w.draw=false;
        int i=0;
        Array<Fixture> fixs=ladder.getFixtureList();
        for(int ix=0; ix<fixs.size;ix++ ){
            Fixture f=fixs.get(ix);
            f.setSensor(true);
        }
    }
}
