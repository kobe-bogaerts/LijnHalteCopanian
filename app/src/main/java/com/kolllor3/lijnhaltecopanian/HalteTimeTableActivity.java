package com.kolllor3.lijnhaltecopanian;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kolllor3.lijnhaltecopanian.adapter.SectionsPagerAdapter;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

public class HalteTimeTableActivity extends AppCompatActivity {

    private int haltenummer;
    private int halteentiteit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halte_time_table);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        haltenummer = getIntent().getIntExtra("haltenummer", 0);
        halteentiteit = getIntent().getIntExtra("halteentiteit", 0);

        //displays the back arrow left top
        ActionBar actionBar = getSupportActionBar();
        if(Utilities.isNotNull(actionBar)){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(String.format("%s %11d",getIntent().getStringExtra("name"), haltenummer));
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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