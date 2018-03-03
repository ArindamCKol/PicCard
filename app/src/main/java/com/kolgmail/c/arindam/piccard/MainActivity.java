package com.kolgmail.c.arindam.piccard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GET = 1;
    private boolean cameraOn = false;
    private Uri fullPhotoUri;

    ImageView imageView;
    EditText topEditText;
    EditText bottomEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.pickedImage);
        topEditText = (EditText) findViewById(R.id.top_edit_text);
        bottomEditText = (EditText) findViewById(R.id.bottom_edit_text);


        bottomEditText.getDrawingCache();


    }

    public void openCameraImage (View view) {
        cameraOn=true;

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.withAppendedPath(fullPhotoUri, imageFileName));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void openMemoryImage (View view) {
        cameraOn=false;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    public void shareImage (View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && cameraOn==true) {
            if (data == null) {
                return;
            }
            Bitmap thumbnail = data.getParcelableExtra("data");
//            fullPhotoUri = data.getData();
//            imageView.setImageURI(fullPhotoUri);
            imageView.setImageBitmap(thumbnail);
//            topEditText.setDrawingCacheEnabled(true);
//            imageView.setImageBitmap(topEditText.getDrawingCache());
        }

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK && cameraOn==false) {
            fullPhotoUri = data.getData();
            imageView.setImageURI(fullPhotoUri);
        }
    }
}
