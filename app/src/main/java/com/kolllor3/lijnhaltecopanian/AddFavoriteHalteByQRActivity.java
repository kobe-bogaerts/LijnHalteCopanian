package com.kolllor3.lijnhaltecopanian;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.BarcodeFormat;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.util.LogUtils;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.AddFavoriteHalteViewModel;

import java.util.Collections;

public class AddFavoriteHalteByQRActivity extends AppCompatActivity implements Constants {

    private CodeScanner mCodeScanner;
    private Activity activity;
    private AddFavoriteHalteViewModel favoriteHalteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorite_halte_by_qr);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(Utilities.isNotNull(actionBar)){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        activity = this;

        favoriteHalteViewModel = ViewModelProviders.of(this).get(AddFavoriteHalteViewModel.class);

        if(Utilities.isNull(savedInstanceState))
            favoriteHalteViewModel.init();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));
        mCodeScanner.setDecodeCallback(result -> activity.runOnUiThread(() -> {
            if(result.getText().startsWith(URL_BEGIN)){
                favoriteHalteViewModel.addFavoriteHalte(getHalteNummerFromUtl(result.getText()));
                finish();
            }
            LogUtils.logI("QR", result.getText());
        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            Intent i = new Intent(this, AskCameraPermissionActivity.class);
            startActivityForResult(i, CAMERA_PERMISSION_ASK);
        }else {
            mCodeScanner.startPreview();
        }
    }

    private int getHalteNummerFromUtl(String url){
        return Integer.parseInt(url.substring(url.lastIndexOf('/')+1));
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PERMISSION_ASK) {
            mCodeScanner.startPreview();
        }
    }
}
