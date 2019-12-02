package com.kolllor3.lijnhaltecopanian;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.AddFavoriteHalteViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddFavoriteHalteByName extends AppCompatActivity {

    private AddFavoriteHalteViewModel favoriteHalteViewModel;
    private LifecycleOwner lifeCycleOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorite_halte_by_name);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lifeCycleOwner = this;

        ActionBar actionBar = getSupportActionBar();
        if(Utilities.isNotNull(actionBar)){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        favoriteHalteViewModel = ViewModelProviders.of(this).get(AddFavoriteHalteViewModel.class);

        if(Utilities.isNull(savedInstanceState))
            favoriteHalteViewModel.init();

        EditText searchInput = findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                favoriteHalteViewModel.getSearchedHaltes(s.toString()).observe(lifeCycleOwner, haltes -> favoriteHalteViewModel.getHalteListAdapter().setHalteItems(haltes));
            }
        });

        RecyclerView halteList = findViewById(R.id.halteList);
        halteList.setLayoutManager(new LinearLayoutManager(this));
        halteList.setAdapter(favoriteHalteViewModel.getHalteListAdapter());

        findViewById(R.id.add_favorite_button).setOnClickListener(v -> {
            for (Halte halte : favoriteHalteViewModel.getCurrentSelectedFavoriteHaltes()) {
                favoriteHalteViewModel.addFavoriteHalte(halte.getHaltenummer());
            }
            favoriteHalteViewModel.resetCurrentSelectedFavoriteHaltes();
            Toast.makeText(getBaseContext(), "Favorites are added!", Toast.LENGTH_SHORT).show();
            finish();
        });

        favoriteHalteViewModel.getSelectedHalte().observe(this, halte -> {
            Intent i = new Intent(this, HalteTimeTableActivity.class);
            i.putExtra("haltenummer", halte.getHaltenummer());
            i.putExtra("name", halte.getOmschrijving());
            i.putExtra("halteentiteit", halte.getEntiteitnummer());
            startActivity(i);
        });
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
}
