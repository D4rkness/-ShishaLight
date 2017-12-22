package com.example.miste.shirem;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.UUID;


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

        if(!isConnected){
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        }

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
        Log.d("BluetoothService", "Sending Mode with ID: "+convertedModeToInt);
        JSONObject command = new JSONObject();
        try {
            command.put("command","m");
            command.put("mode",convertedModeToInt);
            Log.d("BluetoothService",command.toString());
            socket.getOutputStream().write(command.toString().getBytes());
        } catch (IOException e) {
            isConnected =false;
            setChanged();
            notifyObservers();
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendColor() throws BluetoothException {
        if(!isConnected){
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        }
        try {
            JSONObject command = new JSONObject();
            command.put("command","c");
            command.put("value",Model.getInstance().getColor());
            Log.d("BluetoothService: ","Command: "+command);
            socket.getOutputStream().write(command.toString().getBytes());
        } catch (IOException e) {
            isConnected =false;
            setChanged();
            notifyObservers();
            throw new BluetoothException(BluetoothError.NO_CONNECTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("BluetoothService", "Sending Color: "+Model.getInstance().getColor());
    }


    public void sendPainting(){
        JSONObject command = new JSONObject();
        JSONArray colorArray = new JSONArray();
        try {
            command.put("command", "p");

            for(int i = 0; i < Model.getInstance().getColorContainer().length;i++){
                colorArray.put(Model.getInstance().getColorContainer()[i]);
            }
            command.put("value",colorArray);
            Log.d("Bluetoothservice: ","Paintingval "+ command);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


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
