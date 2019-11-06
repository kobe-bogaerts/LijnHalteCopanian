package com.kolllor3.lijnhaltecopanian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kolllor3.lijnhaltecopanian.constants.Constants;

public class AskPermissionActivity extends AppCompatActivity implements Constants {

    boolean hasAcceptedPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permission);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> goToMainActivity());
    }

    private void goToMainActivity(){
        if(hasAcceptedPermission){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
            //todo: show dialog if ok go to main else close dialog and stay
        }
    }

    private void askLocationPermission(){
        ActivityCompat.requestPermissions(AskPermissionActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, LOCATION_PERMISSION_ASK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_PERMISSION_ASK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //todo: show granted text
                hasAcceptedPermission = true;
            }else{
                //todo: show denied text
            }
        }
    }
}
