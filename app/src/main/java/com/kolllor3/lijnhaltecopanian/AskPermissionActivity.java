package com.kolllor3.lijnhaltecopanian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        findViewById(R.id.location_perm_button).setOnClickListener(v -> askLocationPermission());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> goToMainActivity());
    }

    private void goToMainActivity(){
        if(hasAcceptedPermission){
            Intent i = new Intent(this, MainActivity.class);
            i.setAction(ASK_LOCATION_RETURN_ACTION);
            setResult(LOCATION_PERMISSION_RESULT, i);
            finish();
        }else{
            showDialog();
        }
    }

    private void askLocationPermission(){
        ActivityCompat.requestPermissions(AskPermissionActivity.this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_ASK);
    }

    private void showDialog(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.location_permissions_dialog_title)
                .setMessage(R.string.location_permissions_dialog_text)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LOCATION_PERMISSION_ASK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Granted access", Toast.LENGTH_LONG).show();
                hasAcceptedPermission = true;
            }else{
                Toast.makeText(this, "Denied access", Toast.LENGTH_LONG).show();
            }
        }
    }
}
