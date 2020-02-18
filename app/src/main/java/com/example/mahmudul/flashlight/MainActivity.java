package com.example.mahmudul.flashlight;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Image image;
    android.hardware.Camera camera;
    android.hardware.Camera.Parameters parameters;
    boolean isflash =false;
    boolean ison =false;
    ImageView img;

    public static  boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] PERMISSIONS = {
                Manifest.permission.CAMERA
        };
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }

        img = (ImageView) findViewById(R.id.pic);
        if (getApplicationContext().getPackageManager().hasSystemFeature(getPackageManager().FEATURE_CAMERA_FLASH)) {

            camera = camera.open();
            parameters = camera.getParameters();
            isflash = true;
        }
    }

    public void picture(View v) {
        if (isflash)
        {
            if(ison)
            {
                img.setImageResource(R.drawable.on);
                parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                ison =false;
            }

            else
            {
                img.setImageResource(R.drawable.off);
                parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                ison = true;
            }


        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error.....");
            builder.setMessage("Flashlight is not Available on this device.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
    }



    @Override
    protected void onStop() {
        super.onStop();

        if (camera!=null)
        {
           // camera.release();
          // camera=null;
        }
    }
}
