package com.example.naveed.protrucktripreader.Base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.naveed.protrucktripreader.Helper.Constants;
import com.example.naveed.protrucktripreader.Helper.DeviceStorage;

import com.example.naveed.protrucktripreader.Helper.ProgressLoader;
import com.example.naveed.protrucktripreader.R;

public class BaseActivity extends AppCompatActivity {

    public DeviceStorage deviceStorage ;
public static String deviceId;
    private ProgressLoader progressLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
deviceId= Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        // Initializations
        deviceStorage = new DeviceStorage(this);
    }

    public void showProgress() {
        try {
            if (progressLoader == null) {
                progressLoader = new ProgressLoader();
            }

            progressLoader.show(getSupportFragmentManager(), Constants.TAG);
        } catch (IllegalStateException e) {
            // Log.e(TAG, "showProgress:" + e.getMessage());
        }

    }

    public void hideProgress() {
        if (progressLoader != null) {
            try {
                progressLoader.dismissAllowingStateLoss();
            } catch (Exception e) {
                Log.e(Constants.TAG, "hideProgress:" + e.getMessage());
            }
        }
    }

    public void showMessageDailog(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

       /* builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();



    }


}
