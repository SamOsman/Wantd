package com.example.samosman.wantd_final;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Sam Osman on 2017-07-31.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        * Check for permissions again as the activity achieves full user focus
        *
        * */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                 != PackageManager.PERMISSION_GRANTED){
            //permission is not granted, redirect user to GetPerms page
            Intent intent = new Intent(this, GetPerms.class);
            startActivity(intent);
            finish();
        }

    }

    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Result.class);
        //parameter is set during the "execute" call and is used by doInBackGround() function
        new Downloader().execute("http://www.rcmp-grc.gc.ca/en/wanted");
        //takes the user to the result page
        startActivity(intent);
    }



}
