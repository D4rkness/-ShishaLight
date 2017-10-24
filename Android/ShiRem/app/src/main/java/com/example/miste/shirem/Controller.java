package com.example.miste.shirem;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Miste on 23.10.2017.
 */

public class Controller {

    private  static  Controller instance;

    public void setColor(String color) throws BluetoothException {Model.getInstance().setColor(color);}
    public void setMode(Mode mode) throws BluetoothException {Model.getInstance().setMode(mode);}
    public ArrayList<String> searchDevices() throws BluetoothException {return BluetoothService.getInstance().searchDevices();}
    public void connect(int position) throws BluetoothException {BluetoothService.getInstance().connect(position);}


    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

}
