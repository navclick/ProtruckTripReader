package com.example.naveed.protrucktripreader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.naveed.protrucktripreader.Abstract.GeneralCallBack;
import com.example.naveed.protrucktripreader.Abstract.GeneralCallBackService;
import com.example.naveed.protrucktripreader.Base.BaseActivity;
import com.example.naveed.protrucktripreader.Helper.Constants;
import com.example.naveed.protrucktripreader.Network.RestClient;
import com.example.naveed.protrucktripreader.Request.SendTrackRequest;
import com.example.naveed.protrucktripreader.Request.UpdatePodRequest;
import com.example.naveed.protrucktripreader.Responses.SendTrackResponse;
import com.example.naveed.protrucktripreader.Responses.UpdatePodResponse;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import static com.example.naveed.protrucktripreader.Helper.ProgressLoader.hideProgress;

public class BiltyStatusActivity extends BaseActivity implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    Button photoButton,btn_submit;
    public static String imgBase64;
    public String TokenString, mediaPath;

    private AsyncTask mMyTask, updateTask;

    public Bitmap bitmap = null;
    public String file="";
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilty_status);
        imageView =(ImageView) findViewById(R.id.img_pod);
        photoButton = (Button) findViewById(R.id.btn_take_pic);
        btn_submit=(Button) findViewById(R.id.btn_submit);
        photoButton.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_take_pic:
                Log.d(Constants.TAG,"Camera Click");
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                break;

            case R.id.btn_submit:
              Log.d(Constants.TAG,"Submit");
                updateTask = new AsyncTaskLoad().execute(mediaPath);

                break;


        }


    }

    public void updatePod(){

        UpdatePodRequest requestObj = new UpdatePodRequest();
        requestObj.Biltynumber=Bilty.getValue().get(0).getBiltyNo().toString();
        requestObj.image=imgBase64;
        requestObj.status =1;




        RestClient.getAuthAdapter().UpdatePod(requestObj).enqueue(new GeneralCallBack<UpdatePodResponse>(this) {
            @Override
            public void onSuccess(UpdatePodResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);
                if(!response.getIsError()) {


                    Toast.makeText(getApplicationContext(), "Pic Updated",
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            bitmap=photo;
            imageView.setImageBitmap(photo);
        }
    }

    public class AsyncTaskLoad  extends AsyncTask<String, String, String> {
        private final static String TAG = "AsyncTaskLoadImage";

        @Override
        protected String doInBackground(String... params) {


            String encodedImage = "";
           if(bitmap != null) {
               Bitmap bm = bitmap;//BitmapFactory.decodeFile(mediaPath);
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
               byte[] byteArrayImage = baos.toByteArray();
               encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
               encodedImage = encodedImage.replace("\n", "");
           }
           else{

               Log.d(Constants.TAG,"Pic Null");
           }
            return encodedImage;
        }
        @Override
        protected void onPostExecute(String base64) {
            imgBase64 = base64;
           // Toast.makeText(getApplicationContext(), imgBase64, Toast.LENGTH_SHORT).show();
            //setProfileOnServer();
            Log.d("ss","ss");
            updatePod();
        }
    }



}
