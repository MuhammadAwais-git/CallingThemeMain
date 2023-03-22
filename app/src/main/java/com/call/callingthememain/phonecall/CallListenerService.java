package com.call.callingthememain.phonecall;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.call.callingthememain.R;

import androidx.annotation.Nullable;



/**
 *  CALLING LISTENER SERVICE FOR OUTGOING AND INGOING CALLS
 */
public class CallListenerService extends Service {

    private View phoneCallView;
    private TextView tvCallNumber;
    private Button btnOpenApp;

    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;
    private String callNumber;
    private boolean hasShown;
    private boolean isCallingIn;

    @Override
    public void onCreate() {
        super.onCreate();

        initPhoneStateListener();

        initPhoneCallView();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     *
     * Initialize the incoming call status listener
     */
    private void initPhoneStateListener() {
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                callNumber = incomingNumber;

                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE: // Standby, that is, when there is no phone, trigger when hanging up
                        dismiss();
                        break;

                    case TelephonyManager.CALL_STATE_RINGING: //Ring, trigger when an incoming call
                        isCallingIn = true;
                        updateUI();
                        break;

                    case TelephonyManager.CALL_STATE_OFFHOOK: // Trigger when picking up, answering or making a cal
                        updateUI();
                        show();
                        break;

                    default:
                        break;

                }
            }
        };

        // Set the caller listener
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

    }

    private void initPhoneCallView() {
        windowManager = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

        params = new WindowManager.LayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        params.width = width;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        //Set the image format, the effect is transparent
        params.format = PixelFormat.TRANSLUCENT;
        // Set Window flag to system level bulletin | cover the surface
        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                WindowManager.LayoutParams.TYPE_PHONE;

        //Cannot be aggregated (not responding to the return key) | Full screen
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        //
        //Above API 19, you can also open the transparent status bar and navigation bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        }

        FrameLayout interceptorLayout = new FrameLayout(this) {

            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }

                return super.dispatchKeyEvent(event);
            }
        };

    }

    /**
     *
     * Show top-level bullet box to show call information
     */
    private void show() {
        if (!hasShown) {
            windowManager.addView(phoneCallView, params);
            hasShown = true;
        }
    }

    /**
     *
     Cancel display
     */
    private void dismiss() {
        if (hasShown) {
            windowManager.removeView(phoneCallView);
            isCallingIn = false;
            hasShown = false;
        }
    }

    private void updateUI() {
        tvCallNumber.setText(formatPhoneNumber(callNumber));

        int callTypeDrawable = isCallingIn ? R.drawable.ic_phone_call_in : R.drawable.ic_phone_call_out;
        tvCallNumber.setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(callTypeDrawable), null);
    }

    public static String formatPhoneNumber(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum) && phoneNum.length() == 11) {
            return phoneNum.substring(0, 4) + "-"
                    + phoneNum.substring(4, 7) /*+ "-" */
                    + phoneNum.substring(7);
        }
        return phoneNum;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
    }
}
