package com.example.naveed.protrucktripreader.Request;

import com.google.gson.annotations.SerializedName;

public class SendTrackRequest {




    @SerializedName("VehicleId")
    public  int VehicleId=-1 ;
    @SerializedName("RegNumber")
    public  String RegNumber ;
    @SerializedName("Latitude")
    public  String Latitude;
    @SerializedName("Longitude")
    public  String Longitude ;

}
