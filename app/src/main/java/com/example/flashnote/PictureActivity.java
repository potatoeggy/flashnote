package com.example.flashnote;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    @RequiresApi(api = Build.VERSION_CODES.M)



    String photoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void takePic(){
        //if(true) throw new RuntimeException("monke");
        //if(true) throw new RuntimeException("monke2");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.flashnote.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //System.out.println("AWOWOAWOOAWOAOWOAOWOAOWOAOO "+activity.checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED+" WOOT");
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                System.out.println("yeet 1");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);

        takePic();

        new Thread(){
            @Override
            public void run(){
                while(new File(photoPath).length() == 0);
                System.out.println("GOT THE BREAD BROOOOOOOOO");
            }
        }.start();
        //System.out.println(new File(photoPath).toString());
    }
}
