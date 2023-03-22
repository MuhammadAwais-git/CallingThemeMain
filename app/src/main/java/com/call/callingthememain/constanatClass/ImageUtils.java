package com.call.callingthememain.constanatClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ImageUtils {


Context context;
    public static Bitmap getBitmapFromStorage(Context context,String name){


        File file=new File(getFolderUrl(context),name+".png");


        return BitmapFactory.decodeFile(file.getPath());
    }


    public static Bitmap getBitmapFromCustomPath(String path){

        File file=new File(path);

        return BitmapFactory.decodeFile(file.getPath());
    }


    public static String getFolderUrl(Context context){


         return new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), Constants.FOLDER_NAME).getPath();

    }



/*    public static List<Integer> getBackgroundsList() {

        List<Integer> list=new ArrayList<>();

        list.add(R.drawable.bg_1);
        list.add(R.drawable.bg_2);
        list.add(R.drawable.bg_3);
        list.add(R.drawable.bg_4);
        list.add(R.drawable.bg_5);
        list.add(R.drawable.bg_6);
        list.add(R.drawable.bg_7);
        list.add(R.drawable.bg_8);
        list.add(R.drawable.bg_9);
        list.add(R.drawable.bg_10);
        list.add(R.drawable.bg_11);
        list.add(R.drawable.bg_12);
        list.add(R.drawable.bg_13);
        list.add(R.drawable.bg_14);

        return list;
    }*/

    public static List<String> getColorsList() {

        List<String> list=new ArrayList<>();


        list.add("#ffffff");
        list.add("#000000");
        list.add("#e53935");
        list.add("#651FFF");
        list.add("#3D5AFE");
        list.add("#00C853");
        list.add("#4E342E");
        list.add("#E65100");
        list.add("#CE93D8");
        list.add("#A5D6A7");
        list.add("#ff8a80");
        list.add("#9E9E9E");
        list.add("#3eef6c");
        list.add("#4d127f");
        list.add("#11f1bf");
        list.add("#0a2d07");
        list.add("#9e6017");
        list.add("#0e3561");



        return list;
    }
}
