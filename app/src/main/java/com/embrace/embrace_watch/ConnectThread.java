package com.embrace.embrace_watch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by ihunt on 7/23/2017.
 */

public class ConnectThread extends Thread {

    private final String BT_UUID = "c0c491fb-0e5d-4111-aea3-fd6acc74e9cf";

    //final objects may only be assigned once
    private final BluetoothSocket btSocket;
    private final BluetoothDevice btDevice;
    private final InputStream dataIn;
    private final OutputStream dataOut;
    private byte[] streamBuffer;

    public ConnectThread(BluetoothDevice device){
        btDevice = device;

        BluetoothSocket tmp = null;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        //create a client socket
        try {
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(BT_UUID));
        } catch (IOException e) {
            Log.e("SOCK_FAIL", "Socket's create() method failed");
        }
        btSocket = tmp;

        //create input and output streams
        try {
            tmpIn = btSocket.getInputStream();
        } catch (IOException e){
            Log.e("BLU_IN_ERR", "Could not create input stream");
        }
        try {
            tmpOut = btSocket.getOutputStream();
        } catch (IOException e){
            Log.e("BLU_ERR_OUT", "Could not create output stream");
        }

        dataIn = tmpIn;
        dataOut = tmpOut;
    }

    public void run() {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.cancelDiscovery();

        try {
            btSocket.connect();
        } catch (IOException connectErr){
            try {
                btSocket.close();
            } catch (IOException closeErr) {
                Log.e("CLOSE_ERR", "Could not close client socket");
            }
            return;
        }
        Log.e("CON_SUC", "Bluetooth connection established");
        //do work with the connection here
        streamBuffer = new byte[1024];

        //number of bytes returned by the stream
        int numBytes;

        while(true){
            try {
                numBytes = dataIn.read(streamBuffer);
                Log.d("BLU_IN", streamBuffer.toString());
            } catch (IOException e){
                Log.d("STREAM_DIS", "Input stream disconnected", e);
                break;
            }
        }

    }

    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException c){
            Log.e("CANCEL_ERR", "Could not close client socket");
        }
    }
}
