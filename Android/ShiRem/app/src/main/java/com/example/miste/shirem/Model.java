package com.example.miste.shirem;

import android.graphics.Color;
import android.util.Log;

import java.util.Arrays;


public class Model {

    private String color;
    private Mode accMode;
    private int [] colorFieldContainer;
    private static Model instance;
    private int numberFields = 15;
    private int accDrawColor;

    private Model(){
        color = "0x000000";
        accMode = Mode.SOLID;
        accDrawColor = Color.GRAY;
        colorFieldContainer = new int[numberFields*4-2];
        Arrays.fill(colorFieldContainer,accDrawColor);

    }

    public int getAccDrawColor(){
        return accDrawColor;
    }

    public int getNumberFields(){
        return numberFields;
    }

    public void setAccDrawColor(int color){
        accDrawColor = color;
    }

    public void setColor(String color) throws BluetoothException {
        this.color = color;
        Log.d("Model","Color changed to: "+color);
        BluetoothService.getInstance().sendColor();

    }

    public void setAllColor(){
        Arrays.fill(colorFieldContainer,accDrawColor);
    }

    public void resetFieldColor(){
        Arrays.fill(colorFieldContainer,Color.BLACK);
    }

    public void setColorintoField(int position){
        colorFieldContainer[position] = accDrawColor;
    }

    public int [] getColorContainer(){
        return colorFieldContainer;
    }

    public int[] getColorFieldContainer(){
        return colorFieldContainer;
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
