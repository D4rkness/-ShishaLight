package com.example.miste.shirem;

import android.util.Log;

/**
 * Created by Miste on 23.10.2017.
 */

public class Model {

    private String color;
    private Mode accMode;
    private static Model instance;

    private Model(){
        color = "0x000000";
        accMode = Mode.SOLID;
    }

    public void setColor(String color) throws BluetoothException {
        this.color = color;
        Log.d("Model","Color changed to: "+color);
        BluetoothService.getInstance().sendColor();

    }

    public String getColor(){
        return color;
    }

    public void setMode(Mode mode) throws BluetoothException {
        Log.d("Model","Mode changed to: " + mode.toString());
        this.accMode = mode;
        BluetoothService.getInstance().sendMode();
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
