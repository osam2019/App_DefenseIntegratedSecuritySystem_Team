package com.rokdcc.diss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;



public class MainActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "BeaconActivity";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    public static boolean TFLAG = false;
    private BeaconManager beaconManager;
    private Intent backStartIntent;

    TextView maintitleText;

    Button ACUBt, HomeBt, CallListBt;
    IDCardFragment fragment2;
    ACUFragment fragment1;
    CallListFragment fragment3;
    SettingFragment fragment4;
    FrameLayout fragment_container;
    FragmentManager fm;
    private final int F1 = 0;
    private final int F2 = 1;
    private final int F3 = 2;
    private final int F4 = 3;

    public int FRAGMENT_CURSOR = F2;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         init_fragment();
         ACUBt= findViewById(R.id.imageButton);
         HomeBt=findViewById(R.id.imageButton2);
         CallListBt=findViewById(R.id.imageButton3);

         maintitleText=findViewById(R.id.maintitle);
         TFLAG = true;
         backStartIntent = new Intent(MainActivity.this, BackgroundService.class);
         backStartIntent.setAction("Action1");
         //startService(backStartIntent);

         BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Log.i("DEBUG_TAG", "NO Bluetooth Error");
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                Log.i("DEBUG_TAG", "Bluetooth ON");
            }
        }
         verifyBluetooth();

         checkPermissions();

         beaconManager = BeaconManager.getInstanceForApplication(this);
         beaconManager.bind(this);


    }

    public void init_fragment() {

        fragment_container = findViewById(R.id.fragment_container);
        fragment2=new IDCardFragment();
        fragment1 = new ACUFragment();
        fragment3=new CallListFragment();
        fragment4=new SettingFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment2);
        fragmentTransaction.commit();
        FRAGMENT_CURSOR = F2;
    }
    // Checking needed permissions
    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.show();
            }
        }
        catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }
            });
            builder.show();
        }
    }

    // Checking needed permissions
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (beaconManager.isBound(this))
            beaconManager.setBackgroundMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this))
            beaconManager.setBackgroundMode(false);
    }

    @Override
    // Called when the beacon service is running and ready to accept your commands through the BeaconManager
    public void onBeaconServiceConnect() {
        // addRangeNotifier specifies a class that should be called each time the BeaconService gets ranging data, which is nominally once per second when beacons are detected.
        // RangeNotifier interface is implemented by classes that receive beacon ranging notifications
        beaconManager.removeAllMonitorNotifiers();
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
                Toast.makeText(getApplicationContext(), "전투통제실 입장, 차단모드 실행", Toast.LENGTH_LONG).show();
                TFLAG=true;
                startService(backStartIntent);
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
                Toast.makeText(getApplicationContext(), "전투통제실 퇴장, 차단모드 해제", Toast.LENGTH_LONG).show();
                TFLAG=false;
                stopService(backStartIntent);
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitorUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    public void showNotification(String title, String message){
         Intent notifyIntent = new Intent(this, MainActivity.class);
         notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
         PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT );
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }
    public void onToggleBtClicked(View v){
         if(TFLAG==false) {
             TFLAG=true;
             startService(backStartIntent);
         }
         else {
             TFLAG=false;
             stopService(backStartIntent);
         }
    }
    private void checkPermissions() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.enable_usage_stats_title)
                .setMessage(getString(R.string.enable_usage_stats_message))
                .setPositiveButton(R.string.enable_usage_stats_ok_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                            }
                        })
                .setCancelable(false)
                .create()
                .show();

        //Bluetooth Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });
                builder.show();
            }
        }
    }
    public void onACUBtClicked (View v){
        if (FRAGMENT_CURSOR != F1) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment1);
            fragmentTransaction.commit();
            FRAGMENT_CURSOR = F1;
            ACUBt.setBackgroundResource(R.drawable.listclickedimg);
            HomeBt.setBackgroundResource(R.drawable.homeunclickedimg);
            CallListBt.setBackgroundResource(R.drawable.phoneunclickedimg);
            maintitleText.setText("출입기록조회");
        }
        return;
    }
    public void onHomeBtClicked (View v){
        if (FRAGMENT_CURSOR != F2) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment2);
            fragmentTransaction.commit();
            FRAGMENT_CURSOR = F2;
            ACUBt.setBackgroundResource(R.drawable.listunclickedimg);
            HomeBt.setBackgroundResource(R.drawable.homeclickedimg);
            CallListBt.setBackgroundResource(R.drawable.phoneunclickedimg);
            maintitleText.setText("국방통합보안체계");
        }
        return;
    }
    public void onCallListBtClicked (View v){
        if (FRAGMENT_CURSOR != F3) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment3);
            fragmentTransaction.commit();
            FRAGMENT_CURSOR = F3;
            ACUBt.setBackgroundResource(R.drawable.listunclickedimg);
            HomeBt.setBackgroundResource(R.drawable.homeunclickedimg);
            CallListBt.setBackgroundResource(R.drawable.phoneclickedimg);
            maintitleText.setText("전화하기");
        }
        return;
    }
    public void onSettingBtClicked(View v){
        if (FRAGMENT_CURSOR != F4) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment4);
            fragmentTransaction.commit();
            FRAGMENT_CURSOR = F4;
            ACUBt.setBackgroundResource(R.drawable.listunclickedimg);
            HomeBt.setBackgroundResource(R.drawable.homeunclickedimg);
            CallListBt.setBackgroundResource(R.drawable.phoneunclickedimg);
            maintitleText.setText("설정");
        }


        return;
    }
}
