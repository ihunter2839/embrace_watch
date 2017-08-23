package com.embrace.embrace_watch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by ihunt on 7/18/2017.
 */

public class AcceptThread extends Thread {

    private final BluetoothServerSocket serverSocket = null;
    private BluetoothAdapter adapt = null;

    public AcceptThread() {

        BluetoothServerSocket tmp = null;
        adapt = BluetoothAdapter.getDefaultAdapter();
        try {
            UUID id = UUID.randomUUID();
            tmp = adapt.listenUsingRfcommWithServiceRecord("embrace_watch", id);
        } catch (IOException e){
            Log.e("Failed BT", "Socket's listen() method failed");
        }
    }
}
