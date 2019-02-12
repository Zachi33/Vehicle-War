package org.TMH_engine.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

import org.TMH_engine.Levels.Level;

import java.util.ArrayList;


/**
 * Created by Zacki on 01.04.2017.
 */

public class Box2dOBJ {



    private Texture img;
    public  Sprite sprite;
    public Body l;
    private static final Shape.Type polygon = Shape.Type.Polygon;
    float x=0;
    float y=0;
    public boolean draw=false;


    public Box2dOBJ(Body b, String filename){

        l=b;
        Level.balls.add(l);
        Level.bodycount++;
        img = new Texture(filename);
        sprite = new Sprite(img);
        //sprite.setBounds(0,0,MyGdxGame.toSSU(1000),MyGdxGame.toSSU(1000));
        sprite.setSize(Level.toSSU(1000),Level.toSSU(1000));



    }

    public void draw_batch(Batch batch){



        float x= Level.toSSU(l.getPosition().x/Level.worldRatio);
        float y= -Level.h+Level.toSSU(l.getPosition().y)/Level.worldRatio;
        sprite.setPosition(x,y);
        sprite.setOrigin(0,0);
        sprite.setRotation((float)Math.toDegrees(l.getAngle()));
        if (draw)
            sprite.draw(batch,1f);

    }
    public void draw_geometry(ShapeRenderer shapeRenderer){
        Array<Fixture> fixs=l.getFixtureList();
        for(int ix=0; ix<fixs.size;ix++ ){
            Fixture f=fixs.get(ix);
            Shape r = f.getShape();
            Shape.Type zz=r.getType();
            if(zz==Shape.Type.Polygon){

                    PolygonShape w = (PolygonShape) f.getShape();
                    float vrx[]= new float[(w.getVertexCount()*2)];
                    for(int i=0;i<w.getVertexCount();i++)
                    {
                        Transform T = l.getTransform();
                        Vector2 u = new Vector2();
                        w.getVertex(i,u);
                        T.mul(u);
                        vrx[i*2] = u.x;
                        vrx[(i*2)+1] = u.y;


                    }
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.polygon(vrx);
                    shapeRenderer.end();
                    }
            else if(zz==Shape.Type.Circle){
                    CircleShape a = (CircleShape)f.getShape();
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.circle(l.getPosition().x,l.getPosition().y,a.getRadius());
                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.line(l.getPosition().x,l.getPosition().y,l.getPosition().x+(float)(Math.cos(l.getAngle())*a.getRadius()),l.getPosition().y+(float)(Math.sin(l.getAngle())*a.getRadius()));
                    shapeRenderer.end();
                    }

            }
        }
    }

