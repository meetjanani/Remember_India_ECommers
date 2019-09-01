package com.rememberindia.shoppingportal.Activity.Share_post_Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.rememberindia.shoppingportal.Bean.Common_Insert_Response_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Rest.ApiClient;
import com.rememberindia.shoppingportal.Rest.ApiInterface;
import com.rememberindia.shoppingportal.Utility.Session_Manager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upload_Video_Activity extends AppCompatActivity implements View.OnClickListener{

    RequestBody requestFile;
    public VideoView Video;
    private Button buttonChoose;
    private Button buttonUpload;
    private TextView textView;
    private TextView textViewResponse;
    private static final int SELECT_VIDEO = 3;

    private String selectedPath;
    EditText Category , Content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__video_);

        Video = (VideoView)findViewById(R.id.vedio_one);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        textView = (TextView) findViewById(R.id.textView);
        textViewResponse = (TextView) findViewById(R.id.textViewResponse);

        Category = (EditText)findViewById(R.id.Category);
        Content = (EditText)findViewById(R.id.Content);


        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == buttonChoose) {
            chooseVideo();
        }
        if (v == buttonUpload) {
            boolean Flag = Is_Valid();
            if (Flag==true) {
                uploadVideo();
            }

        }

    }

    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                textView.setText(selectedPath);

                MediaController mediaController= new MediaController(this);
                mediaController.setAnchorView(Video);
                Uri uri=Uri.parse(selectedPath);
                Video.setMediaController(mediaController);
                Video.setVideoURI(uri);
                Video.requestFocus();
                Video.start();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    private void uploadVideo() {

        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(Upload_Video_Activity.this, "Uploading File", "Please wait...", false, false);
            }


            @Override
            protected String doInBackground(Void... params) {


                File file = new File(textView.getText().toString());
                requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("video_1", file.getName(), requestFile);
                RequestBody methodname = RequestBody.create(MediaType.parse("text"), "Insert_Post_Video");
                RequestBody message_1 = RequestBody.create(MediaType.parse("text"), "Message_1");
                RequestBody user_id = RequestBody.create(MediaType.parse("text"), Session_Manager.getUser_ID(Upload_Video_Activity.this));
                RequestBody user_name = RequestBody.create(MediaType.parse("text"), Session_Manager.getName(Upload_Video_Activity.this));

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<Common_Insert_Response_Bean> call=apiService.Video_Upload(body , methodname, message_1 , user_id , user_name);
                call.enqueue(new Callback<Common_Insert_Response_Bean>() {
                    @Override
                    public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {
                        Toast.makeText(getApplicationContext(),response.body().getMessage() + "",Toast.LENGTH_LONG).show();
                        uploading.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {

                        Log.d("hello",""+t.getMessage());

                    }
                });


                return "Done";
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

    public boolean Is_Valid()
    {
        boolean Flag = true;

        if (Category.getText().length()<3)
        {
            Flag = false;
            Category.setError("Enter Valid Title");
        }

        return Flag;
    }


}
