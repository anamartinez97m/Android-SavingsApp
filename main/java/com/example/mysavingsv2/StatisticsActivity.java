package com.example.mysavingsv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        final Button graphicBar = findViewById(R.id.buttonGraphic1);
        final Button graphicPie = findViewById(R.id.buttonGraphic2);

        graphicBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, BarChartActivity.class);
                startActivity(intent);
            }
        });

        graphicPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intentProfile = new Intent(StatisticsActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(StatisticsActivity.this, StatisticsActivity.class);
                startActivity(intentStatistics);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(StatisticsActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                finish();
                return true;
            case R.id.action_logout:
                Intent intentLogout = new Intent(StatisticsActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
