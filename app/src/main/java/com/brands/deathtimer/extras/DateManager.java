package com.brands.deathtimer.extras;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Calendar;

public class DateManager {

    public static final int INIT_DAY = 1;
    public static int INIT_MONTH = 0;
    public static int INIT_YEAR = 1990;

    private static final String US_ALPHA_2_CODE = "US";

    private boolean isUS = false;

    public static boolean isUSDevice(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(android.content.Context.TELEPHONY_SERVICE);
        return (tm.getNetworkCountryIso().equals(US_ALPHA_2_CODE));
    }

    public static String getUSDate(int dd, int mm, int yyyy, Context context) {
        return new String((mm+1) + "/" + dd + "/" + yyyy);
    }

    public static String getRegularDate(int dd, int mm, int yyyy, Context context) {
        return new String(dd + "." + (mm+1) + "." + yyyy);
    }

    public static long getTodayInMillis() {
        Calendar cal = Calendar.getInstance();
        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date  = cal.get(Calendar.DATE);
        cal.clear();
        cal.set(year, month, date);
        return cal.getTimeInMillis();
    }
}
