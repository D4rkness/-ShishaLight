package com.example.miste.shirem;

/**
 * Created by Miste on 24.10.2017.
 */

public class BluetoothException extends Exception {

    private BluetoothError err;

    public BluetoothException(BluetoothError err){
        this.err = err;
    }

    public BluetoothError getErr(){
        return err;
    }
}
