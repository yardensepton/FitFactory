package com.example.fitfactory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class MySignal {

    @SuppressLint("StaticFieldLeak")
    private static MySignal mySignal = null;
    private final Finals finals = new Finals();

    private Context context;

    private MySignal(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (mySignal == null) {
            mySignal = new MySignal(context);
        }
    }

    public static MySignal getInstance() {
        return mySignal;
    }


    public void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

//    public void vibrate() {
//        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        // Vibrate for 500 milliseconds
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            v.vibrate(VibrationEffect.createOneShot(finals.VIBRATE_MILLISECONDS, VibrationEffect.DEFAULT_AMPLITUDE));
//        } else {
//            //deprecated in API 26
//            v.vibrate(finals.VIBRATE_MILLISECONDS);
//        }
//    }



}
