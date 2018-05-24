package com.barateknologi.bbplk_cevest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.barateknologi.bbplk_cevest.App;
import com.barateknologi.bbplk_cevest.BuildConfig;


/**
 * Created by akbar on 12/08/17.
 */

public class SessionManager {
    private static final String TAG = "SessionManager";
    private static final String USER_NAME = "USERNAME";

    private static SharedPreferences getPref(){
        return PreferenceManager.getDefaultSharedPreferences(App.getContext());
    }

    public static void putString(Context context, String key, String value) {
        getPref().edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        return getPref().getString(key, null);
    }

    public static void clear(Context context) {
        getPref().edit().clear().apply();
    }

    public static void save(String key, String value){
        if (BuildConfig.DEBUG)
            Log.d(TAG, "saveCache: " + value);
        getPref().edit().putString(key, value).apply();
    }

    public static void save(String key, int value){
        if (BuildConfig.DEBUG)
            Log.d(TAG, "saveCache: " + value);
        getPref().edit().putInt(key, value).apply();
    }

    public static void save(String key, Boolean value){
        if (BuildConfig.DEBUG)
            Log.d(TAG, "saveCache: " + value);
        getPref().edit().putBoolean(key, value).apply();
    }

    public static Boolean checkExist(String key){
        return getPref().contains(key);
    }

    public static String grabString(String key){
        return getPref().getString(key, null);
    }

    public static Boolean grabBoolean(String key){
        return getPref().getBoolean(key, false);
    }

    public static int grabInt(String key){
        return getPref().getInt(key, 0);
    }

    public static String getUsername(Context context){
        return SessionManager.getString(context, USER_NAME);
    }
}
