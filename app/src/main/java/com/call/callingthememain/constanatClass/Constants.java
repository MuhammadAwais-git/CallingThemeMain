package com.call.callingthememain.constanatClass;

import android.content.Context;
import android.content.SharedPreferences;

public class Constants {




    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
    }

    public final static String SHARED_PREFERENCE_NAME="sharedpreference_photophone";
    public static final String TONES = "tones";

    public final static String FONT_JSON="fontjson";
    public final static String THEMES_JSON="themesjson";
    public final static String TONES_JSON="tonesjson";

    public final static String THEME_NAME="themename";
    public final static String DIALER_BG_PATH="dialerbgpath";
    public final static String CALLER_BG_PATH="callerbgpath";

    public static final String FOLDER_NAME = "/PhotoPhoneDialer";

    public static final String DIALER_DRAWABLE_ID = "dialerdrawableid";
    public static final String IS_DIALER_DRAWABLE = "isdialerdrawable";
    public static final String FONT_PATH = "fontpath";
    public static final String TEXT_COLOR = "textcolor";
    public static final String DISCONNECT_BROADCAST = "photophonedisconnectbroadcast";
    public static final String DISCONNECT = "disconnect";

}
