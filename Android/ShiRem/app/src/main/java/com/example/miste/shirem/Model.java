package com.example.miste.shirem;

import android.util.Log;

/**
 * Created by Miste on 23.10.2017.
 */

public class Model {

    private int color;
    private Mode accMode;
    private static Model instance;

    private Model(){
        color = 0;
        accMode = Mode.SOLID;
    }

    public void setColor(int color){
        this.color = color;
        Log.d("Model","Color changed to: "+color);
    }

    public int getColor(){
        return color;
    }

    public void setMode(Mode mode){
        Log.d("Model","Mode changed to: " + mode.toString());
        this.accMode = mode;
    }

    public Mode getAccMode(){
        return accMode;
    }

    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }
}
