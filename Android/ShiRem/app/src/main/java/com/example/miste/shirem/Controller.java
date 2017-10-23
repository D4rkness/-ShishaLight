package com.example.miste.shirem;

import android.bluetooth.BluetoothDevice;

import java.util.List;
import java.util.Set;

/**
 * Created by Miste on 23.10.2017.
 */

public class Controller {

    private  static  Controller instance;

    public void setColor(int color){Model.getInstance().setColor(color);}
    public void setMode(Mode mode){Model.getInstance().setMode(mode);}
    public Set<BluetoothDevice> searchDevices(){return BluetoothService.getInstance().searchDevices();}



    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

}
