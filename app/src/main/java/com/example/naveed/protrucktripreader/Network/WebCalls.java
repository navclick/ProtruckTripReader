package com.example.naveed.protrucktripreader.Network;

import com.example.naveed.protrucktripreader.Request.SendTrackRequest;
import com.example.naveed.protrucktripreader.Responses.SendTrackResponse;
import com.example.naveed.protrucktripreader.Responses.VehicleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebCalls {

    @GET(EndPoints.GET_VEHICLEINFO)
    Call<VehicleResponse> getVehicleInfo(@Query("DeviceId") String DeviceId);

    @POST(EndPoints.POST_SENDTRACK)
    Call<SendTrackResponse> sendTrack(@Body SendTrackRequest order);



    /*
    // Login starts
    @FormUrlEncoded
    @POST(EndPoints.LOGIN)
    Call<Token> GetToken(@Field("username") String username,
                         @Field("password") String password,
                         @Field("grant_type") String grant_type);
    // Login ends

    // Register starts here
    @POST(EndPoints.REGISTER)
    Call<GeneralResponse> Register(@Body SignUpRequest signup);
    // Register ends here

    // Customer Service starts
    @GET(EndPoints.GET_CUSTOMER_SERVICES)
    Call<CustomerService> GetCustomerServices();


    @GET(EndPoints.GET_ORDERS)
    Call<OrdersResponse> GetOrders();

    @GET(EndPoints.GET_USER_PROFILE)
    Call<GetUserResponse> GetProfile();


    @POST(EndPoints.POST_UPDATE_ORDER)
    Call<StatusUpdateResponse> registerUser(@Body StatusUpdateRequest OrderStatus);


    @POST(EndPoints.POST_USER_PROFILE_UPDATE)
    Call<UserUpdateResponse> updateProfile(@Body UserUpdateRequest profile);

    @POST(EndPoints.POST_RATING)
    Call<RatingResponse> rateCustomer(@Body RatingRequest Rating);


    @POST(EndPoints.POST_LOCATION)
    Call<AddLocationResponse> AddLocation(@Body AddLocationRequest Location);


    // Customer Service ends
    */
}