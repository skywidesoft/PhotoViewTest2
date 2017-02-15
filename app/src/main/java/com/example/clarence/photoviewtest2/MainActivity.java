package com.example.clarence.photoviewtest2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private RequestListener<Uri, GlideDrawable> requestListener = new RequestListener<Uri, GlideDrawable>() {

        @Override
        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
            Log.e("MainActivity", "------------------Exception: " + e.getMessage());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        imageView = (ImageView) findViewById(R.id.imageView);

        // Glide.with(this).load("https://controller1.skywidesoft.com/images/product1.jpg").into(imageView);

        File imgFile = null;

        try {
            imgFile = new File("/sdcard/Download/sample1.jpg");

            Log.d("MainActivity", "-----------File created successfully");

            Log.d("MainActivity", "-----------File exists: " + imgFile.exists());
            Log.d("MainActivity", "-----------File size: " + imgFile.length());
        } catch (Exception ex) {
            Log.e("MainActivity", "-------------" + ex.getMessage());
        }

        Uri uri = Uri.fromFile(imgFile);
        Log.d("MainActivity", "---------------File path: " + uri.getPath());

        Glide
                .with(this)
                .load(uri)
                .listener(requestListener)
                .error(R.drawable.test1)
                .into(imageView);

        // Glide.with(this).load("https://controller1.skywidesoft.com/images/product1.jpg").into(imageView);

        // Bitmap bitMap = BitmapFactory.decodeFile("/sdcard/Download/product1.jpg");
        // imageView.setImageBitmap(bitMap);

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
