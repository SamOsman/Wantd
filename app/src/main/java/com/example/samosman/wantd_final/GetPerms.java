package com.example.samosman.wantd_final;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Sam Osman on 2017-07-31.
 */

public class GetPerms extends AppCompatActivity {

    /**
     * MY_PERMISSIONS_REQUEST_READ_CONTACTS:
     * -is used in The callback method 'onRequestPermissionsResult'
     * -pick any number, it does not matter
     * -Each permission request should have a unique final int defined
     */
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_perms);
        text = (TextView)findViewById(R.id.txtInro);
        button = (Button)findViewById(R.id.btnGivePerm);

        try{
            getContactPermissions();

        } catch (InvocationTargetException e){
            e.getCause().printStackTrace();
        } catch (Exception  e){
            e.printStackTrace();
        }

    }

    public void getContactPermissions() throws InvocationTargetException {
        /*
        * first: contextCompact checks if this activity has read permissions,
        * this check will ether return a 'PERMISSION GRANTED' or 'PERMISSION DENIED'
        * */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //no permission granted: ask for permission
            //the above results in the android dialog asking user for permission

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            // permission granted, redirect user to main activity class
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //this method handles the result of the 'getContactPermission' method

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission granted, redirect user to main activity class
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                } else {
                    //consider closing down the app when permission is denied
                    text.setText("Read Contacts permission is mandatory for this app :(");
                }
                return;
            }
            // other 'case' lines to check for other permissions
        }
    }

}
