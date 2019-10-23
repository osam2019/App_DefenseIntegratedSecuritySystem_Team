package com.rokdcc.diss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(CallStateListener.TAG, "CallStateReceiver >>>>>>>> onReceive");
        CallStateListener callStateListener = new CallStateListener(context);
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
class CallStateListener extends PhoneStateListener {
    public static String TAG="";
    private ITelephony telephonyService;;
    private int previousState=TelephonyManager.CALL_STATE_IDLE;
    private Context mContext;
    public CallStateListener(Context context) {
        mContext=context;
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);

        switch(state){
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "IDLE");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                if(previousState != TelephonyManager.CALL_STATE_RINGING){
                    Log.d(TAG, "end call!");

                    TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);


                    try{
                        Class c=Class.forName(telephony.getClass().getName());
                        Method m = c.getDeclaredMethod("getITelephony");
                        m.setAccessible(true);

                        telephonyService = (ITelephony) m.invoke(telephony);
                        telephonyService.endCall();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                previousState = TelephonyManager.CALL_STATE_RINGING;
                break;
        }

    }

}
