package com.example.mysavingsv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    private AdminSQLiteOpenHelper bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        showDataProfile();
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
                Intent intentProfile = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(ProfileActivity.this, StatisticsActivity.class);
                startActivity(intentStatistics);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(ProfileActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                finish();
                return true;
            case R.id.action_logout:
                Intent intentLogout = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDataProfile(){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        Cursor fila = wDatabase.rawQuery("SELECT username, email FROM users WHERE username LIKE '" + LoginActivity.loggedUser + "'",null);

        TextView name = findViewById(R.id.usernameProfile);
        TextView email = findViewById(R.id.emailProfile);

        if(fila.moveToFirst()){
            name.setText(fila.getString(0));
            email.setText(fila.getString(1));
        }

        wDatabase.close();
    }
}
