package org.TMH_engine.Levels;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.LibGdx.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdx.LibGdxLoader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;

import org.TMH_engine.Controlls.Clicker;
import org.TMH_engine.Controlls.InputController;
import org.TMH_engine.Controlls.Progx;
import org.TMH_engine.Controlls.Progy;
import org.TMH_engine.Controlls.Screen;
import org.TMH_engine.Controlls.TouchMemory;
import org.TMH_engine.Display.DebugDraw;
import org.TMH_engine.Objects.Ladder;
import org.TMH_engine.Objects.Player.Alexa;
import org.TMH_engine.Objects.Player.Skeleton;

import java.util.ArrayList;


/**
 * Created by Zacki on 14.08.2017.
 */

public abstract class Level implements ApplicationListener, InputProcessor {

   float time1=1f/30f;
    float time2=1f/5f;
    public static int h=0;
    public static int w=0;
    public static float toSSU(float ASU){return ASU/1000*h;}
    public static float toASU(float SSU){return SSU/h*1000;}
    public static float worldRatio=0.1f;
    public static float delta=0;
    public static boolean texture_or_polygon=true;
    public static World world=null;//
    public static Progy curry=null;//
    public static Progx currx=null;//
    public static InputController input = null;
    public static TouchMemory trier = null;
    public static TouchMemory norm = null;
    public static Ladder first=null;
    public static Body SS=null;
    public static Body ST=null;
    public static Skeleton gracz =null;
    public static Screen Digitizer=null;
    public static DebugDraw lol=null;
    ShapeRenderer renderer=null;
    ShapeRenderer sticked=null;
    public static SpriteBatch batch=null;
    public static SpriteBatch sticking=null;
    public static OrthographicCamera cam=null;
    public static boolean receive = false;
    public Texture img;
    public BitmapFont font;
    static Data data=null;
    static Player player;
    static Drawer<Sprite> drawer;
    LibGdxLoader loader;
    public static Clicker one=null;
    public static Clicker oner=null;



    abstract void initialize();
    abstract void graphics();
    abstract void physics();
    abstract void light();
    abstract void longs();
    public static ArrayList<Body> balls = new ArrayList<Body>(0);
    public static int bodycount = 0;
    @Override
    public void create() {
        h=Gdx.graphics.getHeight();
        w=Gdx.graphics.getWidth();
        cam = new OrthographicCamera();
        cam.zoom = 1f;
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ShapeRenderer();
        sticked=new ShapeRenderer();
        batch = new SpriteBatch();
        sticking = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        Digitizer=new Screen();
        world = new World( new Vector2(0,-1),false);
        lol= new DebugDraw(sticked,world,batch);
        initialize();}
    @Override
    public void render() {
        float time = Gdx.graphics.getDeltaTime();
        cam.position.set(w/2+delta, -h/2, 0f);
        renderer.setProjectionMatrix(cam.combined);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        graphics();
        time1-=time;
        time2-=time;
        if(time1<=0){physics();time1+=1f/30f;}
        if(time2<=0){longs();time2+=1f/5f;}
    }


    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}
    @Override
    public void resize(int width, int height) {}

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Digitizer.T_down(toASU(screenX),toASU(screenY),pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Digitizer.T_up(toASU(screenX),toASU(screenY),pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Digitizer.T_dragged(toASU(screenX),toASU(screenY),pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void font(Color clr){

        font = new BitmapFont();
        font.setColor(clr);
    }
    public void LoadPlayer(String pathfile){

        FileHandle handle = Gdx.files.internal(pathfile);
        data = new SCMLReader(handle.read()).getData();
        loader = new LibGdxLoader(data);
        loader.load(handle.file());
        drawer = new LibGdxDrawer(loader, batch, renderer);
        player = new Player(data.getEntity(0));
        player.setAnimation(0);
        player.scale(toSSU(0.19f));
        gracz = new Alexa(player);
        input = new InputController("DEFAULT",Digitizer,gracz);
        Alexa.giveinput(input);
        gracz.receive(3);


    }




}
