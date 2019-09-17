package com.example.naveed.protrucktripreader.BackGroundServices;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.naveed.protrucktripreader.Abstract.GeneralCallBack;
import com.example.naveed.protrucktripreader.Abstract.GeneralCallBackService;
import com.example.naveed.protrucktripreader.Base.BaseActivity;
import com.example.naveed.protrucktripreader.Helper.Constants;
import com.example.naveed.protrucktripreader.Helper.DeviceStorage;
import com.example.naveed.protrucktripreader.MainActivity;
import com.example.naveed.protrucktripreader.Network.RestClient;
import com.example.naveed.protrucktripreader.R;
import com.example.naveed.protrucktripreader.Request.SendTrackRequest;
import com.example.naveed.protrucktripreader.Responses.SendTrackResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.naveed.protrucktripreader.Helper.ProgressLoader.hideProgress;

public class LocationService extends Service implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener  {

    public DeviceStorage tokenHelper;
    Intent intent;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String lat, lon;
    public int counter = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";

    Context context;
    public static String str_receiver = "servicetutorial.service.receiver";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.d(Constants.TAG, "Service started!!!!!!!!!!");

        super.onCreate();
        tokenHelper = new DeviceStorage(this);
        buildGoogleApiClient();
        intent = new Intent(str_receiver);
        String input ="test";
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, notification);
        }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(Constants.TAG, "LOcation!!!!!!!!!!");

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(7000);
        mLocationRequest.setFastestInterval(0);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
       /*
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setSmallestDisplacement(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
   */
   /* if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

    }*/

        //use if you want location update


        Log.d(Constants.TAG, "Granted");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());



            Log.d(Constants.TAG,lat);
            Log.d(Constants.TAG,lon);

        }
        else{
            Log.d(Constants.TAG,"location filed");
            //Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "service start", Toast.LENGTH_SHORT).show();
        startTimer();

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    Toast.makeText(getApplicationContext(), "service running", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    // TODO: handle exception
                }
                finally{
                    //also call the same runnable to call it at regular interval
                    handler.postDelayed(this, 5000);
                }
            }
        };

//runnable must be execute once
        handler.post(runnable);
        return START_STICKY;
    }

/*@Override
public void onConnected(@Nullable Bundle bundle) {

}*/


    @Override
    public void onDestroy() {
        Toast.makeText(this, "service destroy", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true); //true will remove notification
        }
        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }
    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(Constants.TAG,"after 30");

        if(tokenHelper.GetVehicleID() <1) {


        }
        else{

            UpdateLocationOnServer(lat,lon);
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Constants.TAG,"Connection field");
    }


    public void UpdateLocationOnServer(String Lat, String Lng){

        SendTrackRequest requestObj = new SendTrackRequest();
        requestObj.Latitude=Lat;
        requestObj.Longitude=Lng;
        requestObj.VehicleId =tokenHelper.GetVehicleID();
        requestObj.RegNumber=tokenHelper.GetRegNum();






        Gson g = new Gson();
        String userJson = g.toJson(requestObj);
        Log.d("test", userJson);

        RestClient.getAuthAdapter().sendTrack(requestObj).enqueue(new GeneralCallBackService<SendTrackResponse>(this) {
            @Override
            public void onSuccess(SendTrackResponse response) {

                if(!response.getIsError()) {

                  //  Gson gson = new Gson();
                   // String Reslog = gson.toJson(response);
                    //Log.d("test", Reslog);

                    Toast.makeText(getApplicationContext(), "Location Updated",
                            Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed To Update Location",
                            Toast.LENGTH_LONG).show();


                }

                hideProgress();




            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Toast.makeText(getApplicationContext(), "Failed To Update Location",
                        Toast.LENGTH_LONG).show();

            }



        });



    }


    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
