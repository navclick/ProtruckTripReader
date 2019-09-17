package com.example.naveed.protrucktripreader;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.protrucktripreader.Abstract.GeneralCallBack;
import com.example.naveed.protrucktripreader.BackGroundServices.LocationService;
import com.example.naveed.protrucktripreader.Base.BaseActivity;
import com.example.naveed.protrucktripreader.Helper.Constants;
import com.example.naveed.protrucktripreader.Network.RestClient;
import com.example.naveed.protrucktripreader.Responses.BiltyResponse;
import com.example.naveed.protrucktripreader.Responses.VehicleResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {
    final private int  REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;
  //  protected LocationManager locationManager;
//    protected LocationListener locationListener;
    protected Context context;
    float zoomLevel = (float) 15.0;
    public  LatLng custLocation;

    public Marker MeMarker = null;
    public TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txtMsg=(TextView) findViewById(R.id.txt_msg);
        txtMsg.setText("Not Register. DeviceID:"+deviceId);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_gradient));
        GetPermissions();



        }


   public void setupAll(){

        if(deviceStorage.GetDeviceID()==null || deviceStorage.GetVehicleID() <1){

            getVehicleIfo();


        }
        else{

            setUpMap();

        }



   }


    private BroadcastReceiver br = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            String lat =bundle.getString("lat");
            String lng =bundle.getString("long");

            LatLng Destination = new LatLng(24.8710754, 67.0834147);

           // animateMarker(MeMarker,Destination,true);

            MeMarker.setPosition(new LatLng(Destination.latitude, Destination.longitude));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Destination,zoomLevel));
            Toast.makeText(getApplicationContext(), "From Service "+lat+"    "+lng,
                    Toast.LENGTH_LONG).show();

        }
    };


    public void getVehicleBilty(){


        showProgress();
        Log.d("test","intestFproduct");
        RestClient.getAuthAdapter().getVehicleBilty(String.valueOf(deviceStorage.GetVehicleID())).enqueue(new GeneralCallBack<BiltyResponse>(this) {
            @Override
            public void onSuccess(BiltyResponse response) {

                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("test", Reslog);


                if (!response.getIsError()) {

                    Bilty=response;


                }
                else{

                    showMessageDailog("MAP", response.getMessage());
                }


                hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
                hideProgress();
                Log.d("test","failed");

            }



        });




    }


   public void getVehicleIfo(){


       showProgress();
       Log.d("test","intestFproduct");
       RestClient.getAuthAdapter().getVehicleInfo(deviceId).enqueue(new GeneralCallBack<VehicleResponse>(this) {
           @Override
           public void onSuccess(VehicleResponse response) {

               Gson gson = new Gson();
               String Reslog= gson.toJson(response);
               Log.d("test", Reslog);


               if (!response.getIsError()) {

                   deviceStorage.SetDeviceID(deviceId);
                   deviceStorage.SetVehicleID(response.getValue().getId());
                   deviceStorage.SetRegNum(response.getValue().getRegNumber());

                   setUpMap();


               }
                else{

                   showMessageDailog("MAP", response.getMessage());
               }


               hideProgress();



           }

           @Override
           public void onFailure(Throwable throwable) {
               //onFailure implementation would be in GeneralCallBack class
               hideProgress();
               Log.d("test","failed");

           }



       });




   }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        registerReceiver(br, new IntentFilter("BroadcastLocation"));

    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(br);
    }

    public void setUpMap(){

        if(!isMyServiceRunning(LocationService.class)) {
           // startService(new Intent(MainActivity.this, LocationService.class));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              //  this.startForegroundService(new Intent(this, LocationService.class));
               this.startForegroundService(new Intent(this, LocationService.class));

            } else {
                startService(new Intent(this, LocationService.class));
            }






        }
      //Intent intent = new Intent(this, LocationService.class);
      //startService(intent);
txtMsg.setText(deviceStorage.GetRegNum());
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);

      /*
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.

          showMessageDailog("MAP", Constants.MESSAGE_REQUESTED_PERMISSION_DENIED);
          return;
      }

      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);


*/



  }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


    @Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //custLocation = getLocationFromAddress(this, OrderDetails.CustomerAddress);

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

        mMap.setMyLocationEnabled(true);
    custLocation=HAMBURG;
        if(custLocation != null) {
        Log.d(Constants.TAG, "cusLoc" + String.valueOf(custLocation.latitude));
        //  mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

/*
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(custLocation);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title("you");

        // Clears the previously touched position


        mMap.addMarker(markerOptions).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation, zoomLevel));
        }
        else{
        custLocation=HAMBURG;
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(HAMBURG);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title("title");

        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, zoomLevel));
*/


            this.MeMarker = mMap.addMarker(new MarkerOptions()
                    .position(custLocation)

                    .title("You")

                    .snippet("You")

            );
            this.MeMarker.showInfoWindow();
             //mMap.addMarker(MeMarker.).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(custLocation,zoomLevel));

        }




        }


@Override
public void onStop(){
        super.onStop();


        //locationManager.removeUpdates(this);
    try {
       //  locationManager.removeUpdates(this);
    }
    catch (Exception e){


    }


        }


@Override
public void onClick(View view) {
        switch (view.getId()) {
        case R.id.map:



        break;


        }


        }





    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void GetPermissions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {

            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP){
                List<String> permissionsNeeded = new ArrayList<String>();

                final List<String> permissionsList = new ArrayList<String>();
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
                    permissionsNeeded.add("GPS");
                if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
                    permissionsNeeded.add("Location");




                if (permissionsList.size() > 0) {
                    if (permissionsNeeded.size() > 0) {


                    }
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    return;
                }

            }






            return;
        }

        else{

            setupAll();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


                        ) {
                    // All Permissions Granted
                    setupAll();
                    // setMapForV6();

                } else {
                    // Permission Denied
                    // Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                    //       .show();

                    showMessageDailog(getString(R.string.app_name), Constants.MESSAGE_REQUESTED_PERMISSION_DENIED);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
}
