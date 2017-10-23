package com.example.miste.shirem;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.List;
import java.util.Set;

/**
 * Created by Miste on 23.10.2017.
 */

public class BluetoothService {

    private static BluetoothService instance;
    private boolean isConnected;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice connectedDevice;

    private BluetoothService(){
        isConnected = false;
        mBluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();

    }

    public void connect(){
        Log.d("BluetoothService", "Starting Connection to Bluetoothdevice");
    }

    public void disconnect(){
        Log.d("BluetoothService", "Disconnect from Bluetoothdevice");
    }

    public Set<BluetoothDevice> searchDevices(){
        return mBluetoothAdapter.getBondedDevices();
    }

    public void sendMode(){
        Log.d("BluetoothService", "Sending Mode");
    }

    public void sendColor(){
        Log.d("BluetoothService", "Sending Mode");
    }

    /* Not implemented yet
        Send Painting
        public void sendPainting(){
        }
     */

    public boolean getIsConnected(){
        return isConnected;
    }

    public static BluetoothService getInstance(){
        if(instance == null){
            instance = new BluetoothService();
        }
        return instance;
    }

}
