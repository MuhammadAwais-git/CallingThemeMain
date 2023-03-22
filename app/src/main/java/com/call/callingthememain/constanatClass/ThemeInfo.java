package com.call.callingthememain.constanatClass;

import android.content.Context;
import android.graphics.Color;

public class ThemeInfo {



    public static void setThemeName(Context context, String name){
        Constants.getPrefs(context).edit().putString(Constants.THEME_NAME,name).commit();
    }

    public static String getThemeName(Context context){
        return Constants.getPrefs(context).getString(Constants.THEME_NAME,"");
    }


    public static void setDialerDrawableID(Context context, int id){
        Constants.getPrefs(context).edit().putInt(Constants.DIALER_DRAWABLE_ID,id).commit();
        setIsDialerDrawable(context,true);
    }

    public static int getDialerDrawableID(Context context){
        return Constants.getPrefs(context).getInt(Constants.DIALER_DRAWABLE_ID,-1);
    }



    private static void  setIsDialerDrawable(Context context, boolean isdrawable){
        Constants.getPrefs(context).edit().putBoolean(Constants.IS_DIALER_DRAWABLE,isdrawable).commit();
    }

    public static boolean IsDialerDrawable(Context context){
        return Constants.getPrefs(context).getBoolean(Constants.IS_DIALER_DRAWABLE,false);
    }


    public static void setDialerBgPath(Context context, String path){
        Constants.getPrefs(context).edit().putString(Constants.DIALER_BG_PATH,path).commit();
        setIsDialerDrawable(context,false);
    }

    public static String getDialerBgPath(Context context){
        return Constants.getPrefs(context).getString(Constants.DIALER_BG_PATH,"");
    }

    public static void setCallerScreenBgPath(Context context, String path){
        Constants.getPrefs(context).edit().putString(Constants.CALLER_BG_PATH,path).commit();
    }

    public static String getCallerScreenBgPath(Context context){
        return Constants.getPrefs(context).getString(Constants.CALLER_BG_PATH,"");
    }

    public static void setFontPath(Context context, String fontpath){
        Constants.getPrefs(context).edit().putString(Constants.FONT_PATH,fontpath).commit();
    }

    public static String getFontPath(Context context){
        return Constants.getPrefs(context).getString(Constants.FONT_PATH, "fonts/Arial_regular.ttf");
    }

    public static void setTextColor(Context context, int color){
        Constants.getPrefs(context).edit().putInt(Constants.TEXT_COLOR,color).commit();
    }

    public static int getTextColor(Context context){
        return Constants.getPrefs(context).getInt(Constants.TEXT_COLOR, Color.BLACK);
    }

    public static void setAvaiableFontsList(Context context, String fontsJsonList){
        Constants.getPrefs(context).edit().putString(Constants.FONT_JSON,fontsJsonList).commit();
    }

    public static String getAvaiableFontsList(Context context){
        return Constants.getPrefs(context).getString(Constants.FONT_JSON, "");
    }

    public static void setAvaiableThemesList(Context context, String themesJsonList){
        Constants.getPrefs(context).edit().putString(Constants.THEMES_JSON,themesJsonList).commit();
    }

    public static String getAvaiableThemesList(Context context){
        return Constants.getPrefs(context).getString(Constants.THEMES_JSON, "");
    }


    public static void setAvaiableTonesList(Context context, String tonesJsonList){
        Constants.getPrefs(context).edit().putString(Constants.TONES_JSON,tonesJsonList).commit();
    }

    public static String getAvaiableTonesList(Context context){
        return Constants.getPrefs(context).getString(Constants.TONES_JSON, "");
    }


    public static void setToneApplied(Context context, String tonesname){
        Constants.getPrefs(context).edit().putString(Constants.TONES,tonesname).commit();
    }

    public static String getToneApplied(Context context){
        return Constants.getPrefs(context).getString(Constants.TONES, "");
    }



}
