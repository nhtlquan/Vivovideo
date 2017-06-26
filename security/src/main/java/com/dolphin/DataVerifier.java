package com.dolphin;

import android.content.Context;

/**
 * Created by Don on 2/20/2017.
 */

public class DataVerifier {
    static {
        System.loadLibrary("native-lib");
    }

    public static native String verifyData(Context context, String input);
}
