package com.example.naveed.protrucktripreader;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.naveed.protrucktripreader.Abstract.GeneralCallBack;
import com.example.naveed.protrucktripreader.BackGroundServices.LocationService;
import com.example.naveed.protrucktripreader.Base.BaseActivity;
import com.example.naveed.protrucktripreader.Helper.Constants;
import com.example.naveed.protrucktripreader.Network.RestClient;
import com.example.naveed.protrucktripreader.Responses.VehicleResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, LocationListener {
    final private int  REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    float zoomLevel = (float) 15.0;
    public  LatLng custLocation;

    public Marker MeMarker = null;
    public TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

  public void setUpMap(){
      startService(new Intent(MainActivity.this, LocationService.class));
txtMsg.setText(deviceStorage.GetRegNum());
      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);

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

        if(custLocation != null) {
        Log.d(Constants.TAG, "cusLoc" + String.valueOf(custLocation.latitude));
        //  mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );


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

        }




        }


@Override
public void onStop(){
        super.onStop();


        locationManager.removeUpdates(this);

        }


@Override
public void onLocationChanged(Location location) {
        //txtLat = (TextView) findViewById(R.id.textview1);
        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        Log.d(Constants.TAG,"Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        LatLng MyLocation = new LatLng(location.getLatitude(), location.getLongitude());

        // Toast toast = Toast.makeText(getApplicationContext(),
        //       "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(),
        //     Toast.LENGTH_SHORT);

        //toast.show();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLocation));
        try {
        locationManager.removeUpdates(this);
        }
        catch (Exception e){


        }




        /*
       if (this.MeMarker == null) {




            this.MeMarker = mMap.addMarker(new MarkerOptions()
                    .position(MyLocation)

                    .title("You")
                    .icon(BitmapDescriptorFactory.fromBitmap(
                            BitmapFactory.decodeResource(getResources(),
                                    R.drawable.pin_rickshaw)))
                    .snippet("You")

            );
            this.MeMarker.showInfoWindow();
           // mMap.addMarker(MeMarker).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLocation));


        } else {
           // Log.i("APITEST:", "set" + String.valueOf(rickshawLocation.latitude) + " " + String.valueOf(rickshawLocation.longitude));
            this.MeMarker.setTitle("You");
            this.MeMarker.setPosition(MyLocation);

            this.MeMarker.setSnippet("You");
            this.MeMarker.showInfoWindow();
            this.animateMarker(this.MeMarker, MyLocation, false);

        }


*/


        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocation,zoomLevel));
        }


@Override
public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
        }

@Override
public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
        }

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
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


}
