package com.call.callingthememain.phonecall;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.telecom.Call;
import android.telecom.VideoProfile;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.call.callingthememain.R;

public class PhoneCallManager {

    public static Call call;

    private PhoneCallActivity context;
    private AudioManager audioManager;

    public PhoneCallManager(PhoneCallActivity context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (call!=null){
            Log.d("SETTINGSFRAGMENT","call service in manager");
        }else {
            Log.d("SETTINGSFRAGMENT","not call service in manager");
        }
    }

    /**
     * answer the phone
     */
    public void answer() {
        if (call != null) {
            call.answer(VideoProfile.STATE_AUDIO_ONLY);
            openSpeaker();
        }
    }

    /**
     * Disconnect the phone, including the callback and the hang up after answering
     */
    public void disconnect() {
        if (call != null) {
            call.disconnect();
        }

    }

    /**
     * Turn on the handsfree
     */
    public void openSpeaker() {
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(true);

        }
    }

    /**
     * Turn off  Speakers
     */
    public void turnOffSpeaker() {
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.stopBluetoothSco();
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(false);
        }
    }
    /*
     * Turn on the BT headset
     */
    public void turnOnBluetooth(){
        if (audioManager!=null){
            //For BT
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            audioManager.startBluetoothSco();
            audioManager.setBluetoothScoOn(true);
        }
    }
    /**
     *
     * Destroy resources
     * */
    public void destroy() {
        call = null;
        context = null;
        audioManager = null;
         //System.exit(0);

        if(context!=null){
            context.finish();
        }


    }

    /*
     *** Turn on the loud speaker
     */
    public void loudSpeaker(){
        if (audioManager!=null){

            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.stopBluetoothSco();
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(true);
           // audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        }
    }
    /*
     *** Turn off the microphone
     */
    public void muteMicroPhone(){
        if (audioManager!=null){
            audioManager.setMicrophoneMute(true);
        }
    }
    /*
     *** Turn on the microphone
     */
    public void openMicroPhone(){
        if (audioManager!=null){
            audioManager.setMicrophoneMute(false);
        }
    }
    /*
     *** Place call on hold
     */
    public void putCallOnHold(){
        if (call != null) {
            call.hold();
        }
    }
    /*
     *** Remove call from hold
     */
    public void removeCallFromHold(){
        if (call != null) {
            call.unhold();
        }
    }
    /*
    *** increase call volume
     */
    public void extraVolume(){
        if (audioManager!=null){
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 20, 0);
        }
    }
}
