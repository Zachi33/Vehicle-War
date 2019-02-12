package org.VehicleWar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import org.VehicleWar.Screens.LoadingTest;
import org.VehicleWar.Screens.exScreen;

public class Main extends Game {

    public static Screen a=null;
    public static Screen b=null;
    @Override
    public void create() {
        b = new exScreen(this);
        a = new LoadingTest(this);

        setScreen(a);
    }

    @Override
    public void dispose(){}



}
