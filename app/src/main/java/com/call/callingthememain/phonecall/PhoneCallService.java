package com.call.callingthememain.phonecall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.telecom.Call;
import android.telecom.InCallService;
import android.util.Log;

import androidx.annotation.RequiresApi;


/**
 *  IN-CALL phone service
 */
public class PhoneCallService extends InCallService {

    private SharedPreferences sharedPreferences;
    public static boolean onOrOff, lowBattery;

    private Call.Callback callback = new Call.Callback() {
        @Override
        public void onStateChanged(Call call, int state) {
            super.onStateChanged(call, state);

            switch (state) {
                case Call.STATE_ACTIVE: {

                    break;
                }

                case Call.STATE_DISCONNECTED: {
                    Log.d("PhoneCallService", "in call disconnected");
                    //System.exit(0);

                    Intent intent=new Intent("photophonedisconnectbroadcast");
                    intent.putExtra("disconnect",true);
                    getApplicationContext().sendBroadcast(intent);


                    break;
                }

            }
        }
    };

    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        onOrOff = sharedPreferences.getBoolean("onOrOff", false);
        lowBattery = sharedPreferences.getBoolean("lowBatteryFlash", false);
        Log.d("PhoneCallService", "led status=" + onOrOff);

        call.registerCallback(callback);
      PhoneCallManager.call = call;

      CallType callType = null;

        if (call.getState() == Call.STATE_RINGING) {
            callType = CallType.CALL_IN;
            Log.d("PhoneCallService", "led status calling=" + onOrOff);

        } else if (call.getState() == Call.STATE_CONNECTING) {
            callType = CallType.CALL_OUT;
            Log.d("PhoneCallService", "out going call");
        }

        if (callType == CallType.CALL_IN) {
            Log.d("PhoneCallService", "in here");
            Call.Details details = call.getDetails();
            String phoneNumber = details.getHandle().getSchemeSpecificPart();
          PhoneCallActivity.actionStart(this, phoneNumber, callType);
        } else {
            Log.d("PhoneCallService", "in here");
            Call.Details details = call.getDetails();
            String phoneNumber = details.getHandle().getSchemeSpecificPart();
            PhoneCallActivity.actionStart(this, phoneNumber, callType);

        }
    }

    @Override
    public void onCallRemoved(Call call) {
        super.onCallRemoved(call);



        call.unregisterCallback(callback);
        PhoneCallManager.call = null;
        //this.onCallRemoved(call);
    }


}

