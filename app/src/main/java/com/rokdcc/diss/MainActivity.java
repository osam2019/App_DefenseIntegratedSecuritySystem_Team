package com.rokdcc.diss;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null){
            Log.i("DEBUG_TAG", "NO Bluetooth Error");
        }
        else{
            if(!mBluetoothAdapter.isEnabled()){
                mBluetoothAdapter.enable();
                Log.i("DEBUG_TAG","Bluetooth ON");
            }
        }
    }
}
