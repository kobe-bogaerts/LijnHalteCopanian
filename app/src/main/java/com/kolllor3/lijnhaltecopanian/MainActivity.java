package com.kolllor3.lijnhaltecopanian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.HalteViewModel;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class MainActivity extends AppCompatActivity implements Constants {

    private HalteViewModel halteViewModel;
    private boolean isEditing;
    private MaterialButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        halteViewModel = ViewModelProviders.of(this).get(HalteViewModel.class);

        if(Utilities.isNull(savedInstanceState))
            halteViewModel.init();

        RecyclerView halteList = findViewById(R.id.halteList);
        halteList.setLayoutManager(new LinearLayoutManager(this));
        halteList.setAdapter(halteViewModel.getHalteListAdapter());

        deleteButton = findViewById(R.id.delete_favorite_halte_button);
        deleteButton.setOnClickListener(v -> {
            if(isEditing){
                for (Halte halte : halteViewModel.getCurrentSelectedFavoriteHaltes()) {
                    halteViewModel.removeFavoriteHalte(halte.getHaltenummer());
                }
                isEditing(false);
                if (deleteButton.getText().equals(getText(R.string.delete_selected_favorite_button_text)))
                    Toast.makeText(getBaseContext(), "Selected favorites are deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        SpeedDialView menu = findViewById(R.id.fab);
        setupSpeedDailMenu(menu);

        halteViewModel.getFavoriteHaltes().observe(this, haltes -> {
            if(haltes.size() == 0)
                findViewById(R.id.empty_list_txt).setVisibility(View.VISIBLE);
            else
                findViewById(R.id.empty_list_txt).setVisibility(View.GONE);
            halteViewModel.getHalteListAdapter().setHalteItems(haltes);
        });

        halteViewModel.getCurrentSelectedFavoriteHaltesCount().observe(this, size -> {
            if(size == 0){
                deleteButton.setText(android.R.string.cancel);
            }else{
                deleteButton.setText(R.string.delete_selected_favorite_button_text);
            }
        });

        halteViewModel.getSelectedHalte().observe(this, halte -> {
            Intent i = new Intent(this, HalteTimeTableActivity.class);
            i.putExtra("haltenummer", halte.getHaltenummer());
            i.putExtra("name", halte.getOmschrijving());
            i.putExtra("halteentiteit", halte.getEntiteitnummer());
            startActivity(i);
        });
    }

    private void isEditing(boolean isEditing){
        halteViewModel.getHalteListAdapter().setHalteItems(halteViewModel.getHalteListAdapter().getHalteItems());
        halteViewModel.resetCurrentSelectedFavoriteHaltes();
        halteViewModel.setIsCheckboxVisible(isEditing);
        deleteButton.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        this.isEditing = isEditing;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_edit) {
            isEditing(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSpeedDailMenu(SpeedDialView view){
        view.addActionItem(new SpeedDialActionItem.Builder(R.id.add_by_name, R.drawable.ic_search).setTheme(R.style.SpeedDialerItems).setLabel(R.string.add_by_name).create());
        view.addActionItem(new SpeedDialActionItem.Builder(R.id.add_by_location, R.drawable.ic_location).setTheme(R.style.SpeedDialerItems).setLabel(R.string.add_with_location).create());
        view.addActionItem(new SpeedDialActionItem.Builder(R.id.add_by_qr, R.drawable.ic_qr_code).setTheme(R.style.SpeedDialerItems).setLabel(R.string.add_with_qr_code).create());
        view.setOnActionSelectedListener(actionItem -> {
            switch (actionItem.getId()){
                case R.id.add_by_name:
                        Intent i2 = new Intent(this, AddFavoriteHalteByName.class);
                        startActivity(i2);
                    break;
                    case R.id.add_by_location:
                        Intent i = new Intent(this, AddFavoriteHalteByLocationActivity.class);
                        startActivity(i);
                    break;
                    case R.id.add_by_qr:
                        Intent i3;
                        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                            i3 = new Intent(this, AskCameraPermissionActivity.class);
                        }else{
                            i3 = new Intent(this, AddFavoriteHalteByQRActivity.class);
                        }
                        startActivity(i3);
                    break;
                    default:
                        break;
            }
            view.close();
            return false;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isEditing(false);
    }
}
