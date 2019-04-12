package com.su.hang.nap.util;

import android.util.Log;

public class Logger {
    public static void d(String d) {
        Log.d("NAP", d);
    }

    public static void e(String e) {
        Log.d("NAP", e);
    }

    public static void i(String i) {
        Log.i("NAP", i);
    }

    public static void d(Double d) {

        d(String.valueOf(d));
    }

    public static void d(int d) {
        d(String.valueOf(d));
    }
}
