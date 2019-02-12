package org.TMH_engine.Controlls;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGdxGame;

import org.TMH_engine.Levels.Level;
import org.TMH_engine.Objects.Player.Skeleton;

/**
 * Created by Zacki on 01.05.2017.
 */

public class InputController {
    Clicker left=null;
    Clicker right=null;
    private Skeleton robak;
    String OUTPUT="NOTHING";
    String mode="DEFAULT";
    public boolean onladder=false;
    public int pos=2;
    public int old=0;
    int msg=0;


    public InputController(String setup,Screen swi, Skeleton kukla){

        if(setup=="DEFAULT") {
            left = new Clicker(0, 0, Level.toASU(Level.w/2 ), 1000, new Runnable() {
                @Override
                public void run() {

                }
            }, "Slider");
            right = new Clicker(Level.toASU(Level.w/2),0, Level.toASU(Level.w/2), 1000, new Runnable() {
                @Override
                public void run() {}
            },"Slider2");
            swi.AddClicker(left);
            swi.AddClicker(right);
        }

        left.addProg_x(new Progx(200, new Runnable() {
            @Override
            public void run() {OUTPUT="right_fast";msg=1;Level.currx.resetTODO = true;send();}
        },true));
        left.addProg_x(new Progx(50, new Runnable() {
            @Override
            public void run() {OUTPUT="right";msg=2;Level.currx.resetTODO = true;send();}
        },true));
        left.addProg_x(new Progx(0, new Runnable() {
            @Override
            public void run() {OUTPUT="stoj";msg=3;Level.currx.resetTODO = true;send();}
        },true));
        left.addProg_x(new Progx(-200, new Runnable() {
            @Override
            public void run() {OUTPUT="left_fast";msg=4;Level.currx.resetTODO = true;send();}
        },false));
        left.addProg_x(new Progx(-50, new Runnable() {
            @Override
            public void run() {OUTPUT="left";msg=5;Level.currx.resetTODO = true;send();}
        },false));
        left.addProg_x(new Progx(0, new Runnable() {
            @Override
            public void run() {OUTPUT="stoj";msg=6;Level.currx.resetTODO = true;send();}
        },false));


        right.addProg_y(new Progy(100, new Runnable() {
            @Override
            public void run() {if(onladder&&(msg==3||msg==6||msg==11||msg==12||msg==13)){Level.curry.resetTODO = true;msg=12;send();}else{Level.curry.resetTODO = true;old=pos;increase();translate();}}},true));
        right.addProg_y(new Progy(-100, new Runnable() {
            @Override
            public void run() {if(onladder&&(msg==3||msg==6||msg==11||msg==12||msg==13)){Level.curry.resetTODO = true;msg=13;send();}else{Level.curry.resetTODO = true;old=pos;decrease();translate();}}},false));

        robak=kukla;



    }


    void draw(ShapeRenderer rysuj){
        left.draw(rysuj);
        right.draw(rysuj);
    }
    void RELEASED(Clicker question){
        if(question==left){OUTPUT="stoj";msg=3;send();}
        else if(question==right){msg=11;send();}
    }
    void increase(){
        if(pos==1){pos=2;}
        else if(pos==2){pos=3;}
        else if(pos==3){pos=3;}
    }
    void decrease(){
        if(pos==1){pos=1;}
        else if(pos==2){pos=1;}
        else if(pos==3){pos=2;}
    }
    void translate(){
        if(pos==3&&old==2){OUTPUT="skok";msg=7;send();}
        else if(pos==3&&old==3){OUTPUT="**już w locie***";}
        else if(pos==2&&old==3){OUTPUT="szybszy powrót na ziemie";msg=8;send();}
        else if(pos==2&&old==1){OUTPUT="wstan";msg=9;send();}
        else if(pos==1&&old==2){OUTPUT="kucnij";msg=10;send();}
        else if(pos==1&&old==1){OUTPUT="***juz kucam**";}
    }
    private void send(){robak.receive(msg);}
}
