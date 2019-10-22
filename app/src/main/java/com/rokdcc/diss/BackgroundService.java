package com.rokdcc.diss;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BackgroundService extends Service {
    private int a = 0;
    public BackgroundService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals("Action1")) {
            Log.i("onStartCommand-Action1", "---서비스 스타트--- ");

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction("Action2");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher_round);

            Notification notification = new NotificationCompat.Builder(this, "channel1")
                    .setContentTitle("전투통제실 입장")
                    .setTicker("측정중... ")
                    .setContentText("프로세스 차단중")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true).build();

            startForeground(111, notification);

            Thread bt = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(3000);

                            //wifi off
                            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifi.setWifiEnabled(false);

                            //connected bluetooth disconnet
                            Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                            if (pairedDevices.size() > 0) {

                                for (BluetoothDevice d: pairedDevices) {
                                    String deviceName = d.getName();
                                    String macAddress = d.getAddress();
                                    Log.i("DISS", "paired device: " + deviceName + " at " + macAddress);
                                    unpairDevice(d);
                                    // do what you need/want this these list items
                                }
                            }
                            ActivityManager mActivityManager = mActivityManager = (ActivityManager) getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
                            mActivityManager.killBackgroundProcesses("com.sec.android.app.camera");
                            
                            ArrayList<Integer> pids = new ArrayList<Integer>();
                            ActivityManager  manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                            List<ActivityManager.RunningAppProcessInfo> listOfProcesses = manager.getRunningAppProcesses();
                            for (ActivityManager.RunningAppProcessInfo process : listOfProcesses)
                            {
                                if (pids.contains(process.pid))
                                {
                                    // Ends the app
                                    manager.restartPackage(process.processName);
                                }
                            }

                            Handler mHandler = new Handler(Looper.getMainLooper());
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 내용
                                    Toast.makeText(getApplicationContext(), "서비스 실행중", Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        if(!MainActivity.TFLAG){
                            Log.i("background-counter","---백스레드 중지---");
                            break;
                        }

                        Log.i("background-counter",String.valueOf(++a));
                    }
                }
            });
            bt.setName("백그라운드스레드");
            bt.start();

        }else if(intent.getAction().equals("Action2")){
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        MainActivity.TFLAG = false;
        Log.i("SERVICE TAG ", "==== 서비스 destroyed ===");

    }
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
