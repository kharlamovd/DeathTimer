package com.brands.deathtimer.extras;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class DateManager {

    private static final String US_ALPHA_2_CODE = "US";

    private static boolean isUSDevice(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(android.content.Context.TELEPHONY_SERVICE);
        return (tm.getNetworkCountryIso().equals(US_ALPHA_2_CODE));
    }

    public static String getDate(int dd, int mm, int yyyy, Context context) {
        if (isUSDevice(context))
            return new String((mm+1) + "/" + dd + "/" + yyyy);
        else
            return new String(dd + "." + (mm+1) + "." + yyyy);
    }

}
