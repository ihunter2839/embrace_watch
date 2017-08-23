package com.embrace.embrace_watch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ihunt on 7/17/2017.
 */

public class ConnectBluetooth extends Activity {

    BluetoothAdapter btAdapt;
    int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle sis){
        super.onCreate(sis);
        setContentView(R.layout.activity_connect_bluetooth);
        populatePairedDevices();
    }

    private void populatePairedDevices(){
        //Check for paired bluetooth devices
        btAdapt = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = btAdapt.getBondedDevices();
        ArrayList<String> deviceNames = new ArrayList<>();
        ArrayList<String> macAds = new ArrayList<>();
        if (pairedDevices.size() > 0){
            for (BluetoothDevice device : pairedDevices) {
                deviceNames.add(device.getName());
                macAds.add(device.getAddress());
            }
            ListView deviceList = (ListView) findViewById(R.id.bluetooth_device_list);
            ArrayAdapter<String> adapt = new ArrayAdapter<>(this, R.layout.bluetooth_card, R.id.bluetooth_device_name_text);
            adapt.addAll(deviceNames);
            deviceList.setAdapter(adapt);

            Spinner bt_device_spinner = (Spinner) findViewById(R.id.bluetooth_device_list_spinner);
            ArrayAdapter<String> spinAdapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, deviceNames);
            bt_device_spinner.setAdapter(spinAdapt);
        }

    }

    public void connectBluetooth(View view){
        //Bluetooth is not supported if btAdapt is null
        if (btAdapt == null){
            TextView bt_warning = (TextView) findViewById(R.id.bluetooth_unavailable_text);
            bt_warning.setText("Bluetooth not available");
            return;
        }
        //Check that bluetooth is enabled on the device
        if (!btAdapt.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Set<BluetoothDevice> pairedDevices = btAdapt.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            ConnectThread btConnection = new ConnectThread(device);
            btConnection.run();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        TextView bt_warning = (TextView) findViewById(R.id.bluetooth_unavailable_text);
        if (requestCode == REQUEST_ENABLE_BT && resultCode != RESULT_OK){
            bt_warning.setText("Bluetooth access denied");
            return;
        }
        else if (requestCode == REQUEST_ENABLE_BT){
            connectBluetooth((Button) findViewById(R.id.connect_bt_button));
            bt_warning.setText("");
        }
    }
}
