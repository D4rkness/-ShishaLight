package com.example.miste.shirem;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.UUID;
import java.util.concurrent.Semaphore;


public class BluetoothService extends Observable{

    private static BluetoothService instance;
    private boolean isConnected;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<String> boundDevicesArrayList;
    private BluetoothDevice connectedDevice;
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket socket ;
    private boolean connectionRunningLocked = false;


    private BluetoothService(){
        isConnected = false;
        boundDevicesArrayList = new ArrayList<String>();
        mBluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();

    }

    public void connect(int position) throws BluetoothException {
        Log.d("BluetoothService", "Starting Connection to Bluetoothdevice");
        //mBluetoothAdapter.getBondedDevices()
        String searchedName = boundDevicesArrayList.get(position);
        for(BluetoothDevice bt :mBluetoothAdapter.getBondedDevices()){
            if(bt.getName().equals(searchedName)){
                Log.d("BluetoothHandler","Connecting to: "+bt.getAddress());
                if(connectionRunningLocked){
                    throw new BluetoothException(BluetoothError.SEARCH_RUNNING);
                }
                new ConnectionThread(bt).start();
                break;
            }
        }


    }

    public void disconnect(){
        Log.d("BluetoothService", "Disconnect from Bluetoothdevice");
    }

    public ArrayList searchDevices() throws BluetoothException {
        if(!mBluetoothAdapter.isEnabled()){
            throw new BluetoothException(BluetoothError.BLUETOOTH_OFF);
        }
        if(!boundDevicesArrayList.isEmpty()){
            boundDevicesArrayList.clear();
        }
        for(BluetoothDevice bt: mBluetoothAdapter.getBondedDevices()){
            boundDevicesArrayList.add(bt.getName());}

        return boundDevicesArrayList;
    }

    public void sendMode() throws BluetoothException {
        /*
        if(!isConnected){
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        }
        */
        int convertedModeToInt = -1;
        switch(Model.getInstance().getAccMode()){
            case SOLID:
                convertedModeToInt = 0;
                break;
            case RAINBOW:
                convertedModeToInt = 1;
                break;
            case FADE:
                convertedModeToInt = 2;
                break;
            case LOADING:
                convertedModeToInt = 3;
                break;
            case RUNNING:
                convertedModeToInt = 4;
                break;
            case LIGHTNING:
                convertedModeToInt = 5;
                break;
            case BREATH:
                convertedModeToInt = 6;
                break;
            case JOGGLING:
                convertedModeToInt = 7;
                break;
            case SELFPAINTING:
                convertedModeToInt = 99;
                break;
        }
        // Hier dann als JSON verpacken
        Log.d("BluetoothService", "Sending Mode with ID: "+convertedModeToInt);
        if(!isConnected){
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        }
        try {
            String command = convertedModeToInt+"";
            Log.d("BluetoothService",command);
            socket.getOutputStream().write(command.getBytes());
        } catch (IOException e) {
            isConnected =false;
            setChanged();
            notifyObservers();
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        }
    }

    public void sendColor() throws BluetoothException {
        /*
        if(!isConnected){
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        }
        */
        Log.d("BluetoothService", "Sending Color: "+Model.getInstance().getColor());
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




    private class ConnectionThread extends Thread {

        BluetoothDevice btDevice;

        public ConnectionThread(BluetoothDevice btDevice){
            this.btDevice = btDevice;
        }

        public void run() {
            try {
                connectionRunningLocked = true;
                socket = btDevice.createInsecureRfcommSocketToServiceRecord(SPP_UUID);
                socket.connect();
                Log.d("Bluetoothhandler","connected");
                isConnected = true;
                setChanged();
                notifyObservers();
            } catch (IOException e) {
                Log.e("Bluetoothhandler","connect no socket available");
            }finally {
               connectionRunningLocked = false;
            }
        }

    }



}
