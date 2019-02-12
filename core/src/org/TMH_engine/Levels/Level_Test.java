package org.TMH_engine.Levels;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.TMH_engine.Controlls.Clicker;
import org.TMH_engine.Objects.JSONreader;
import org.TMH_engine.Objects.Ladder;
import org.TMH_engine.Objects.Player.Alexa;


/**
 * Created by Zacki on 15.08.2017.
 */


public class Level_Test extends Level  {

public boolean juz=false;

    public Level_Test(){}

    @Override
    void initialize() {

        img = new Texture("badlogic.jpg");
        font(Color.BLUE);                                        //font
                   //box2d word declaration


        JSONreader Space_terrein = new JSONreader("space/space.json",world);
        ST=Space_terrein.getBody(0,0,"space/space.png");

        JSONreader Space_ship = new JSONreader("space/space3FLOOR.json",world);
        SS=Space_ship.getBody(0,1000,"space/space.png");

        first = new Ladder("space/space2LAD.json","space/space.png",world,0,0 );
        LoadPlayer("Terrese/shrinked.scml");

        one = new Clicker(0, 500, 250, 250, new Runnable() {
            @Override
            public void run() {
                delta=delta+10;
            }},"Slider");
        oner = new Clicker(0, 800, 200, 200, new Runnable() {
            @Override
            public void run() {texture_or_polygon=!texture_or_polygon;}},"Slider2");
        Digitizer.AddClicker(one);
        Digitizer.AddClicker(oner);

        juz=true;

    }

    @Override
    void graphics() {
        Alexa.Petla_fizyczna();


        batch.begin();
        batch.draw(img, 0, -h/2);
        if(juz)lol.draw();
        //if(texture_or_polygon){lol.draw();}
        Alexa.pos_up_to_date();
        player.update();
        drawer.draw(player);
        font.draw(batch,"Oto info: "+Boolean.toString(receive),0,0);
        batch.end();
        one.draw(sticked);
        oner.draw(sticked);


    }

    @Override
    void physics() {
        world.step(0.3f,1,1);
        light();
    }

    @Override
    void light() {

    }

    @Override
    void longs() {
Alexa.wymiana();

    }


}
