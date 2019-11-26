package br.com.project.samsung.inventarioapp.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.util.Strings;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;


public class Util {

    public static final String DATETIME_FORMAT_KEY = "yyyyMddHHmmssSSS";
    public static Calendar calendar = Calendar.getInstance();

    public static String getCurrentDateTime(String format){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = calendar.getTime();
        return dateFormat.format(today);
    }


    public static int getCurrentDay(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getCurrentMonth(){
        return calendar.get(Calendar.MONTH);
    }
    public static int getCurrentYear(){
        return calendar.get(Calendar.YEAR);
    }

    public static boolean isNull(String string){
        return Strings.isEmptyOrWhitespace(string);
    }

    public static void log(String tag, String msg){
        Log.i(tag,msg);
    }


    public static void exibirImagemTelaCorrespondente(Context context, String urlImagem, ImageView imageView){
        if (!Util.isNull(urlImagem)){
            Picasso.with(context).load(Uri.parse(urlImagem)).into(imageView);
        }
    }

    public static boolean isNumeric(String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        try {
            Integer.parseInt(string);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }

    }



}
