package org.TMH_engine.Objects.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.IKObject;
import com.brashmonkey.spriter.IKResolver;
import com.brashmonkey.spriter.Player;
import com.badlogic.gdx.physics.box2d.Body;
import org.TMH_engine.Collisions.CollisionBOX;
import org.TMH_engine.Collisions.Collision_checker;
import org.TMH_engine.Controlls.InputController;
import org.TMH_engine.Levels.Level;
import org.TMH_engine.Objects.Box2dOBJ;

import static org.TMH_engine.Levels.Level.world;
import static org.TMH_engine.Levels.Level.worldRatio;

/**
 * Created by Zacki on 03.05.2017.
 */

public class Alexa extends Skeleton {


    static Player animacja=null;
    public static Body kulka =null;
    public static Body Boundry =null;
    public static Body floor =null;
    float x;
    float y;
    static int polecenie=0;
    public static InputController xxx=null;
    private ContactListener listen;
    static boolean jumpable=false;
    static boolean jumpOnce=false;
    static boolean stop_once=true;
    static boolean strona=true;
    static boolean aktualna_strona=true;
    static boolean delayed=false;
    static boolean odwrocLEWO=false;//bo na poczatku w prawo
    static boolean impulse=false;
    static boolean onLadder;
    static boolean LAD=false;
    static boolean box=false;
    static int touch=0;
    static int kerunek=3;//1-lewo fast,2-lewo,3-stoj,4-prawo,5-praawo fast
    static int postrunek=3;
    static int initial_speed=0;
    static int laddertouch=0;
    static int boxtouch=0;
    public static boolean zobaczmy=false;
    public static float xsprit=0;
    public static float ysprit=0;
    static IKResolver resolver=null;
    static IKObject ikObject=null;
    public static String OUTPUT="NYC";
    public static Collision_checker kul=null;
    public static Collision_checker boxer=null;
    public static Collision_checker ladd=null;
    public static Collision_checker floorek=null;
    public static CollisionBOX bob=null;
    public static float CastRAY=0;
    public static float CastRAY2=0;
    static boolean switcher=true;
    public static float angle=0;
    public static float y1=0;
    public static float y2=0;




    public Alexa( Player player){


        animacja=player;
        initial_speed=animacja.speed;



        {
            BodyDef g = new BodyDef();
            g.position.set(500*worldRatio, 500*worldRatio);
            kulka = world.createBody(g);
            FixtureDef b = new FixtureDef();
            CircleShape x = new CircleShape();
            x.setRadius(20*worldRatio);
            b.shape = x;
            b.density=3f;
            b.friction=6f;
            b.restitution=0f;
            kulka.createFixture(b);
            //A.setFixedRotation(true);
            kulka.setType(BodyDef.BodyType.DynamicBody);
            Box2dOBJ gg = new Box2dOBJ(kulka,"badlogic.jpg");
            kulka.setUserData(gg);

        }

        {
            Body B = null;
            BodyDef g = new BodyDef();
            g.position.set(500*worldRatio, 500*worldRatio);
            B = world.createBody(g);
            FixtureDef b = new FixtureDef();
            CircleShape x = new CircleShape();
            x.setRadius(30*worldRatio);
            b.shape = x;
            b.density=0.01f;
            b.friction=6f;
            b.restitution=0f;
            b.isSensor=true;
            B.createFixture(b);
            B.setFixedRotation(true);
            B.setType(BodyDef.BodyType.DynamicBody);
            Box2dOBJ gg = new Box2dOBJ(B,"badlogic.jpg");
            B.setUserData(gg);

            floorek=new Collision_checker(B,Level.ST,true);//kulka z czynkolwiek

            RevoluteJointDef bb = new RevoluteJointDef();
            bb.initialize(kulka,B,new Vector2(500*worldRatio, 500*worldRatio));
            world.createJoint(bb);
        }

        {
            BodyDef g = new BodyDef();
            g.position.set(500*worldRatio, 600*worldRatio);
            Boundry = world.createBody(g);
            FixtureDef b = new FixtureDef();
            PolygonShape x = new PolygonShape();
            Boundry.setFixedRotation(true);
            x.setAsBox(5*worldRatio, 60*worldRatio);
            b.shape = x;
            b.density=0f;
            b.friction=0f;
            b.restitution=0;
            Boundry.createFixture(b).setSensor(true);
            Box2dOBJ ramka = new Box2dOBJ(Boundry,"badlogic.jpg");
            Boundry.setType(BodyDef.BodyType.DynamicBody);
            Boundry.setUserData(ramka);


            RevoluteJointDef bb = new RevoluteJointDef();
            bb.initialize(kulka,Boundry,new Vector2(500*worldRatio, 500*worldRatio));
            world.createJoint(bb);

        }
        kul=new Collision_checker(kulka,Level.SS,false);//kulka z platfo
        boxer=new Collision_checker(Level.SS,Boundry,false);//protokat z platfo
        ladd=new Collision_checker(Boundry,Level.first.ladder);//prostokat z drabin

        //floorek.excludeSensors(true);
        //floorek.exclude(Level.first.ladder);
        bob=new CollisionBOX();
        bob.add(floorek);
        bob.add(boxer);
        bob.add(kul);
        bob.add(ladd);


//        animacja.setBone("leftUpperLeg",a.position.x,a.position.y+MyGdxGame.toSSU(z));
//
//        resolver = new CCDResolver(animacja);
//        ikObject = new IKObject(0, 0, 2, 5);
//        resolver.mapIKObject(ikObject,animacja.getBone("rightLowerLeg"));



    }
    public static void giveinput(InputController lol){xxx=lol;}
    public static void pos_up_to_date() {
        float x = Boundry.getPosition().x / worldRatio;
        float y = Boundry.getPosition().y / worldRatio;
        animacja.setPosition(Level.toSSU(x), Level.toSSU(y - 88) - Level.h);

//        CastRAY2 = MyGdxGame.toASU(animacja.getBone("bone_016").position.y + MyGdxGame.h) * MyGdxGame.worldRatio;
//        if (animacja.getBone("bone_016").position.y < MyGdxGame.toSSU((CastRAY + 5 / 2) / MyGdxGame.worldRatio) - MyGdxGame.h){
//            animacja.setBone("bone_016", animacja.getBone("bone_016").position.x, MyGdxGame.toSSU((CastRAY + 2) / MyGdxGame.worldRatio) - MyGdxGame.h);
//    }

    }
    public static void wymiana(){
        Level.receive=floorek.result;
    }

    public static void Petla_fizyczna(){

//strefa wymiany informacji
        jumpable=floorek.result;
        box=(!boxer.result);
        if(jumpable&&xxx.pos==3){xxx.pos=2;}
        xxx.onladder=onLadder;

        Array<Fixture> fixs=Level.SS.getFixtureList();
        for(int ix=0; ix<fixs.size;ix++ ){
            Fixture f=fixs.get(ix);
            if(!LAD&&box||LAD)
                f.setSensor(LAD);
        }


        if(ladd.result){Level.first.touching=true;onLadder=true;}
        else{Level.first.touching=false;onLadder=false;}


//
//        RayCastCallback b = new RayCastCallback() {
//            @Override
//            public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
//
//                y2=point.y;
//                return fraction;
//            }
//        };
//
//
//        world.raycast(b,new Vec2(kulka.getPosition().x+1.8f,kulka.getPosition().y),new Vec2(kulka.getPosition().x+7,kulka.getPosition().y-100));
//
//        RayCastCallback bb = new RayCastCallback() {
//            @Override
//            public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
//
//                y1=point.y;
//                return fraction;
//            }
//        };
//
//        world.raycast(bb,new Vec2(kulka.getPosition().x-1.8f,kulka.getPosition().y),new Vec2(kulka.getPosition().x-7,kulka.getPosition().y-100));

        angle=(float)Math.atan2(y2-y1,3.6f);




        switch (polecenie){
            case(0):{break;}//nic
            case(3):{

                if(stop_once)

                {


                    if(jumpable) {
                        kulka.setLinearVelocity(new Vector2(0, 0));
                        kulka.setAngularDamping(10f);
                        Boundry.setLinearVelocity(new Vector2(0, 0));
                        stop_once = false;
                        animacja.setAnimation(0);
                        animacja.speed=initial_speed;
                        kulka.setType(BodyDef.BodyType.StaticBody);
                        break;

                    }
                    else if(!jumpable) {


                        kulka.setAngularDamping(10f);
                        animacja.setAnimation(0);
                        animacja.speed=initial_speed;
                        stop_once = false;

                        break;

                    }
                }

                break;}//zatrzymaj

            case(1):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpable) {animacja.setAnimation(1);animacja.speed=initial_speed*14/10;kulka.setLinearVelocity(new Vector2(1,0));}
            else if(impulse)  {impulse=false;kulka.setAngularDamping(0.1f);kulka.applyLinearImpulse(new Vector2(50f,0),kulka.getPosition(),true);}break;}//prawo
            case(2):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpable) {animacja.setAnimation(1);animacja.speed=initial_speed*14/10;kulka.setAngularDamping(0.1f);kulka.setLinearVelocity(new Vector2(-2,-1));}
            else if(impulse){impulse=false;kulka.setAngularDamping(0.1f);kulka.applyLinearImpulse(new Vector2(-50f,0),kulka.getPosition(),true);}break;}//lewo

            case(4):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpable) {animacja.setAnimation(2);animacja.speed=initial_speed*3/2;kulka.setAngularDamping(0.1f);kulka.setLinearVelocity(new Vector2(4,-2));}
            else if(impulse){impulse=false;kulka.setAngularDamping(0.1f);kulka.applyLinearImpulse(new Vector2(75f,0),kulka.getPosition(),true);}break;}//prawo szybko
            case(5):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpable) {animacja.setAnimation(2);animacja.speed=initial_speed*3/2;kulka.setAngularDamping(0.1f);kulka.setLinearVelocity(new Vector2(-4,-2));}
            else if(impulse){impulse=false;kulka.setAngularDamping(0.1f);kulka.applyLinearImpulse(new Vector2(-75f,0),kulka.getPosition(),true);}break;}//lewo szybko
            case(6):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpOnce){kulka.setLinearVelocity(new Vector2(-2,3));jumpOnce=false;}break;}//skok w lewo_slow
            case(7):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpOnce){kulka.setLinearVelocity(new Vector2(2,3));jumpOnce=false;}break;}//skok w prawo_slow
            case(8):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpOnce){kulka.setLinearVelocity(new Vector2(0,3));jumpOnce=false;}break;}//skok
            case(9):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpOnce){kulka.setLinearVelocity(new Vector2(-4,4));jumpOnce=false;}break;}//skok w lewo_fast
            case(10):{kulka.setType(BodyDef.BodyType.DynamicBody);if(jumpOnce){kulka.setLinearVelocity(new Vector2(4,4));jumpOnce=false;}break;}//skok w prawo_fast
            case(11):{if(onLadder){kulka.setType(BodyDef.BodyType.DynamicBody);kulka.setLinearVelocity(new Vector2(0,1));}break;}//drabina do gory
            case(12):{if(onLadder){kulka.setType(BodyDef.BodyType.DynamicBody);kulka.setLinearVelocity(new Vector2(0,-1));}break;}//drabina na dul
            case(13):{if(onLadder)kulka.setType(BodyDef.BodyType.StaticBody);break;}//drabina zatrzym sie
            case(14):{
                if(kulka.getType()== BodyDef.BodyType.DynamicBody) {
                    switch (kerunek) {
                        case (1): {
                            if (jumpable) {
                                polecenie = 9;
                                jumpOnce = true;
                            }
                            break;
                        }
                        case (2): {
                            if (jumpable) {
                                polecenie = 6;
                                jumpOnce = true;
                            }
                            break;
                        }
                        case (3): {
                            if (jumpable) {
                                polecenie = 8;
                                jumpOnce = true;
                            }
                            break;
                        }
                        case (4): {
                            if (jumpable) {
                                polecenie = 7;
                                jumpOnce = true;
                            }
                            break;
                        }
                        case (5): {
                            if (jumpable) {
                                polecenie = 10;
                                jumpOnce = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void right() {


        LAD=false;
        polecenie = 1;
        kerunek = 4;
        stop_once = true;
        impulse=true;
        strona=true;
        if(strona!=aktualna_strona){
            animacja.flipX();
            aktualna_strona=!aktualna_strona;
        }

    }

    @Override
    public void left() {

        LAD=false;
        polecenie = 2;
        kerunek = 2;
        stop_once = true;
        impulse=true;
        strona=false;
        if(strona!=aktualna_strona){
            animacja.flipX();
            aktualna_strona=!aktualna_strona;
        }

    }

    @Override
    public void stop() {
        LAD=false;
        postrunek=polecenie;
        polecenie = 3;
        kerunek = 3;



    }

    @Override
    public void jump() {
        LAD=false;
        kulka.setType(BodyDef.BodyType.DynamicBody);

        polecenie=14;

    }

    @Override
    public void sneak() {
        LAD=false;

    }

    @Override
    public void stand_up() {
        LAD=false;


    }


    @Override
    public void right_run() {

        LAD=false;
        polecenie = 4;
        kerunek = 5;
        stop_once = true;
        impulse=true;
        strona=true;
        if(strona!=aktualna_strona){
            animacja.flipX();
            aktualna_strona=!aktualna_strona;
        }

    }


    @Override
    public void left_run() {


        LAD=false;
        polecenie = 5;
        kerunek = 1;
        stop_once = true;
        impulse=true;
        strona=false;
        if(strona!=aktualna_strona){
            animacja.flipX();
            aktualna_strona=!aktualna_strona;
        }

    }

    @Override
    public void towards_ground() {LAD=false;

    }

    @Override
    public void ladder_stand() {if(LAD) {polecenie=13;}

    }

    @Override
    public void ladder_up() {polecenie=11;LAD=true;

    }

    @Override
    public void ladder_down() {polecenie = 12;LAD=true;

    }


}

