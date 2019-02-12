package org.TMH_engine.Objects.Player;


/**
 * Created by Zacki on 03.05.2017.
 */

public abstract class Skeleton{


    public Skeleton(){}


   public void receive(int instruction_number){
        switch (instruction_number){
            case(1):{right_run();break;}//prawo szybko
            case(2):{right();break;}//prawo
            case(3):{stop();break;}//zatrzymanie sie z iscia w prawo
            case(4):{left_run();break;}//lewo szybko
            case(5):{left();break;}//lewo
            case(6):{stop();break;}//zatrzymanie sie z iscia w lewo
            case(7):{jump();break;}//skok
            case(8):{towards_ground();break;}//szybszy powrót na ziemię (?)
            case(9):{stand_up();break;}//wstanie z pozycji kucania
            case(10):{sneak();break;}//kucanie
            case(11):{ladder_stand();break;}//chwycenie drabiny
            case(12):{ladder_up();break;}//poruszanie się po drabinie do góry
            case(13):{ladder_down();break;}//poruszanie sie na drabinie na duł
        }
    }
    public abstract void right();
    public abstract void left();
    public abstract void stop();
    public abstract void jump();
    public abstract void sneak();
    public abstract void stand_up();
    public abstract void right_run();
    public abstract void left_run();
    public abstract void towards_ground();
    public abstract void ladder_stand();
    public abstract void ladder_up();
    public abstract void ladder_down();


}
