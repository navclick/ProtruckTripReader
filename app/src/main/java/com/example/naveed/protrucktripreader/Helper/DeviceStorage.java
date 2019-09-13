package com.example.naveed.protrucktripreader.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DeviceStorage {

    private final Context context;
    private final SharedPreferences sharedPreferences;

    public DeviceStorage(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean SetDeviceID(String device_id){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("deviceid", device_id);
        editor.commit();
        return true;
    }

    public String GetDeviceID(){
        String restoredText = sharedPreferences.getString("deviceid", null);
        return restoredText;
    }

    public boolean removeALL() {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.clear();
        return prefsEditor.commit();
    }



    public boolean SetRegNum(String reg_nnum){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("regnum", reg_nnum);
        editor.commit();
        return true;
    }

    public String GetRegNum(){
        String restoredText = sharedPreferences.getString("regnum", null);
        return restoredText;
    }



    public boolean SetVehicleID(int vid){
        SharedPreferences.Editor editor = sharedPreferences.edit().putInt("vid", vid);
        editor.commit();
        return true;
    }

    public int GetVehicleID(){
        int restoredText = sharedPreferences.getInt("vid", 0);
        return restoredText;
    }

    public boolean SetUserPhoto(String usephoto){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("userphoto", usephoto);
        editor.commit();
        return true;
    }



}
